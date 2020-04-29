package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;
    }

    @Override
    public double next() {
        state += 1;
        return normalize();
    }


    // helper function
    private double normalize() {
        double converted = -1.0 + 2.0 * (state % period) / (period - 1);
        if (Double.compare(1.0, converted) <= 0) {
            period = (int) (period * factor);
            state = 0;
        }
        return converted;
    }

}
