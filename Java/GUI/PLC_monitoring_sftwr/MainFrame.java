package Prototype;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

// sql к которому стермится теперь такой вот. Обязательно добавлять эту хуйню с точчкой перед тайм колом, ибо он моги ебать будет шо пизда, я уже это предчувсвую.
//SELECT TOP 1000 [Temp Uscita PS3],[Temp Pastorizzazione PS4],[Temp Uscita PS4],[PortataFark],[DeltaP],[Deviazione],[PS3].[TimeCol],[PS4].[TimeCol] FROM [Cosma].[dbo].[PS4],[Cosma].[dbo].[PS3],[Cosma].[dbo].[FARK]

public class MainFrame implements ActionListener, PropertyChangeListener, TimeChangeListener {
    ///////////////////////-----|   Combo Box item names |-----///////////////////////
    String[] itemNamesPS1 = {"Portata Bactofuga", "Portata Centrifuga 1", "Portata Centrifuga 2"};
    String[] itemNamesPS2 = {"Temp Pastorizzazione PS2", "Temp Uscita PS2", "Temp Acqua Calda"};
    String[] itemNamesPS3 = {"Temp Pastorizzazzione PS3", "Temp Uscita PS3", "Temp Acqua Calda", "Portata"};
    String[] itemNamesPS4 = {"Temp Pastorizzazione PS4", "Temp Uscita PS4", "Temp Acqua Calda"};
    String[] exportAsItems = {"Esporta come Excel", "Esporta come PDF", "Esporta come Testo"};
    ///////////////////////-----|   String Variables |-----///////////////////////
    String url;      //*
    String user;     //*
    String password; //Are used to take url,user,password from logginFrame and save as local variables
    String sqlPs1 = "Null"; //*
    String sqlPs2 = "Null"; //*
    String sqlPS3 = "Null"; //*
    String sqlPS4 = "Null"; //This are different versions of final SQL request.
    String sqlPs1Ver2 = "Null"; //*
    String sqlPs2Ver2 = "Null"; //*
    String sqlPS3Ver2 = "Null"; //*
    String sqlPS4Ver2 = "Null"; //This are different versions of final SQL request.
    String sqlPs1Ver3 = "Null"; //*
    String sqlPs2Ver3 = "Null"; //*

    //
    String sql_1 = "Null";
    String sql_2 = "Null";
    String sql_3 = "Null";
    String sql_4 = "Null";

    String sql_1_selected = "Null";
    String sql_2_selected = "Null";
    String sql_3_selected = "Null";
    String sql_4_selected = "Null";
    String sql_5_selected = "Null";
    String sql_6_selected = "Null";
    String sql_7_selected = "Null";
    String sql_8_selected = "Null";
    String sql_9_selected = "Null";
    String sql_10_selected = "Null";
    String sql_11_selected = "Null";
    String sql_12_selected = "Null";
    String sql_13_selected = "Null";
    String sql_14_selected = "Null";

    String sql_1_time = null;
    String sql_2_time = null;
    String sql_3_time = null;
    String sql_4_time = null;

    //
    int addOneMoreGraphQ = 10; // JOptionPane YES = 0 NO = 1
    String pattern = "yyyy-MM-dd"; //pattern used by JDatePickers
    String date_1;
    String date_2;
    String time_1;
    String time_2;
    String date_1Ver2;
    String date_2Ver2;
    String time_1Ver2;
    String time_2Ver2;
    ///////////////////////-----|   Example JButtons  |-----///////////////////////
    JButton FarkButton; //*
    JButton FarkButton1; //*
    JButton FarkButton2; //*
    JButton FarkButton3; //*
    JButton FarkButton4; //*
    JButton FarkButton5; //*
    JButton FarkButton6; //*
    JButton FarkButton7; //*
    JButton FarkButton8; //*
    JButton FarkButton9; //*
    JButton FarkButton10; //*
    JButton FarkButton11; //*
    JButton FarkButton12; //*
    JButton DeltaPButton; //*
    JButton Fark2Button; //*
    JButton Fark3Button; //This are different buttons for selecting machinery
    JButton Fark_qualcosaButton; //*
    JButton LavaggiButton; //*
    JButton RicettaButton; //*
    JButton Fark4Button; //This are different buttons for selecting machinery
    ///////////////////////-----|   JButtons  |-----///////////////////////
    JButton PS1Button; //*
    JButton PS2Button; //*
    JButton PS3Button; //*
    JButton PS4Button; //This are different buttons for selecting machinery
    JButton goOnChooseDateButton;//*
    JButton createTableButton;   //This button takes loggin data and creates a JTable
    JButton createGraphButton;   //This button takes loggin data and creates a Graph
    JButton refreshValue;
    JButton rTG; // real-time-graph
    ///////////////////////-----|   JComboBoxes  |-----///////////////////////
    JComboBox<String> ps1ComboBox = new JComboBox<>(itemNamesPS1);
    JComboBox<String> ps2ComboBox = new JComboBox<>(itemNamesPS2);
    JComboBox<String> ps3ComboBox = new JComboBox<>(itemNamesPS3);
    JComboBox<String> ps4ComboBox = new JComboBox<>(itemNamesPS4);
    JComboBox<String> exportAsComboBox = new JComboBox<>(exportAsItems);
    ///////////////////////-----|   JButtons  |-----///////////////////////
    JDateChooser dateFromChooser;
    JDateChooser dateToChooser;
    TimePicker timeFromPicker;
    TimePicker timeToPicker;
    Date date = new Date();
    ///////////////////////-----|   JButtons  |-----///////////////////////
    JPanel jLabelsPanel;
    JPanel jButtonsPanel;
    JFrame mainFrame;
    JLabel labelChooseMacynery;
    JLabel labelChooseValue;
    JLabel labelChooseFromDate;
    JLabel labelChooseToDate;
    //Will be used to create a graph
    DrawLine drawLine = new DrawLine();
    LogginFrame logginFrame = new LogginFrame();
    JTableFrame jTableFrame;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    //these variables are used to take values from DB
    String value1;
    String value1_1;
    String value2;
    String value2_1;
    String value3;
    String value3_1;
    String value4;
    String value4_1;
    String value5;
    String value5_1;
    String value6; //utsu
    String value6_1;
    String value7;
    String value7_1;
    String value8;
    String value8_1;
    String value9;
    String value9_1;
    String value10;
    String value10_1;
    String value11;
    String value11_1;
    String value12;
    String value12_1;
    String value13;
    String value13_1;
    String value14 = "tempoPS1";
    String value14_1;

    ArrayList<String> values = new ArrayList<>();
    String newName;
    ImageIcon imageIcon_1 = new ImageIcon("C:\\robochiy stol\\Logo-ETM.jpg");
    int i = 0;
    JTextField textField;

