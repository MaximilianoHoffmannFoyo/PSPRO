package socketstcp_servidormultihiloencuesta;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ResultadosEncuesta {

  private final HashMap<String, Integer> totalPorRespuesta = new HashMap<>();
  private final HashMap<String, Integer> totalPorZona = new HashMap<>();

  // Suma uno a número de respuestas para la zona y para la respuesta
  synchronized public void anotaRespuesta(String idZona, String respuesta) {
    Integer numRespValor = this.totalPorRespuesta.get(respuesta);
    this.totalPorRespuesta.put(respuesta, numRespValor == null ? 1 : numRespValor + 1);
    Integer numRespZona = this.totalPorZona.get(idZona);
    this.totalPorZona.put(idZona, numRespZona == null ? 1 : numRespZona + 1);
  }

  synchronized public Set<String> obtenZonas() {
    return this.totalPorZona.keySet();
  }

  synchronized public Set<String> obtenRespuestas() {
    return this.totalPorRespuesta.keySet();
  }

  synchronized public int obtenNumRespuestasZona(String zona) {
    return this.totalPorZona.get(zona);
  }

  synchronized public int obtenNumRespuestas(String respuesta) {
    return this.totalPorRespuesta.get(respuesta);
  }

  synchronized public String obtenResultadosXML() {
    String result = "<resultados><porzonas>";
    Set<String> zonas = this.obtenZonas();
    int granTotalPorZonas = 0;
    for (String unaZona : zonas) {
      int totalParaZona = this.obtenNumRespuestasZona(unaZona);
      result += "<zona id=\"" + unaZona + "\">" + totalParaZona + "</zona>";
      granTotalPorZonas += totalParaZona;
    }
    result += "</porzonas><grantotalzonas>" + granTotalPorZonas + "</grantotalzonas>"
            + "<porrespuestas>";
    Set<String> respuestas = this.obtenRespuestas();
    int granTotalPorRespuestas = 0;
    for (String unaRespuesta : respuestas) {
      int totalParaRespuesta = this.obtenNumRespuestas(unaRespuesta);
      result += "<respuesta valor=\""
              + (!unaRespuesta.equals("") ? unaRespuesta : "NS/NC") + "\">"
              + totalParaRespuesta + "</respuesta>";
      granTotalPorRespuestas += totalParaRespuesta;
    }
    result += "</porrespuestas><grantotalrespuestas>" + granTotalPorRespuestas + "</grantotalrespuestas>"
            + "</resultados>";
    return result;
  }

}

class HiloSesionCliente implements Runnable {

  enum Estado {
    INICIAL, LEYENDO_RESPUESTAS
  };

  private final static String COD_TEXTO = "UTF-8";
  private final ResultadosEncuesta resultados;
  private final Socket socketComunicacion;

  HiloSesionCliente(ResultadosEncuesta resultados, Socket socketComunicacion) {
    this.resultados = resultados;
    this.socketComunicacion = socketComunicacion;
  }

  @Override
  public void run() {

    Estado estado = Estado.INICIAL;
    String zona = null;
    int nResp = 0;

    try (InputStream isDeCliente = socketComunicacion.getInputStream();
            OutputStream osACliente = socketComunicacion.getOutputStream();
            InputStreamReader isrDeCliente = new InputStreamReader(isDeCliente, COD_TEXTO);
            BufferedReader brDeCliente = new BufferedReader(isrDeCliente);
            OutputStreamWriter osrACliente = new OutputStreamWriter(osACliente, COD_TEXTO);
            BufferedWriter bwACliente = new BufferedWriter(osrACliente);
            ) {

      String linea;
      boolean lineaOk;

      while (((linea = brDeCliente.readLine()) != null) && linea.length() > 0) {
        lineaOk = false;  // Si se puede hacer algo con la línea, se pone a true

        switch (estado) {

          case INICIAL:
            Pattern patRespuesta = Pattern.compile("@respuestas#(.+)@");
            Matcher m = patRespuesta.matcher(linea);
            if (m.find()) {
              lineaOk = true;
              zona = m.group(1);  // zona para recibir respuestas
              nResp = 0;
              estado = Estado.LEYENDO_RESPUESTAS;
            } else if (linea.equals("@resultados@")) {
              lineaOk = true;
              String datosResultados = resultados.obtenResultadosXML()+"\n";
              osACliente.write(datosResultados.getBytes(COD_TEXTO));
              osACliente.flush();
            }
            break;

          case LEYENDO_RESPUESTAS:  // Cada línea leida es una respuesta, hasta llegar a una vacía
            lineaOk = true;
            if (linea.length() > 0) {
              resultados.anotaRespuesta(zona, linea);
            } else {
              System.out.printf("#Termina registro en servidor, %d respuesas anotadas para zona %s\n", nResp, zona);
              estado = Estado.INICIAL;  // Se terminó de procesar las líneas
            }
            break;

        }

        if (!lineaOk) {
          bwACliente.write("ERR: línea incorrecta: "+linea);
          bwACliente.newLine();
          bwACliente.flush();
        }

      }
    } catch (IOException ex) {
      System.out.println("Excepción de E/S");
      ex.printStackTrace();
      System.exit(1);
    } finally {
      if (socketComunicacion != null) {
        try {
          socketComunicacion.close();
          System.out.println("Cliente desconectado.");
        } catch (IOException ex) {
        }
      }
    }
  }

}

class SocketsTCP_ServidorMultihiloEncuesta {

  public static void main(String[] args) {

    if (args.length < 1) {
      System.err.println("ERROR, indicar: puerto.");
      System.exit(1);
    }
    int numPuerto = Integer.parseInt(args[0]);

    final ResultadosEncuesta resultados = new ResultadosEncuesta();

    try (ServerSocket socketServidor = new ServerSocket(numPuerto)) {
      System.out.printf("Creado socket de servidor en puerto %d. Esperando conexiones de clientes.\n", numPuerto);

      while (true) {    // Acepta una conexión de cliente tras otra
        Socket socketComNuevoCliente = socketServidor.accept();
        System.out.printf("Cliente conectado desde %s:%d.\n",
                socketComNuevoCliente.getInetAddress().getHostAddress(),
                socketComNuevoCliente.getPort());

        Thread hiloSesion = new Thread(new HiloSesionCliente(resultados, socketComNuevoCliente));
        hiloSesion.start();

      }
    } catch (IOException ex) {
      System.out.println("Excepción de E/S");
      ex.printStackTrace();
      System.exit(1);
    }
  }

}
