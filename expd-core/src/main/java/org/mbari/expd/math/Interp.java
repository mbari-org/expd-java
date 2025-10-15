package org.mbari.expd.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class Interp {

        /**
     *
     * @param x
     * @param y
     * @param xi
     * @return
     */
    public static BigDecimal[] interpolate(BigDecimal[] x, BigDecimal[] y, BigDecimal[] xi) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("X and Y must be the same length");
        }
        if (x.length == 1) {
            throw new IllegalArgumentException("X must contain more than one value");
        }
        BigDecimal[] dx = new BigDecimal[x.length - 1];
        BigDecimal[] dy = new BigDecimal[x.length - 1];
        BigDecimal[] slope = new BigDecimal[x.length - 1];
        BigDecimal[] intercept = new BigDecimal[x.length - 1];

        // Calculate the line equation (i.e. slope and intercept) between each point
        BigInteger zero = new BigInteger("0");
        BigDecimal minusOne = new BigDecimal(-1);

        for (int i = 0; i < x.length - 1; i++) {

            //dx[i] = x[i + 1] - x[i];
            dx[i] = x[i + 1].subtract(x[i]);
            if (dx[i].equals(new BigDecimal(zero, dx[i].scale()))) {
                throw new IllegalArgumentException("X must be montotonic. A duplicate " + "x-value was found");
            }
            if (dx[i].signum() < 0) {
                throw new IllegalArgumentException("X must be sorted");
            }

            //dy[i] = y[i + 1] - y[i];
            dy[i] = y[i + 1].subtract(y[i]);

            //slope[i] = dy[i] / dx[i];
            slope[i] = dy[i].divide(dx[i]);

            //intercept[i] = y[i] - x[i] * slope[i];
            intercept[i] = x[i].multiply(slope[i]).subtract(y[i]).multiply(minusOne);

            //intercept[i] = y[i].subtract(x[i]).multiply(slope[i]);
        }

        // Perform the interpolation here
        BigDecimal[] yi = new BigDecimal[xi.length];
        for (int i = 0; i < xi.length; i++) {

            //if ((xi[i] > x[x.length - 1]) || (xi[i] < x[0])) {
            if ((xi[i].compareTo(x[x.length - 1]) > 0) || (xi[i].compareTo(x[0]) < 0)) {
                yi[i] = null;    // same as NaN
            }
            else {
                int loc = Arrays.binarySearch(x, xi[i]);
                if (loc < -1) {
                    loc = -loc - 2;

                    //yi[i] = slope[loc] * xi[i] + intercept[loc];
                    yi[i] = slope[loc].multiply(xi[i]).add(intercept[loc]);
                }
                else {
                    yi[i] = y[loc];
                }
            }
        }

        return yi;
    }
    
}
