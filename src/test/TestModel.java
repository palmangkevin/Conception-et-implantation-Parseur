package test;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import parseur.ExceptionError;
import parseur.Parseur;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test du Model
 */

public class TestModel {

    ArrayList<String> fileContent;

    @Test
    public void testModel() throws ExceptionError {
        String [] content = {"MODEL","Lique"};
        fileContent = new ArrayList<String>(Arrays.asList(content));

        Parseur parseur = new Parseur(fileContent);

        assertEquals("Lique", parseur.getModel().getName());

    }

    @Test(expected = ExceptionError.class)
    public void testModelError() throws ExceptionError {
        String [] content = {"MODEL"};
        fileContent = new ArrayList<String>(Arrays.asList(content));

        Parseur parseur = new Parseur(fileContent);

        /* Avec des exceptions errors parce qu'il manque le nom du model */
    }


}
