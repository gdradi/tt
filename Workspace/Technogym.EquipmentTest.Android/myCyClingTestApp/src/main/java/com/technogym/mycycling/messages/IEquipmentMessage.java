package com.technogym.mycycling.messages;

/***
 * Interface related to the messages to send to the equipment
 */
public interface IEquipmentMessage {

    /***
     * Gets the message's tag
     * @return the message's tag
     */
    public String getMessageTag();

    /***
     * Gets the message's content
     * @return the message's content
     */
    public String getContent();
}
