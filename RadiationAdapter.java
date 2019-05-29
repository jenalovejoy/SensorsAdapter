// Jena Lovejoy
// CS 410 - Software Engineering
// Due: 5/28/19
// RadiationAdapter functions as a wrapper for RadiationSensor to be used in a consistent way with the other sensors


import sensor.RadiationSensor;

public class RadiationAdapter implements SensorAdapter {

    RadiationSensor sensor;

    public RadiationAdapter(){ sensor = new RadiationSensor(); }

    public double getValue(){
        return sensor.getRadiationValue();
    }

    public String getReport(){
        return sensor.getStatusInfo();
    }

    public String getName(){
        return sensor.getName();
    }

}