    /////////////////////////////////////////////////
    MainFrame() {
        PS1Button = new JButton();
        PS2Button = new JButton();
        PS3Button = new JButton();
        PS4Button = new JButton();
        goOnChooseDateButton = new JButton();
        refreshValue = new JButton();
        jLabelsPanel = new JPanel();
        jButtonsPanel = new JPanel();
        mainFrame = new JFrame();
        labelChooseMacynery = new JLabel();
        labelChooseValue = new JLabel();
        labelChooseFromDate = new JLabel();
        labelChooseToDate = new JLabel();
        textField = new JTextField();

        FarkButton = new JButton(); //*
        FarkButton1 = new JButton(); //*
        FarkButton2 = new JButton(); //*
        FarkButton3 = new JButton(); //*
        FarkButton4 = new JButton(); //*
        FarkButton5 = new JButton(); //*
        FarkButton6 = new JButton(); //*
        FarkButton7 = new JButton(); //*
        FarkButton8 = new JButton(); //*
        FarkButton9 = new JButton(); //*
        FarkButton10 = new JButton(); //*
        FarkButton11 = new JButton(); //*
        FarkButton12 = new JButton(); //*

        DeltaPButton = new JButton(); //*
        Fark2Button = new JButton(); //*
        Fark3Button = new JButton(); //These are useless for a moment
        Fark_qualcosaButton = new JButton(); //*
        LavaggiButton = new JButton(); //*
        RicettaButton = new JButton(); //*
        Fark4Button = new JButton(); //*
        ///////////////////////-----|   Stuff  |-----///////////////////////
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, Color.blue);
        timeSettings.initialTime = LocalTime.now();
        timeSettings.getAllowKeyboardEditing();

        logginFrame.logginFrame.setVisible(false);
        url = logginFrame.url;
        user = logginFrame.user;
        password = logginFrame.password;

        ImageIcon imageIcon = new ImageIcon("C:\\robochiy stol\\Logo-ETM2.jpg");

        ///////////////////////-----|   Machinery Part  |-----///////////////////////

        labelChooseMacynery.setText("Scegli il macchinario: ");
        labelChooseMacynery.setFont(new Font("MV Bolly", Font.BOLD, 20));
        labelChooseMacynery.setBounds(35, 5, 300, 60);

        PS1Button.setText("PS1");
        PS1Button.setBounds(30, 60, 90, 50);
        PS1Button.setFocusable(false);

        PS2Button.setText("PS2");
        PS2Button.setBounds(30, 130, 90, 50);
        PS2Button.setFocusable(false);

        PS3Button.setText("PS3");
        PS3Button.setBounds(30, 200, 90, 50);
        PS3Button.setFocusable(false);

        PS4Button.setText("PS4");
        PS4Button.setBounds(30, 270, 90, 50);
        PS4Button.setFocusable(false);

        goOnChooseDateButton.setText("Conferma");
        goOnChooseDateButton.setBounds(700, 270, 120, 60);
        goOnChooseDateButton.setFont(new Font("MV Bolly", Font.BOLD, 15));
        goOnChooseDateButton.setFocusable(false);
        goOnChooseDateButton.setVisible(false);

///////////////////////////////////////////////////////////////////////////////////////////////
        FarkButton.setText("FarkButton");
        FarkButton.setBounds(30, 370, 90, 50);
        FarkButton.setVisible(true);
        FarkButton1.setText("FarkButton1");
        FarkButton1.setBounds(30, 370, 90, 50);
        FarkButton1.setVisible(true);
        FarkButton2.setText("FarkButton2");
        FarkButton2.setBounds(30, 370, 90, 50);
        FarkButton2.setVisible(true);
        FarkButton3.setText("FarkButton3");
        FarkButton3.setBounds(30, 370, 90, 50);
        FarkButton3.setVisible(true);
        FarkButton4.setText("FarkButton4");
        FarkButton4.setBounds(30, 370, 90, 50);
        FarkButton4.setVisible(true);
        FarkButton5.setText("FarkButton5");
        FarkButton5.setBounds(30, 370, 90, 50);
        FarkButton5.setVisible(true);
        FarkButton6.setText("FarkButton6");
        FarkButton6.setBounds(30, 370, 90, 50);
        FarkButton6.setVisible(true);
        FarkButton7.setText("FarkButton7");
        FarkButton7.setBounds(30, 370, 90, 50);
        FarkButton7.setVisible(true);
        FarkButton8.setText("FarkButton8");
        FarkButton8.setBounds(30, 370, 90, 50);
        FarkButton8.setVisible(true);
        FarkButton9.setText("FarkButton9");
        FarkButton9.setBounds(30, 370, 90, 50);
        FarkButton9.setVisible(true);
        FarkButton10.setText("FarkButton10");
        FarkButton10.setBounds(30, 370, 90, 50);
        FarkButton10.setVisible(true);
        FarkButton11.setText("FarkButton11");
        FarkButton11.setBounds(30, 370, 90, 50);
        FarkButton11.setVisible(true);
        FarkButton12.setText("FarkButton12");
        FarkButton12.setBounds(30, 370, 90, 50);
        FarkButton12.setVisible(true);
        DeltaPButton.setText("DeltaPButton");
        DeltaPButton.setBounds(30, 470, 90, 50);
        DeltaPButton.setVisible(true);
        Fark2Button.setText("Fark2Button");
        Fark2Button.setBounds(30, 570, 90, 50);
        Fark2Button.setVisible(true);
        Fark3Button.setText("Fark3Button");
        Fark3Button.setBounds(30, 670, 90, 50);
        Fark3Button.setVisible(true);
        Fark_qualcosaButton.setText("Fark_qualcosaButton");
        Fark_qualcosaButton.setBounds(30, 770, 90, 50);
        Fark_qualcosaButton.setVisible(true);
        LavaggiButton.setText("LavaggiButton");
        LavaggiButton.setSize(90, 50);
        LavaggiButton.setVisible(true);
        RicettaButton.setText("RicettaButton");
        RicettaButton.setSize(90, 50);
        RicettaButton.setVisible(true);
        Fark4Button.setText("Fark4Button");
        Fark4Button.setSize(90, 50);
        Fark4Button.setFocusable(true);
        Fark4Button.setVisible(true);
        ///////////////////////-----|   Choose Value Part  |-----///////////////////////

        labelChooseValue.setText("Scegli il tipo di dato: ");
        labelChooseValue.setFont(new Font("MV Bolly", Font.BOLD, 20));
        labelChooseValue.setBounds(300, 5, 300, 60);
        labelChooseValue.setVisible(false);

        ///////////////////////-----|   Choose Date and Time Part  |-----///////////////////////

        labelChooseFromDate.setText("Scegli la data di inizio: ");
        labelChooseFromDate.setFont(new Font("MV Bolly", Font.BOLD, 20));
        labelChooseFromDate.setBounds(700, 5, 300, 60);
        labelChooseFromDate.setVisible(false);

        dateFromChooser = new JDateChooser();
        dateFromChooser.setVisible(false);
        dateFromChooser.setLocale(Locale.ITALY);
        dateFromChooser.setDate(date);

        timeFromPicker = new TimePicker(timeSettings);
        timeFromPicker.setVisible(false);
        timeFromPicker.addTimeChangeListener(this);

        labelChooseToDate.setText("Scegli la data di fine: ");
        labelChooseToDate.setFont(new Font("MV Bolly", Font.BOLD, 20));
        labelChooseToDate.setBounds(950, 5, 300, 60);
        labelChooseToDate.setVisible(false);

        timeToPicker = new TimePicker(timeSettings);
        timeToPicker.setVisible(false);
        timeToPicker.addTimeChangeListener(this);

        dateToChooser = new JDateChooser();
        dateToChooser.setVisible(false);
        dateToChooser.setLocale(Locale.ITALY);
        dateToChooser.setDate(date);

        ///////////////////////-----|   Buttons Part  |-----///////////////////////

        //Create Table Button settings.
        createTableButton = new JButton("Crea Tabella");
        createTableButton.setBounds(250, 400, 180, 80);
        createTableButton.setFont(new Font("MV Bolly", Font.BOLD, 20));
        createTableButton.setFocusable(false);
        createTableButton.setVisible(false);

