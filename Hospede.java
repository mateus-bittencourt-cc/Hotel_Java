//Criação da Classe Hospede como Thread
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Hospede extends Thread {
    private String nome;
    private Quarto[] quartos;
    private Semaphore semaforo;

    public Hospede(String nome, Quarto[] quartos, Semaphore semaforo) {
        this.nome = nome;
        this.quartos = quartos;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        try {
            for (Quarto quarto : quartos) {
                semaforo.acquire();
                if (!quarto.estaOcupado()) {
                    quarto.ocupar(this);
                    System.out.println("A Recepcionista " + (ThreadLocalRandom.current().nextInt(5) + 1) + " colocou " + nome + " no Quarto " + quarto.getNumero() + ".");
                    semaforo.release();

                    Thread.sleep((long) (Math.random() * 5000));
                    System.out.println(nome + " deixou a Chave na Recepção e está saindo para passear na cidade do Quarto " + quarto.getNumero() + ".");
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println(nome + " retornou do passeio, pegou a chave na recepção e voltou ao Quarto " + quarto.getNumero() + ".");

                    quarto.sair();
                    System.out.println(nome + " Fez o check-out do Quarto " + quarto.getNumero() + ".");
                } else {
                    semaforo.release();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getNome() {
        return nome;
    }
}
