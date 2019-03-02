package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;

public class Generalization {

    String name;
    ArrayList<String> subClasses;

    /**
     * Constructeur Generalization
     * @param name
     * @param array
     */
    public Generalization(String name, ArrayList<String> array){
        this.name = name;
        this.subClasses = array;
    }

    /**
     * Get name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get des sous-classes
     * @return
     */
    public ArrayList<String> getSubClasses(){
        return this.subClasses;
    }

}
