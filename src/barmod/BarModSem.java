package barmod;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BarModSem {
    private static int filaCassa = 0;
    private static int filaBancone = 0;
    private static Semaphore semFilaCassa = new Semaphore(1);
    private static Semaphore semCassa = new Semaphore(1);
    private static Semaphore semFilaBancone = new Semaphore(1);
    private static Semaphore semBancone = new Semaphore(4);


    private static class Persona extends Thread {

        @Override
        public void run() {
            inizia(scegli());
        }

        private void paga(){
            try{
                semFilaCassa.acquire();
                filaCassa++;
                semFilaCassa.release();
                semCassa.acquire();
                TimeUnit.SECONDS.sleep((10-5)+5);
                semFilaCassa.acquire();
                filaCassa--;
                semFilaCassa.release();
                System.out.println("clinete " + this.getName() + " paga");
                semCassa.release();
            } catch (Exception e ){}
        }

        private void bevi(){
            try {
                semFilaBancone.acquire();
                filaBancone++;
                semFilaBancone.release();
                semBancone.acquire();
                TimeUnit.SECONDS.sleep((40-20)+20);
                semFilaBancone.acquire();
                filaBancone--;
                semFilaBancone.release();
                System.out.println("clinete " + this.getName() + " beve");
                semBancone.release();
            } catch (Exception e){}
        }

        public int scegli() {
            int ret = 1;
            try {
                semFilaCassa.acquire();
                semFilaBancone.acquire();
                if(filaBancone<filaCassa) ret = 2;
                semFilaCassa.release();
                semFilaBancone.release();
            } catch (Exception e) {}
            return ret;
        }

        public void inizia(int i){
            if (i == 1) {
                paga();
                finisci(i);
            }else {
                bevi();
                finisci(i);
            }
        }
        public void finisci(int i){
            if (i==1) bevi();
            else paga();
        }
    }

    public static void main(String[] args) {
        for (int i = 0 ; i<20; i++) new Persona().start();
    }
}
