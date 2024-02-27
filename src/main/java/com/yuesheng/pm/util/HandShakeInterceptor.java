package com.yuesheng.pm.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by 宋正根 on 2016/9/30.
 * webSocket握手拦截器，用于拦截webSocket初始化连接的请求
 * @author XiaoSong
 * @date 2016/09/30
 */
public class HandShakeInterceptor implements HandshakeInterceptor {

    /**
     * 连接之前调用
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map){
        //解决The extension [x-webkit-deflate-frame] is not supported问题
        if(serverHttpRequest.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            serverHttpRequest.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            try {
                //强转成httpRequest对象获取session中用户
                HttpSession session = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getSession();
                //保存请求用户的ip地址带session中，便于后面对客户端推送消息
                session.setAttribute(Constant.ADDRESS_CLIENT,serverHttpRequest.getRemoteAddress().getHostName());
                //为socketSession对象创建参数
                map.put(Constant.SESSION_KEY,session.getAttribute(Constant.SESSION_KEY));
                //如果用戶为null，不予连接
                if(map.get(Constant.SESSION_KEY) == null){
                    return false;
                }
            } catch (IllegalStateException ignore) {
                //session失效，不予连接
                return false;
            }
        }
        return true;
    }

    /**
     * 连接之后调用
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        if(serverHttpRequest instanceof ServletServerHttpRequest){
            try {
                if(e != null){
                    System.out.println("afterHandshake---error:"+e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e1) {
                System.out.println("afterHandshake---error:"+e1.getMessage());
            }
        }
    }
}
