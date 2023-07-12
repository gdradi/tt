package com.technogym.equipmenttest.appbleuart.manager;

public interface IUpgradeManager {

     String downloadLowKitUpgradeFile(String modelCode, String widVersion, String token);
     String downloadHighKitUpgradeFile(String modelCode, String widVersion, String token);
     void copyLowKitFileToEquipmentMemory(String path);
     void copyHighKitFileToEquipmentMemory(String path);
}
