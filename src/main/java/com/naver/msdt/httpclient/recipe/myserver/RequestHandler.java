package com.naver.msdt.httpclient.recipe.myserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by john on 2016-11-06.
 */
public class RequestHandler extends Thread {

    private static final int DELAY = 1000;

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {

        try (OutputStream out = connection.getOutputStream();
             DataOutputStream dos = new DataOutputStream(out)
        ) {

            Thread.sleep(DELAY);
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.flush();

            Thread.sleep(DELAY);
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.flush();

            Thread.sleep(DELAY);
            byte [] body = "ok".getBytes();
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.flush();

            Thread.sleep(DELAY);
            dos.writeBytes("\r\n");
            dos.flush();

            Thread.sleep(DELAY);
            dos.write(body, 0, body.length);
            dos.flush();


        } catch (Exception e) {

        }

    }

}
