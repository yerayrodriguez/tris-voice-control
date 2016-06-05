package com.trisvc.modules.openhab;

import javax.websocket.ClientEndpoint;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

@ClientEndpoint
public class OpenHabStatusCheck extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig EndpointConfig) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                OpenHabItem o = OpenHabUtil.unmarshalItem(message);
                OpenHab.updateState(o);
            }
        });
    }



}