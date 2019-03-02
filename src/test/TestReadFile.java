package test;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import parseur.readFile;

/**
 * Test de la classe readFile qui permet de s'assurer du bon découpage des mots du fichier que la fonction Readfile
 * reçoit en paramètre.
 */

public class TestReadFile{

    File file;
    ArrayList<String> fileContent;


    @Before
    public void initFile(){
        file = new File("testFile/readFileTest.txt"); // Fichier à découper.
    }

    @Before
    public void fileContentResult(){
        fileContent = new ArrayList<String>();

        fileContent.add("Je");
        fileContent.add("realise");
        fileContent.add("ce");
        fileContent.add("test");
        fileContent.add("dans");
        fileContent.add("le");
        fileContent.add("cadre");
        fileContent.add("des");
        fileContent.add("travaux");
        fileContent.add("pratiques");
        fileContent.add("du");
        fileContent.add("cours");
        fileContent.add("IFT");
        fileContent.add("3913");
        fileContent.add(";");
        fileContent.add("Ceci");
        fileContent.add("est");
        fileContent.add("un");
        fileContent.add("test");
        fileContent.add("(");
        fileContent.add("Merci");
        fileContent.add("de");
        fileContent.add("ne");
        fileContent.add("pas");
        fileContent.add("le");
        fileContent.add("prendre");
        fileContent.add("en");
        fileContent.add("consideration");
        fileContent.add(")");

    }

    @Test
    public void TestContent(){
        readFile ReadFile = new readFile(file);
        assertEquals(fileContent, ReadFile.get_fileContent());
    }
}
