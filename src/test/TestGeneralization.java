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
 * Test de Generalization
 */
public class TestGeneralization {

    ArrayList<String> fileContent;
    Model model;
    Generalization generalization;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGeneralization() throws ExceptionError{
        String [] content ={
                "MODEL", "Ligue",
                "GENERALIZATION", "Participant",
                "SUBCLASSES", "Joueur",",", "Entraineur",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);
        model = parseur.getModel();
        generalization = model.getGeneralizations().get(0);

        assertEquals("Participant", generalization.getName());
        assertEquals("Joueur", generalization.getSubClasses().get(0));
        assertEquals("Entraineur", generalization.getSubClasses().get(1));

    }

    /**
     * On teste quand il manque "SUBCLASSES" apres l'identifiant GENERALIZATION
     * @throws ExceptionError
     */
    @Test
    public void testGeneralizationWithError() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("En attente de `SUBCLASSES` apres IDENTIFIER");

        String [] content ={
                "MODEL", "Ligue",
                "GENERALIZATION", "Participant",
                "Joueur",",", "Entraineur",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

    }

    /**
     * On teste quand il manque "," entre les sous-classes
     * @throws ExceptionError
     */
    @Test
    public void testGeneralizationWithError2() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("En attente de `,` entre les sous-classes");

        String [] content ={
                "MODEL", "Ligue",
                "GENERALIZATION", "Participant",
                "SUBCLASSES", "Joueur", "Entraineur",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

    }


}
