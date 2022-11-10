package lanzahilos;

/**
 *
 * @author maxi
 */
public class Contador {
    
    private long valor = 0;
    
    public long inc(){
        valor++;
        return valor;
    }
    //public long valor

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }
    
}
