package test;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import parseur.*;

import java.util.ArrayList;

public class TestMetrique {

    /**
     * Test du nombre moyen d’arguments des méthodes locales pour la classe ci.
     */
    @Test
    public void testANA(){

        float result;
        Metrique metrique;
        Model model = new Model("TestModel");
        Classe classe = new Classe("TestClasse");
        Operation operation;
        ArrayList<Argument> arguments = new ArrayList<>();

        /* On ajoute les arguments*/
        arguments.add(new Argument("premier_argument", "Int"));
        arguments.add(new Argument("deuxieme_argument", "String"));
        arguments.add(new Argument("troisieme_argument", "Float"));
        arguments.add(new Argument("quatrieme_argument","Int"));

        /* On cree une operation et on ajoute les arguments */
        operation = new Operation("premiere_operation","",arguments);

        /* On ajoute l'operation a la classe*/
        classe.addOperation(operation);

        /* On ajoute la classe au Model*/
        model.addClasse(classe);

        /* La seconde operation*/
        arguments = new ArrayList<>();
        operation = new Operation("deuxieme_operation","void",arguments);

        /* On ajoute l'operation a la classe */
        classe.addOperation(operation);

        /* On ajoute la classe a un model */
        model.addClasse(classe);

        /* La troisieme operation*/
        arguments = new ArrayList<>();
        operation = new Operation("troisieme_operation","void",arguments);

        /* On ajoute l'operation a la classe */
        classe.addOperation(operation);

        /* On ajoute la classe a un model */
        model.addClasse(classe);


        /* On calcule le metrique ANA */
        metrique = new Metrique(model,"TestClasse");

        /* Resultat attendu */
        result = (float) 4 / 3;

        assertEquals(result, metrique.getANA(),0.01);
    }

    /**
     * Test de nombre d'operations d'une classe sans heritage
     */
    @Test
    public void testNOM_sans_heritage(){

        Model model = new Model("TestModel");
        Classe classe = new Classe("TestClasse");
        ArrayList<Argument> arguments = new ArrayList<>();
        Metrique metrique;

        classe.addOperation(new Operation("operation1", "void",arguments));
        classe.addOperation(new Operation("operation2", "void",arguments));
        classe.addOperation(new Operation("operation3", "static",arguments));
        classe.addOperation(new Operation("operation4", "void",arguments));
        classe.addOperation(new Operation("operation5", "static",arguments));

        /* On ajoute la classe au model */
        model.addClasse(classe);

        metrique = new Metrique(model,"TestClasse");

        assertEquals(5,metrique.getNOM());

    }

    /**
     * Test de nombre d'operations d'une classe avec heritage et redefinition d'une methode.
     */
    @Test
    public void testNOM_avec_heritage_et_redefinition_operation(){

        Model model = new Model("TestModel");
        Classe classeParent = new Classe("TestClasseParent");
        Classe classeEnfant = new Classe("TestClasseEnfant");
        Generalization generalization;
        Metrique metrique;

        ArrayList<Argument> arguments = new ArrayList<Argument>();
        ArrayList<String> sousClasses = new ArrayList<String>();

        /* On ajoute des methodes a la classe TestClasseParent */
        classeParent.addOperation(new Operation("parent_operation1","void",arguments));
        classeParent.addOperation(new Operation("parent_operation2","void",arguments));
        model.addClasse(classeParent);

        /* On ajoute des methodes a la classe TestClasseEnfant avec une methode de la classe Parent redefinie */
        classeEnfant.addOperation(new Operation("enfant_operation1","static",arguments));
        classeEnfant.addOperation(new Operation("parent_operation2","void",arguments));
        model.addClasse(classeEnfant);

        /* On ajoute la classe enfant a la liste sousClasses */
        sousClasses.add("TestClasseEnfant");

        /* On cree la generalization */
        generalization = new Generalization("TestClasseParent", sousClasses);
        model.addGeneralization(generalization);

        metrique = new Metrique(model, "TestClasseEnfant");

        assertEquals(3, metrique.getNOM());

    }

