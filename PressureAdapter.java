// Jena Lovejoy
// CS 410 - Software Engineering
// Due: 5/28/19
// PressureAdapter functions as a wrapper for PressureSensor to be used in a consistent way with the other sensors


import sensor.PressureSensor;

public class PressureAdapter implements SensorAdapter {

    PressureSensor sensor;

    public PressureAdapter(){
        sensor = new PressureSensor();
    }

    public double getValue(){
        return sensor.readValue();
    }

    public String getReport(){
        return sensor.getReport();
    }

    public String getName(){
        return sensor.getSensorName();
    }

}
