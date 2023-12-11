package org.example;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

/**
 1.	Diese Klasse erweitert JFrame und erstellt das Hauptfenster für die Anzeige von Personendaten.
 2.	Sie verwendet ein CardLayout, um zwischen verschiedenen Personendatenansichten zu navigieren.
 3.	Die Methode build wird verwendet, um das Hauptfenster zu erstellen.
 4.	Es werden Schaltflächen zum Navigieren zwischen Personen und zum Bearbeiten von Personen hinzugefügt.
 5.	Die Methode updateData wird verwendet, um die Daten in der Datenbank zu aktualisieren.
 6.	Die Methode showDialog erstellt ein Dialogfenster zum Bearbeiten von Personendaten.
 7.	Das Dialogfenster enthält Eingabefelder für Name, Vorname, Geschlecht, Geburtsdatum, AHV-Nummer, Region und Anzahl der Kinder.
 8.	Wenn "speichern" gedrückt wird, werden die Änderungen an der aktuellen Person gespeichert und das Hauptfenster neu aufgebaut.
 9.	Die Klasse verwendet die Connection1-Klasse zur Datenbankverbindung.
 */

public class PersonView extends JFrame{

    private ArrayList<Person> persons;
    private int index = 1;
    private Connection1 con;
    public PersonView(ArrayList<Person> persons,Connection1 con) {
        this.persons = persons;
        this.con=con;
        build();

    }

    public void build() {

        this.setTitle("Personen");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JLabel p = new JLabel("Person:");
        header.add(p);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        header.setBorder(bottomBorder);
        JPanel bodypannel;
        CardLayout cardLayout;
        bodypannel = new JPanel();
        cardLayout = new CardLayout();
        bodypannel.setLayout(cardLayout);
        int i = 0;
        for (Person person : persons) {
            i++;
            JPanel ppanel = new JPanel();
            ppanel.setLayout(new BoxLayout(ppanel, BoxLayout.Y_AXIS));
            int leftPadding = 10;
            int bottomPadding = 60;
            int topPadding = 20;
            ppanel.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 0));
            JLabel lname = new JLabel(person.getLname());
            JLabel fname = new JLabel(person.getFname());
            ppanel.add(lname);
            ppanel.add(fname);

