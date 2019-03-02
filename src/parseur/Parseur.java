package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import java.util.ArrayList;

public class Parseur {

    String file;
    String fin = "!!END!!";
    String[] motsCles = {
            "MODEL",
            "CLASS",
            "SUBCLASSES",
            "ATTRIBUTES",
            "OPERATIONS",
            "GENERALIZATION",
            "RELATION",
            "AGGREGATION",
            "CONTAINER",
            "PARTS",
    };
    ArrayList<String> fileContent;
    Model model;
    int index;

    /**
     * Constructeur Parseur
     * @param fileContent
     * @throws ExceptionError
     */
    public Parseur(ArrayList<String> fileContent) throws ExceptionError {
        this.fileContent = fileContent;
        index = 0;
        initParseur(); /* Initialisation du parseur*/
    }

    /**
     * Get model
     * @return
     */
    public Model getModel() {
        return model;
    }

    /**
     * Fonction qui permet de passer au prochain caractere
     */
    public void nextIndex() {
        if(index == fileContent.size() - 1) {
            this.file = fin;
        }
        else {
            this.file = fileContent.get(++index);
        }
    }

    /**
     * Fonction permettant de detecter la fin de la lecture des elements dans mon fileContent
     * @return
     */
    private String endFileContent() {
        if(index == fileContent.size() - 1) {
            return fin;
        }
        else {
            return fileContent.get(index + 1);
        }
    }

    /**
     * Initialisation du parseur
     * @throws ExceptionError
     */
    public void initParseur()throws ExceptionError {

        if(!fileContent.get(index).equals("MODEL")) {
            throw new ExceptionError("Le diagramme UML devrait commencer avec MODEL");
        }

        nextIndex();

        if(this.file == fin){
            throw new ExceptionError("Le diagramme UML doit avoir un MODEL");
        }

        model = new Model(this.file);
        parseDeclarations();

    }

