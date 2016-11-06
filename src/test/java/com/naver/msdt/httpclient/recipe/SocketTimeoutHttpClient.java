package com.naver.msdt.httpclient.recipe;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by john on 2016-11-06.
 */
public class SocketTimeoutHttpClient {

    @Test
    public void 대기_5초() throws Exception {
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/longtime");

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();

        // then
        assertEquals("ok", result);
        System.out.println(result);
    }

    @Test(expected = SocketTimeoutException.class)
    public void 소켓타임아웃_3초_대기5초() throws Exception {
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/longtime");

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .build();

        httpGet.setConfig(requestConfig);

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();

        // then
        assertEquals("ok", result);
        System.out.println(result);
    }


    @Test
    public void MY_WEB_SERSER_테스트() throws Exception {
        // given
        StopWatch stopWatch = new StopWatch();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:9999");

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .build();

        httpGet.setConfig(requestConfig);

        // when
        stopWatch.start(); // StopWath Start
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        stopWatch.stop(); // StopWath Stop
        response.close();

        // then
        assertEquals("ok", result);
        System.out.println("걸린시간 " + stopWatch.getTotalTimeSeconds() + " 초");
    }

    @Test(expected = SocketTimeoutException.class)
    public void 타임아웃500ms_MY_WEB_SERSER_테스트() throws Exception {
        // given
        StopWatch stopWatch = new StopWatch();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:9999");

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(500)
                .build();

        httpGet.setConfig(requestConfig);

        // when
        stopWatch.start(); // StopWath Start
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        stopWatch.stop(); // StopWath Stop
        response.close();

        // then
        assertEquals("ok", result);
        System.out.println("걸린시간 " + stopWatch.getTotalTimeSeconds() + " 초");
    }

    @Test(expected = SocketTimeoutException.class)
    public void POST_요청에_타임아웃_걸기() throws Exception {
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/longtime");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("service", "umon"));
        nameValuePairs.add(new BasicNameValuePair("member", "hanzo"));
        nameValuePairs.add(new BasicNameValuePair("member", "genzi"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .build();

        httpPost.setConfig(requestConfig);

        // when
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();

        // then
        assertEquals("{\"service\":[\"umon\"],\"member\":[\"hanzo\",\"genzi\"]}", result);
        System.out.println(result);
    }

}
