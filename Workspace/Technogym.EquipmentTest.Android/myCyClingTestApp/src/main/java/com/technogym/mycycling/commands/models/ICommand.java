package com.technogym.mycycling.commands.models;

import java.security.PublicKey;
import java.util.List;

/**
 * Interfare related to the Equipment Command
 *
 * @author Federico Foschini
 */
public interface ICommand {

    /***
     * Gets the command's name
     * @return the command's name
     */
    public String getName();

    /***
     * Gets the command's parameters
     * @return the command's parameters
     */
    public List<String> getParameters();

    /**
     * Gets the command's serialization as string
     * @return the command's serialization as string
     */
    public String serializeAsString();

    /**
     * Gets the command's serialization as bytes
     * @return the command's serialization as bytes
     */
    public byte[] serialize();

    /***
     * Check if the command is equipment informations related
     * @return true if it's related to the equipment informations, otherwise false
     */
    public boolean isEquipmentInfoCommand();
}
