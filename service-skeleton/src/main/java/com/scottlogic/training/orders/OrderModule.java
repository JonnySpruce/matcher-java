package com.scottlogic.training.orders;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * This is where you wire up your socket.io namespaces and events.
 *
 * Currently it just prints log messages on the events but you'll want to wire these events up to your matcher code.
 */
@Component
public class OrderModule {

    private final SocketIONamespace namespace;

    @Autowired
    public OrderModule(SocketIOServer server) {
        this.namespace = server.addNamespace("/order");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("order", OrderDTO.class, onOrderReceived());
    }

    private DataListener<OrderDTO> onOrderReceived() {
        return (client, data, ackSender) -> {
            log.debug("Client[{}] - Received order message '{}'", client.getSessionId().toString(), data);
            namespace.getBroadcastOperations().sendEvent("order", data);
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to order module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.debug("Client[{}] - Disconnected from order module.", client.getSessionId().toString());
        };
    }
    private static final Logger log = LoggerFactory.getLogger(OrderModule.class);
}
