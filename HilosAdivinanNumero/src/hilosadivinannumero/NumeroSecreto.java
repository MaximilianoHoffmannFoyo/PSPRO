package hilosadivinannumero;

import java.util.Random;

/**
 *
 * @author maxi
 */
public class NumeroSecreto {
    
    private int numeroSecreto;
    private boolean yaSeAdivino;

    public NumeroSecreto() {
        Random r = new Random();
        this.numeroSecreto = r.nextInt(1, 100);
        this.yaSeAdivino = false;
    }

    public int getNumeroSecreto() {
        return numeroSecreto;
    }

    public void setNumeroSecreto(int numeroSecreto) {
        this.numeroSecreto = numeroSecreto;
    }

    public boolean isYaSeAdivino() {
        return yaSeAdivino;
    }

    public void setYaSeAdivino(boolean yaSeAdivino) {
        this.yaSeAdivino = yaSeAdivino;
    }

    @Override
    public String toString() {
        return "NumeroSecreto{" + "numeroSecreto=" + numeroSecreto + '}';
    }
    
    public int propuestaNumero(int num){
        if (yaSeAdivino) {
            return -1;
        }else{
            if (num == numeroSecreto) {
                yaSeAdivino = true;
                return 1;
            }else{
                return 0 ;
            }
        }
    }
    /*
    0 si el juego no ha terminado y el número propuesto no es el número secreto.

    1 si el juego no ha terminado y el número propuesto es el número secreto. 
    En ese caso, el hilo debe terminar después de mostrar un mensaje diciendo 
    que ha ganado. Entonces, el juego ha terminado para todos los hilos.

    -1 si el juego ha terminado porque otro hilo ha acertado el número secreto. 
    Entonces, el hilo debe terminar después de mostrar un mensaje diciendo que 
    otro hilo ha ganado.
    */
}
