package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Metrique {

    Model model;
    Classe classe;
    public static final String [] DEFINITIONS_METRIQUES = {
            "Nombre moyen d'arguments des méthodes locales de la classe sélectionée",
            "Nombre de méthodes locales/héritées de la classe sélectionée. Dans le cas où une méthode héritée est " +
                    "redéfinie localement, elle ne compte qu'une fois",
            "Nombre d'attributs locaux/héritées de la classe sélectionée",
            "Nombre de fois où d'autres classes du diagramme apparaissent comme types des arguments des méthodes de " +
                    "la classe sélectionée",
            "Nombre de fois où la classe sélectionnée apparaît comme type des arguments dans les méthodes des autres " +
                    "classes du diagramme",
            "Nombre d'associations (incluant les aggrégations) locales/héritées auxquelles participe la classe " +
                    "sélectionée",
            "Taille du chemin le plus long reliant la classe sélectionée à une classe racine dans le graphe d'héritage",
            "Taille du chemin le plus long reliant la classe sélectionée à une classe feuille dans le graphe d'héritage",
            "Nombre de sous-classes directes de la classe sélectionée",
            "Nombre de sous-classes directes et indirectes de la classe sélectionée"
    };

    /**
     * Constructeur de la classe Metrique
     * @param m
     * @param s
     */
    public Metrique (Model m, String s){

        for(Classe c : m.getClasses()){
            if(c.getName().equals(s)){
                this.classe = c;
            }
        }
        this.model = m;
    }

    /**
     * Trouve la classe dans la liste des classes
     * @param name
     * @return La classe (si elle est trouvée) sinon null
     */
    public Classe findClasse (String name){
        for(Classe c : model.getClasses()){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * Compare deux lists d'arguments
     * @param args1
     * @param args2
     * @return True ou False
     */
    public boolean argumentsCompare (ArrayList<Argument> args1, ArrayList<Argument> args2){
        Argument a1;
        Argument a2;

        if(args1.size() != args2.size()){
            return false;
        }
        for(int i = 0; i < args1.size(); i++){
            a1 = args1.get(i);
            a2 = args2.get(i);

            if(! a1.getName().equals(a2.getName()) || !a1.getType().equals(a2.getType())){
                return false;
            }
        }

        return true;
    }

    /**
     * Cette fonction prend en parametre deux Array d'opérations et les compare en elevant toutes les méthodes
     * avec les memes définitions et retourne un nouvel Array.
     * @param operations1
     * @param operations2
     * @return Noouvelle list d'operations différentes.
     */
    public ArrayList<Operation> operationsMerge (ArrayList<Operation> operations1, ArrayList<Operation> operations2){
        ArrayList<Operation> newOperations = new ArrayList<Operation>();
        Operation operation;
        Iterator<Operation> iterator;

        for(Operation operation1 : operations1){
            iterator = operations2.iterator();
            while(iterator.hasNext()){
                operation = iterator.next();
                if(operation1.getName().equals(operation.getName()) && operation1.getType().equals(operation.getType())
                        && argumentsCompare(operation1.getArguments(),operation.getArguments())){
                    iterator.remove();
                }
            }
            newOperations.add(operation1);
        }

        newOperations.addAll(operations2);
        return newOperations;
    }

    /**
     * Cherche la liste des parents.
     * @param c
     * @return une liste avec toutes les classes parents.
     */
    public ArrayList<Classe> findParents(Classe c){
        ArrayList<Classe> classes = new ArrayList<Classe>();

        for(Generalization generalization : model.getGeneralizations()){
            if(generalization.getSubClasses().contains(c.getName())){
                classes.add(findClasse(generalization.getName()));
            }
        }

        return classes;
    }

    /**
     * Cherche la liste des enfants
     * @param c
     * @return une liste avec tous les classes enfants.
     */
    public ArrayList<Classe> findChildren(Classe c){
        ArrayList<Classe> classes = new ArrayList<Classe>();

        for(Generalization generalization : model.getGeneralizations()){
            if(generalization.getName().equals(c.getName())){
                for(String s : generalization.getSubClasses()){
                    classes.add(findClasse(s));
                }
            }
        }
        return classes;
    }

    /**
     * Calcule la moyenne de nombre d'arguments.
     * @return La moyenne en float.
     */
    public float getANA(){
        int number_of_arguments = 0;
        int number_of_methods;

        number_of_methods = classe.getOperations().size();
        if(number_of_methods == 0){
            return 0;
        }
        for(Operation operation : classe.getOperations()){
            number_of_arguments += operation.getArguments().size();
        }
        return (float)number_of_arguments/number_of_methods;
    }

    /**
     * Calcule le nombre de méthodes.
     * @return nombre de méthodes
     */
    public int getNOM(){
        return getNOM(classe, classe.getOperations());
    }

    /**
     * Fonction récursive qui calcule le nombre de méthodes spécifique à une classe donnée.
     * @param c
     * @param operationArrayList
     * @return total de nombre de méthodes.
     */
    public int getNOM(Classe c, ArrayList<Operation> operationArrayList){
        Classe parent;
        for (Generalization generalization : model.getGeneralizations()){
            if(generalization.getSubClasses().contains(c.getName())){
                parent = findClasse(generalization.getName());
                return getNOM(parent, operationsMerge(operationArrayList, parent.getOperations()));
            }
        }
        return operationArrayList.size();
    }

    /**
     * Calcule le nombre d'arguments.
     * @return le nombre d'arguments.
     */
    public int getNOA(){
        return getNOA(classe);
    }

    /**
     * Fonction récursive qui calcule le nombre d'arguments
     * @param classe
     * @return le nombre d'arguments
     */
    public int getNOA(Classe classe){

        for(Generalization generalization: model.getGeneralizations()){
            if(generalization.getSubClasses().contains(classe.getName())){
                return classe.getAttributes().size() + getNOA(findClasse(generalization.getName()));
            }
        }
        return classe.getAttributes().size();
    }

    /**
     * Calcule le nombre d'accouplement interne
     * @return le nombre d'accouplement interne
     */
    public int getITC(){
        int number = 0;

        for(Operation operation : classe.getOperations()){
            for (Argument args : operation.getArguments()){
                if(findClasse(args.getType()) != null){
                    number++;
                }
            }
        }
        return number;
    }

    /**
     * Calcule le nombre d'accouplement externe
     * @return le nombre d'accouplement externe
     */
    public int getETC(){
        int number = 0;

        for(Classe c: model.getClasses()){
            if(c.getName().equals(classe.getName())){
                continue;
            }
            for(Operation operation: c.getOperations()){
                for(Argument args : operation.getArguments()){
                    if(args.getType().equals(classe.getName())){
                        number++;
                    }
                }
            }
        }
        return number;
    }

    /**
     * Calcule le nombre de fois où d'autres classes du diagramme apparaissent comme types des arguments des méthodes d'
     * une classe
     * @return le nombre de fois que cela a été fait
     */
    public int getCAC(){
        return getCAC(classe);
    }

    /**
     * Fonction récursive qui calcule le nombre de fois où d'autres classes du diagramme apparaissent comme types des
     * arguments des méthodes de la classe c passé en parametre.
     * @param c
     * @return
     */
    public int getCAC(Classe c){
        int number = 0;

        /*Recherche dans les aggregations*/
        for(Aggregation aggregation : model.getAggregations()){
            if(aggregation.getContainer().getName().equals(c.getName())){
                number++;
            }
            else{
                for(Role role: aggregation.getParts()){
                    if(role.getName().equals(c.getName())){
                        number++;
                        break;
                    }
                }
            }
        }

        /*Recherche dans les associations*/
        for(Association association : model.getAssociations()){
            if(association.getPremierRole().getName().equals(c.getName()) || association.getSecondRole().getName()
                    .equals(c.getName())){
                number++;
            }
        }

        /*Recherche dans generalizations*/
        for(Generalization generalization : model.getGeneralizations()){
            if(generalization.getSubClasses().contains(c.getName())){
                return number + getCAC(findClasse(generalization.getName()));
            }
        }

        return number;
    }

    /**
     * Calcule la taille du chemin le plus long reliant une classe c à une classe racine dans le graphe d’héritage.
     * @return la taille du chemin
     */
    public int getDIT(){
        return getDIT(classe);
    }

    /**
     * Fonction récursive qui calcule la taille du chemin le plus long reliant une classe c à une classe racine dans
     * le graphe d’héritage.
     * @param c
     * @return la taille du chemin
     */
    public int getDIT(Classe c){
        int max = 0;
        ArrayList<Classe> parents = findParents(c);

        if(parents.size() == 0){
            return 0;
        }
        for(Classe c1 : parents){
            if(max < getDIT(c1)){
                max = getDIT(c1);
            }
        }
        return 1 + max;
    }

    /**
     * Calcule la taille du chemin le plus long reliant une classe c à une classe feuille dans le graphe d’héritage.
     * @return la taille du chemin
     */
    public int getCLD(){
        return getCLD(classe);
    }

    /**
     * Fonction récursive qui calcule la taille du chemin le plus long reliant une classe c (passé en paramètre)
     * à une classe feuille dans le graphe d’héritage.
     * @param c
     * @return la taille du chemin
     */
    public int getCLD(Classe c){
        ArrayList<Classe> children = findChildren(c);
        int max = 0;

        if(children.size() == 0){
            return 0;
        }

        for (Classe c1 : children){
            if(max < getCLD(c1)){
                max = getCLD(c1);
            }
        }
        return 1 + max;
    }

    /**
     * Calcule le nombre de sous-classes directes à une classe
     * @return le nombre de sous-classes
     */
    public int getNOC(){
        return getNOC(classe);
    }

    /**
     * Calcule le nombre de sous-classes directes à la classe c (passé en paramètre)
     * @param c
     * @return le nombre de sous-classes directes
     */
    public int getNOC(Classe c){
        int number = 0;

        for(Generalization generalization : model.getGeneralizations()){
            if(generalization.getName().equals(c.getName())){
                number += generalization.getSubClasses().size();
            }
        }
        return number;
    }

    /**
     * Calcule le nombre de sous-classes directes et indirectes à une classe
     * @return le nombre de sous-classes directes et indirectes
     */
    public int getNOD(){
        return getNOD(classe);
    }

    /**
     * Fonction récursive qui calcule le nombre de sous-classes directes et indirectes à
     * une classe c (passé en paramètre)
     * @param c
     * @return le nombre de sous-classes directes et indirectes
     */
    public int getNOD (Classe c){
        ArrayList<Classe> sousClasses = findChildren(c);
        int number = 0;

        if(sousClasses.size() == 0){
            return 0;
        }
        number += sousClasses.size();

        for(Classe c1 : sousClasses){
            number += getNOD(c1);
        }
        return number;

    }

}