    /**
     * Retourne false quand le mots cle n'est pas trouve, sinon retourne true
     * @param s
     * @return
     */
    public boolean motsCles_notFound(String s) {
        for(String reserved : motsCles) {
            if(s.equalsIgnoreCase(reserved)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verification des caracteres
     * @param s
     * @return
     */
    public boolean validIdent(String s) {
        return s.matches("^[a-zA-Z0-9_-]+");
    }

    /**
     * Parseur de declarations
     * @throws ExceptionError
     */
    public void parseDeclarations() throws ExceptionError {
        nextIndex();
        if(this.file.equals(fin)) {
            return;
        }
        if(this.file.equals("CLASS")) {
            parseClasses();
        }
        else if(this.file.equals("RELATION")) {
            parseAssociation();
        }
        else if(this.file.equals("AGGREGATION")) {
            parseAggregation();
        }
        else if(this.file.equals("GENERALIZATION")) {
            parseGeneralization();
        }
        else {
            System.out.println(this.file);
            throw new ExceptionError("Declaration pas valide");
        }
    }

    /**
     * Prseur de classes
     * @throws ExceptionError
     */
    public void parseClasses() throws ExceptionError {
        nextIndex();

        if(! validIdent(this.file)) {
            throw new ExceptionError("Identificateur de CLASS pas valide");
        }
        Classe classe = new Classe(this.file);
        nextIndex();

        if(! this.file.equals("ATTRIBUTES")) {
            throw new ExceptionError("Attributs introuvable");
        }
        classe = parseAttribute(classe);
        nextIndex();

        if(! this.file.equals("OPERATIONS")) {
            throw new ExceptionError("Operations introuvable");
        }

        classe = parseOperations(classe);
        model.addClasse(classe);
        parseDeclarations();
    }


    /**
     * Parseur d'attributs
     * @param c
     * @return
     * @throws ExceptionError
     */
    public Classe parseAttribute(Classe c) throws ExceptionError {
        Classe classe = c;
        String name;
        String type;

        if(endFileContent().equals("OPERATIONS")){
            return classe;
        }
        nextIndex();

        if(validIdent(this.file) && ! classe.getAttributes().isEmpty()) {
            throw new ExceptionError("Manque `,` entre les attributs");
        }
        if(this.file.equals(",")) {
            nextIndex();
        }
        if (! validIdent(this.file)) {
            throw new ExceptionError(" Attributs pas valide identifier");
        }

        name = this.file;
        nextIndex();

        if (!this.file.equals(":")) {
            throw new ExceptionError("En Attente de `:` apres un nom d'attribut");
        }

        nextIndex();

        if(! validIdent(this.file)) {
            throw new ExceptionError("Type non valide identifier");
        }

        type = this.file;
        Attribute attribute = new Attribute(name,type);
        classe.addAttribute(attribute);

        return parseAttribute(classe);
    }

    /**
     * Parseur d'operations
     * @param c
     * @return
     * @throws ExceptionError
     */
    public Classe parseOperations(Classe c) throws ExceptionError {

        Classe classe = c;
        Operation operation;
        ArrayList<Argument> arguments;
        String name;
        String type;

        nextIndex();

        if(this.file.equals(";")) {
            return classe;
        }
        else if(validIdent(this.file) && !classe.getOperations().isEmpty()) {
            throw new ExceptionError("Manque `,` entre les operations");
        }
        else if(this.file.equals(",")) {
            nextIndex();
        }

        if(! validIdent(this.file)) {
            throw new ExceptionError("Operation non valide identifier");
        }

        name = this.file;
        nextIndex();

        if(! this.file.equals("(")) {
            throw new ExceptionError("En attente de `(`");
        }

        if(! endFileContent().equals(")")) {
            arguments = parseArguments();
        }
        else {
            arguments = new ArrayList<Argument>();
        }

        nextIndex();
        nextIndex();

        if(! this.file.equals(":")) {
            throw new ExceptionError("En attente de `:`");
        }

        nextIndex();

        if(! validIdent(this.file)) {
            throw new ExceptionError("Operation non valide identifier");
        }

        type = this.file;
        operation = new Operation(name, type, arguments);
        classe.addOperation(operation);

        return parseOperations(classe);
    }

    /**
     * Parseur d'arguments
     * @return
     * @throws ExceptionError
     */
    public ArrayList<Argument> parseArguments() throws ExceptionError {

        ArrayList<Argument> arguments = new ArrayList<Argument>();
        Argument argument;
        String name;
        String type;

        while(true) {
            nextIndex();
            if(! validIdent(this.file)) {
                throw new ExceptionError("Argument invalide");
            }
            name = this.file;
            nextIndex();

            if(! this.file.equals(":")) {
                throw new ExceptionError("En attente de `:`");
            }

            nextIndex();

            if(! validIdent(this.file)) {
                throw new ExceptionError("Argument invalide");
            }

            type = this.file;
            argument = new Argument(name, type);
            arguments.add(argument);

            if(endFileContent().equals(")")) {
                break;
            }
            else if(endFileContent().equals(",")) {
                nextIndex();
            }
            else {
                throw new ExceptionError("Manque `,` entre les arguments");
            }
        }
        return arguments;
    }

    /**
     * Parseur d'associations
     * @throws ExceptionError
     */
    public void parseAssociation() throws ExceptionError {

        String name;
        Role first_role;
        Role second_role;
        Association association;

        nextIndex();

        if(! validIdent(this.file)) {
            throw new ExceptionError("Association non valide identidier");
        }

        name = this.file;
        nextIndex();

        if(! this.file.equals("ROLES")) {
            throw new ExceptionError("En Attente de 'Roles' apres une Association identifier");
        }

        first_role = parseRole();
        nextIndex();

        if(! this.file.equals(",")) {
            throw new ExceptionError("Manque `,` entre les roles");
        }

        second_role = parseRole();
        nextIndex();

        if(! this.file.equals(";")) {
            throw new ExceptionError("En attente de `;` apres une association");
        }

        association = new Association(name, first_role, second_role);
        model.addAssociation(association);
        parseDeclarations();
    }

    /**
     * Parseur de role
     * @return
     * @throws ExceptionError
     */
    public Role parseRole() throws ExceptionError {
        String name;
        String multiplicity;

        nextIndex();

        if(! this.file.equals("CLASS")) {
            throw new ExceptionError("En attente de `CLASS` avant l'identification d'un role");
        }

        nextIndex();

        if(! validIdent(this.file)) {
            throw new ExceptionError("Role non valide identifier");
        }

        name = this.file;
        nextIndex();

        if(! Role.isMultiplicityValid(this.file)) {
            throw new ExceptionError("Multiplicite non valide");
        }
        multiplicity = this.file;
        return new Role(name, multiplicity);
    }

    /**
     * Parseur d'Aggregation
     * @throws ExceptionError
     */
    public void parseAggregation() throws ExceptionError {
        Aggregation aggregation;
        Role container;
        Role part;
        ArrayList<Role> parts = new ArrayList<Role>();

        nextIndex();

        if(! this.file.equals("CONTAINER")) {
            throw new ExceptionError("En attente de`CONTAINER` apres `AGGREGATION`");
        }

        container = parseRole();
        nextIndex();

        if(! this.file.equals("PARTS")) {
            throw new ExceptionError("En attente de `PARTS` apres container");
        }

        while(true) {
            part = parseRole();
            parts.add(part);
            nextIndex();

            if(file.equals(";")) {
                break;
            }
            else if(file.equals("CLASS")) {
                throw new ExceptionError("En attente de  `,` entre les part");
            }
            else if(file.equals(",")) {
                continue;
            }
            else {
                throw new ExceptionError("En attente de `;` apres aggregation");
            }
        }

        aggregation = new Aggregation(container, parts);
        model.addAggregation(aggregation);
        parseDeclarations();

    }

    /**
     * Parseur de Generalization
     * @throws ExceptionError
     */
    public void parseGeneralization() throws ExceptionError {

        Generalization generalization;
        String name;
        ArrayList<String> subclasses = new ArrayList<String>();

        nextIndex();
        if(! validIdent(this.file)) {
            throw new ExceptionError("Generalization non valide identifier");
        }
        name = this.file;
        nextIndex();

        if(! this.file.equals("SUBCLASSES")) {
            throw new ExceptionError("En attente de `SUBCLASSES` apres IDENTIFIER");
        }

        while(true) {

            nextIndex();

            if(! validIdent(this.file)) {
                throw new ExceptionError("Sous-classes non valide identifier");
            }

            subclasses.add(this.file);
            nextIndex();

            if(this.file.equals(";")) {
                break;
            }
            else if(validIdent(this.file)) {
                throw new ExceptionError("En attente de `,` entre les sous-classes");
            }
            else if(this.file.equals(",")){
                continue;
            }
            else {
                throw new ExceptionError("En attente de `;` apres une generalization");
            }
        }

        generalization = new Generalization(name, subclasses);
        this.model.addGeneralization(generalization);
        parseDeclarations();
    }




}
