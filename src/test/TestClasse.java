package test;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;

import parseur.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Test de Classe
 */
public class TestClasse {

    ArrayList<String> fileContent;
    ArrayList<Attribute> attributes;
    ArrayList<Operation> operations;
    ArrayList<Argument> arguments;
    Model model;
    Classe classe;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testClasse () throws ExceptionError{
        String [] content = {
                "MODEL", "Ligue",
                "CLASS", "Equipe",
                "ATTRIBUTES",
                "nom_equipe", ":", "String",
                "OPERATIONS",
                    "nombre_joueurs", "(", ")", ":", "Integer",",",
                    "entraineur","(", ")", ":", "String", ",",
                    "add_joueur", "(", "element", ":", "Joueur", ")", ":", "void",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

        model = parseur.getModel();
        classe = model.getClasses().get(0);
        attributes = classe.getAttributes();
        operations = classe.getOperations();
        arguments = operations.get(2).getArguments(); // Pour avoir les arguments de la 3eme operations vu que les
                                                        // autres n'ont pas d'arguments.

        assertEquals("Ligue", model.getName());
        assertEquals("Equipe", classe.getName());
        assertEquals("nom_equipe", attributes.get(0).getName());
        assertEquals("nombre_joueurs", operations.get(0).getName());
        assertEquals("entraineur", operations.get(1).getName());
        assertEquals("add_joueur", operations.get(2).getName());
        assertEquals("element", arguments.get(0).getName());
        assertEquals("Joueur", arguments.get(0).getType());
        assertEquals("void", operations.get(2).getType());

    }

    /**
     * On teste quand il manque une "," entre les differentes operations.
     * @throws ExceptionError
     */
    @Test
    public void testClasseWithError () throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("Manque `,` entre les operations");

        String [] content = {
                "MODEL", "Ligue",
                "CLASS", "Equipe",
                "ATTRIBUTES",
                "nom_equipe", ":", "String",
                "OPERATIONS",
                "nombre_joueurs", "(", ")", ":", "Integer",
                "entraineur","(", ")", ":", "String", ",",
                "add_joueur", "(", "element", ":", "Joueur", ")", ":", "void",
                ";"
        };

        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

    }

    /**
     * On teste quand il manque un ":" apres un nom d'attribut
     * @throws ExceptionError
     */
    @Test
    public void testClasseWithError2 () throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("En Attente de `:` apres un nom d'attribut");

        String [] content = {
                "MODEL", "Ligue",
                "CLASS", "Equipe",
                "ATTRIBUTES",
                "nom_equipe", "String",
                "OPERATIONS",
                "nombre_joueurs", "(", ")", ":", "Integer",",",
                "entraineur","(", ")", ":", "String", ",",
                "add_joueur", "(", "element", ":", "Joueur", ")", ":", "void",
                ";"
        };

        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

    }

}
