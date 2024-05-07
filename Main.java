//Criação da Classe Main
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final String[] NOMES = {"Alice", "Bob", "Carol", "David", "Eva", "Frank", "Gina", "Harry", "Ivy", "Jack",
            "Kathy", "Leo", "Mia", "Nick", "Olivia", "Paul", "Quinn", "Rachel", "Sam", "Tina",
            "Ursula", "Victor", "Wendy", "Xavier", "Yvonne", "Zane", "April", "Benjamin", "Cindy", "Derek",
            "Emily", "Felix", "Grace", "Hannah", "Isaac", "Julia", "Kevin", "Linda", "Michael", "Natalie",
            "Oscar", "Pamela", "Quentin", "Rita", "Steven", "Tiffany", "Vincent", "Wendy", "Xander", "Yasmine"};

    public static void main(String[] args) {
        int nomeIndex = 0;

        while (true) {
            Quarto[] quartos = new Quarto[10];
            for (int i = 0; i < 10; i++) {
                quartos[i] = new Quarto(i + 1);
            }

            Queue<Hospede> filaEspera = new LinkedList<>();
            Semaphore semaforo = new Semaphore(1);

            Thread[] hospedes = new Thread[50];
            for (int i = 0; i < 50; i++) {
                hospedes[i] = new Thread(new Hospede(NOMES[nomeIndex], quartos, semaforo));
                nomeIndex = (nomeIndex + 1) % NOMES.length;
            }

            Thread[] camareiras = new Thread[10];
            for (int i = 0; i < 10; i++) {
                camareiras[i] = new Thread(new Camareira("" + (i + 1), quartos, semaforo));
            }

            Thread[] recepcionistas = new Thread[5];
            for (int i = 0; i < 5; i++) {
                recepcionistas[i] = new Thread(new Recepcionista("Recepcionista " + (i + 1), filaEspera, quartos, semaforo));
            }

            for (Thread hospede : hospedes) {
                hospede.start();
            }

            for (Thread camareira : camareiras) {
                camareira.start();
            }

            for (Thread recepcionista : recepcionistas) {
                recepcionista.start();
            }

            try {
                for (Thread hospede : hospedes) {
                    hospede.join();
                }
                for (Thread camareira : camareiras) {
                    camareira.join();
                }
                for (Thread recepcionista : recepcionistas) {
                    recepcionista.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
