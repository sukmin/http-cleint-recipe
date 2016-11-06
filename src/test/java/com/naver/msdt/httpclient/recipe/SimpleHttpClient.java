package com.naver.msdt.httpclient.recipe;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by john on 2016-11-04.
 */
public class SimpleHttpClient {

    // 간단한 GET 호출
    @Test
     public void request_simple_get() throws Exception{
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/simple");

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();

        // then
        assertEquals("ok",result);
        System.out.println(result);
    }

    // 간단한 POST 호출
    @Test
    public void request_simple_post() throws Exception{
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/simple");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("service", "umon"));
        nameValuePairs.add(new BasicNameValuePair("member", "hanzo"));
        nameValuePairs.add(new BasicNameValuePair("member", "genzi"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        // when
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();

        // then
        assertEquals("{\"service\":[\"umon\"],\"member\":[\"hanzo\",\"genzi\"]}",result);
        System.out.println(result);
    }
}
