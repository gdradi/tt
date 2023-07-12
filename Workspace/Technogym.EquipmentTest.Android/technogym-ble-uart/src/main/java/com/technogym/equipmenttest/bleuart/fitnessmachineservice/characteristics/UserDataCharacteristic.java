package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;


import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;

/**
 * Created by sventurini on 03/02/2017.
 */

public class UserDataCharacteristic extends BaseCharacteristic {

    @Override
    public boolean elabData() {
        return true;
    }
}
