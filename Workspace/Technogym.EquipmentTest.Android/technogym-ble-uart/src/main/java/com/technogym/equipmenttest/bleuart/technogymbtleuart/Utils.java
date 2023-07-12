package com.technogym.equipmenttest.bleuart.technogymbtleuart;

/**
 * Created by sventurini on 04/12/15.
 */
public class Utils {

    /**
     *
     * @param value
     * @param checkSum
     * @param module
     * @return
     */
    public static boolean checkSumModule(String value, String checkSum, int module) {
        try {
            int chechSumVal = Integer.valueOf(checkSum);
            int sumValue = 0;
            for (int charVal : value.getBytes()) {
                sumValue += charVal;
            }
            int moduleVal = (sumValue % module);
            return moduleVal == chechSumVal;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
