package Prototype;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class DTSCTest extends JFrame {

    final String TITLE = "Dynamic Time Chart Sample";
    final String START = "Start";
    final String STOP = "Stop";
    final float MINMAX = 100;
    final int COUNT = 2 * 60;
    final int FAST = 100;
    final int SLOW = FAST * 5;
    Random random = new Random();
    Timer timer;
    final ImageIcon imageIcon = new ImageIcon("C:\\robochiy stol\\Logo-.jpg");

    public DTSCTest(String title) {
        super(title);
        final DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(1, COUNT, new Second());
        dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));
        dataset.addSeries(gaussianData(), 0, "Random Values");
        JFreeChart chart = createChart(dataset);
        final JButton run = new JButton(STOP);
        run.addActionListener(e -> {
            String cmd = e.getActionCommand();
            if (STOP.equals(cmd)) {
                timer.stop();
                run.setText(START);
            } else {
                timer.start();
                run.setText(STOP);
            }
        });

        final JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Veloce");
        combo.addItem("Lento");
        combo.addActionListener(e -> {
            if ("Veloce".equals(combo.getSelectedItem())) {
                timer.setDelay(FAST);
            } else {
                timer.setDelay(SLOW);
            }
        });
        this.setIconImage(imageIcon.getImage());
        this.add(new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        }, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(run);
        btnPanel.add(combo);
        this.add(btnPanel, BorderLayout.SOUTH);

        timer = new Timer(FAST, new ActionListener() {
            final float[] newData = new float[1];

            @Override
            public void actionPerformed(ActionEvent e) {
                newData[0] = randomValue();
                System.out.println(Arrays.toString(newData));
                dataset.advanceTime();
                dataset.appendData(newData);
            }
        });

    }

    private float randomValue() {
        return (float) (random.nextGaussian() * MINMAX / 3);
    }

    private float[] gaussianData() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = randomValue();
        }
        return a;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE, "Tempo", "title", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(-MINMAX, MINMAX);
        return result;
    }

    public void start() {
        timer.start();
    }
}
