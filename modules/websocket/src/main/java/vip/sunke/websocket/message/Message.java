package vip.sunke.websocket.message;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import vip.sunke.websocket.SkWebSocket;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunke
 * @Date 2019-05-22 09:58
 * @description
 */
@Slf4j
public class Message implements Serializable {


    private String from;//发送者
    private String to;//接收者
    private Date sendTime;//发送时间
    private int contentType;//发送信息内容类型
    private String data;

    private boolean target;//true 发给接收者  false 给发送者

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public Message() {

    }

    public Message(int contentType) {
        this.contentType = contentType;
    }

    public Message(Date sendTime) {
        this.sendTime = sendTime;
    }
    public Message(String from, String to, Date sendTime) {
        this.from = from;
        this.to = to;
        this.sendTime = sendTime;
    }



    public static Class messageContentClass(int contentType) {

        if (MessageContentTypeEnum.IMAGE.getType() == contentType)
            return ImageMessage.class;

        if (MessageContentTypeEnum.VOICE.getType() == contentType)
            return VoiceMessage.class;

        if (MessageContentTypeEnum.VIDEO.getType() == contentType)
            return VideoMessage.class;

        if (MessageContentTypeEnum.FILE.getType() == contentType)
            return FileMessage.class;

        return TextMessage.class;

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MessageContentTypeEnum messageContentType() {

        return MessageContentTypeEnum.TEXT;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 发送消息
     *
     * @param skWebSocket
     */
    public final void sendMessage(SkWebSocket skWebSocket) {
        if (skWebSocket == null)
            return;
        try {
            skWebSocket.getSession().getBasicRemote().sendText(JSONObject.toJSONString(this));
        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
