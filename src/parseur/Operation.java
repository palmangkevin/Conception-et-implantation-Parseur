package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;

public class Operation {
    private String name;
    private String type;
    private ArrayList<Argument> arguments;

    /**
     * Constructeur Operation
     * @param name
     * @param type
     * @param args
     */
    public Operation(String name, String type, ArrayList<Argument> args) {
        this.name = name;
        this.type = type;
        arguments = args;
    }

    /**
     * Get arguments
     * @return
     */
    public ArrayList<Argument> getArguments() {
        return this.arguments;
    }

    /**
     * Get name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get type
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Convertir en string
     * @return
     */
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        String type;

        type = (this.type.equalsIgnoreCase("void")) ? "" : this.type+" ";

        for(int i = 0;i < this.arguments.size(); i++) {
            stringBuilder.append(arguments.get(i).getType());
            if(i != (this.arguments.size() - 1)) {
                stringBuilder.append(", ");
            }
        }

        return type+this.name+"("+stringBuilder.toString()+")";
    }

}