        //Create Graph Button settings.
        createGraphButton = new JButton("Crea Grafico");
        createGraphButton.setBounds(450, 400, 180, 80);
        createGraphButton.setFont(new Font("MV Bolly", Font.BOLD, 20));
        createGraphButton.setFocusable(false);
        createGraphButton.setVisible(false);

        jLabelsPanel.setBounds(0, 0, 1466, 820);
        jLabelsPanel.setLayout(new BorderLayout());


        jButtonsPanel.setSize(100, 20);
        jButtonsPanel.setLayout(new GridLayout(40, 1, 5, 3));


        exportAsComboBox.setBounds(280, 480, 150, 50);
        exportAsComboBox.setFont(new Font("MV Bolly", Font.BOLD, 15));
        exportAsComboBox.setFocusable(false);
        exportAsComboBox.setVisible(false);

        refreshValue = new JButton("Annula");
        refreshValue.setBounds(530, 480, 100, 50);
        refreshValue.setFont(new Font("MV Bolly", Font.BOLD, 15));
        refreshValue.setFocusable(false);
        refreshValue.setVisible(false);

        rTG = new JButton("RTG");
        rTG.setBounds(450, 480, 100, 50);
        rTG.setFont(new Font("MV Bolly", Font.BOLD, 15));
        rTG.setFocusable(false);
        rTG.setVisible(false);

        textField.setBounds(130, 340, 200, 50);
        textField.setText("Рекурсивная гетерохромия");
        textField.setVisible(true);

        ///////////////////////-----|   Adding Everything  |-----///////////////////////

        PS1Button.addActionListener(this);
        PS2Button.addActionListener(this);
        PS3Button.addActionListener(this);
        PS4Button.addActionListener(this);
        ps1ComboBox.addActionListener(this);
        ps2ComboBox.addActionListener(this);
        ps3ComboBox.addActionListener(this);
        ps4ComboBox.addActionListener(this);
        createTableButton.addActionListener(this);
        createGraphButton.addActionListener(this);
        refreshValue.addActionListener(this);
        exportAsComboBox.addActionListener(this);
        rTG.addActionListener(this);
        textField.addActionListener(this);
        goOnChooseDateButton.addActionListener(this);


        jButtonsPanel.add(labelChooseMacynery);
        jButtonsPanel.add(textField);
        jButtonsPanel.add(PS1Button);
        jButtonsPanel.add(PS2Button);
        jButtonsPanel.add(PS3Button);
        jButtonsPanel.add(PS4Button);

        jButtonsPanel.add(FarkButton);
        jButtonsPanel.add(DeltaPButton);
        jButtonsPanel.add(Fark2Button);
        jButtonsPanel.add(Fark3Button);
        jButtonsPanel.add(Fark_qualcosaButton);
        jButtonsPanel.add(LavaggiButton);
        jButtonsPanel.add(RicettaButton);
        jButtonsPanel.add(Fark4Button);
        jButtonsPanel.add(FarkButton1);
        jButtonsPanel.add(FarkButton2);
        jButtonsPanel.add(FarkButton3);
        jButtonsPanel.add(FarkButton4);
        jButtonsPanel.add(FarkButton5);
        jButtonsPanel.add(FarkButton6);
        jButtonsPanel.add(FarkButton7);
        jButtonsPanel.add(FarkButton8);
        jButtonsPanel.add(FarkButton9);
        jButtonsPanel.add(FarkButton10);
        jButtonsPanel.add(FarkButton11);
        jButtonsPanel.add(FarkButton12);
        //

        //
        jLabelsPanel.add(ps1ComboBox);
        jLabelsPanel.add(ps2ComboBox);
        jLabelsPanel.add(ps3ComboBox);
        jLabelsPanel.add(ps4ComboBox);
        jLabelsPanel.add(createGraphButton);
        jLabelsPanel.add(createTableButton);
        jLabelsPanel.add(dateFromChooser);
        jLabelsPanel.add(dateToChooser);
        jLabelsPanel.add(timeFromPicker);
        jLabelsPanel.add(timeToPicker);
        jLabelsPanel.add(labelChooseValue);
        jLabelsPanel.add(labelChooseFromDate);
        jLabelsPanel.add(labelChooseToDate);
        jLabelsPanel.add(exportAsComboBox);
        jLabelsPanel.add(refreshValue);
        jLabelsPanel.add(rTG);
        jLabelsPanel.add(goOnChooseDateButton);
        jLabelsPanel.add(jButtonsPanel);
        JScrollPane jScrollPane = new JScrollPane(jButtonsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jLabelsPanel.add(jScrollPane, BorderLayout.LINE_START);

        mainFrame.setIconImage(imageIcon.getImage());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 820);
        mainFrame.add(jLabelsPanel);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void setComponentsNotVisible() {
        refreshValue.setVisible(false);
        ps1ComboBox.setVisible(false);
        ps2ComboBox.setVisible(false);
        ps3ComboBox.setVisible(false);
        ps4ComboBox.setVisible(false);
        dateFromChooser.setVisible(false);
        dateToChooser.setVisible(false);
        labelChooseValue.setVisible(false);
        createGraphButton.setVisible(false);
        createTableButton.setVisible(false);
        exportAsComboBox.setVisible(false);
        timeFromPicker.setVisible(false);
        timeToPicker.setVisible(false);
        labelChooseToDate.setVisible(false);
        labelChooseFromDate.setVisible(false);
        rTG.setVisible(false);
        goOnChooseDateButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textField) {
            search(textField.getText());
        }

        if (e.getSource() == PS1Button) {
            ps1ComboBox.setBounds(300, 60, 210, 50);
            setComponentsNotVisible();
            labelChooseValue.setVisible(true);
            ps1ComboBox.setVisible(true);
            ps2ComboBox.setVisible(true);
            ps3ComboBox.setVisible(true);
            ps4ComboBox.setVisible(true);

            sqlPs1Ver2 = " FROM [Cosma].[dbo].[Linea1] ";
            sqlPs1 = " FROM [Cosma].[dbo].[Linea1] ";

            sql_1 = " [Cosma].[dbo].[Linea1] ";
            System.out.println(sql_1);

            sql_1_time = "Linea1";

            sqlPs1Ver3 = "FROM [Cosma].[dbo].[Linea1]";
//            value14 = "Tempo PS1";
            newName = "Risultato di PS1";

//            dateFromChooser.setBounds(700, 60, 150, 50);
//            dateToChooser.setBounds(950, 60, 150, 50);
        }
        System.out.println(value1);

        if (e.getSource() == PS2Button) {
            ps2ComboBox.setBounds(300, 130, 210, 50);
            setComponentsNotVisible();
            labelChooseValue.setVisible(true);
            ps1ComboBox.setVisible(true);
            ps2ComboBox.setVisible(true);
            ps3ComboBox.setVisible(true);
            ps4ComboBox.setVisible(true);

            sqlPs1Ver2 = "FROM [Cosma].[dbo].[Linea2]";

            sqlPs1 = "FROM [Cosma].[dbo].[Linea2]";

            sql_2 = " [Cosma].[dbo].[Linea2]";
            sql_2_time = "Linea2";
            System.out.println(sql_2);
//            value14 = "Tempo PS2";

            sqlPs1Ver3 = "FROM [Cosma].[dbo].[Linea2]";
            newName = "Risultato di PS2";

//            dateFromChooser.setBounds(700, 130, 150, 50);
//            dateToChooser.setBounds(950, 130, 150, 50);
        }

