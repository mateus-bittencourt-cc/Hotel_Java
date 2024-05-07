//Criação da Classe Quarto
import java.util.concurrent.Semaphore;

class Quarto {
    private int numero;
    private boolean ocupado;
    private Semaphore semaforo;
    static final int CAPACIDADE_MAXIMA = 4;
    private Hospede[] hospedes;

    public Quarto(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.semaforo = new Semaphore(1);
        this.hospedes = new Hospede[CAPACIDADE_MAXIMA];
    }

    public int getNumero() {
        return numero;
    }

    public synchronized boolean estaOcupado() {
        return ocupado;
    }

    public synchronized void ocupar(Hospede hospede) {
        if (!ocupado) {
            for (int i = 0; i < CAPACIDADE_MAXIMA; i++) {
                if (hospedes[i] == null) {
                    hospedes[i] = hospede;
                    ocupado = true;
                    break;
                }
            }
        }
    }

    public synchronized void sair() {
        for (int i = 0; i < CAPACIDADE_MAXIMA; i++) {
            if (hospedes[i] != null) {
                hospedes[i] = null;
                ocupado = false;
                break;
            }
        }
    }

    public synchronized boolean chaveNaRecepcao() {
        return !ocupado;
    }

    public synchronized void deixarChave() {
        ocupado = false;
    }

    public synchronized void limpar() {
    }

    boolean isNecessariaLimpeza() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
