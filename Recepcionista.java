//Criação da Classe Recepcionista
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Recepcionista extends Thread {
    private String nome;
    private Queue<Hospede> filaEspera;
    private Quarto[] quartos;
    private Semaphore semaforo;

    public Recepcionista(String nome, Queue<Hospede> filaEspera, Quarto[] quartos, Semaphore semaforo) {
        this.nome = nome;
        this.filaEspera = filaEspera;
        this.quartos = quartos;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < quartos.length; i++) {
                    semaforo.acquire();
                    Quarto quarto = quartos[i];
                    if (quarto.chaveNaRecepcao() && !filaEspera.isEmpty()) {
                        Hospede hospede = filaEspera.poll();
                        quarto.ocupar(hospede);
                        System.out.println("Recepcionista " + nome + " (número " + (i + 1) + ") alocou " + hospede.getNome() + " no Quarto " + quarto.getNumero() + ".");
                    }
                    semaforo.release();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
