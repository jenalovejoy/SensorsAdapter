// Jena Lovejoy
// CS 410 - Software Engineering
// Due: 5/28/19
// SensorApplication generates a GUI based on Pressure, Radiation, and Temperature sensor data

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class SensorApplication extends JFrame {

    // GUI Constants
    final int WINDOW_SIZE = 600;
    final int MAX_BAR_WIDTH = (int) (WINDOW_SIZE * .6);

    // Adapters to access Sensor data
    PressureAdapter pressureSensor;
    RadiationAdapter radiationSensor;
    TemperatureAdapter temperatureSensor;

    public SensorApplication() {

        pressureSensor = new PressureAdapter();
        radiationSensor = new RadiationAdapter();
        temperatureSensor = new TemperatureAdapter();

        // Sensor information organized as [Pressure, Radiation, Temperature]
        SensorAdapter[] sensors = {pressureSensor, radiationSensor, temperatureSensor};

        // Sensor Data
        String[] report = new String[3];
        double[] value = new double[3];
        String[] output = new String[3];

        // Interpreted Sensor Data
        int[] warningBarWidth = new int[3];
        Color[] reportColor = new Color[3];

        // Information for calculating warningBarWidth
        double[] minimumDanger = {6.58, 4, 300}; // The minimum danger value, for the GUI the maximum length of the warning bar

        // Iterate over sensor data arrays
        int i = 0;

        // Obtain information from each sensor
        for (SensorAdapter sensor : sensors){
            value[i] = sensor.getValue();                   // Sensor value
            report[i] = sensor.getReport();                 // Sensor status - OK / CRITICAL / DANGER
            output[i] = report[i] + " ---> " + value[i];    // Formatted sensor status
            reportColor[i] = intepretReport(report[i]);     // Sensor status color - Green / Yellow / Red
            warningBarWidth[i] = interpretValue(value[i], minimumDanger[i]);    // Warning bar width
            System.out.println(warningBarWidth[i]);
            i++;
        }

        // Set up main window
        setTitle("Sensor Tracker");
        setLayout(new GridLayout(3,2));

        // Set up pressure sensor section
        JPanel pressurePnl = new JPanel(new GridBagLayout());
        GridBagConstraints pressureC = new GridBagConstraints();
        pressurePnl.setBorder(new TitledBorder("Pressure"));
        add(pressurePnl);

        // Manage pressure warning bar -- formatting comments apply to each of the three sections
        JPanel pressureBar = new JPanel();                                             // individual sensor
        pressureBar.setBackground(reportColor[0]);                                     // color for status
        pressureC.weightx = .5;                                                        // content alignment
        pressureC.anchor = GridBagConstraints.LINE_START;                              // left/center align
        pressureBar.setPreferredSize(new Dimension(warningBarWidth[0], 100));   // width of warning bar
        pressurePnl.add(pressureBar, pressureC);                                       // combine bar with panel
        pressurePnl.add(new JLabel(output[0]), pressureC);                             // sensor value
        pressureBar.setVisible(true);                                                  // load information

        // Set up radiation sensor section
        JPanel radiationPnl = new JPanel(new GridBagLayout());
        GridBagConstraints radiationC = new GridBagConstraints();
        radiationPnl.setBorder(new TitledBorder("Radiation"));
        add(radiationPnl);

        // Manage radiation warning bar -- pressure formatting comments apply
        JPanel radiationBar = new JPanel();
        radiationBar.setBackground(reportColor[1]);
        radiationC.weightx = .5;
        radiationC.anchor = GridBagConstraints.LINE_START;
        radiationBar.setPreferredSize(new Dimension(warningBarWidth[1], 100));
        radiationPnl.add(radiationBar, radiationC);
        radiationPnl.add(new JLabel(output[1]), radiationC);
        radiationBar.setVisible(true);

        // Set up temperature sensor section
        JPanel temperaturePnl = new JPanel(new GridBagLayout());
        GridBagConstraints temperatureC = new GridBagConstraints();
        temperaturePnl.setBorder(new TitledBorder("Temperature"));
        add(temperaturePnl);

        // Manage temperature warning bar -- pressure formatting comments apply
        JPanel temperatureBar = new JPanel();
        temperatureBar.setBackground(reportColor[2]);
        temperatureC.weightx = .5;
        temperatureC.anchor = GridBagConstraints.LINE_START;
        temperatureBar.setPreferredSize(new Dimension(warningBarWidth[2], 100));
        temperaturePnl.add(temperatureBar, temperatureC);
        temperaturePnl.add(new JLabel(output[2]), temperatureC);
        temperatureBar.setVisible(true);

        // Load GUI
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false); // Window cannot be resize - important for warning bar proportions
        pack();
    }

    public static void main(String[] args) {
        SensorApplication app = new SensorApplication();

    }

    // Based on status of sensor report, returns the corresponding color
    public Color intepretReport(String report){

        switch(report){
            case "OK":
                return Color.GREEN;

            case "CRITICAL":
                return Color.YELLOW;

            case "DANGER":
                return Color.RED;

        }
        throw new InvalidParameterException();
    }

    // Based on the current value of the sensor, and the minimum value for a "DANGER" status, determines the proportion of the width of the GUI window for the warning window to span
    // The minimum designation for danger is chosen for the "100%" mark on the bar to allow for a reasonable proportion up to that point, and prevent any overflow for enormous numbers
    public int interpretValue(double value, double max){

        // If the value is above the minimum DANGER classification, have the warning bar span the entire designated space
        if (value >= max){
            return MAX_BAR_WIDTH;

        // If the value is zero, have the warning bar span a small amount to remain visible
        }  else if (value == 0) {
            return 2;

        // The warning bar will span a proportion of the current sensor value to the minimum danger classification
        } else {
            return (int) (value / max * MAX_BAR_WIDTH);
        }
    }
}
