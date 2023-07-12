package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.util.Log;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by sventurini on 20/04/16.
 */
public class Random {

    public static double getDouble(double rangeMax, double rangeMin) {
        java.util.Random r = new java.util.Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    public static int getInt(int rangeMax, int rangeMin) {
        java.util.Random r = new java.util.Random();
        return Math.round((float) (rangeMin + (rangeMax - rangeMin) * r.nextDouble()));
    }

    /**
     * valueArray and weigthArray must have the same size
     * @param valueArray
     * @param weigthArray (the sum of every whegthArray value must be equal to 100%)
     * @return
     */
    public static Double getRandomIndexForWeigth(double[] valueArray, int[] weigthArray) {
        if (valueArray.length != weigthArray.length) {
            Log.d("Formula", "error: valueArray and weigthArray must have the same length");
            return 0d;

        }
        int total = 0;
        NavigableMap<Integer, Double> map = new TreeMap<Integer, Double>();
        for (int i = 0; i < weigthArray.length; i++) {
            int weigth = weigthArray[i];
            total += weigth;
            map.put(total, valueArray[i]);

        }
        Integer randomKey = getInt(total, 0);
        return map.ceilingEntry(randomKey).getValue();
    }
}