    /**
     * Test de nombre d'operations d'une classe avec heritage et redefinition differente d'une methode.
     */
    @Test
    public void testNOM_avec_heritage_et_redefinition_operation_different(){

        Model model = new Model("TestModel");
        Classe classeParent = new Classe("TestClasseParent");
        Classe classeEnfant = new Classe("TestClasseEnfant");
        Generalization generalization;
        Metrique metrique;

        ArrayList<Argument> arguments = new ArrayList<Argument>();
        ArrayList<Argument> arguments1 = new ArrayList<Argument>();
        ArrayList<String> sousClasses = new ArrayList<String>();

        /* On ajoute des methodes a la classe TestClasseParent */
        classeParent.addOperation(new Operation("parent_operation1","void",arguments));
        classeParent.addOperation(new Operation("parent_operation2","void",arguments));
        model.addClasse(classeParent);

        /* Redefinition de la methode parent_operation1 */
        classeEnfant.addOperation(new Operation("parent_operation1","static",arguments));

        /* Arguments pour la methode */
        arguments1.add(new Argument("argument1","Int"));

        /* On redefinie une methode de la classe parent */
        classeEnfant.addOperation(new Operation("parent_operation2","void",arguments1));

        model.addClasse(classeEnfant);

        /* On ajoute la classe enfant a la liste sousClasses */
        sousClasses.add("TestClasseEnfant");

        /* On cree la generalization */
        generalization = new Generalization("TestClasseParent", sousClasses);
        model.addGeneralization(generalization);

        metrique = new Metrique(model, "TestClasseEnfant");

        assertEquals(4, metrique.getNOM());

    }

    /**
     * Test de nombre d'arguments locaux d'une classe.
     */
    @Test
    public void testNOA(){

        Model model = new Model("TestModel");
        Classe classe = new Classe("TestClasse");
        Metrique metrique;

        /* On ajoute des differents attributs a la classe  */
        classe.addAttribute(new Attribute("attribut1","Int"));
        classe.addAttribute(new Attribute("attribut2","String"));
        classe.addAttribute(new Attribute("attribut3","float"));
        model.addClasse(classe);

        metrique = new Metrique(model, "TestClasse");

        assertEquals(3,metrique.getNOA());
    }

    /**
     * Test de nombre d'arguments locaux et herites d'une classe.
     */
    @Test
    public void testNOA_avec_heritage(){

        Model model = new Model("TestModel");
        Classe classeParent = new Classe("TestClasseParent");
        Classe classeEnfant = new Classe("TestClasseEnfant");
        ArrayList<String> sousClasses = new ArrayList<>();
        Generalization generalization;
        Metrique metrique;

        /* On ajoute des attributs a la classe parent */
        classeParent.addAttribute(new Attribute("attribut1","Int"));
        classeParent.addAttribute(new Attribute("attribut2","String"));
        classeParent.addAttribute(new Attribute("attribut3","float"));
        model.addClasse(classeParent);

        /* On ajoute des attributs a la classe enfant */
        classeEnfant.addAttribute(new Attribute("attribut4","Int"));
        classeEnfant.addAttribute(new Attribute("attribut5","Int"));
        model.addClasse(classeEnfant);

        sousClasses.add("TestClasseEnfant");
        generalization = new Generalization("TestClasseParent",sousClasses);
        model.addGeneralization(generalization);

        metrique = new Metrique(model, "TestClasseEnfant");

        assertEquals(5,metrique.getNOA());

    }

    /**
     * Test du nombre de fois où d’autres classes du diagramme apparaissent comme types
     * des arguments des méthodes de ci.
     */
    @Test
    public void testITC(){

        Model model = new Model("TestModel");
        Classe classe1 = new Classe("TestClasse1");
        Classe classe2 = new Classe("TestClasse2");
        Classe classe3 = new Classe("TestClasse3");
        Operation operation;
        Metrique metrique;

        ArrayList<Argument> arguments = new ArrayList<>();

        /* Arguemts de la premiere methode */
        arguments.add(new Argument("arguments1","TestClasse2"));
        arguments.add(new Argument("arguments2", "TestClasse3"));
        operation = new Operation("operation1","void",arguments);
        classe1.addOperation(operation);

        /* Arguments de la seconde methode */
        arguments = new ArrayList<Argument>();
        arguments.add(new Argument("arguments1","Int"));
        operation = new Operation("operation2","static", arguments);
        classe1.addOperation(operation);

        /* Ajouter les classes au Model */
        model.addClasse(classe1);
        model.addClasse(classe2);
        model.addClasse(classe3);

        metrique = new Metrique(model,"TestClasse1");

        assertEquals(2, metrique.getITC());

    }

