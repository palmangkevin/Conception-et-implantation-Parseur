package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;

public class Aggregation {

    private Role container;
    private ArrayList<Role> parts;

    /**
     * Constructeur Aggregation
     * @param r
     * @param roles
     */
    public Aggregation(Role r, ArrayList<Role> roles) {
        this.container = r;
        this.parts = roles;
    }

    /**
     *
     * @return
     */
    public Role getContainer() {
        return this.container;
    }

    /**
     *
     * @return
     */
    public ArrayList<Role> getParts() {
        return this.parts;
    }
}
