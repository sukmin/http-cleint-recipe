package com.naver.msdt.httpclient.recipe.myserver;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by john on 2016-11-06.
 */
@Component
public class MyServer extends Thread {

    private static int MY_SERVER_PORT = 9999;

    @Override
    public void run() {
        try (ServerSocket listenSocket = new ServerSocket(MY_SERVER_PORT)) {
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
