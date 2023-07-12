package com.technogym.mycycling.support;

import android.util.Log;

import java.io.UnsupportedEncodingException;

public class ConversionHelper {

    public static String stringToHex(String base)
    {
        StringBuffer buffer = new StringBuffer();
        int intValue;
        for(int x = 0; x < base.length(); x++)
        {
            int cursor = 0;
            intValue = base.charAt(x);
            String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
            for(int i = 0; i < binaryChar.length(); i++)
            {
                if(binaryChar.charAt(i) == '1')
                {
                    cursor += 1;
                }
            }
            if((cursor % 2) > 0)
            {
                intValue += 128;
            }
            buffer.append(Integer.toHexString(intValue) + " ");
        }
        return buffer.toString();
    }

    public static String BytesToString(byte[] data) {
        String strData = null;
        try {
            strData = new String(data, "UTF-8");
            Log.i("Conversion Helper", "Conversion Completed - Result: " + strData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("Conversion Helper", "Error during conversion: " + e.getMessage());
        }
        return strData;
    }
}
