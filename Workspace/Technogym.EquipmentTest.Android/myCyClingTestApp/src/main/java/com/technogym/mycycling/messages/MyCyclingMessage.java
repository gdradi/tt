package com.technogym.mycycling.messages;

/***
 * Messages to send to the MyCycling equipment
 */
public class MyCyclingMessage implements IEquipmentMessage {

    protected String mContent;
    protected String mTag;

    // { Constructors

    private MyCyclingMessage(String tag, String content) {
        mTag = tag;
        mContent = content;
    }

    // }

    // { Public methods

    /***
     * Gets a message instance
     * @param tag: message's tag
     * @param content: message's content
     * @return a new message instance
     */
    public static MyCyclingMessage create(String tag, String content) {
        return (new MyCyclingMessage(tag, content));
    }

    // }

    // { IEquipmentMessage implementation

    @Override
    public String getMessageTag() {
        return mTag;
    }

    @Override
    public String getContent() {
        return mContent;
    }

    // }
}
