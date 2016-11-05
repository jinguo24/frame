package com.simple.elasticsearch.v200;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class EsFactory {
	
	private static JestClient client;
	private static final String URL = "http://114.215.132.86:9200";
	
	
	public static JestClient getClient() {
		if ( null == client) {
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig
		                        .Builder(URL)
		                        .multiThreaded(true)
		                        .build());
			client = factory.getObject();
		}
		return client;
	}
}
