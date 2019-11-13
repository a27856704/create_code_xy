package vip.sunke.websocket.message;

/**
 * @author sunke
 * @Date 2019-05-22 11:16
 * @description
 */
@Deprecated
public class VideoMessage extends Message {

    @Override
    public MessageContentTypeEnum messageContentType() {
        return MessageContentTypeEnum.VIDEO;
    }


}
