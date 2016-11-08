package com.naver.msdt.httpclient.recipe;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * @author 권석민
 */
public class ConnectionManagerHttpClient {

	@Test
	public void 라우터값이_기본이_2라서_3번째요청부터는_행이걸린다는데_진짜_행이걸리는지_한번_보자() throws Exception {

		int threadCount = 10;
		String callUrl = "http://127.0.0.1:8080/longtime";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		MyThread[] threads = new MyThread[threadCount];

		for (int i = 0; i < threadCount; i++) {
			threads[i] = new MyThread(i, httpclient, callUrl);
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].join();
		}

	}

	@Test
	public void 진짜_행이_걸리는데_각종_타임아웃을_걸어보자() throws Exception {

		int threadCount = 10;
		String callUrl = "http://127.0.0.1:8080/longtime";
		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(6000)
				.setConnectTimeout(1000)
				.build();

		MyThread[] threads = new MyThread[threadCount];

		for (int i = 0; i < threadCount; i++) {
			threads[i] = new MyThread(i, httpclient, callUrl, requestConfig);
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].join();
		}

	}

	@Test
	public void 커넥션_매니져에서_행이_걸리는데_그걸_타임아웃해보자() throws Exception {

		int threadCount = 10;
		String callUrl = "http://127.0.0.1:8080/longtime";
		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(6000)
				.setConnectTimeout(1000)
				.setConnectionRequestTimeout(2000)
				.build();

		MyThread[] threads = new MyThread[threadCount];

		for (int i = 0; i < threadCount; i++) {
			threads[i] = new MyThread(i, httpclient, callUrl, requestConfig);
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].join();
		}

	}

	@Test
	public void 커스톰으로_만들되_그냥_빌드되어_나온놈은_어떨까() throws Exception {

		int threadCount = 10;
		String callUrl = "http://127.0.0.1:8080/longtime";
		CloseableHttpClient httpclient = HttpClients.custom().build();
		MyThread[] threads = new MyThread[threadCount];

		for (int i = 0; i < threadCount; i++) {
			threads[i] = new MyThread(i, httpclient, callUrl);
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].join();
		}

	}

	@Test
	public void 커넥션매니져를_설정해봄() throws Exception {

		int threadCount = 10;
		String callUrl = "http://127.0.0.1:8080/longtime";
		PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
		pcm.setMaxTotal(200);
		pcm.setDefaultMaxPerRoute(5);
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(pcm).build();
		MyThread[] threads = new MyThread[threadCount];

		for (int i = 0; i < threadCount; i++) {
			threads[i] = new MyThread(i, httpclient, callUrl);
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].join();
		}

	}

	@Test
	public void 어마어마하게_많이_호출하면_FD_오류가난다더데_재현을_못하겠음() throws Exception {

		int taskCount = 10000000;
		String callUrl = "http://127.0.0.1:8080/simple";
		CloseableHttpClient httpclient = HttpClients.custom().build();
		ExecutorService es = Executors.newFixedThreadPool(128);

		for (int i = 0; i < taskCount; i++) {
			es.submit(() -> {
				try {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					HttpGet httpGet = new HttpGet(callUrl);
					CloseableHttpResponse response = httpclient.execute(httpGet);
					HttpEntity httpEntity = response.getEntity();
					String result = EntityUtils.toString(httpEntity);
					response.close();
					stopWatch.stop();
					System.out.println("소요시간 : " + stopWatch.getTotalTimeSeconds() + ", 결과 : " + result);
				} catch (Exception e) {
					System.out.println(e);
				}

			});
		}

	}

	public class MyThread extends Thread {

		private CloseableHttpClient httpClient;
		private int threadNumber;
		private String callUrl;
		private RequestConfig requestConfig;

		public MyThread(int threadNumber, CloseableHttpClient httpClient, String callUrl) {
			this(threadNumber, httpClient, callUrl, null);
		}

		public MyThread(int threadNumber, CloseableHttpClient httpClient, String callUrl, RequestConfig requestConfig) {
			this.threadNumber = threadNumber;
			this.httpClient = httpClient;
			this.callUrl = callUrl;
			this.requestConfig = requestConfig;
		}

		@Override
		public void run() {

			try {
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				HttpGet httpGet = new HttpGet(callUrl);
				if (requestConfig != null) {
					httpGet.setConfig(requestConfig);
				}
				CloseableHttpResponse response = httpClient.execute(httpGet);
				HttpEntity httpEntity = response.getEntity();
				String result = EntityUtils.toString(httpEntity);
				response.close();
				stopWatch.stop();
				System.out.println(threadNumber + "번 쓰레드. 소요시간 : " + stopWatch.getTotalTimeSeconds() + ", 결과 : " + result);
			} catch (Exception e) {
				System.out.println(threadNumber + "번 쓰레드 오류 " + e);
			}
		}
	}

}