    /**
     * Test du nombre de fois où la classe apparaît comme type des arguments dans les méthodes
     * des autres classes du diagramme.
     */
    @Test
    public void testETC(){
        Model model = new Model("TestModel");
        Classe classe1 = new Classe("TestClasse1");
        Classe classe2 = new Classe("TestClasse2");
        Operation operation;
        Metrique metrique;

        ArrayList<Argument> arguments = new ArrayList<>();

        /* On ajoute les arguments de la premiere methode */
        arguments.add(new Argument("argument1","TestClasse1"));
        arguments.add(new Argument("argument2","TestClasse1"));

        /* On ajoute les arguments a une operation */
        operation = new Operation("operation1","void",arguments);

        /* On ajoute l'operation a la classe "classe2" */
        classe2.addOperation(operation);

        arguments.add(new Argument("argument3","Int"));

        /* On ajoute a une deuxieme operation deux arguments type classe et un INT */
        operation = new Operation("operation2","void",arguments);
        classe2.addOperation(operation);

        /* 3 eme methode */
        arguments = new ArrayList<>();
        operation = new Operation("operation3","void",arguments);
        classe2.addOperation(operation);

        model.addClasse(classe1);
        model.addClasse(classe2);

        metrique = new Metrique(model, "TestClasse1");

        assertEquals(4, metrique.getETC());
    }

    /**
     * Test du nombre d’associations (incluant les aggrégations) locales auxquelles
     * participe une classe ci.
     */
    @Test
    public void testCAC(){
        Model model = new Model("TestModel");
        Classe classe1 = new Classe("TestClasse1");
        Classe classe2 = new Classe("TestClasse2");
        Classe classe3 = new Classe("TestClasse3");
        Classe classe4 = new Classe("TestClasse4");
        Role role;
        Aggregation aggregation;
        Association association;
        Metrique metrique;


        ArrayList<Role> roles = new ArrayList<Role>();

        /* TestClasse2 en CONTAINER */
        role = new Role("TestClasse3","ONE_OR_MANY");
        roles.add(role);
        aggregation = new Aggregation(new Role("TestClasse2","ONE"),roles);
        model.addAggregation(aggregation);

        /* TestClasse2 en PART */
        roles = new ArrayList<>();
        role = new Role("TestClasse2","ONE_OR_MANY");
        roles.add(role);
        aggregation = new Aggregation(new Role("testClasse1","ONE"),roles);
        model.addAggregation(aggregation);

        /* TestClasse2 en premierRole */
        association = new Association("TEST", new Role("TestClasse2","MANY"),
                new Role("TestClasse1","ONE"));
        model.addAssociation(association);

        /* TestClasse2 en secondRole */
        association = new Association("TEST@", new Role("TestClasse4","ONE"),
                new Role("TestClasse2","MANY"));
        model.addAssociation(association);

        model.addClasse(classe1);
        model.addClasse(classe2);
        model.addClasse(classe3);
        model.addClasse(classe4);

        metrique = new Metrique(model,"TestClasse2");

        assertEquals(4, metrique.getCAC());

    }

    /**
     * Test du nombre d’associations (incluant les aggrégations) locales/héritées auxquelles
     * participe une classe ci.
     */
    @Test
    public void testCAC_avec_heritage(){
        Model model = new Model("TestModel");
        Classe classe1 = new Classe("TestClasse1");
        Classe classe2 = new Classe("TestClasse2");
        Classe classe3 = new Classe("TestClasse3");
        Classe classe4 = new Classe("TestClasse4");
        Classe classe5 = new Classe("TestClasse5");
        Classe classe6 = new Classe("TestClasse6");
        Aggregation aggregation;
        Association association;
        Role role;
        Metrique metrique;

        ArrayList<String> sousClasses = new ArrayList<>();
        ArrayList<Role> roles = new ArrayList<>();

        /* TestClasse4 en CONTAINER */
        role = new Role("TestClasse5","ONE_OR_MANY");
        roles.add(role);
        role = new Role ("TestClasse4","ONE");
        aggregation = new Aggregation(role,roles);
        model.addAggregation(aggregation);

        /* TestClasse4 en PART */
        roles = new ArrayList<>();
        role = new Role ("TestClasse4","ONE_OR_MANY");
        roles.add(role);
        role = new Role ("TestClasse3","ONE");
        aggregation = new Aggregation(role,roles);
        model.addAggregation(aggregation);

        /* TestClasse4 comme premierRole */
        association = new Association("test_de", new Role("TestClasse4","MANY"),
                new Role("TestClasse3","ONE"));
        model.addAssociation(association);

        /* TestClasse4 comme secondRole */
        association = new Association("test_le", new Role ("TestClasse6", "ONE"),
                new Role("TestClasse4","MANY"));
        model.addAssociation(association);


        /* TestClasse2 en CONTAINER */
        roles = new ArrayList<>();
        role = new Role("TestClasse3","MANY");
        roles.add(role);
        role = new Role ("TestClasse2","ONE");
        aggregation = new Aggregation(role,roles);
        model.addAggregation(aggregation);

        /* TestClasse2 en PART */
        role = new Role ("TestClasse2","MANY");
        roles.add(role);
        role = new Role ("TestClasse1","ONE");
        aggregation = new Aggregation(role,roles);
        model.addAggregation(aggregation);

        /* TestCLasse2 comme premierRole */
        association = new Association("test_de", new Role("TestClasse2","MANY"),
                new Role("TestClasse1","ONE"));
        model.addAssociation(association);

        /* TestClasse2 comme secondRole */
        association = new Association("test_le", new Role ("TestClasse6", "ONE_OR_MANY"),
                new Role("TestClasse2","ONE_OR_MANY"));
        model.addAssociation(association);

        sousClasses.add("TestClasse4");
        model.addGeneralization(new Generalization("TestClasse2",sousClasses));

        model.addClasse(classe1);
        model.addClasse(classe2);
        model.addClasse(classe3);
        model.addClasse(classe4);
        model.addClasse(classe5);
        model.addClasse(classe6);

        metrique = new Metrique(model,"TestClasse4");

        assertEquals(8,metrique.getCAC());

    }

