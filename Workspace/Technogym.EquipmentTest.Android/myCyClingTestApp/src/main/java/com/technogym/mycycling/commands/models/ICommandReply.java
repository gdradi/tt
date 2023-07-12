package com.technogym.mycycling.commands.models;

import java.util.List;

/**
 * Interfare related to the Equipment Commands' reply
 *
 * @author Federico Foschini
 */
public interface ICommandReply {

    /***
     * Gets the command's name
     * @return the command's name
     */
    public String getName();

    /***
     * Gets the command's reply values
     * @return the command's reply values
     */
    public List<String> getValues();

    /***
     * Check if the command is equipment informations related
     * @return true if it's related to the equipment informations, otherwise false
     */
    public boolean isEquipmentInfoCommand();
}
