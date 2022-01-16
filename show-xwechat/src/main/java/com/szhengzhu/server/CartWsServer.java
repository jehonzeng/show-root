package com.szhengzhu.server;

import com.alibaba.fastjson.JSON;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.CartServerData;
import com.szhengzhu.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
@Slf4j
@ServerEndpoint("/cart/{tableId}")
@Component
public class CartWsServer {

    public static ShowUserClient showUserClient;

    public static ShowOrderingClient showOrderingClient;
    
    private final static Map<String, List<Session>> CLINT = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("tableId") String tableId) {
        log.info("onOpen: {}", tableId);
        if (CLINT.containsKey(tableId)) {
            List<Session> list = CLINT.get(tableId);
            if (!list.contains(session)) {
                list.add(session);
            }
        } else {
            List<Session> list = new LinkedList<>();
            list.add(session);
            CLINT.put(tableId, list);
        }
    }

    /**
     * 客户端关闭
     * 
     * @param session session
     */
    @OnClose
    public void onClose(Session session, @PathParam("tableId") String tableId) {
        if (CLINT.containsKey(tableId)) {
            CLINT.get(tableId).remove(session);
        }
    }

    /**
     * 发生错误
     * 
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * 
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发消息
     * 
     * @param data 消息内容
     */
    public static void sendTableCartMsg(CartServerData data) {
        String tableId = data.getTableId();
        Result<Map<String, Object>> result = showOrderingClient.listCartByTable(tableId);
        data.setCartList(result.getData());
        List<Session> list = CLINT.get(tableId);
        if (list != null) {
            for (Session session : list) {
                session.getAsyncRemote().sendText(JSON.toJSONString(data));
            }
        }
    }
}