        if (e.getSource() == PS3Button) {
            ps3ComboBox.setBounds(300, 200, 210, 50);
            setComponentsNotVisible();
            labelChooseValue.setVisible(true);
            ps1ComboBox.setVisible(true);
            ps2ComboBox.setVisible(true);
            ps3ComboBox.setVisible(true);
            ps4ComboBox.setVisible(true);

            sqlPs1Ver2 = "FROM [Cosma].[dbo].[PS3]";

            sqlPs1 = "FROM [Cosma].[dbo].[PS3]";
            sql_3_time = "PS3";

            sql_3 = " [Cosma].[dbo].[PS3]";
//            value14 = "Tempo PS3";
            System.out.println(sql_3);

            sqlPs1Ver3 = "FROM [Cosma].[dbo].[PS3]";
            newName = "Risultato di PS3";

//            dateFromChooser.setBounds(700, 200, 150, 50);
//            dateToChooser.setBounds(950, 200, 150, 50);
        }

        if (e.getSource() == PS4Button) {
            ps4ComboBox.setBounds(300, 270, 210, 50);
            setComponentsNotVisible();
            labelChooseValue.setVisible(true);
            ps1ComboBox.setVisible(true);
            ps2ComboBox.setVisible(true);
            ps3ComboBox.setVisible(true);
            ps4ComboBox.setVisible(true);

            sqlPs1Ver2 = "FROM [Cosma].[dbo].[PS4]";

            sqlPs1 = "FROM [Cosma].[dbo].[PS4]";
            sql_4_time = "PS4";


            sql_4 = " [Cosma].[dbo].[PS4]";
//            value14 = "Tempo PS4";
            System.out.println(sql_4);

            sqlPs1Ver3 = "FROM [Cosma].[dbo].[PS4]";
            newName = "Risultato di PS4";


//            dateFromChooser.setBounds(700, 270, 150, 50);
//            dateToChooser.setBounds(950, 270, 150, 50);
        }

        if (e.getSource() == ps1ComboBox) {
            switch (Objects.requireNonNull(ps1ComboBox.getSelectedItem()).toString()) {
                case "Portata Bactofuga":
                    sql_1_selected = " [Portata Bactofuga],([Linea1].[TimeCol]) AS 'Tempo PS1' ";

                    value1 = "Portata Bactofuga";

                    break;

                case "Portata Centrifuga 1":
                    sql_2_selected = "[Portata Centrifuga 1],([Linea1].[TimeCol]) AS 'Tempo PS1' ";
                    value2 = "Portata Centrifuga 1";
                    break;

                case "Portata Centrifuga 2":
                    sql_3_selected = "[Portata Centrifuga 2],([Linea1].[TimeCol]) AS 'Tempo PS1' ";
                    value3 = "Portata Centrifuga 2";
                    break;
            }
            rTG.setVisible(true);
//            timeFromPicker.setBounds(700,160,150,50);
//            timeFromPicker.setVisible(true);
//
//            timeToPicker.setBounds(950,160,150,50);
//            timeToPicker.setVisible(true);

            goOnChooseDateButton.setVisible(true);

            createGraphButton.setVisible(false);
            createTableButton.setVisible(false);
            exportAsComboBox.setVisible(false);
            refreshValue.setVisible(false);
        }
        System.out.println(value1 + " new VAlue1");
        if (e.getSource() == ps2ComboBox) {
            switch (Objects.requireNonNull(ps2ComboBox.getSelectedItem()).toString()) {
                case "Temp Pastorizzazione PS2":
                    sql_4_selected = "[Temp Pastorizzazione PS2], ([Linea2].[TimeCol]) AS 'Tempo PS2'";
                    value4 = "Temp Pastorizzazione PS2";
                    break;

                case "Temp Uscita PS2":
                    sql_5_selected = "[Temp Uscita PS2], ([Linea2].[TimeCol]) AS 'Tempo PS2'";
                    value5 = "Temp Uscita PS2";
                    break;
                case "Temp Acqua Calda":
                    sql_6_selected = "[Temp Acqua Calda]";
                    value6 = "Temp Acqua Calda PS2";
                    break;
            }
            rTG.setVisible(true);

//            timeFromPicker.setBounds(700,230,150,50);
//            timeFromPicker.setVisible(true);
//
//            timeToPicker.setBounds(950,230,150,50);
//            timeToPicker.setVisible(true);

            goOnChooseDateButton.setVisible(true);

            createGraphButton.setVisible(false);
            createTableButton.setVisible(false);
            exportAsComboBox.setVisible(false);
            refreshValue.setVisible(false);
        }

        if (e.getSource() == ps3ComboBox) {
            switch (Objects.requireNonNull(ps3ComboBox.getSelectedItem()).toString()) {
                case "Temp Pastorizzazzione PS3":
                    sql_7_selected = "[Temp Pastorizzazzione PS3], ([PS3].[TimeCol]) AS 'Tempo PS3'";
                    value7 = "Temp Pastorizzazzione PS3";
                    break;

                case "Temp Uscita PS3":
                    sql_8_selected = "[Temp Uscita PS3], ([PS3].[TimeCol]) AS 'Tempo PS3'";
                    value8 = "Temp Uscita PS3";
                    break;

                case "Temp Acqua Calda":
                    sql_9_selected = "([PS3].[Temp Acqua Calda]) AS 'Temperatura Acqua Calda PS3', ([PS3].[TimeCol]) AS 'Tempo PS3'";
                    value9 = "Temp Acqua Calda PS3";
                    break;

                case "Portata":
                    sql_10_selected = "[Portata], ([PS3].[TimeCol]) AS 'Tempo PS3'";
                    value10 = "Portata";
                    break;
            }
            rTG.setVisible(true);

//            timeFromPicker.setBounds(700,300,150,50);
//            timeFromPicker.setVisible(true);
//
//            timeToPicker.setBounds(950,300,150,50);
//            timeToPicker.setVisible(true);
            goOnChooseDateButton.setVisible(true);

            dateFromChooser.addPropertyChangeListener(this);
            dateToChooser.addPropertyChangeListener(this);
            createGraphButton.setVisible(false);
            createTableButton.setVisible(false);
            exportAsComboBox.setVisible(false);
            refreshValue.setVisible(false);
        }

