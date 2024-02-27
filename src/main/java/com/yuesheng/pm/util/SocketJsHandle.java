package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.service.FlowApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Objects;

/**
 * Created by 96339 on 2017/5/17.
 * @author XiaoSong
 * @date 2017/05/17
 */
public class SocketJsHandle extends TextWebSocketHandler {
    @Autowired
    private FlowApproveService flowApproveService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MyWebSocketHandle.doMsgDispose(session,message,flowApproveService);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session instanceof StandardWebSocketSession) {
            Map<String, Object> a = session.getAttributes();
            Object staff = a.get(Constant.SESSION_KEY);
            if (!Objects.isNull(staff)) {
                MyWebSocketHandle.addToMap(session,(Staff) staff);
            }

        }

    }
}
