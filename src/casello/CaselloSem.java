package casello;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CaselloSem {
    private static Random random = new Random();
    private static final int numPorte = random.nextInt(9)+1;
    private static Semaphore[] porte = new Semaphore[numPorte];
    private static final int tempo = 1;
    private static final int T = 10; //tariffa
    private static int incasso = 0;
    private static Semaphore sIncasso = new Semaphore(1);
    private static Veicolo[] veicoli = new Veicolo[10];

    private static class Veicolo extends Thread {
        private final int kmPercorsi = random.nextInt(50,100);

        @Override
        public void run() {
            try {
                for (int i = 0; i < kmPercorsi; i++) TimeUnit.SECONDS.sleep(tempo);
                int portaUsata = random.nextInt(numPorte);
                porte[portaUsata].acquire();
                sIncasso.acquire();
                TimeUnit.SECONDS.sleep(random.nextInt(3,6));
                incasso += kmPercorsi*T;
                System.out.println("il veicolo ha percorso "+kmPercorsi + " e pagato " + kmPercorsi*T+"; incasso = "+incasso);
                sIncasso.release();
                porte[portaUsata].release();
                this.interrupt();
            } catch (Exception e) {}
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ; i < numPorte; i++) porte[i] = new Semaphore(1);
        for (int i = 0; i < 20; i++) {
            new Veicolo().start();
        }
    }
}
