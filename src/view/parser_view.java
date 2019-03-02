package view;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

import parseur.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class parser_view extends JFrame {

    /**
     * Variables
     */
    JPanel topPanel;
    JPanel classesPanel;
    JPanel attributsPanel;
    JPanel methodesPanel;
    JPanel sousClassesPanel;
    JPanel associationsAggregationsPanel;
    JPanel centerPanel;
    JPanel centerPanelContainer;
    JPanel detailsPanel;
    JPanel metriquesPanel;
    JTextField pathArea;
    JTextArea detailsArea;
    JFileChooser jfc;
    JFileChooser jfc_csv;
    JList classesList;
    JList attributsList;
    JList methodesList;
    JList sousClassesList;
    JList associationsAggregationsList;
    JList metriquesList;
    JButton loadFileButton;
    JButton parseFileButton;
    JButton csvButton;
    File file;
    readFile read;
    Model model;
    Parseur parseur;


    /**
     * Constructeur du parser_view
     */
    public parser_view(){
        initGUI();
    }

    public void initGUI(){

        /** Initialisation du JFrame */
        setTitle("Parseur - Fichiers UML");
        setSize(850,550);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /** TOP JPanel */
        topPanel = new JPanel();

        /*Button "Charger le fichier"*/
        loadFileButton = new JButton("Charger le fichier");
        loadFileButton.addActionListener(new loadFileButtonHandler());
        topPanel.add(loadFileButton); // Ajouter le bouton "Charger le fichier" au Top Panel

        /*TextField du path*/
        pathArea = new JTextField();
        pathArea.setPreferredSize(new Dimension(300,22));
        pathArea.setEditable(false);
        topPanel.add(pathArea);

        /*Button pour parser le fichier chargé*/
        parseFileButton = new JButton("Parser le fichier");
        parseFileButton.addActionListener(new parseFileButtonHandler());
        topPanel.add(parseFileButton);

        /*Button pour enregistrer les metriques en fichier CSV*/
        csvButton = new JButton("Sauvegarder metriques");
        csvButton.addActionListener(new csvButtonHandler());
        topPanel.add(csvButton);


        /*Extension du File Chooser*/
        jfc = new JFileChooser(".");
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("Diagramme UML", "ucd"));

        /*File Chooser pour l'enregistrement CSV*/
        jfc_csv = new JFileChooser(".");
        jfc_csv.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separaved Values (.csv)", "csv"));


        /*Ajouter le JPanel topPanel au JFrame*/
        getContentPane().add(topPanel, BorderLayout.NORTH);


        /**
         * Creation du JPanel metriques
         */
        metriquesPanel = new JPanel();
        metriquesPanel.setBorder(BorderFactory.createTitledBorder("Metriques"));
        metriquesPanel.setPreferredSize(new Dimension(200,0));
        metriquesList = new JList();
        metriquesList.addListSelectionListener(new metriquesSelectionHandler());
        metriquesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        metriquesPanel.setLayout(new BorderLayout());
        metriquesPanel.add(new JScrollPane(metriquesList), BorderLayout.CENTER);

        /*Ajouter le JPanel metriquesPanel au JFrame*/
        getContentPane().add(metriquesPanel, BorderLayout.EAST);


        /**
         * Creation du JPanel Classes
         */
        classesPanel = new JPanel();
        classesPanel.setBorder(BorderFactory.createTitledBorder("Classes"));
        classesPanel.setPreferredSize(new Dimension(200,0));
        classesList = new JList();
        classesList.addListSelectionListener(new classesSelectionHandler());
        classesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        classesPanel.setLayout(new BorderLayout());
        classesPanel.add(new JScrollPane(classesList), BorderLayout.CENTER);

        /*Ajouter le JPanel classesPanel au JFrame*/
        getContentPane().add(classesPanel, BorderLayout.WEST);


        /**
         * Creation du JPanel du centre avec les elements 2 lignes 1 colonne
         */
        centerPanel = new JPanel(new GridLayout(2,1));
        /*On separe le center panel en 2 lignes deux colonnes*/
        centerPanelContainer = new JPanel(new GridLayout(2,2));

        /**
         * Details Panel
         */
        detailsPanel = new JPanel();
        detailsPanel.setSize(new Dimension(100,100));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("D\u00E9tails"));
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);


        /**
         *  Attributs
         */
        attributsPanel = new JPanel();
        attributsPanel.setBorder(BorderFactory.createTitledBorder("Attributs"));
        attributsPanel.setLayout(new BorderLayout());
        attributsList = new JList();
        attributsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attributsPanel.add(new JScrollPane(attributsList), BorderLayout.CENTER);
        centerPanelContainer.add(attributsPanel);
        attributsList.addListSelectionListener(new centerPanelSelectionHandler());

        /**
         * Methodes
         */
        methodesPanel = new JPanel();
        methodesPanel.setBorder(BorderFactory.createTitledBorder("M\u00E9thodes"));
        methodesPanel.setLayout(new BorderLayout());
        methodesList = new JList();
        methodesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        methodesPanel.add(new JScrollPane(methodesList), BorderLayout.CENTER);
        centerPanelContainer.add(methodesPanel);
        methodesList.addListSelectionListener(new centerPanelSelectionHandler());

        /**
         * Sous-classes
         */
        sousClassesPanel = new JPanel();
        sousClassesPanel.setBorder(BorderFactory.createTitledBorder("Sous-classes"));
        sousClassesPanel.setLayout(new BorderLayout());
        sousClassesList = new JList();
        sousClassesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sousClassesPanel.add(new JScrollPane(sousClassesList), BorderLayout.CENTER);
        centerPanelContainer.add(sousClassesPanel);
        sousClassesList.addListSelectionListener(new centerPanelSelectionHandler());

        /**
         * Associations & Aggregations
         */
        associationsAggregationsPanel = new JPanel();
        associationsAggregationsPanel.setBorder(BorderFactory.createTitledBorder("Associations & Agg\u00E9rgations"));
        associationsAggregationsPanel.setLayout(new BorderLayout());
        associationsAggregationsList = new JList();
        associationsAggregationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        associationsAggregationsPanel.add(new JScrollPane(associationsAggregationsList), BorderLayout.CENTER);
        centerPanelContainer.add(associationsAggregationsPanel);
        associationsAggregationsList.addListSelectionListener(new aggregationsAssociationsSelectionHandler());


        /**
         * Ajouter des panels
         */
        centerPanel.add(centerPanelContainer);
        centerPanel.add(detailsPanel);

        getContentPane().add(centerPanel);

    }

    /**
     * Button "Charger le ficher" Handler
     */
    class loadFileButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int return_value = jfc.showOpenDialog(null);
            if(return_value == jfc.APPROVE_OPTION) {
                file = jfc.getSelectedFile();
                pathArea.setText(file.getAbsolutePath());
            }

        }
    }

    /**
     * Button "Parser le ficher" Handler
     */
    class parseFileButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String pathFile = pathArea.getText();
            file = new File(pathFile);

            if(pathFile.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun fichier n'a été sélectionné.", "Erreur de fichier", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(! file.exists()) {
                JOptionPane.showMessageDialog(null, "Le fichier n'existe pas.", "Erreur de fichier", JOptionPane.ERROR_MESSAGE);
                return;
            }

            read = new readFile(file);

            try {
                /* Parse le fichier */
                parseur = new Parseur(read.get_fileContent());
                model = parseur.getModel();
                classesList.setListData(model.getClasses().toArray());
            }
            catch (ExceptionError ex){
                JOptionPane.showMessageDialog(parser_view.this, ex.getMessage(),"Erreur Parsing", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Button "Sauvegarder métriques en fichier csv" Handler
     */
    class csvButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath;
            File file;
            BufferedWriter bufferWriter;

            int returnedValue = jfc_csv.showSaveDialog(null);
            if(model == null){
                JOptionPane.showMessageDialog(null, "Aucun fichier n'a été parsé",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(returnedValue == JFileChooser.APPROVE_OPTION){
                filePath = jfc_csv.getSelectedFile().getAbsolutePath();

                /*Mettre l'extension .csv si il n'y a pas d'extension*/
                if(! filePath.endsWith(".csv")){
                    filePath += ".csv";
                }

                try{

                    file = new File (filePath);
                    if(! file.exists()){
                        file.createNewFile();
                    }
                    bufferWriter = new BufferedWriter(new FileWriter(file));
                    bufferWriter.write(get_csv_file());
                    bufferWriter.close();
                    JOptionPane.showMessageDialog(null, "Fichier CSV créé ");

                }catch(IOException a){
                    a.printStackTrace();
                }
            }
        }
    }

    /**
     * Fonction qui cree le contenu du fichier csv
     */
    public String get_csv_file(){
        Metrique metrique;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Classes/Metriques,ANA,NOM,NOA,ITC,ETC,CAC,DIT,CLD,NOC,NOD");
        stringBuilder.append("\n");
        for(Classe c : model.getClasses()){
            metrique = new Metrique(model, c.getName());
            stringBuilder.append(c.getName()+",");
            stringBuilder.append(metrique.getANA()+",");
            stringBuilder.append(metrique.getNOM()+",");
            stringBuilder.append(metrique.getNOA()+",");
            stringBuilder.append(metrique.getITC()+",");
            stringBuilder.append(metrique.getETC()+",");
            stringBuilder.append(metrique.getCAC()+",");
            stringBuilder.append(metrique.getDIT()+",");
            stringBuilder.append(metrique.getCLD()+",");
            stringBuilder.append(metrique.getNOC()+",");
            stringBuilder.append(metrique.getNOD());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();

    }

    /**
     * Class qui gere Click sur les metriques.
     */
    class metriquesSelectionHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e){
            int index = metriquesList.getSelectedIndex();
            selectionClear((JList) e.getSource());
            if(index >= 0){
                detailsArea.setText(Metrique.DEFINITIONS_METRIQUES[index]);
            }

        }
    }

    /**
     * Class qui gere Click sur les classes.
     */
    class classesSelectionHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {

            Metrique metrique;
            int index = classesList.getSelectedIndex();
            detailsArea.setText("");
            Classe classeSelected = model.getClasses().get(index);
            ArrayList<String> subClasses = findSubClasses(classeSelected);
            DefaultListModel aggregations_associations = findAssociationsAggregations(classeSelected);
            metrique = new Metrique(model, classeSelected.getName());

            attributsList.setListData(classeSelected.getAttributes().toArray());
            methodesList.setListData(classeSelected.getOperations().toArray());
            sousClassesList.setListData(subClasses.toArray());
            associationsAggregationsList.setModel(aggregations_associations);
            metriquesList.setListData(getMetriques(metrique).toArray());
        }
    }

    /**
     * Fonction qui retourne un tableau avec toutes les métriques pour remplir la JList metriquesList
     */
    public ArrayList<String> getMetriques (Metrique metrique){
        ArrayList<String> metriques = new ArrayList<String>();

        metriques.add("ANA: "+String.format("%.2f", metrique.getANA()));
        metriques.add("NOM: "+metrique.getNOM());
        metriques.add("NOA: "+metrique.getNOA());
        metriques.add("ITC: "+metrique.getITC());
        metriques.add("ETC: "+metrique.getETC());
        metriques.add("CAC: "+metrique.getCAC());
        metriques.add("DIT: "+metrique.getDIT());
        metriques.add("CLD: "+metrique.getCLD());
        metriques.add("NOC: "+metrique.getNOC());
        metriques.add("NOD: "+metrique.getNOD());

        return metriques;
    }

    /**
     * Fonction qui parcours la liste de generalizations pour trouver les sous classes qui correpondent aux
     * classes respectives.
     * @param a
     * @return
     */
    public ArrayList<String> findSubClasses (Classe a){

        String classeName = a.getName();
        ArrayList<String> sousClasses = new ArrayList<String>();
        ArrayList<Generalization> generalizations = model.getGeneralizations();

        for(Generalization generalization : generalizations){
            if(generalization.getName().endsWith(classeName)){
                return generalization.getSubClasses();
            }
        }
        return sousClasses;
    }

    /**
     * Trouver les associations et Aggregations lieé a une classe
     * @param a
     * @return
     */

    public DefaultListModel findAssociationsAggregations(Classe a){

        String classeName = a.getName();
        ArrayList<Association> associations = model.getAssociations();
        ArrayList<Aggregation> aggregations = model.getAggregations();
        DefaultListModel listModel = new DefaultListModel();

        /*Trouver les associations*/
        for(Association association: associations){
            if(association.getPremierRole().getName().equals(classeName)){
                listModel.addElement("(R) [N] " +association.getName()); /*Relation Normale*/
            }
            if(association.getSecondRole().getName().equals(classeName)){
                listModel.addElement("(R) [I] "+association.getName()); /*Relation Inverse*/
            }
        }

        /*Trouver les aggregations*/
        for(Aggregation aggregation: aggregations){

            if(aggregation.getContainer().getName().equals(classeName)){
                for(Role role: aggregation.getParts()){
                    listModel.addElement("(A) [P] " + role.getName());
                }
            }

            for(Role role: aggregation.getParts()){
                if(role.getName().equals(classeName)){
                    listModel.addElement("(A) [C] " + aggregation.getContainer().getName());
                }
            }
        }

        return listModel;

    }

    /**
     *
     * @param a
     */
    public void selectionClear(JList a){
        if(! attributsList.equals(a)){
            attributsList.clearSelection();
        }
        if(! methodesList.equals(a)){
            methodesList.clearSelection();
        }
        if(! associationsAggregationsList.equals(a)){
            associationsAggregationsList.clearSelection();
        }
        if(! sousClassesList.equals(a)){
            sousClassesList.clearSelection();
        }
    }

    /**
     *
     * @param s
     * @return
     */
    public String part(String s) {
        ArrayList<Aggregation> aggregations = model.getAggregations();
        StringBuilder details = new StringBuilder();
        Aggregation aggregation = null;
        Role role = null;

        for(Aggregation aggrega : aggregations) {
            for(Role r : aggrega.getParts()) {
                if(r.getName().equals(s)) {
                    role = r;
                    break;
                }
            }

            if(role != null) {
                aggregation = aggrega;
                break;
            }
        }
        details.append("AGGREGATION\n");
        details.append("CONTAINER\n");
        details.append("   CLASS "+aggregation.getContainer().getName()+" "+aggregation.getContainer().getMultiplicity()+"\n");
        details.append("PARTS\n");

        for(Role r : aggregation.getParts()) {
            details.append("   CLASS "+r.getName()+" "+r.getMultiplicity()+"\n");
        }

        return details.toString();
    }

    /**
     *
     * @param s
     * @return
     */
    public String container(String s) {
        ArrayList<Aggregation> aggregations = model.getAggregations();
        StringBuilder details = new StringBuilder();
        Aggregation aggregation = null;

        for(Aggregation aggrega : aggregations) {
            if(aggrega.getContainer().getName().equals(s)){
                aggregation = aggrega;
                break;
            }
        }
        details.append("AGGREGATION\n");
        details.append("CONTAINER\n");
        details.append("   CLASS "+aggregation.getContainer().getName()+" "+aggregation.getContainer().getMultiplicity()+"\n");
        details.append("PARTS\n");

        for(Role role : aggregation.getParts()) {
            details.append("   CLASS "+role.getName()+" "+role.getMultiplicity()+"\n");
        }

        return details.toString();
    }

    /**
     * Details sur les aggregations.
     * @param s
     * @return
     */
    public String aggregationDetails(String s) {
        String type;
        String identifier;

        type = s.substring(5,6);
        identifier = s.substring(8);

        if(type.equals("C")){
            return container(identifier);
        }
        else {
            return part(identifier);
        }

    }

    /**
     * CenterPanel CLICK Handler
     */
    class centerPanelSelectionHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            selectionClear((JList) e.getSource());
            detailsArea.setText(" ");
        }
    }

    /**
     * Panel Aggregations et Associations CLICK Handler
     */
    class aggregationsAssociationsSelectionHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String s;
            String type;
            String details;
            JList list = (JList) e.getSource();
            ListModel listModel = list.getModel();

            selectionClear((JList) e.getSource());

            if(list.getSelectedIndex() == -1){
                return;
            }

            s = (String) listModel.getElementAt(list.getSelectedIndex());
            type = s.substring(1,2);

            if(type.equals("A")){
                details = aggregationDetails(s);
            }
            else{
                details = associationDetails(s);
            }

            detailsArea.setText(details);
        }
    }

    /**
     *
     * @param s
     * @return
     */
    public String associationDetails(String s) {
        String a;
        ArrayList<Association> associations = model.getAssociations();
        Association association = null;
        StringBuilder stringBuilder = new StringBuilder();

        a = s.substring(8);

        for(Association ass : associations) {
            if(ass.getName().equals(a)) {
                association = ass;
                break;
            }
        }
        stringBuilder.append("RELATION ");
        stringBuilder.append(association.getName());
        stringBuilder.append("\n");
        stringBuilder.append("   ROLES\n");
        stringBuilder.append("   CLASS "+association.getPremierRole().getName()+" ");
        stringBuilder.append(association.getPremierRole().getMultiplicity()+"\n");
        stringBuilder.append("   CLASS "+association.getSecondRole().getName()+" ");
        stringBuilder.append(association.getSecondRole().getMultiplicity()+"\n");

        return stringBuilder.toString();
    }

    /**
     * Main pour l'execution
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        parser_view parser_view = new parser_view();
        parser_view.setVisible(true);
    }


}
