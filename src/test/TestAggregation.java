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
 * Test de Aggregation
 */

public class TestAggregation {

    ArrayList<String> fileContent;
    Model model;
    Aggregation aggregations;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testAggregation() throws ExceptionError{
        String [] content ={
                "MODEL", "Ligue",
                "AGGREGATION",
                "CONTAINER",
                "CLASS", "Equipe", "ONE",
                "PARTS",
                "CLASS", "Joueur", "ONE_OR_MANY",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);

        model = parseur.getModel();

        aggregations = model.getAggregations().get(0);

        assertEquals("Equipe", aggregations.getContainer().getName());
        assertEquals("ONE", aggregations.getContainer().getMultiplicity());
        assertEquals("Joueur", aggregations.getParts().get(0).getName());
        assertEquals("ONE_OR_MANY", aggregations.getParts().get(0).getMultiplicity());

    }

    /**
     * On test quand il manque "CONTAINER" apres la declaration d'une AGGREGATION
     * @throws ExceptionError
     */
    @Test
    public void testAggregationWithError() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("En attente de`CONTAINER` apres `AGGREGATION`");

        String [] content ={
                "MODEL", "Ligue",
                "AGGREGATION",
                "CLASS", "Equipe", "ONE",
                "PARTS",
                "CLASS", "Joueur", "ONE_OR_MANY",
                ";"
        };
        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);
    }

    /**
     * On teste quand il manque "PARTS" apres la declaration du CONTAINER
     * @throws ExceptionError
     */
    @Test
    public void testAggregationWithError2() throws ExceptionError{
        thrown.expect(ExceptionError.class);
        thrown.expectMessage("En attente de `PARTS` apres container");

        String [] content ={
                "MODEL", "Ligue",
                "AGGREGATION",
                "CONTAINER",
                "CLASS", "Equipe", "ONE",
                "CLASS", "Joueur", "ONE_OR_MANY",
                ";"
        };

        fileContent = new ArrayList<String>(Arrays.asList(content));
        Parseur parseur = new Parseur(fileContent);
    }


}
