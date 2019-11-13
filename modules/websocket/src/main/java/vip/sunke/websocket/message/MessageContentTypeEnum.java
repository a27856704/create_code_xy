package vip.sunke.websocket.message;

/**
 * @author sunke
 * @Date 2019-05-22 10:01
 * @description 信息内容类型
 */

public enum MessageContentTypeEnum {

    TEXT(1, "text", "文本"),
    IMAGE(2, "image", "图片"),
    VOICE(3, "voice", "语音"),
    VIDEO(4, "video", "视频"),
    FILE(5, "file", "文件");

    private int type;
    private String desc;
    private String style;

    MessageContentTypeEnum(int type, String style, String desc) {
        this.type = type;
        this.desc = desc;
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static MessageContentTypeEnum getEnumByType(int type) {


        for (MessageContentTypeEnum typeEnum : MessageContentTypeEnum.values()) {
            if (type == typeEnum.type)
                return typeEnum;
        }


        return TEXT;

    }

}
