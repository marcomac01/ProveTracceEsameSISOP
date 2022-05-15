package museo;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MuseoCSem extends MuseoC{
    protected static Random random = new Random();
    private Semaphore sSA = new Semaphore(40, true);
    protected Semaphore sSD = new Semaphore(5, true);
    @Override
    void visitaSA() {
        try{
            sSA.acquire();
            int attesa = random.nextInt(20,40) +1;
            System.out.println("un visitatore entra nella sala archeologica per " + attesa +" minuti");
            TimeUnit.SECONDS.sleep(attesa);
        } catch (Exception e) {}
    }

    @Override
    void terminaVisitaSA() {
        try{
            System.out.println("un visitatore esce dalla sala archeologica");
            sSA.release();
        } catch (Exception e) {}
    }

    @Override
    void VisitaSD() {
        try{
            sSD.acquire();
            int attesa = random.nextInt(5,8) +1;
            System.out.println("un visitatore entra nella sala della dama per " + attesa +" minuti");
            TimeUnit.SECONDS.sleep(attesa);
        } catch (Exception e) {}
    }

    @Override
    void TerminaVisitaSD() {
        try{
            System.out.println("un visitatore esce dalla sala della dama");
            sSD.release();
        } catch (Exception e) {}
    }
}
