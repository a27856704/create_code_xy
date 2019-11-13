package vip.sunke.websocket.message;

import java.util.Date;

/**
 * @author sunke
 * @Date 2019-05-22 10:43
 * @description
 */
@Deprecated
public class ImageMessage extends Message {

    public ImageMessage() {
    }

    public ImageMessage(Date sendTime) {
        super(sendTime);
    }

    public ImageMessage(String from, String to, Date sendTime) {
        super(from, to, sendTime);
    }

    @Override
    public MessageContentTypeEnum messageContentType() {
        return MessageContentTypeEnum.IMAGE;
    }

    /**
     * 发送消息
     *
     * @param skWebSocket
     */

}
