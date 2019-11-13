package vip.sunke.websocket.message;

import java.util.Date;

/**
 * @author sunke
 * @Date 2019-05-22 10:04
 * @description
 */
@Deprecated
public class TextMessage extends Message {


    public TextMessage() {

    }

    public TextMessage(Date sendTime) {
        super(sendTime);
    }

    public TextMessage(String from, String to, Date sendTime) {
        super(from, to, sendTime);

    }

    @Override
    public MessageContentTypeEnum messageContentType() {
        return MessageContentTypeEnum.TEXT;
    }



}
