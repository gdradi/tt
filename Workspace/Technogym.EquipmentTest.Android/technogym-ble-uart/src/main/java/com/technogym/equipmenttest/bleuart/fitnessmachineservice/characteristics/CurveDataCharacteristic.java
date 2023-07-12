package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;

import java.util.ArrayList;

/**
 * Created by sventurini on 13/01/2017.
 */

public class CurveDataCharacteristic extends BaseCharacteristic {

    private final static int TOTAL_AVG_NUMBER = 10;

    private int indexSample0;   // range 0 to 512
    private double sample0;        // rad/s^2
    private double sample1;        // rad/s^2
    private double sample2;        // rad/s^2
    private double sample3;        // rad/s^"
    private double sample4;        // rad/s^"

    private ArrayList<Double> chartYValues = new ArrayList<Double>();
    private ArrayList<Double> precChartYValues = new ArrayList<Double>();

    public ArrayList<Double> getPrecChartYValues() {
        return precChartYValues;
    }

    public void setPrecChartYValues(ArrayList<Double> precChartYValues) {
        this.precChartYValues = precChartYValues;
    }

    public int getIndexSample0() {
        return indexSample0;
    }

    public void setIndexSample0(int indexSample0) {
        this.indexSample0 = indexSample0;
    }

    public double getSample0() {
        return sample0;
    }

    public void setSample0(int sample0) {
        this.sample0 = sample0;
    }

    public double getSample1() {
        return sample1;
    }

    public void setSample1(int sample1) {
        this.sample1 = sample1;
    }

    public double getSample2() {
        return sample2;
    }

    public void setSample2(int sample2) {
        this.sample2 = sample2;
    }

    public double getSample3() {
        return sample3;
    }

    public void setSample3(int sample3) {
        this.sample3 = sample3;
    }

    public double getSample4() {
        return sample4;
    }

    public void setSample4(int sample4) {
        this.sample4 = sample4;
    }

    public CurveDataCharacteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);
    }

    public CurveDataCharacteristic() {
        super();

        resetAvgChartVector(avgChartYValues);
        //resetAvgChartVector(avgPrecChartYValues);
    }

    @Override
    public boolean elabData() {
        indexSample0 = getCharacteristic().getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
        if (indexSample0 == 0) {
            precChartYValues.clear();
            precChartYValues.addAll(chartYValues);
            chartYValues.clear();
        }

        boolean exit = false;
        int offset = 0;
        while (!exit) {
            Integer sampleInt = getCharacteristic().getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 1);
            if (sampleInt != null) {
                double sample = sampleInt * 0.66;
                int x = indexSample0 * 10 + offset;
                chartYValues.add(sample);
                Log.d("Curva_di_vogata", "X: " + x + " - Y: " + sample);
            }
            else exit = true;
            offset++;
        }
        return true;
    }



    private Double[] avgChartYValues = new Double[TOTAL_AVG_NUMBER];
    private Double[] avgPrecChartYValues = new Double[TOTAL_AVG_NUMBER];

    public Double[] getAvgPrecChartYValues() {
        return avgPrecChartYValues;
    }

    public void setAvgPrecChartYValues(Double[] avgPrecChartYValues) {
        this.avgPrecChartYValues = avgPrecChartYValues;
    }


    public boolean fakeElabData() {
        if (indexSample0 == 0) {
            precChartYValues.clear();
            precChartYValues.addAll(chartYValues);
            chartYValues.clear();

            avgPrecChartYValues = adjustAvgChartYValues(avgChartYValues);
            //resetAvgChartVector(avgChartYValues); //NON Devo azzerrare i valori precedenti

        }

        boolean exit = false;
        int offset = 0;
        while (!exit) {
            Integer sampleInt = null;
            switch (offset) {
                case 0:
                    sampleInt = (int)getSample0();
                    break;
                case 1:
                    sampleInt = (int)getSample1();
                    break;
                case 2:
                    sampleInt = (int)getSample2();
                    break;
                case 3:
                    sampleInt = (int)getSample3();
                    break;
                case 4:
                    sampleInt = (int)getSample4();
                    break;
                default:
                    break;
            }
            if (sampleInt != null) {
                double sample = sampleInt * 0.66;
                int x = indexSample0 * 10 + offset;
                chartYValues.add(sample);
                Log.d("Curva_di_vogata", "X: " + x + " - Y: " + sample);

                //Nel caso più campioni confluiscano in uno stesso bin, si procede a una media tra i campioni in collisione.
                int avgIndex = getIndexBySample(sample);
                if(avgChartYValues[avgIndex] > 0)
                {
                    avgChartYValues[avgIndex] = (avgChartYValues[avgIndex] + sample) / 2;
                }
                else {
                    avgChartYValues[avgIndex] = sample;
                }
            }
            else exit = true;
            offset++;
        }
        return true;
    }

    
    //Nel caso in un bin non confluiscano campioni si procede a una media tra il campione finale del bin precedente e successivo se disponibili, si inserisce 0 altrimenti.
    private static Double[] adjustAvgChartYValues(Double[] values)
    {
        Double[] result = values.clone();;
        //result[0] = values[0];

        //Il primo e l'ultimo lo ignora
        for(int i=1;i<TOTAL_AVG_NUMBER-1; i++) {
            if( result[i] <= 0 && result[i-1] > 0 && result[i+1] > 0)
            {
                result[i] = (result[i-1] + result[i+1]) / 2;
            }
        }
        return result;
    }

    /*
    Per garantire la correttezza della curva media, al ricevimento di ogni curva istantanea ogni bin
    deve essere aggiornato con un nuovo valore. A tale proposito:
    Nel caso più campioni confluiscano in uno stesso bin, si procede a una media tra i campioni in collisione.
     */
    private static int getIndexBySample(double value){
        int tmp1 = (int)(value / 18.0);
        if(tmp1 >= TOTAL_AVG_NUMBER)
            tmp1 = TOTAL_AVG_NUMBER - 1;
        return tmp1;
    }

    private static void resetAvgChartVector(Double[] array){
        for(int i=0;i<TOTAL_AVG_NUMBER; i++)
        {
            array[i] = 0.0;
        }
    }


}
