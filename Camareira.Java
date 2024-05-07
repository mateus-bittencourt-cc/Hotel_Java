//Criação da Classe Camareira como Thread
import java.util.concurrent.Semaphore;

class Camareira extends Thread {
    private String nome;
    private Quarto[] quartos;
    private Semaphore semaforo;

    public Camareira(String nome, Quarto[] quartos, Semaphore semaforo) {
        this.nome = nome;
        this.quartos = quartos;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaforo.acquire();
                for (Quarto quarto : quartos) {
                    if (quarto.chaveNaRecepcao()) {
                        System.out.println("A Camareira " + nome + " está limpando o Quarto " + quarto.getNumero() + ".");
                        Thread.sleep((long) (Math.random() * 5000));
                        quarto.limpar();
                        System.out.println("A Camareira " + nome + " terminou de limpar o Quarto " + quarto.getNumero() + ".");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforo.release();
            }
        }
    }
}