            bodypannel.add(ppanel, "Panel" + i);

        }

        bodypannel.setBorder(bottomBorder);
        JPanel navig = new JPanel();
        navig.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 20));
        JPanel left = new JPanel();
        JButton back = new JButton(" < ");

        JButton next = new JButton(" > ");
        back.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (index > 1) {
                    index--;
                    cardLayout.show(bodypannel, "Panel" + index);
                }

            }
        });
        next.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (index < persons.size()) {
                    index++;
                    cardLayout.show(bodypannel, "Panel" + index);
                }

            }
        });
        left.add(back);
        left.add(next);

        JPanel right = new JPanel();
        JButton edit = new JButton("bearbeiten");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showDialog();
                } catch (ParseException ex) {
                    Logger.getLogger(PersonView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    updateData(con.statement,persons);
                } catch (SQLException ex) {
                    Logger.getLogger(PersonView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        right.add(edit);
        navig.add(left);
        navig.add(right);

        this.add(header);
        this.add(bodypannel);
        this.add(navig);
        this.setSize(400,400);
        this.pack();
        cardLayout.show(bodypannel, "Panel" + index);
        this.setVisible(true);

    }
    private static void updateData(Statement statement, ArrayList<Person> persons) throws SQLException {
        String clearSQL = "TRUNCATE TABLE person"; // Use TRUNCATE or DELETE as needed
        statement.executeUpdate(clearSQL);

        for (Person person : persons) {
            String insertSQL = "INSERT INTO person (Fname, Lname, Gender, DateOfBirth, Region, AHV_Number, Kinder) " +
                    "VALUES ('" + person.getFname() + "', '" + person.getLname() + "', '" + person.getGender() +
                    "', '" + new java.sql.Date(person.getDateofbirth().getTime()) + "', '" + person.getRegion() + "', '" + person.getAHV() +
                    "', " + person.getKinder() + ")";
            statement.executeUpdate(insertSQL);
        }
    }

    public void showDialog() throws ParseException {
        JDialog editInfo = new JDialog(this, "Person erfassen", true);

        editInfo.setLayout(new BoxLayout(editInfo.getContentPane(), BoxLayout.Y_AXIS));
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);

        JPanel header = new JPanel();
        header.setBorder(bottomBorder);

        header.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel p = new JLabel("Person erfassen:");
        header.add(p);

        editInfo.add(header);

        JPanel ln = new JPanel();
        ln.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel lnm = new JLabel("Name:                ");
        JTextField inlnm = new JTextField(10); //iA 0,1,2,3,4,5,....,size-1;
        // panel1 for p1 , panel1 for p2
        // index =1 for p1 , index =2 for p2
        inlnm.setText(persons.get(index - 1).getLname());
        ln.add(lnm);
        ln.add(inlnm);
        editInfo.add(ln);
        JPanel fn = new JPanel();
        fn.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
        JLabel fnm = new JLabel("Vorname:          ");
        JTextField infnm = new JTextField(10);
        infnm.setText(persons.get(index - 1).getFname());
        fn.add(fnm);
        fn.add(infnm);
        editInfo.add(fn);
        JPanel gender = new JPanel();
        gender.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 4));
        JRadioButton r1 = new JRadioButton("Mann");
        JRadioButton r2 = new JRadioButton("Frau");
        if (persons.get(index - 1).getGender().equals("Male")) {
            r1.setSelected(true);

        } else {
            r2.setSelected(true);

        }

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);
        gender.add(r1);
        gender.add(r2);
        editInfo.add(gender);
        JPanel dateoffb = new JPanel();
        dateoffb.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
        JLabel dateofb = new JLabel("Geburtsdatum:");
        MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
        JFormattedTextField dateField = new JFormattedTextField(dateFormatter);
        dateField.setPreferredSize(new Dimension(120, dateField.getPreferredSize().height));
        dateField.setEnabled(true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the Date to a String
        String dateStr = dateFormat.format(persons.get(index - 1).getDateofbirth());

        dateField.setValue(dateStr);
        dateoffb.add(dateofb);
        dateoffb.add(dateField);
        editInfo.add(dateoffb);
        JPanel AHV = new JPanel();
        AHV.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
        JLabel AHVNum = new JLabel("AHV Nummer:  ");
        MaskFormatter AHVFormatter = new MaskFormatter("###.####.####.##");
        JFormattedTextField AhvField = new JFormattedTextField(AHVFormatter);
        AhvField.setPreferredSize(new Dimension(120, AhvField.getPreferredSize().height));
        AhvField.setEnabled(true);
        AhvField.setValue(persons.get(index - 1).getAHV());
        AHV.add(AHVNum);
        AHV.add(AhvField);
        editInfo.add(AHV);
        JPanel Region = new JPanel();
        Region.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
        JLabel region = new JLabel("Region:              ");
        String[] valuesreg = {"Genferseeregion", "Escape Mittelland", "Nordwestschweiz", "Zürich", "Ostschweiz", "Zentralschweiz", "Tessin"};
        SpinnerListModel model = new SpinnerListModel(valuesreg);
        JSpinner spinner = new JSpinner(model);
        spinner.setValue(persons.get(index - 1).getRegion());
        spinner.setPreferredSize(new Dimension(140, 20));
        Region.add(region);
        Region.add(spinner);
        editInfo.add(Region);
        JPanel Kind = new JPanel();
        Kind.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        JLabel kind = new JLabel("Kinder:               ");

        SpinnerNumberModel modelchildren = new SpinnerNumberModel(0, 0, 10, 1); // Initial value, min, max, step

        JSpinner spinnerchild = new JSpinner(modelchildren);
        spinnerchild.setValue(persons.get(index - 1).getKinder());
        Kind.add(kind);
        Kind.add(spinnerchild);
        editInfo.add(Kind);

        Border bottomBorder1 = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        Kind.setBorder(bottomBorder1);

        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton save = new JButton("speichern");
        JFrame current = this;
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                persons.get(index - 1).setFname(infnm.getText());
                persons.get(index - 1).setLname(inlnm.getText());
                if (r1.isSelected()) {
                    persons.get(index - 1).setGender("Male");
                } else {
                    persons.get(index - 1).setGender("Female");
                }
                String ndate = dateField.getText();
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                Date datee = null;

                try {
                    // Parse the String to a Date
                    datee = dateFormat1.parse(ndate);
                } catch (ParseException ex) {
                    Logger.getLogger(PersonView.class.getName()).log(Level.SEVERE, null, ex);
                }
                persons.get(index - 1).setDateofbirth(datee);

                persons.get(index - 1).setAHV(AhvField.getText());
                persons.get(index - 1).setRegion((String) spinner.getValue());
                persons.get(index - 1).setKinder((int) spinnerchild.getValue());
                current.getContentPane().removeAll();
                build();
            }
        });
        JButton cancel = new JButton("abbrechen");
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                editInfo.dispose();
            }
        });

        footer.add(save);
        footer.add(cancel);
        editInfo.add(footer);
        editInfo.setSize(400, 450);
        editInfo.setLocationRelativeTo(this);

        editInfo.setVisible(true);


    }

}

