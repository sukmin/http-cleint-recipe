package com.naver.msdt.httpclient.recipe;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * Created by john on 2016-11-04.
 */
public class JavaNet {

    @Test
    public void request_네이버() throws Exception{

        // given
        URL url = new URL("http://www.naver.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // when
        InputStream inputStream = conn.getInputStream();
        String result = intpuStreamToString(inputStream);

        // then
        System.out.println(result);

    }
    
    private String intpuStreamToString(InputStream is) throws Exception{
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) != -1;) {
            sb.append(new String(b, 0, n, Charset.forName("UTF-8")));
        }
        return sb.toString();
    }

}