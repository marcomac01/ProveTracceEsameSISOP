package museo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MuseoSemVariante extends MuseoCSem{
    private Semaphore mutex = new Semaphore(1, true);
    private int visitatoriDonna = 0;

    private int dammiVisitatori() throws InterruptedException{
        int ret;
            mutex.acquire();
            ret = visitatoriDonna;
            mutex.release();
        return ret;
    }
    private void resettaVisitatori()throws InterruptedException{
            mutex.acquire();
            visitatoriDonna = 0;
            mutex.release();
    }
    private void incrementaVisitatori() throws InterruptedException{
            mutex.acquire();
            visitatoriDonna++;
            mutex.release();
    }
    @Override
    void VisitaSD() {
        try{
            sSD.acquire();
            incrementaVisitatori();
            int attesa = random.nextInt(5,8) +1;
            System.out.println("un visitatore entra nella sala della dama per " + attesa +" minuti");
            TimeUnit.SECONDS.sleep(attesa);
        } catch (Exception e) {}
    }

    @Override
    void TerminaVisitaSD() {
        try {
            System.out.println("un visitatore esce dalla sala della dama");
            if (dammiVisitatori() == 5){
                resettaVisitatori();
                sSD.release(5);
            }
        }catch (Exception e) {}
    }
}
