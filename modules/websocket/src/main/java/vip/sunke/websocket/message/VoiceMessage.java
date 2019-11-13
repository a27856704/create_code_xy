package vip.sunke.websocket.message;

/**
 * @author sunke
 * @Date 2019-05-22 11:15
 * @description
 */
@Deprecated
public class VoiceMessage extends Message {

    @Override
    public MessageContentTypeEnum messageContentType() {
        return MessageContentTypeEnum.VOICE;
    }


}