        if (e.getSource() == ps4ComboBox) {
            switch (Objects.requireNonNull(ps4ComboBox.getSelectedItem()).toString()) {
                case "Temp Pastorizzazione PS4":
                    sql_11_selected = "[Temp Pastorizzazione PS4], ([PS4].[TimeCol]) AS 'Tempo PS4'";
                    value11 = "Temp Pastorizzazione PS4";
                    break;
                case "Temp Uscita PS4":
                    sql_12_selected = "[Temp Uscita PS4], ([PS4].[TimeCol]) AS 'Tempo PS4'";
                    value12 = "Temp Uscita PS4";
                    break;

                case "Temp Acqua Calda":
                    sql_13_selected = "([PS4].[Temp Acqua Calda]) AS 'Temperatura Acqua Calda PS4', ([PS4].[TimeCol]) AS 'Tempo PS4'";
                    value13 = "Temp Acqua Calda PS4";
                    break;
            }
            rTG.setVisible(true);

//            timeFromPicker.setBounds(700,370,150,50);
//            timeFromPicker.setVisible(true);
//
//            timeToPicker.setBounds(950,370,150,50);
//            timeToPicker.setVisible(true);
            goOnChooseDateButton.setVisible(true);

            createGraphButton.setVisible(false);
            createTableButton.setVisible(false);
            exportAsComboBox.setVisible(false);
            refreshValue.setVisible(false);
        }
        if (e.getSource() == rTG) {
            EventQueue.invokeLater(() -> {
                DTSCTest demo = new DTSCTest(value4);
                demo.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                demo.pack();
                demo.setLocationRelativeTo(null);
                demo.setVisible(true);
                demo.start();
            });
        }
//Select [Portata Centrifuga 1], [Linea1].[TimeCol], [Temp Uscita PS2], [Linea2].[TimeCol], [Temp Uscita PS3], [PS3].[TimeCol], [PS4].[Temp Acqua Calda], [PS3].[Temp Acqua Calda],[Linea2].[Temp Acqua Calda], [PS4].[TimeCol] From [Cosma].[dbo].[Linea1] ,  [Cosma].[dbo].[Linea2],  [Cosma].[dbo].[PS3],  [Cosma].[dbo].[PS4]
        if (e.getSource() == goOnChooseDateButton) {
            System.out.println("Hello world");
            int answer = JOptionPane.showConfirmDialog(null, "Hai scelto sequento valori: -----. Continuare?", "Messaggio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == 0) {
                setComponentsNotVisible();
                System.out.println(answer);
                dateFromChooser.addPropertyChangeListener(this);
                dateToChooser.addPropertyChangeListener(this);

                goOnChooseDateButton.setVisible(false);
                timeFromPicker.setBounds(700, 230, 150, 50);
                timeFromPicker.setVisible(true);

                dateFromChooser.setVisible(true);
                dateFromChooser.setBounds(700, 130, 150, 50);

                dateToChooser.setVisible(true);
                dateToChooser.setBounds(950, 130, 150, 50);

                timeToPicker.setBounds(950, 230, 150, 50);
                timeToPicker.setVisible(true);
            }
        }

        if (e.getSource() == refreshValue) {
            addOneMoreGraphQ = 10;
            value4 = null;
            value7 = "null";
            value5 = null;
            timeFromPicker.setTimeToNow();
            timeToPicker.setTimeToNow();
            dateFromChooser.setDate(date);
            dateToChooser.setDate(date);
            sqlPs2Ver2 = null;
            sqlPS3Ver2 = null;
            sqlPS4Ver2 = null;
            sqlPs1 = null;
            sqlPs2 = null;
            sqlPS3 = null;
            sqlPS4 = null;
            i = 0;
            setComponentsNotVisible();
            textField.setText("Рекурсивная гетерохромия");
            System.out.println("Anulated");
        }

        if (e.getSource() == createGraphButton) {
//        addOneMoreGraphQ = JOptionPane.showConfirmDialog(null,"Aggiungere il valore al grafico esistente","Messaggio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        System.out.println(addOneMoreGraphQ);
//        if (addOneMoreGraphQ == 0) {
//            i++;
//            if (i/2 != 1) {
//                JOptionPane.showMessageDialog(null, "Scegli valori per la seconda linea", "Mesaggio", JOptionPane.PLAIN_MESSAGE);
//                drawLine.drawLineFrame = null;
//                timeFromPicker.setTimeToNow();
//                timeToPicker.setTimeToNow();
//                dateFromChooser.setDate(date);
//                dateToChooser.setDate(date);
//            }
//            setComponentsNotVisible();
//            timeFromPicker.setTimeToNow();
//            timeToPicker.setTimeToNow();
//            dateFromChooser.setDate(date);
//            dateToChooser.setDate(date);
//
//            if (i/2 == 1){
//                drawLine.createChart(sqlPS4,sqlPS4Ver2,value4,value5,value7);
//                addOneMoreGraphQ = 10;
//                timeFromPicker.setTimeToNow();
//                timeToPicker.setTimeToNow();
//                i = 1;
//            }
//        }else{
            drawLine.createChart(sqlPs1, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, time_1, time_2);
            System.out.println(sqlPs1 + " SDSDSDD");
//        }

            setComponentsNotVisible();
            createTableButton.setVisible(true);
            createGraphButton.setVisible(true);
            refreshValue.setVisible(true);
            rTG.setVisible(true);
            exportAsComboBox.setVisible(true);
            drawLine.drawLineFrame.setVisible(true);
        }

