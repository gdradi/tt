package com.technogym.equipmenttest.bleuart.fitnessmachineservice.interfaces;

/**
 * Created by sventurini on 24/04/16.
 */
public interface CharacteristicListener<T> {

    void onReadSuccessful(T characteristic);

    void onReadFailed(T characteristic);

    void onWriteSuccessful(T characteristic);

    void onWriteFailed(T characteristic);

    void onNotify(T characteristic);

 }
