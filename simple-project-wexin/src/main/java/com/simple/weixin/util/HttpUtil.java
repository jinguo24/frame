package com.simple.weixin.util;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 
 * @Description:
 * @author lining
 * @date 2015年5月5日 下午5:10:08
 */
public class HttpUtil {

	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	private static final int http_client_count = 100;
	private static final int http_connect_timeout = 1000*3;
	private static final int http_socket_timeout = 1000*5;
	private static final int http_request_connect_timeout = 1000*3;

	private static CloseableHttpClient httpClient;
	
	static {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setConnectTimeout(http_connect_timeout)	//连接超时
				.setSocketTimeout(http_socket_timeout)		//等待数据相应超时
				.setConnectionRequestTimeout(http_request_connect_timeout)	//从连接池获取连接超时
				.build();

		httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(http_client_count)
				.setDefaultRequestConfig(defaultRequestConfig)
				.addInterceptorLast(new HttpResponseInterceptor() {
					public void process(final HttpResponse response, final HttpContext context) 
							throws HttpException, IOException {
						HttpEntity entity = response.getEntity();
						Header ceheader = entity.getContentEncoding();
						if (ceheader != null && ceheader.getValue().toLowerCase().contains("gzip")) {
							response.setEntity(new GzipDecompressingEntity(response.getEntity()));
						}
					}
				}).build();
		log.info("httpclient [client count:{}, connect timeout:{}, soket timeout:{}, request connect timeout:{}]", 
				http_client_count, http_connect_timeout, http_socket_timeout, http_request_connect_timeout);
	}

	public static HttpResponse execute(HttpUriRequest request) throws Exception {
		HttpResponse httpResponse;
		httpResponse = httpClient.execute(request);
		return httpResponse;
	}
}
