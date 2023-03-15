package Prototype;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DrawLine extends JPanel {
    JFrame drawLineFrame;
    final ImageIcon imageIcon = new ImageIcon("C:\\robochiy stol\\Logo-.jpg");
    boolean CHART_IS_VISIBLE = false;
    public XYDataset createDataset(String sql, String value1,String value2,String value3,String value4, String value5,String value6,String value7,String value8,String value9,String value10,String value11,String value12,String value13,String value14, String time_1, String time_2){
        MainFrame mainFrame = new MainFrame();


        ////////////////yyyy-mm-dd hh:mm
        Timestamp TimeValue1;
        Timestamp TimeValue2;
        Timestamp TimeValue3;
        Timestamp TimeValue4;
        Timestamp TimeValue5;
        Timestamp TimeValue6;
        Timestamp TimeValue7;
        Timestamp TimeValue8;
        Timestamp TimeValue9;
        Timestamp TimeValue10;
        Timestamp TimeValue11;
        Timestamp TimeValue12;
        Timestamp TimeValue13;
//        String value1 = mainFrame.value1;
        double value1_1 = 0;
        double value2_1 = 0;
        double value3_1 = 0;
        double value4_1 = 0;
        double value5_1 = 0;
        double value6_1 = 0;
        double value7_1 = 0;
        double value8_1 = 0;
        double value9_1 = 0;
        double value10_1 = 0;
        double value11_1 = 0;
        double value12_1 = 0;
        double value13_1 = 0;
        String value14_1;
        String value15_1;
        String value16_1;

        ////////////////

        TimeSeriesCollection dataset = new TimeSeriesCollection();

            LogginFrame logginFrame = new LogginFrame(); // Is needed to have these 3 variables from MainFrame class

            TimeSeries series1 = new TimeSeries("Portata Bactofuga");
            TimeSeries series2 = new TimeSeries("Portata Centrifuga 1");
            TimeSeries series3 = new TimeSeries("Portata Centrifuga 2");
            TimeSeries series4 = new TimeSeries("Tempo Pastorizzazione PS2");
            TimeSeries series5 = new TimeSeries("Tempo Uscita PS2");
            TimeSeries series6 = new TimeSeries("Temperatura Acqua Calda PS2");
            TimeSeries series7 = new TimeSeries("Tempo Pastorizzazione PS2");
            TimeSeries series8 = new TimeSeries("Temperatura Uscita PS3");
            TimeSeries series9 = new TimeSeries("Temperatura Acqua Calda PS3");
            TimeSeries series10 = new TimeSeries("Portata");
            TimeSeries series11 = new TimeSeries("Tempo Pastorizzazione PS4");
            TimeSeries series12 = new TimeSeries("Temperatura Uscita PS4");
            TimeSeries series13 = new TimeSeries("Temperatura Acqua Calda PS4");
            mainFrame.mainFrame.setVisible(false);
            String user = logginFrame.user;
            String url = logginFrame.url;
            String password = logginFrame.password;
            logginFrame.logginFrame.setVisible(false);           //Creating MainFrame object also creates main window, but there it is not needed so is set to not be visible

            try (Connection connection = DriverManager.getConnection(url, user, password)) {   //DriverManager tries to connect to Data Base using data inputted by user.
                System.out.println("Connected successfully");                                  //if is connected prints this to console.
                try (Statement statement = connection.createStatement()) {                     //When connected creates SQL request.
                    sql = sql.replace("CONVERT(Char(16), TimeCol,20) AS tempoPS1\n" +
                            "        from [Cosma].[dbo].[Linea1]\n" +
                            "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '"+ time_1 +"',20)AND\n" +
                            "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                            "        group by CONVERT(Char(16), TimeCol,20)","CONVERT(VARCHAR(20), TimeCol,120) AS tempoPS1\n" +
                            "        from [Cosma].[dbo].[Linea1]\n" +
                            "where     CONVERT(VARCHAR(20), TimeCol, 120)> CONVERT(VARCHAR(20),'"+ time_1 +"', 120)AND\n" +
                            "                CONVERT(VARCHAR(20), TimeCol, 120)<CONVERT(VARCHAR(20), '"+ time_2 +"', 120)\n" +
                            "        group by CONVERT(VARCHAR(20), TimeCol, 120)");
                    sql = sql.replace("CONVERT(Char(16), TimeCol,20) AS tempoPS2\n" +
                            "        from [Cosma].[dbo].[Linea2]\n" +
                            "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '"+ time_1 +"',20)AND\n" +
                            "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                            "        group by CONVERT(Char(16), TimeCol,20)","CONVERT(VARCHAR(20), TimeCol,120) AS tempoPS2\n" +
                            "        from [Cosma].[dbo].[Linea2]\n" +
                            "where     CONVERT(VARCHAR(20), TimeCol, 120)> CONVERT(VARCHAR(20),'"+ time_1 +"', 120)AND\n" +
                            "                CONVERT(VARCHAR(20), TimeCol, 120)<CONVERT(VARCHAR(20), '"+ time_2 +"', 120)\n" +
                            "        group by CONVERT(VARCHAR(20), TimeCol, 120)");
                    sql = sql.replace("CONVERT(Char(16), TimeCol,20) AS tempoPS3\n" +
                            "        from [Cosma].[dbo].[PS3]\n" +
                            "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '"+ time_1 +"',20)AND\n" +
                            "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                            "        group by CONVERT(Char(16), TimeCol,20)","CONVERT(VARCHAR(20), TimeCol,120) AS tempoPS3\n" +
                            "        from [Cosma].[dbo].[PS3]\n" +
                            "where     CONVERT(VARCHAR(20), TimeCol, 120)> CONVERT(VARCHAR(20),'"+ time_1 +"', 120)AND\n" +
                            "                CONVERT(VARCHAR(20), TimeCol, 120)<CONVERT(VARCHAR(20), '"+ time_2 +"', 120)\n" +
                            "        group by CONVERT(VARCHAR(20), TimeCol, 120)");
                    sql = sql.replace("CONVERT(Char(16), TimeCol,20) AS tempoPS4\n" +
                            "        from [Cosma].[dbo].[PS4]\n" +
                            "        where     CONVERT(Char(16), TimeCol,20)> CONVERT(Char(16), '"+ time_1 +"',20)AND\n" +
                            "                CONVERT(Char(16), TimeCol,20)<CONVERT(Char(16), '"+ time_2 +"',20)\n" +
                            "        group by CONVERT(Char(16), TimeCol,20)","CONVERT(VARCHAR(20), TimeCol,120) AS tempoPS4\n" +
                            "        from [Cosma].[dbo].[PS4]\n" +
                            "where     CONVERT(VARCHAR(20), TimeCol, 120)> CONVERT(VARCHAR(20),'"+ time_1 +"', 120)AND\n" +
                            "                CONVERT(VARCHAR(20), TimeCol, 120)<CONVERT(VARCHAR(20), '"+ time_2 +"', 120)\n" +
                            "        group by CONVERT(VARCHAR(20), TimeCol, 120)");
                    try (ResultSet result = statement.executeQuery(sql)){
                        System.out.println(sql + "a4 in graph query");
                            while (result.next()) {
                                if (value1 != null) {
                                    value1_1 = result.getDouble(value1);
                                    value14_1 = result.getString(value14);
                                    TimeValue1 = Timestamp.valueOf(value14_1);
                                    System.out.println(value1_1);
                                    System.out.println(TimeValue1);
                                    series1.addOrUpdate(new Millisecond(TimeValue1), value1_1);
                                }
                                if (value2 != null) {
                                    value2_1 = result.getDouble(value2);
                                    value15_1 = result.getString(value14);
                                    TimeValue2 = Timestamp.valueOf(value15_1);
                                    series2.addOrUpdate(new Millisecond(TimeValue2), value2_1);
                                }
                                if (value3 != null) {
                                    value3_1 = result.getDouble(value3);
                                    value16_1 = result.getString(value14);
                                    TimeValue3 = Timestamp.valueOf(value16_1);
                                    series3.addOrUpdate(new Millisecond(TimeValue3), value3_1);
                                }
                                if (value4 != null) {
                                    value4_1 = result.getDouble(value4);
                                    TimeValue4 = result.getTimestamp(value14);
                                    series4.addOrUpdate(new Millisecond(TimeValue4), value4_1);
                                }
                                if (value5 != null) {
                                    value5_1 = result.getDouble(value5);
                                    TimeValue5 = result.getTimestamp(value14);
                                    series5.addOrUpdate(new Millisecond(TimeValue5), value5_1);
                                }
                                if (value6 != null) {
                                    value6_1 = result.getDouble(value6);
                                    TimeValue6 = result.getTimestamp(value14);
                                    series6.addOrUpdate(new Millisecond(TimeValue6), value6_1);
                                }
                                if (value7 != null) {
                                    value7_1 = result.getDouble(value7);
                                    TimeValue7 = result.getTimestamp(value14);
                                    series7.addOrUpdate(new Millisecond(TimeValue7), value7_1);
                                }
                                if (value8 != null) {
                                    value8_1 = result.getDouble(value8);
                                    TimeValue8 = result.getTimestamp(value14);
                                    series8.addOrUpdate(new Millisecond(TimeValue8), value8_1);
                                }
                                if (value9 != null) {
                                    value9_1 = result.getDouble(value9);
                                    TimeValue9 = result.getTimestamp(value14);
                                    series9.addOrUpdate(new Millisecond(TimeValue9), value9_1);
                                }
                                if (value10 != null) {
                                    value10_1 = result.getDouble(value10);
                                    TimeValue10 = result.getTimestamp(value14);
                                    series10.addOrUpdate(new Millisecond(TimeValue10), value10_1);
                                }
                                if (value11 != null) {
                                    value11_1 = result.getDouble(value11);
                                    TimeValue11 = result.getTimestamp(value14);
                                    series11.addOrUpdate(new Millisecond(TimeValue11), value11_1);
                                }
                                if (value12 != null) {
                                    value12_1 = result.getDouble(value12);
                                    TimeValue12 = result.getTimestamp(value14);
                                    series12.addOrUpdate(new Millisecond(TimeValue12), value12_1);
                                }
                                if (value13 != null) {
                                    value13_1 = result.getDouble(value13);
                                    TimeValue13 = result.getTimestamp(value14);

                                    series13.addOrUpdate(new Millisecond(TimeValue13), value13_1);
                                }
                        }
                        if (value1 != null) {
                            dataset.addSeries(series1);
                        }
                        if (value2 != null) {
                            dataset.addSeries(series2);
                        }
                        if (value3 != null) {
                            dataset.addSeries(series3);
                        }
                        if (value4 != null) {
                            dataset.addSeries(series4);
                        }
                        if (value5 != null) {
                            dataset.addSeries(series5);
                        }
                        if (value6 != null) {
                            dataset.addSeries(series6);
                        }
                        if (value7 != null) {
                            dataset.addSeries(series7);
                        }
                        if (value8 != null) {
                            dataset.addSeries(series8);
                        }
                        if (value9 != null) {
                            dataset.addSeries(series9);
                        }
                        if (value10 != null) {
                            dataset.addSeries(series10);
                        }
                        if (value11 != null) {
                            dataset.addSeries(series11);
                        }
                        if (value12 != null) {
                            dataset.addSeries(series12);
                        }
                        if (value13 != null) {
                            dataset.addSeries(series13);
                        }
                        if (value13 != null) {
                            dataset.addSeries(series13);
                        }
                        CHART_IS_VISIBLE = true;
                        System.out.println(dataset);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null,"La connesione non riuscita. Riprova piu tardi.","Errore",JOptionPane.WARNING_MESSAGE);
            }
            return dataset;
    }

    public void createChart(String sqlPs1, String value1,String value2,String value3,String value4, String value5,String value6,String value7,String value8,String value9,String value10,String value11,String value12,String value13,String value14, String time_1, String time_2){
        drawLineFrame = new JFrame();
        drawLineFrame.setSize(1280,720);
        drawLineFrame.setLocationRelativeTo(null);
        drawLineFrame.setIconImage(imageIcon.getImage());

        JFreeChart chart;


        System.out.println(sqlPs1 + " sql in create chart");
        chart = ChartFactory.createTimeSeriesChart("Grafico di:"+"value4", "Tempo", "value4", createDataset(sqlPs1, value1, value2, value3, value4,  value5, value6,value7, value8, value9, value10, value11, value12, value13, value14, time_1, time_2), true, true, true);
        ChartPanel chartPanel = new ChartPanel(chart);
        JLabel label = new JLabel();
        JLabel label_2 = new JLabel();
        label.setText("");
        label.setSize(300,80);
        label.setFont(new Font("MV Bolly", Font.ITALIC, 30));
        label_2.setSize(200,80);
        label_2.setText(" - 177013(CE)");
        label_2.setFont(new Font("MV Bolly", Font.BOLD, 25));
        chartPanel.setPreferredSize(new Dimension(1070,580));

        drawLineFrame.setLayout(new BorderLayout());
        drawLineFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        drawLineFrame.add(chartPanel,BorderLayout.SOUTH);
        drawLineFrame.add(label,BorderLayout.LINE_START);
        drawLineFrame.add(label_2, BorderLayout.LINE_END);
    }

    public XYDataset createDataset(String sql1,String sql2, String value4, String value5, String value7){
        System.out.println(sql1+" SQL1");
        System.out.println(sql2+" SQL2");


        ////////////////
        Timestamp value;
        double value_2;
        double value_3;
        ////////////////

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series1 = new TimeSeries(value4);
        TimeSeries series2 = new TimeSeries(value5);

        LogginFrame logginFrame = new LogginFrame(); // Is needed to have these 3 variables from MainFrame class
        MainFrame mainFrame = new MainFrame();

        String user = logginFrame.user;
        String url = logginFrame.url;
        String password = logginFrame.password;
        logginFrame.logginFrame.setVisible(false);           //Creating MainFrame object also creates main window, but there it is not needed so is set to not be visible
        mainFrame.mainFrame.setVisible(false);
        try (Connection connection = DriverManager.getConnection(url, user, password)) {   //DriverManager tries to connect to Data Base using data inputted by user.
            System.out.println("Connected successfully");                                  //if is connected prints this to console.
            try (Statement statement = connection.createStatement()) {                     //When connected creates SQL request.

                try (ResultSet result = statement.executeQuery(sql1)) {                    //Tries to execute created before sql request.
                    while (result.next()) {                                                //Goes for all results of request.
                        value           = result.getTimestamp("TimeCol");
                        value_2         = result.getDouble   (value4);
                        series1.addOrUpdate(new Millisecond(value), value_2);
                    }
                }
            }

            try (Statement statement = connection.createStatement()) {                     //When connected creates SQL request.
                try (ResultSet result = statement.executeQuery(sql2)) {                    //Tries to execute created before sql request.
                    while (result.next()) {                                                //Goes for all results of request.
                        value           = result.getTimestamp("TimeCol");
                        value_3         = result.getDouble   (value5);
                        series2.addOrUpdate(new Millisecond(value),value_3);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null,"La connesione non riuscita. Riprova piu tardi.","Errore",JOptionPane.WARNING_MESSAGE);
        }
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }
    public void createChart(String sqlPS4, String sqlPS4Ver2, String value4, String value5, String value7){
        drawLineFrame = new JFrame();
        drawLineFrame.setSize(1280,720);
        drawLineFrame.setLocationRelativeTo(null);
        drawLineFrame.setIconImage(imageIcon.getImage());
        XYDataset dataset;
        JFreeChart chart;

        dataset  = createDataset(sqlPS4,sqlPS4Ver2, value4, value5, value7);

        chart = ChartFactory.createTimeSeriesChart("Grafico di:"+value4 + ", " + value5, "Tempo", value4 + ", " +value5, dataset, true, true, true);

        ChartPanel chartPanel = new ChartPanel(chart);
        final XYPlot plot = chart.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        JLabel label = new JLabel();
        JLabel label_2 = new JLabel();
        label.setText("");
        label.setBounds(10,0,300,80);
        label.setFont(new Font("MV Bolly", Font.ITALIC, 30));
        label_2.setBounds(970,0,200,80);
        label_2.setText(" - 177013(CE)");
        label_2.setFont(new Font("MV Bolly", Font.BOLD, 25));
        chartPanel.setBounds(0,100,1270,580);

        drawLineFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        drawLineFrame.add(chartPanel);
        drawLineFrame.add(label);
        drawLineFrame.add(label_2);
        drawLineFrame.setLayout(new BorderLayout());
        drawLineFrame.setResizable(false);
    }
    public XYDataset createDynamicDataset(String sql, String value4, String value7){


        ////////////////
        Timestamp value;
        double value_2;
        ////////////////

        LogginFrame logginFrame = new LogginFrame(); // Is needed to have these 3 variables from MainFrame class
        MainFrame mainFrame = new MainFrame();
        mainFrame.mainFrame.setVisible(false);
        String user = logginFrame.user;
        String url = logginFrame.url;
        String password = logginFrame.password;
        logginFrame.logginFrame.setVisible(false);           //Creating MainFrame object also creates main window, but there it is not needed so is set to not be visible

        TimeSeriesCollection dynamicDataset;
        TimeSeries sensorSeries;
        sensorSeries = new TimeSeries(value4);
        sensorSeries.setMaximumItemAge(60);
        dynamicDataset = new TimeSeriesCollection();
        dynamicDataset.addSeries(sensorSeries);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {   //DriverManager tries to connect to Data Base using data inputted by user.
            System.out.println("Connected successfully");                                  //if is connected prints this to console.
            try (Statement statement = connection.createStatement()) {                     //When connected creates SQL request.
                try (ResultSet result = statement.executeQuery(sql)) {                    //Tries to execute created before sql request.
                    System.out.println(sql + " SQL PS 2 VER 3 ");
                    while (result.next()) {                                                //Goes for all results of request.
                        value           = result.getTimestamp("TimeCol");
                        value_2         = result.getDouble   (value4);
                        sensorSeries.addOrUpdate(new Millisecond(value), value_2);
                    }
                    dynamicDataset.addSeries(sensorSeries);
                    CHART_IS_VISIBLE = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null,"La connesione non riuscita. Riprova piu tardi.","Errore",JOptionPane.WARNING_MESSAGE);
        }
        return dynamicDataset;
    }
    public void createDynamicChart(String sqlPS2Ver3, String value4, String value7){
        drawLineFrame = new JFrame();
        drawLineFrame.setSize(1280,720);
        drawLineFrame.setLocationRelativeTo(null);
        drawLineFrame.setIconImage(imageIcon.getImage());
        XYDataset dynamicDataset;
        JFreeChart chart;
        System.out.println(sqlPS2Ver3 + "SQL PS 2 VER 3");
        dynamicDataset  = createDynamicDataset(sqlPS2Ver3, value4, value7);

        chart = ChartFactory.createTimeSeriesChart("Grafico di:"+value4, "Tempo", value4, dynamicDataset, true, true, true);

        ChartPanel chartPanel = new ChartPanel(chart);
        JLabel label = new JLabel();
        JLabel label_2 = new JLabel();
        label.setText("");
        label.setBounds(10,0,300,80);
        label.setFont(new Font("MV Bolly", Font.ITALIC, 30));
        label_2.setBounds(970,0,200,80);
        label_2.setText(" - 177013(CE)");
        label_2.setFont(new Font("MV Bolly", Font.BOLD, 25));
        chartPanel.setBounds(0,100,1270,580);

        drawLineFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        drawLineFrame.add(chartPanel);
        drawLineFrame.add(label);
        drawLineFrame.add(label_2);
        drawLineFrame.setLayout(new BorderLayout());
    }
}
