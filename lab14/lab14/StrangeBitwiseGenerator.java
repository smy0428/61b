package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
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
        //int weirdState = state & (state >> 3) & (state >> 8) % period;
        int weirdState = state & (state >> 7) % period;
        double converted = -1.0 + 2.0 * (weirdState % period) / (period - 1);
        return converted;
    }
}
