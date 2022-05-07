package visualizzatore;

import java.util.*;
import java.util.concurrent.Semaphore;

public class VisualizzatoreSem {
    private static LinkedList<String> lista = new LinkedList<>();
    private static Semaphore semStruttura = new Semaphore(1);

    private static class Visualizzatore extends Thread{

    }

    private static class Utente extends Thread {

    }
}
