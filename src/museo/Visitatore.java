package museo;

public class Visitatore extends Thread{
    private MuseoC museo;

    public Visitatore(MuseoC museo){
        this.museo = museo;
    }

    @Override
    public void run() {
        museo.visitaSA();
        museo.terminaVisitaSA();
        museo.VisitaSD();
        museo.TerminaVisitaSD();
    }
}
