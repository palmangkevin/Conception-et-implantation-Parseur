package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;

public class Classe {

    String name;
    ArrayList<Attribute> attributes;
    ArrayList<Operation> operations;

    /**
     * Constructeur Classe
     * @param name
     */
    public Classe (String name){
        this.name = name;
        attributes = new ArrayList<Attribute>();
        operations = new ArrayList<Operation>();
    }

    /**
     * Get name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Ajouter un attribut
     * @param attribute
     */
    public void addAttribute (Attribute attribute){
        this.attributes.add(attribute);
    }

    /**
     * Get attributes
     * @return
     */
    public ArrayList<Attribute> getAttributes(){
        return this.attributes;
    }

    /**
     * Ajouter une operation
     * @param a
     */
    public void addOperation (Operation a){
        this.operations.add(a);
    }

    /**
     * Get operations
     * @return
     */
    public ArrayList<Operation> getOperations(){
        return this.operations;
    }

    /**
     * Convertir en string
     * @return
     */
    public String toString(){
        return this.name;
    }
}

