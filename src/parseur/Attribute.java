package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

public class Attribute {
    private String name;
    private String type;

    /**
     * Constructeur Attribute
     * @param name
     * @param type
     */
    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Get name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get type
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Convertir en String
     * @return
     */
    public String toString() {
        return this.type+" "+this.name;
    }
}