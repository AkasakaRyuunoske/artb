package Prototype;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class JTableFrame {
    JFrame tableFrame;                                                 //Frame object.
    JTable table;                                                      //Table Object.
    DefaultTableModel model;                                      //Model that will be used after to add new rows to the table.
    ImageIcon imageIcon = new ImageIcon("C:\\robochiy stol\\Logo-ETM2.jpg");
    JTableFrame(String resultName, ArrayList<String> Names) {

        table = new JTable();
        model = new DefaultTableModel();

        //table model configuration.
        model.setColumnIdentifiers(Names.toArray());                                            //Adding Columns to table.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);                            //Adding auto resize for all columns.
        table.setFillsViewportHeight(true);                                                 //When is true makes columns take all the available space.

        //Scroll Pane configuration.
        JScrollPane scroll = new JScrollPane(table);                                        //Adding scroller for table.
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);    //Horizontal scrollbar will only appear when is needed.
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);        //Vertical scrollbar will only appear when is needed.

        //Frame configuration.
        tableFrame = new JFrame();
        tableFrame.setTitle(resultName);
        tableFrame.add(scroll);                                                                  //Adding only scroll bar object because it became parent for table.
        tableFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);                      //Setting to only hide the table window and not close when X is pressed.
        tableFrame.setSize(500, 200);                                                //Setting starting size.
        tableFrame.setLocationRelativeTo(null);                                                  //Setting starting location in the middle of the screen.
        tableFrame.setIconImage(imageIcon.getImage());
        tableFrame.setVisible(true);                                                             //Must always be last otherwise the components will not be shown correctly.
    }

    public void addRowToTable(ArrayList<String> rowData) {                                           //is needed to write the data received from Data Base on the table.
        model.addRow(rowData.toArray()); //adding
        table.setModel(model); //than implementing.
    }
}
