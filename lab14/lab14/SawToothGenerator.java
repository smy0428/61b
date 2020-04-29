package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;


    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        state += 1;
        return normalize();
    }


    // helper function
    private double normalize() {
        double converted = -1.0 + 2.0 * (state % period) / (period - 1);
        return converted;
    }
}
