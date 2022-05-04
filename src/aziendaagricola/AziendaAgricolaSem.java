package aziendaagricola;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AziendaAgricolaSem {
    // N.B. ho accorciato i tempi di traspoprto e rifornimeto a 1 e 10 secondi, inoltre i sacchi max sono 20

    private static int maxSacchi = 20;
    private static int magazzino = maxSacchi;
    private static Semaphore semMagazzino = new Semaphore(1);
    private static Random random = new Random();
    private static Semaphore cassa = new Semaphore(1); //funge da semaforo mutex per la moneta acquisita
    private static int monetaAcquisita = 0;
    private static Magazziniere magazziniere= new Magazziniere();

    private static class Cliente extends Thread {
        private int decisioneSacchi = random.nextInt(9) + 1;

        @Override
        public void run() {
            try {
                cassa.acquire();
                monetaAcquisita += 3*decisioneSacchi;
                System.out.println("i sacchi pagati dal cliente attuale sono " + decisioneSacchi);
                System.out.println("il denaro totale in cassa è " + monetaAcquisita);
                cassa.release();
                semMagazzino.acquire();
                for (int i = 0; i < decisioneSacchi; i++) {
                    if(magazzino == 0) {magazziniere.start(); magazziniere.join();}
                    TimeUnit.SECONDS.sleep(1);
                    magazzino--;
                }
                System.out.println("in magazzino ci sono sacchi "+magazzino);
                semMagazzino.release();
            } catch (Exception e) {}
        }
    }

    private static class Magazziniere extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("comincio rifornimento");
                TimeUnit.SECONDS.sleep(10);
                magazzino = maxSacchi;
                System.out.println("rifornimento completato");
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Cliente().start();
        }
    }

}
