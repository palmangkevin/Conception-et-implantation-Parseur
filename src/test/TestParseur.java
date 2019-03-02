package test;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;

import parseur.*;

import java.io.File;
import java.util.ArrayList;

public class TestParseur {

    File file;
    readFile Readfile;
    Parseur parseur;
    Model model;
    ArrayList<String> fileContent;
    ArrayList<Classe> classes;
    ArrayList<Association> associations;
    ArrayList<Aggregation> aggregations;
    ArrayList<Generalization> generalizations;


    @Test
    public void testParseur() throws ExceptionError{
        file = new File("testFile/Ligue.ucd");
        Readfile = new readFile(file);
        parseur = new Parseur(Readfile.get_fileContent());
        model = parseur.getModel();
        classes = model.getClasses();
        associations = model.getAssociations();
        aggregations = model.getAggregations();
        generalizations = model.getGeneralizations();

        assertEquals("Ligue", model.getName());
        assertEquals("Participant", model.getClasses().get(1).getName());
        assertEquals("Joueur", model.getClasses().get(2).getName());
        assertEquals("Integer", classes.get(2).getAttributes().get(0).getType());
        assertEquals("Equipe", classes.get(4).getOperations().get(0).getArguments().get(0).getType());
        assertEquals("ONE_OR_MANY", associations.get(1).getPremierRole().getMultiplicity());
        assertEquals("Joueur", aggregations.get(0).getParts().get(0).getName());
        assertEquals("Entraineur",generalizations.get(0).getSubClasses().get(1));

    }

}
