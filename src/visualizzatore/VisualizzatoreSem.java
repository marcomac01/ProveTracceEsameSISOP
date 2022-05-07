package visualizzatore;

import java.util.*;
import java.util.concurrent.Semaphore;

public class VisualizzatoreSem {
    private static Random r = new Random();
    private static LinkedList<String> lista = new LinkedList<>();
    private static Semaphore semStruttura = new Semaphore(1);

    private static class Visualizzatore extends Thread{

    }

    private static class Utente extends Thread {
        @Override
        public void run() {
            try{
                int mettere = r.nextInt(1, 5);
                semStruttura.acquire();
                for (int i = 0; i < mettere; i++) {
                    while (!(lista.size() > 100))
                        lista.addLast("" + r.nextInt());
                }
                semStruttura.release();
            }catch(Exception e){}
        }
    }
}
