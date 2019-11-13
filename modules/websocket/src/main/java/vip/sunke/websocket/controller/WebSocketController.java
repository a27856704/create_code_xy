package vip.sunke.websocket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.sunke.common.Const;
import vip.sunke.pubInter.BaseController;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.web.common.SkMap;
import vip.sunke.websocket.WsPool;
import vip.sunke.websocket.message.Message;
import vip.sunke.websocket.message.MessageContentTypeEnum;

import java.io.File;
import java.util.Date;

/**
 * @author sunke
 * @Date 2019-05-23 10:07
 * @description
 */

@RestController
@RequestMapping("/websocket")
public class WebSocketController extends BaseController {


    /**
     * @param file
     * @param from
     * @param to
     * @param contentType
     * @return
     */
    @PostMapping("/uploadFile")
    public SkMap uploadFile(
            MultipartFile file
            , @RequestParam String from
            , @RequestParam String to
            , @RequestParam(value = "contentType", defaultValue = "2", required = false) int contentType) throws BusinessException {

        String filPath = getUploadFile(file, from + File.separator + MessageContentTypeEnum.getEnumByType(contentType).getStyle(), Const.IMG_TYPE);

        Message message = null;
        try {
            message = new Message();
            message.setContentType(contentType);
            message.setFrom(from);
            message.setTo(to);
            message.setSendTime(new Date());
            message.setData("files/" + filPath);


            sendMessage(message);
            //  WsPool.sendMessageToUserAndMyself(message);


            return SkMap.ok("path", filPath);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }


    /**
     * 发送文本信息
     *
     * @param from
     * @param to
     * @param data
     * @return
     * @throws BusinessException
     */
    @PostMapping("/sendMessage")
    public SkMap sendTextMessage(@RequestParam String from, @RequestParam String to, String data) throws BusinessException {
        Message message = new Message(MessageContentTypeEnum.TEXT.getType());
        message.setFrom(from);
        message.setTo(to);
        message.setData(data);
        message.setSendTime(new Date());
        //   WsPool.sendMessageToUserAndMyself(message);


        sendMessage(message);

        return SkMap.ok();
    }


    private void sendMessage(Message message) {

        if (message == null)
            return;
        String from = message.getFrom();
        String to = message.getTo();

        message.setTarget(false);
        WsPool.sendMessageToUser(from, message);


        message.setTarget(true);
        WsPool.sendMessageToUser(to, message);


    }


}
