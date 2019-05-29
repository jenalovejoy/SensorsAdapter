// Jena Lovejoy
// CS 410 - Software Engineering
// Due: 5/28/19
// TemperatureAdapter functions as a wrapper for TemperatureSensor to be used in a consistent way with the other sensors

import sensor.TemperatureSensor;

public class TemperatureAdapter implements SensorAdapter {

    TemperatureSensor sensor;

    public TemperatureAdapter(){
        sensor = new TemperatureSensor();
    }

    public double getValue(){
        return sensor.senseTemperature();
    }

    public String getReport(){
        return sensor.getTempReport();
    }

    public String getName(){
        return sensor.getSensorType();
    }
}
