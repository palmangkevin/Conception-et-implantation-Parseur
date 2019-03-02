package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

public class Argument {

    private String name;
    private String type;

    /**
     * Constructeur Argument
     * @param name
     * @param type
     */
    public Argument(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }
}
