package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.io.*;
import java.util.ArrayList;

public class readFile {

    ArrayList <String> fileContent;

    /**
     * Constructeur readFile
     * @param file
     */
    public readFile (File file){

        try{
            ReadFile(file);
        }
        catch(FileNotFoundException a){
            a.printStackTrace();
        }
    }

    /**
     * Fonction ReadFile
     * @param file
     * @throws FileNotFoundException
     */
    public void ReadFile(File file) throws FileNotFoundException{

        fileContent = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        int r;
        char c;

        try{
            BufferedReader input = new BufferedReader(new FileReader(file));
            while((r = input.read()) != -1) {
                c = (char) r;
                if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                    if(!stringBuilder.toString().isEmpty()) fileContent.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
                else if(c == '(' || c == ')' || c ==',' || c == ';' || c == ':'){
                    if(!stringBuilder.toString().isEmpty()) fileContent.add(stringBuilder.toString());
                    fileContent.add(String.valueOf(c));
                    stringBuilder = new StringBuilder();
                }
                else {
                    stringBuilder.append(c);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get fileContent
     * @return
     */
    public ArrayList<String> get_fileContent(){
        return fileContent;
    }

}
