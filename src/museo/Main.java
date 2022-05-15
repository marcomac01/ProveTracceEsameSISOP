package museo;

public class Main {
    public static void main(String[] args) {
        MuseoC museo = new MuseoSemVariante();
        for (int i = 0; i < 10; i++) new Visitatore(museo).start();
    }
}
