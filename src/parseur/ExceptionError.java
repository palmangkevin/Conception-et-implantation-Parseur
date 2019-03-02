package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

public class ExceptionError extends Exception {

    /**
     * Generation de message d'exception
     * @param e
     */
    public ExceptionError(String e){
        super(e);
    }
}