    /**
     * Test de la taille du chemin le plus long reliant une classe ci à une classe racine dans le graphe d’héritage
     */
    @Test
    public void testDIT(){
        Model model = new Model("TestModel");
        Classe enfant = new Classe("Enfant");
        Classe parent = new Classe("Parent");
        Classe grandParent = new Classe ("GrandParent");
        Classe parentGrandParent = new Classe ("Parent_GrandParent");
        Metrique metrique;

        ArrayList<String> sousClasses = new ArrayList<>();

        sousClasses.add("Enfant");
        model.addGeneralization(new Generalization("Parent",sousClasses));

        sousClasses = new ArrayList<>();
        sousClasses.add("Parent");
        model.addGeneralization(new Generalization("GrandParent",sousClasses));

        sousClasses = new ArrayList<>();
        sousClasses.add("GrandParent");
        model.addGeneralization(new Generalization("Parent_GrandParent",sousClasses));

        model.addClasse(enfant);
        model.addClasse(parent);
        model.addClasse(grandParent);
        model.addClasse(parentGrandParent);

        metrique = new Metrique(model,"Enfant");
        assertEquals(3,metrique.getDIT());

        metrique = new Metrique(model, "Parent");
        assertEquals(2,metrique.getDIT());

        metrique = new Metrique(model,"GrandParent");
        assertEquals(1,metrique.getDIT());

        metrique = new Metrique (model, "Parent_GrandParent");
        assertEquals(0,metrique.getDIT());

    }


    @Test
    public void testDIT_avec_heritage(){

        Model model = new Model("TestModel");
        Classe enfant = new Classe ("Enfant");
        Classe parent = new Classe ("Parent");
        Classe grandParent = new Classe ("GrandParent");
        Classe parentGrandParent = new Classe ("Parent_GrandParent");
        Classe grandParent_grandParent = new Classe ("GrandParent_grandParent");
        Classe parent_grandParent_grandParent = new Classe("Parent_grandParent_grandParent");
        Metrique metrique;

        ArrayList<String> sousClasses = new ArrayList<>();

        sousClasses.add("Enfant");
        model.addGeneralization(new Generalization("Parent",sousClasses));
        model.addGeneralization(new Generalization("Parent_GrandParent",sousClasses));

        sousClasses = new ArrayList<String>();
        sousClasses.add("Parent");
        model.addGeneralization(new Generalization("GrandParent",sousClasses));

        sousClasses = new ArrayList<String>();
        sousClasses.add("Parent_GrandParent");
        model.addGeneralization(new Generalization("GrandParent_grandParent",sousClasses));

        sousClasses = new ArrayList<String>();
        sousClasses.add("GrandParent_grandParent");
        model.addGeneralization(new Generalization("Parent_grandParent_grandParent",sousClasses));

        model.addClasse(enfant);
        model.addClasse(parent);
        model.addClasse(grandParent);
        model.addClasse(parentGrandParent);
        model.addClasse(grandParent_grandParent);
        model.addClasse(parent_grandParent_grandParent);

        metrique = new Metrique(model,"Enfant");
        assertEquals(3,metrique.getDIT());

    }

