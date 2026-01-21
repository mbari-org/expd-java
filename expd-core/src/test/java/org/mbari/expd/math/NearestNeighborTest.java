package org.mbari.expd.math;

/*-
 * #%L
 * org.mbari.expd:expd-core
 * %%
 * Copyright (C) 2008 - 2026 Monterey Bay Aquarium Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Brian Schlining
 * @since 2015-06-08T15:35:00
 */
public class NearestNeighborTest {

    @Test
    public void testTooHigh() {
        double[] x = {1D, 3D, 5D};
        double[] xi = {12D};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {-1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testHigh() {
        double[] x = {1D, 3D, 5D};
        double[] xi = {6D};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {2};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testTooLow() {
        double[] x = {1D, 3D, 5D};
        double[] xi = {-3D};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {-1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testLow() {
        double[] x = {1D, 3D, 5D};
        double[] xi = {0D};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {0};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testDoubleArray() {
        double[] x = {1d, 3d, 5d};
        double[] xi = {2d, 4d};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {0, 1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testDoubleArrayWithMatch() {
        double[] x = {1d, 3d, 5d};
        double[] xi = {1d, 2d, 4d};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {0, 0, 1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testDoubleOutOfEpsilon() {
        double[] x = {1d, 3d, 11d};
        double[] xi = {1d, 7d};
        int[] i = NearestNeighbor.apply(x, xi, 2D);
        int[] expected = {0, -1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testLongArray() {
        long[] x = {1L, 3L, 5L};
        long[] xi = {2L, 4L};
        int[] i = NearestNeighbor.apply(x, xi, 2);
        int[] expected = {0, 1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testLongArrayWithMatch() {
        double[] x = {1L, 3L, 5L};
        double[] xi = {1L, 2L, 4L};
        int[] i = NearestNeighbor.apply(x, xi, 2);
        int[] expected = {0, 0, 1};
        assertArrayEquals(expected, i);
    }

    @Test
    public void testLongOutOfEpsilon() {
        double[] x = {1L, 3L, 11L};
        double[] xi = {1L, 7L};
        int[] i = NearestNeighbor.apply(x, xi, 2);
        int[] expected = {0, -1};
        assertArrayEquals(expected, i);
    }
}
