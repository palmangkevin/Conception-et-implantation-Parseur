package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;

public class Model {

    String name;
    ArrayList<Classe> classes;
    ArrayList<Generalization> generalizations;
    ArrayList<Association> associations;
    ArrayList<Aggregation> aggregations;

    /**
     * Constructeur Model
     * @param name
     */
    public Model (String name){
        this.name = name;
        classes = new ArrayList<Classe>();
        generalizations = new ArrayList<Generalization>();
        associations = new ArrayList<Association>();
        aggregations = new ArrayList<Aggregation>();
    }

    /**
     * Get name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Ajouter une classe
     * @param c
     */
    public void addClasse (Classe c){
        this.classes.add(c);
    }

    /**
     * Get Classes
     * @return
     */
    public ArrayList<Classe> getClasses(){
        return this.classes;
    }

    /**
     * Ajouter une generalization
     * @param a
     */
    public void addGeneralization (Generalization a){
        this.generalizations.add(a);
    }

    /**
     * Get Generalization
     * @return
     */
    public ArrayList<Generalization> getGeneralizations(){
        return this.generalizations;
    }

    /**
     * Ajouter une association
     * @param a
     */
    public void addAssociation (Association a){
        this.associations.add(a);
    }

    /**
     * Get Associations
     * @return
     */
    public ArrayList<Association> getAssociations(){
        return this.associations;
    }

    /**
     * Ajouter une aggregation
     * @param a
     */
    public void addAggregation(Aggregation a){
        this.aggregations.add(a);
    }

    /**
     * Get Aggregations
     * @return
     */
    public ArrayList<Aggregation> getAggregations(){
        return this.aggregations;
    }

}
