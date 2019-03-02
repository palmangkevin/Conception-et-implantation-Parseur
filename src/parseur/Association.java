package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

public class Association {

    String name;
    Role premierRole;
    Role secondRole;

    /**
     * Constructeur Association
     * @param name
     * @param premierRole
     * @param secondRole
     */
    public Association (String name, Role premierRole, Role secondRole){
        this.name = name;
        this.premierRole = premierRole;
        this.secondRole = secondRole;
    }

    /**
     * Get premierRole
     * @return
     */
    public Role getPremierRole(){
        return this.premierRole;
    }

    /**
     * Get secondRole
     * @return
     */
    public Role getSecondRole(){
        return this.secondRole;
    }

    /**
     * Get name
     * @return
     */
    public String getName() {
        return this.name;
    }
}