        if (e.getSource() == createTableButton) {
            try (Connection connection = DriverManager.getConnection(url, user, password)) {                             //DriverManager tries to connect to Data Base using data inputted by user.
                System.out.println("Connected successfully");                                                            //If is connected prints this to console.
                try (Statement statement = connection.createStatement()) {                                               //When connected creates SQL request.
                    String sql = sqlPs1;

                    try (ResultSet result = statement.executeQuery(sql)) {
                        ArrayList<String> names = new ArrayList<>();
                        if (value14 != null) {
                            names.add(value14);
                        }
                        if (value1 != null) {
                            names.add(value1);
                        }
                        if (value2 != null) {
                            names.add(value2);
                        }
                        if (value3 != null) {
                            names.add(value3);
                        }
                        if (value4 != null) {
                            names.add(value4);
                        }
                        if (value5 != null) {
                            names.add(value5);
                        }
                        if (value6 != null) {
                            names.add(value6);
                        }
                        if (value7 != null) {
                            names.add(value7);
                        }
                        if (value8 != null) {
                            names.add(value8);
                        }
                        if (value9 != null) {
                            names.add(value9);
                        }
                        if (value10 != null) {
                            names.add(value10);
                        }
                        if (value11 != null) {
                            names.add(value11);
                        }
                        if (value12 != null) {
                            names.add(value12);
                        }
                        if (value13 != null) {
                            names.add(value13);
                        }

//Select  CONVERT([varchar](13), a.TimeCol, 120) AS tempo1,
//	a.[Portata Bactofuga],
//	a.[Portata Centrifuga 1],
//	a.[Portata Centrifuga 2],
//	b.[Temp Pastorizzazione PS2],
//	b.[Temp Uscita PS2],
//	b.[Temp Acqua Calda],
//	c.[Temp Pastorizzazzione PS3],
//	c.[Temp Uscita PS3],
//	c.[Temp Acqua Calda] ,
//	c.[Portata],
//	d.[Temp Pastorizzazione PS4],
//	d.[Temp Uscita PS4],
//	d.[Temp Acqua Calda]
//From
//	[Cosma].[dbo].[Linea1] AS a
//	Left JOIN [COsma].[dbo].[linea2] AS b
//	On DATEADD(MINUTE, DATEDIFF(MINUTE, 0,  a.TimeCol), 0) = DATEADD(MINUTE, DATEDIFF(MINUTE, 0,  b.TimeCol), 0)
//	left join [COsma].[dbo].[PS3] as c
//	On DATEADD(MINUTE, DATEDIFF(MINUTE, 0,  a.TimeCol), 0) = DATEADD(MINUTE, DATEDIFF(MINUTE, 0,  c.TimeCol), 0)
//	left join [COsma].[dbo].[PS4] as d
//	On DATEADD(MINUTE, DATEDIFF(MINUTE, 0,  a.TimeCol), 0) = DATEADD(MINUTE, DATEDIFF(MINUTE, 0,  d.TimeCol), 0)
//
                        jTableFrame = new JTableFrame(newName, names);
                        try (ResultSet result_1 = statement.executeQuery(sqlPs1)) {
                            while (result_1.next()) {
                                if (value14 != null) {
                                    value14_1 = result_1.getString(value14);
                                    values.add(value14_1);
                                }
                                if (value1 != null) {
                                    value1_1 = result_1.getString(value1) ;
                                    values.add(value1_1);
                                }
                                if (value2 != null) {
                                    value2_1 = result_1.getString(value2);
                                    values.add(value2_1);
                                }
                                if (value3 != null) {
                                    value3_1 = result_1.getString(value3) ;
                                    values.add(value3_1);
                                }

                                if (value4 != null) {
                                    value4_1 = result_1.getString(value4) ;
                                    values.add(value4_1);
                                }
                                if (value5 != null) {
                                    value5_1 = result_1.getString(value5) ;
                                    values.add(value5_1);
                                }
                                if (value6 != null) {
                                    value6_1 = result_1.getString(value6) ;
                                    values.add(value6_1);
                                }
                                if (value7 != null) {
                                    value7_1 = result_1.getString(value7);
                                    values.add(value7_1);
                                }
                                if (value8 != null) {
                                    value8_1 = result_1.getString(value8) ;
                                    values.add(value8_1);
                                }
                                if (value9 != null) {
                                    value9_1 = result_1.getString(value9) ;
                                    values.add(value9_1);
                                }
                                if (value10 != null) {
                                    value10_1 = result_1.getString(value10) ;
                                    values.add(value10_1);
                                }
                                if (value11 != null) {
                                    value11_1 = result_1.getString(value11) ;
                                    values.add(value11_1);
                                }
                                if (value12 != null) {
                                    value12_1 = result_1.getString(value12) ;
                                    values.add(value12_1);
                                }
                                if (value13 != null) {
                                    value13_1 = result_1.getString(value13) ;
                                    values.add(value13_1);
                                }
                                jTableFrame.addRowToTable(values);
                                jTableFrame.table.setVisible(true);
                                values.clear();

                            }
                        }
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }




            setComponentsNotVisible();
            createGraphButton.setVisible(true);
            createTableButton.setVisible(true);
            refreshValue.setVisible(true);
            rTG.setVisible(true);
            exportAsComboBox.setVisible(true);
        } catch(
                SQLException throwables){
            JOptionPane.showMessageDialog(null, "Qualcosa e' andata male", "Errore", JOptionPane.ERROR_MESSAGE);
            throwables.printStackTrace();
        }
    }





                    //SELECT TOP 1000 ([TimeCol]) AS 'Tempo PS1',[Portata bactofuga],[Portata Centrifuga 1],[Portata Centrifuga 2] FROM [Cosma].[dbo].[Linea1]
//Union
//SELECT TOP 1000 ([TimeCol]) AS 'Tempo PS2',[Temp Pastorizzazione PS2],[Temp Uscita PS2],([Temp Acqua Calda])  AS 'Temperatura Acqua Calda PS2' FROM [Cosma].[dbo].[Linea2]
//Union
//SELECT TOP 1000 ([TimeCol]) AS 'Tempo PS3',[Temp Pastorizzazzione PS3],[Temp Uscita PS3],([Temp Acqua Calda]) AS 'Temperatura Acqua Calda PS3' FROM [Cosma].[dbo].[PS3]
//Union
//SELECT TOP 1000 ([TimeCol]) AS 'Tempo PS4',[Temp Pastorizzazione PS4],[Temp Uscita PS4],([Temp Acqua Calda])  AS 'Temperatura Acqua Calda PS4'  FROM [Cosma].[dbo].[PS4]
        //Here The Magic Starts :)
        if (e.getSource()==exportAsComboBox){
            if (Objects.requireNonNull(exportAsComboBox.getSelectedItem()).toString().equalsIgnoreCase("Esporta come Excel")){
            JFileChooser excelFileChooser = new JFileChooser("C:/");
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("File di tipo EXCEL ","xls","xlsx","xlsm");
            excelFileChooser.setFileFilter(fileNameExtensionFilter);
            int excelChooser = excelFileChooser.showSaveDialog(null);
            int u = 0;

            if(excelChooser == JFileChooser.APPROVE_OPTION) {
                FileOutputStream excelFOU = null;
                BufferedOutputStream excelBOU;
                XSSFWorkbook excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("value4");

                for (int i = 0; i < jTableFrame.model.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < jTableFrame.model.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(jTableFrame.model.getValueAt(i, j).toString());
                    }
                }
                try {
                    excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                } catch (FileNotFoundException fileNotFoundException) {
                    JOptionPane.showMessageDialog(null, "Qualcosa e' andata male", "Errore", JOptionPane.ERROR_MESSAGE);
                    fileNotFoundException.printStackTrace();
                }

                excelBOU = new BufferedOutputStream(Objects.requireNonNull(excelFOU));
                try {
                    excelJTableExporter.write(excelBOU);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Qualcosa e' andata male", "Errore", JOptionPane.ERROR_MESSAGE);
                    ioException.printStackTrace();
                }

                try {
                    excelBOU.close();
                    excelFOU.close();
                    excelJTableExporter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    u++;
                }
                if (u == 0) {
                    JOptionPane.showMessageDialog(null, "Esportazione e' ruiuscita senza problemi.", "Messaggio", JOptionPane.PLAIN_MESSAGE);
                }else{
                    u = 0;
                    JOptionPane.showMessageDialog(null,"Qualcosa e' andato male","Messaggio",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }


            if (Objects.requireNonNull(exportAsComboBox.getSelectedItem()).toString().equalsIgnoreCase("Esporta come PDF")){
                JFileChooser pdfChooser = new JFileChooser("C:/");
                FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("File di tipo PDF",".pdf",".ps",".eps",".html",".htm");
                pdfChooser.setFileFilter(fileNameExtensionFilter);

                int columnsCount = jTableFrame.table.getColumnCount();
                int u = 0;

                PdfPTable pdfPTable = new PdfPTable(columnsCount);
                pdfChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int userAnsver = pdfChooser.showSaveDialog(null);
                if (userAnsver == JFileChooser.APPROVE_OPTION) {
                    Document document = new Document();
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream(pdfChooser.getSelectedFile() + ".pdf"));
                        document.open();
                        pdfPTable.addCell(value4);
                        pdfPTable.addCell("Tempo");
                        for (int i = 0; i < jTableFrame.model.getRowCount(); i++) {
                            for (int j = 0; j < jTableFrame.model.getColumnCount(); j++) {
                                String pdfValue = jTableFrame.model.getValueAt(i, j).toString();
                                pdfPTable.addCell(pdfValue);
                            }
                        }
                        document.add(pdfPTable);
                        document.close();
                    } catch (DocumentException | FileNotFoundException documentException) {
                        documentException.printStackTrace();
                        u++;
                    }
                    if (u == 0) {
                        JOptionPane.showMessageDialog(null, "Esportazione e' ruiuscita senza problemi.", "Messaggio", JOptionPane.PLAIN_MESSAGE);
                    }else{
                        u = 0;
                        JOptionPane.showMessageDialog(null,"Qualcosa e' andato male","Messaggio",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }


            if (Objects.requireNonNull(exportAsComboBox.getSelectedItem()).toString().equalsIgnoreCase("Esporta come Testo")){
                int u = 0;
                    JFileChooser textFileChooser = new JFileChooser("C:/");

                    FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("File di Testo","txt");
                    textFileChooser.setFileFilter(fileNameExtensionFilter);
                    textFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    int textChooser = textFileChooser.showSaveDialog(null);

                    if(textChooser == JFileChooser.APPROVE_OPTION) {
                    FileWriter fw;
                    BufferedWriter bw;
                    try {
                        fw = new FileWriter(textFileChooser.getSelectedFile() + ".txt");
                        bw = new BufferedWriter(fw);
                        for(int i = 0; i < jTableFrame.model.getRowCount(); i++){
                            for(int j = 0; j < jTableFrame.model.getColumnCount(); j++){
                                bw.write(jTableFrame.model.getValueAt(i, j).toString()+" ");
                            }
                            bw.newLine();
                        }
                        bw.close();
                        fw.close();
                    } catch (IOException ex) {
                       u++;
                    }
                    if (u == 0) {
                        JOptionPane.showMessageDialog(null, "Esportazione e' ruiuscita senza problemi.", "Messaggio", JOptionPane.PLAIN_MESSAGE);
                    }else{
                        u = 0;
                        JOptionPane.showMessageDialog(null,"Qualcosa e' andato male","Messaggio",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    //here the magic ends =*

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource()==dateFromChooser){
            Date dateFrom = dateFromChooser.getDate();
            if (addOneMoreGraphQ == 0){
                date_1Ver2 = simpleDateFormat.format(dateFrom);
            }else{
                date_1 = simpleDateFormat.format(dateFrom);
            }
        }
        if (evt.getSource()==dateToChooser){
            Date dateTo = dateToChooser.getDate();
            if (addOneMoreGraphQ == 0) {
                date_2Ver2 = simpleDateFormat.format(dateTo);
            }else{
                date_2 = simpleDateFormat.format(dateTo);
            }
        }
    }

    @Override
    public void timeChanged(TimeChangeEvent timeChangeEvent) {  //SELECT [Temp Uscita PS4],[TimeCol]  FROM [Cosma].[dbo].[PS4] Where TimeCol >= CAST(CAST('2019-09-12 00:00:00' AS DATETIME) AS TIMESTAMP) AND TimeCol <= CAST(CAST('2019-18-12 13:00:00' AS DATETIME) AS TIMESTAMP)
        if (timeChangeEvent.getSource()==timeFromPicker){
                time_1 = timeFromPicker.getTime().toString();
                time_1 = date_1 + " " + timeFromPicker.getTime().toString();


        }

        if (timeChangeEvent.getSource()==timeToPicker){
                time_2 = timeToPicker.getTime().toString();
                time_2 = date_2 + " " + timeToPicker.getTime().toString();
                sqlPS4 = sqlPs2 + sqlPS3 + "["+sql_1_time+"].TimeCol <= CAST(CAST('" + time_2 + "' AS DATETIME) AS TIMESTAMP)";

            sqlPs1 ="select "+
                    "        [Portata Bactofuga],\n" +
                    "        [Portata Centrifuga 1],\n" +
                    "        [Portata Centrifuga 2],\n" +
                    "        [Temp Pastorizzazione PS2],\n" +
                    "        [Temp Uscita PS2],\n" +
                    "        [Temp Acqua Calda PS2],\n" +
                    "        [Temp Pastorizzazzione PS3],\n" +
                    "        [Temp Uscita PS3],\n" +
                    "        [Temp Acqua Calda PS3],\n" +
                    "        [Portata],\n" +
                    "        [Temp Uscita PS4],\n" +
                    "        [Temp Pastorizzazione PS4],\n" +
                    "        [Temp Acqua Calda PS4],\n" +
                            "tempoPS1\n" +
                    "from\n" +
                    "    (SELECT AVG([Temp Pastorizzazione PS2]) as [Temp Pastorizzazione PS2],\n" +
                    "            AVG([Temp Uscita PS2]) as [Temp Uscita PS2],\n" +
                    "            AVG([Temp Acqua Calda PS2]) as [Temp Acqua Calda PS2], \n" +
                    "            CONVERT(Char(16), TimeCol,20) AS tempoPS2\n" +
                    "        from [Cosma].[dbo].[Linea2]\n" +
                    "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
                    "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                    "        group by CONVERT(Char(16), TimeCol,20)\n" +
                    "    ) AS B,\n" +
                    "    (SELECT AVG([Temp Uscita PS3]) as [Temp Uscita PS3],\n" +
                    "            AVG([Temp Pastorizzazzione PS3]) as [Temp Pastorizzazzione PS3],\n" +
                    "            AVG([Temp Acqua Calda PS3]) as [Temp Acqua Calda PS3],\n" +
                    "            AVG([Portata]) as [Portata], \n" +
                    "            CONVERT(Char(16), TimeCol,20) AS tempoPS3\n" +
                    "        from [Cosma].[dbo].[PS3]\n" +
                    "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
                    "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                    "        group by CONVERT(Char(16), TimeCol,20)\n" +
                    "    ) AS C,\n" +
                    "    (SELECT AVG([Temp Uscita PS4]) as [Temp Uscita PS4],\n" +
                    "            AVG([Temp Pastorizzazione PS4]) as [Temp Pastorizzazione PS4],\n" +
                    "            AVG([Temp Acqua Calda PS4]) as [Temp Acqua Calda PS4],\n" +
                    "            CONVERT(Char(16), TimeCol,20) AS tempoPS4\n" +
                    "        from [Cosma].[dbo].[PS4]\n" +
                    "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
                    "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                    "        group by CONVERT(Char(16), TimeCol,20)\n" +
                    "    ) AS D,\n" +
                    "    (SELECT AVG([Portata Bactofuga]) as [Portata Bactofuga],\n" +
                    "            AVG([Portata Centrifuga 1]) as [Portata Centrifuga 1], \n" +
                    "            AVG([Portata Centrifuga 2]) as [Portata Centrifuga 2], \n" +
                    "            CONVERT(Char(16), TimeCol,20) AS tempoPS1\n" +
                    "        from [Cosma].[dbo].[Linea1]\n" +
                    "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
                    "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                    "        group by CONVERT(Char(16), TimeCol,20)\n" +
                    "    ) AS A\n" +
                    "Where      " +
                    "           tempoPS1 = tempoPS3 AND\n" +
                    "           tempoPS1 = tempoPS4 AND\n" +
                    "           tempoPS1 = tempoPS2\n";
            System.out.println(sqlPs1 + " B4");
//            if(value4 == null &&  value5 == null && value6 == null && value7 == null &&  value8 == null && value9 == null && value10 == null &&  value11 == null && value12 == null && value13 == null) {
//                sqlPs1 = sqlPs1.replace(                    "Where     tempoPS1 = tempoPS2 AND\n" +
//                        "          tempoPS1 = tempoPS3 AND\n" +
//                        "          tempoPS1 = tempoPS4 AND\n" +
//                        "          tempoPS1 = tempoPS1","");
//            }
//            if(value4 == null &&  value5 == null && value6 == null){
//            sqlPs1 = sqlPs1.replace(                    "    (SELECT AVG([" + value4 + "]) as [Temp Pastorizzazione PS2],\n" +
//                    "            AVG([" + value5 + "]) as [Temp Uscita PS2],\n" +
//                    "            AVG([" + value6 + "]) as [Temp Acqua Calda PS2], \n" +
//                    "            CONVERT(Char(16), TimeCol,20) AS tempoPS2\n" +
//                    "        from [Cosma].[dbo].[Linea2]\n" +
//                    "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
//                    "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
//                    "        group by CONVERT(Char(16), TimeCol,20)\n" +
//                    "    ) AS B,\n","");
//                sqlPs1 = sqlPs1.replace("tempoPS1 = tempoPS2 AND","");
//            }

//            if(value7 == null &&  value8 == null && value9 == null && value10 == null) {
//                sqlPs1 = sqlPs1.replace(                    "    (SELECT AVG([" + value8 + "]) as [Temp Uscita PS3],\n" +
//                        "            AVG([" + value7 + "]) as [Temp Pastorizzazzione PS3],\n" +
//                        "            AVG([" + value9 + "]) as [Temp Acqua Calda PS3],\n" +
//                        "            AVG([" + value10 + "]) as [Portata], \n" +
//                        "            CONVERT(Char(16), TimeCol,20) AS tempoPS3\n" +
//                        "        from [Cosma].[dbo].[PS3]\n" +
//                        "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
//                        "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
//                        "        group by CONVERT(Char(16), TimeCol,20)\n" +
//                        "    ) AS C,\n", "");
//                sqlPs1 = sqlPs1.replace("tempoPS1 = tempoPS3 AND","");
//            }
//            if(value11 == null &&  value12 == null && value13 == null) {
//                sqlPs1 = sqlPs1.replace(                     "    (SELECT AVG([" + value12 + "]) as [Temp Uscita PS4],\n" +
//                        "            AVG([" + value11 + "]) as [Temp Pastorizzazione PS4],\n" +
//                        "            AVG([" + value13 + "]) as [Temp Acqua Calda PS4],\n" +
//                        "            CONVERT(Char(16), TimeCol,20) AS tempoPS4\n" +
//                        "        from [Cosma].[dbo].[PS4]\n" +
//                        "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '" + time_1 + "',20)AND\n" +
//                        "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
//                        "        group by CONVERT(Char(16), TimeCol,20)\n" +
//                        "    ) AS D,\n", "");
//                sqlPs1 = sqlPs1.replace("tempoPS1 = tempoPS4 AND","");
//            }
//
//            sqlPs1 = sqlPs1.replace("[null],","");
//            sqlPs1 = sqlPs1.replace("[null]","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Portata Bactofuga],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Portata Centrifuga 1],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Portata Centrifuga 2],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Uscita PS2],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Pastorizzazione PS2],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Uscita PS3],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Acqua Calda PS2],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Pastorizzazzione PS3],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Acqua Calda PS3],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Portata],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Uscita PS4],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Pastorizzazione PS4],","");
//            sqlPs1 = sqlPs1.replace("AVG() as [Temp Acqua Calda PS4],","");
//            sqlPs1 = sqlPs1.replace("Null,", "");
//            System.out.println(sqlPs1 + " A4");
            refreshValue.setVisible(true);
            createTableButton.setVisible(true);
            createGraphButton.setVisible(true);
        }
    }

    public void setButtonsNotVisible(){ //Sets invisible only buttons below this method
        PS1Button.setVisible(false);
        PS2Button.setVisible(false);
        PS3Button.setVisible(false);
        PS4Button.setVisible(false);
        DeltaPButton.setVisible(false);
        LavaggiButton.setVisible(false);
        RicettaButton.setVisible(false);
        FarkButton.setVisible(false);
        Fark_qualcosaButton.setVisible(false);
        Fark2Button.setVisible(false);
        Fark3Button.setVisible(false);
        Fark4Button.setVisible(false);
    }

    public void search(String key){
        key = key.toLowerCase();
        do {
        if (textField.getText().equals("Рекурсивная гетерохромия")){
            textField.setText("");
        break;
        }

        if (key.startsWith("ps1") || key.startsWith("1ps") || key.startsWith("ps 1") || key.startsWith("1 ps")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            PS1Button.setVisible(true);
        break;
        }

        if (key.startsWith("ps2") || key.startsWith("2ps") || key.startsWith("ps 2") || key.startsWith("2 ps")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            PS2Button.setVisible(true);
        break;
        }

        if (key.startsWith("ps3") || key.startsWith("3ps") || key.startsWith("ps 3") || key.startsWith("3 ps")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            PS3Button.setVisible(true);
        break;
        }

        if (key.startsWith("ps4") || key.startsWith("4ps") || key.startsWith("ps 4") || key.startsWith("4 ps")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            PS4Button.setVisible(true);
        break;
        }

        if (key.startsWith("ps") || key.startsWith("p")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            PS1Button.setVisible(true);
            PS2Button.setVisible(true);
            PS3Button.setVisible(true);
            PS4Button.setVisible(true);
        break;
        }

        if (key.startsWith("fark2") || key.startsWith("2fark") || key.startsWith("fark 2") || key.startsWith("2 fark")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            Fark2Button.setVisible(true);
        break;
        }

        if (key.startsWith("fark3") || key.startsWith("3fark") || key.startsWith("fark 3") || key.startsWith("3 fark")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            Fark3Button.setVisible(true);
        break;
        }

        if (key.startsWith("fark4") || key.startsWith("4fark") || key.startsWith("fark 4") || key.startsWith("4 fark")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            Fark4Button.setVisible(true);
        break;
        }

        if (key.startsWith("f") || key.startsWith("a") || key.startsWith("r") || key.startsWith("k") || key.startsWith("fark")){
            System.out.println(key + " Starts with ps");
            setButtonsNotVisible();
            FarkButton.setVisible(true);
            Fark_qualcosaButton.setVisible(true);
            Fark2Button.setVisible(true);
            Fark3Button.setVisible(true);
            Fark4Button.setVisible(true);
        break;
        }

            if (key.endsWith("1")){
            System.out.println(key + " Ends with ps");
            setButtonsNotVisible();
            PS1Button.setVisible(true);
            Fark_qualcosaButton.setVisible(true);
        break;
        }

        if (key.endsWith("2")){
            System.out.println(key + " Ends with ps");
            setButtonsNotVisible();
            PS2Button.setVisible(true);
            Fark2Button.setVisible(true);
        break;
        }

        if (key.endsWith("3")){
            System.out.println(key + " Ends with ps");
            setButtonsNotVisible();
            PS3Button.setVisible(true);
            Fark3Button.setVisible(true);
        break;
        }

        if (key.endsWith("4")){
            System.out.println(key + " Ends with ps");
            setButtonsNotVisible();
            PS4Button.setVisible(true);
            Fark4Button.setVisible(true);
        break;
        }

        if (key.startsWith(" ")){
            PS1Button.setVisible(true);
            PS2Button.setVisible(true);
            PS3Button.setVisible(true);
            PS4Button.setVisible(true);

            DeltaPButton.setVisible(true);
            LavaggiButton.setVisible(true);
            RicettaButton.setVisible(true);
            FarkButton.setVisible(true);

            Fark_qualcosaButton.setVisible(true);
            Fark2Button.setVisible(true);
            Fark3Button.setVisible(true);
            Fark4Button.setVisible(true);
        break;
        }

        if (key.contains("fark")) {
            System.out.println(key + " contains ps");
            setButtonsNotVisible();
            FarkButton.setVisible(true);
            Fark_qualcosaButton.setVisible(true);
            Fark2Button.setVisible(true);
            Fark3Button.setVisible(true);
            Fark4Button.setVisible(true);
        break;
        }

        if (key.contains("ps")) {
            System.out.println(key + " contains ps");

            System.out.println(key.replace("ps",""));
            setButtonsNotVisible();
            PS1Button.setVisible(true);
            PS2Button.setVisible(true);
            PS3Button.setVisible(true);
            PS4Button.setVisible(true);
        break;
        }else{
            System.out.println(key + " was not found");
            setButtonsNotVisible();
        break;
        }
        }while(true);
    }
}
