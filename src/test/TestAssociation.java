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
 * Test de Association
 */
public class TestAssociation {

    ArrayList<String> fileContent;
    Model model;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAssociation() throws ExceptionError{
        String [] content = {
                "MODEL", "Ligue",
                "RELATION", "dirige",
                "ROLES",
                "CLASS", "Entraineur", "ONE_OR_MANY",",",
                "CLASS", "Equipe", "ONE",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

        model = parseur.getModel();

        assertEquals("dirige", model.getAssociations().get(0).getName());
        assertEquals("Entraineur", model.getAssociations().get(0).getPremierRole().getName());
        assertEquals("ONE_OR_MANY", model.getAssociations().get(0).getPremierRole().getMultiplicity());
        assertEquals("Equipe", model.getAssociations().get(0).getSecondRole().getName());
        assertEquals("ONE", model.getAssociations().get(0).getSecondRole().getMultiplicity());

    }

    /**
     * On teste quand il manque "," entre les differents roles
     * @throws ExceptionError
     */
    @Test
    public void testAssociationWithError() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("Manque `,` entre les roles");

        String [] content = {
                "MODEL", "Ligue",
                "RELATION", "dirige",
                "ROLES",
                "CLASS", "Entraineur", "ONE_OR_MANY",
                "CLASS", "Equipe", "ONE",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

    }

    /**
     * On teste quand il manque "ROLES" apres un identifiant "Association".
     * @throws ExceptionError
     */
    @Test
    public void testAssociationWithError2() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("En Attente de 'Roles' apres une Association identifier");

        String [] content = {
                "MODEL", "Ligue",
                "RELATION", "dirige",
                "CLASS", "Entraineur", "ONE_OR_MANY",",",
                "CLASS", "Equipe", "ONE",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);
    }

    /**
     * On teste quand la mutiplicite n'est pas VALIDE
     * @throws ExceptionError
     */
    @Test
    public void testAssociationWithError3() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("Multiplicite non valide");

        String [] content = {
                "MODEL", "Ligue",
                "RELATION", "dirige",
                "ROLES",
                "CLASS", "Entraineur", "ONE_OR_MANY",",",
                "CLASS", "Equipe", "TWO",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);
    }


}
