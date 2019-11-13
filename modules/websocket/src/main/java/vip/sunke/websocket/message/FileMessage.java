package vip.sunke.websocket.message;

/**
 * @author sunke
 * @Date 2019-05-22 11:32
 * @description
 */

@Deprecated
public class FileMessage extends Message {

    @Override
    public MessageContentTypeEnum messageContentType() {
        return MessageContentTypeEnum.FILE;
    }


}