    /**
     * Test de la taille du chemin le plus long reliant une classe ci à une classe feuille dans le graphe d’héritage.
     */
    @Test
    public void testCLD_avec_heritage(){

        Model model = new Model("TestModel");
        Classe enfant = new Classe ("Enfant");
        Classe parent = new Classe ("Parent");
        Classe grandParent = new Classe ("GrandParent");
        Classe parentGrandParent = new Classe ("Parent_GrandParent");
        Classe grandParent_grandParent = new Classe ("GrandParent_grandParent");
        Classe parent_grandParent_grandParent = new Classe("Parent_grandParent_grandParent");
        Metrique metrique;

        ArrayList<String> sousClasses = new ArrayList<>();

        sousClasses.add("Enfant");
        model.addGeneralization(new Generalization("Parent",sousClasses));
        model.addGeneralization(new Generalization("Parent_GrandParent",sousClasses));

        sousClasses = new ArrayList<String>();
        sousClasses.add("Parent");
        model.addGeneralization(new Generalization("GrandParent",sousClasses));

        sousClasses = new ArrayList<String>();
        sousClasses.add("Parent_GrandParent");
        model.addGeneralization(new Generalization("GrandParent_grandParent",sousClasses));

        sousClasses = new ArrayList<String>();
        sousClasses.add("GrandParent_grandParent");
        model.addGeneralization(new Generalization("Parent_grandParent_grandParent",sousClasses));

        model.addClasse(enfant);
        model.addClasse(parent);
        model.addClasse(grandParent);
        model.addClasse(parentGrandParent);
        model.addClasse(grandParent_grandParent);
        model.addClasse(parent_grandParent_grandParent);

        metrique = new Metrique(model,"GrandParent");
        assertEquals(2,metrique.getCLD());

    }

    /**
     * Test du nombre de sous-classes directes de ci.
     */
    @Test
    public void testNOC(){
        Model model = new Model("TestModel");
        Classe parent = new Classe ("Parent");
        Classe enfant1 = new Classe ("Enfant1");
        Classe enfant2 = new Classe ("Enfant2");
        Classe enfant3 = new Classe ("Enfant3");
        Classe enfant4 = new Classe ("Enfant4");

        model.addClasse(parent);
        model.addClasse(enfant1);
        model.addClasse(enfant2);
        model.addClasse(enfant3);
        model.addClasse(enfant4);

        ArrayList<String> sousClasses = new ArrayList<>();
        sousClasses.add("Enfant1");
        sousClasses.add("Enfant2");
        sousClasses.add("Enfant3");
        sousClasses.add("Enfant4");

        model.addGeneralization(new Generalization("Parent",sousClasses));

        Metrique metrique;
        metrique = new Metrique(model,"Parent");

        assertEquals(4,metrique.getNOC());

    }

    /**
     * Test du nombre de sous-classes directes et indirectes de ci.
     */
    @Test
    public void testNOD(){
        Model model = new Model("TestModel");
        Classe parent = new Classe ("Parent");
        Classe enfant1 = new Classe ("Enfant1");
        Classe enfant2 = new Classe ("Enfant2");
        Classe enfant3 = new Classe ("Enfant3");
        Classe enfant4 = new Classe ("Enfant4");
        Classe enfantEnfant1 = new Classe ("EnfantEnfant1");
        Classe enfantEnfant2 = new Classe ("EnfantEnfant2");

        model.addClasse(parent);
        model.addClasse(enfant1);
        model.addClasse(enfant2);
        model.addClasse(enfant3);
        model.addClasse(enfant4);
        model.addClasse(enfantEnfant1);
        model.addClasse(enfantEnfant2);

        ArrayList<String> sousClasses = new ArrayList<>();
        sousClasses.add("Enfant1");
        sousClasses.add("Enfant2");
        sousClasses.add("Enfant3");
        sousClasses.add("Enfant4");

        model.addGeneralization(new Generalization("Parent",sousClasses));

        ArrayList<String> sousClasses2 = new ArrayList<>();
        sousClasses2.add("EnfantEnfant1");
        sousClasses2.add("EnfantEnfant2");

        model.addGeneralization(new Generalization("Enfant1",sousClasses2));

        Metrique metrique;
        metrique = new Metrique(model,"Parent");

        assertEquals(6,metrique.getNOD());

    }

}
