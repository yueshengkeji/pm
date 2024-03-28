package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yuesheng.pm.entity.FlowApprove;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.service.FlowApproveService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 宋正根 on 2016/9/30.
 * 用来与webSocket客户端来进行交互的接口，Spring WebSocket提供了一些实现类，可以根据自己的需求进行选择与重写
 *
 * @author XiaoSong
 * @date 2016/09/30
 */
public class MyWebSocketHandle extends TextWebSocketHandler {
    @Autowired
    private FlowApproveService flowApproveService;

    /**
     * 在线session集合
     */
    private static Map<String, WebSocketSession> staffMap = new HashMap(16);


    /**
     * 接收到客户端消息时调用
     *
     * @param session
     * @param message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        doMsgDispose(session, message, this.flowApproveService);
    }

    public static void doMsgDispose(WebSocketSession session, TextMessage message, FlowApproveService flowApproveService) {
        Staff staff = (Staff) session.getAttributes().get(Constant.SESSION_KEY);
        String userId = staff.getId();
        if(StringUtils.equals(message.getPayload(),"ping")){
            sendMeg(userId,"ok");
            return;
        }
        String temp;
        List<FlowApprove> approveList;
        HashMap<String, Object> object = JSON.parseObject(message.getPayload(), HashMap.class);
        try {
            approveList = flowApproveService.getMessages(userId, (String) object.get("startDate"), (String) object.get("endDate"),
                    (List<String>) object.get("states"), (List<String>) object.get("msgState"), 1, null, 1,20, null);
            object.clear();
            object.put("type", 0);
            object.put("data", approveList);
            temp = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
            session.sendMessage(new TextMessage(temp));
        } catch (SocketException e) {
            System.out.println("handleTextMessage----------------------socket send error for song:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("handleTextMessage----------------------date send error for song：" + e.getMessage());
        }
    }

    /**
     * 与客户端完成连接后调用
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //连接完成后通过地址保存用户的session
        if (session instanceof StandardWebSocketSession) {
            Object staff = getUser(session);
            if (!Objects.isNull(staff)) {
                addToMap(session, (Staff) staff);
            }
        }
    }

    private static Object getUser(WebSocketSession session) {
        Map<String, Object> a = session.getAttributes();
        Object staff = a.get(Constant.SESSION_KEY);
        return staff;
    }

    /**
     * 添加连接用户到map中
     *
     * @param session
     */
    public static void addToMap(WebSocketSession session,Staff staff) {
        staffMap.put(staff.getId(), session);
    }

    /**
     * 消息传输出错时调用
     * 列：java.io.EOFException: The client aborted the connection.=客户端关闭
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) {
        /*
         * 此异常暂时未发现影响程序，暂不处理
         */
        try {
            super.handleTransportError(session, exception);
        } catch (Exception e) {

        }
    }

    /**
     * 一个客户端连接断开时关闭
     *
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) {
        try {
            Object staff = getUser(session);
            if(!Objects.isNull(staff)) {
                staffMap.remove(((Staff)staff).getId());
            }
            super.afterConnectionClosed(session, closeStatus);
        } catch (Exception e) {
            System.out.println("close client session error for song:" + closeStatus);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 判断职员是否登录
     *
     * @param staff 职员对象
     * @return
     */
    public boolean staffExists(Staff staff) {
        return staffMap.containsKey(staff.getId());
    }

    /**
     * socket消息发送
     *
     * @param userId 用户id
     * @param content     消息内容
     */
    public static void sendMeg(String userId, String content) {
        WebSocketSession session = staffMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(content));
            } catch (IOException e) {
                System.out.println("send msg for auto error:" + e.getMessage());
                LoggerFactory.getLogger(MyWebSocketHandle.class).error(e.getLocalizedMessage());
            } catch (Exception e) {
                LoggerFactory.getLogger(MyWebSocketHandle.class).error(e.getLocalizedMessage());
            }
        }
    }

    /**
     * socket消息推送的封装
     *
     * @param userId  用户id
     * @param content 消息内容
     */
    public static void sendMsg(String userId, String content) {
        sendMeg(userId, content);
    }

    public static Map<String, WebSocketSession> getStaffMap() {
        return staffMap;
    }
}
