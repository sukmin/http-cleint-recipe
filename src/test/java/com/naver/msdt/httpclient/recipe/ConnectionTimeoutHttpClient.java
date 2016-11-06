package com.naver.msdt.httpclient.recipe;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runners.model.TestTimedOutException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by john on 2016-11-05.
 */
public class ConnectionTimeoutHttpClient {

    @Test(expected = UnknownHostException.class)
    public void 존재하지_않는_호스트를_호출하는_경우() throws Exception{
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.mynaver999.com");

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();
    }

    @Test(expected = HttpHostConnectException.class)
    public void 커넥션시_해당_포트가_리스닝을_하고있지_않은_경우() throws Exception{
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:59999");
        // 의도적으로 다른 포트를 사용

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();
    }

    @Test(timeout = 10000)
    public void 커넥션시_해당_포트가_리슨은_하고있으나_응답이_없는_경우_네이버9999() throws Exception{
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.naver.com:9999");

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();

        // then
        assertEquals("ok",result);
        System.out.println(result);
    }

    @Test(expected = ConnectTimeoutException.class)
    public void 커넥션타임아웃설정_커넥션시_해당_포트가_리슨은_하고있으나_응답이_없는_경우_네이버9999() throws Exception{
        // given
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.naver.com:9999");

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .build();

        httpGet.setConfig(requestConfig);

        // when
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        response.close();
    }

}
