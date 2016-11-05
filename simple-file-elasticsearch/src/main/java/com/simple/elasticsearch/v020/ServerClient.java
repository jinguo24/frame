package com.simple.elasticsearch.v020;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.simple.common.config.EnvPropertiesConfiger;

import io.searchbox.client.JestClient;  
import io.searchbox.client.JestClientFactory;  
import io.searchbox.client.config.ClientConfig;  
import io.searchbox.client.config.ClientConstants; 

public class ServerClient {
	
	
	/** 
     * 静态,单例... 
     */  
    private static JestClient JestClient ;  
	
    
    
    /** 
     * 配置jest客户端,到时使用spring时,可以用配置方式 ,现在暂时使用new ... 
     *  
     * @return 
     */  
    private static ClientConfig clientConfig() {  
        // es的服务端地址,暂时我是用我虚拟机的(ubuntu)做服务器  
        String connectionUrl = "http://114.215.132.86:9200";// 一般都是9200端口  
        ClientConfig clientConfig = new ClientConfig();  
        // 当你用集群时,就有可能会有多个es的服务端,这里我暂时没有集群  
        LinkedHashSet servers = new LinkedHashSet();  
        servers.add(connectionUrl);  
        clientConfig.getServerProperties().put(ClientConstants.SERVER_LIST, servers);  
        clientConfig.getClientFeatures().put(ClientConstants.IS_MULTI_THREADED, false);  
        return clientConfig;  
    }  
    
    /** 
     * 获取一个jest的对象 
     *  
     * @return 
     */  
    public static JestClient jestClient() {  
        JestClientFactory factory = new JestClientFactory();  
        factory.setClientConfig(clientConfig());  
        if (JestClient == null) {  
            JestClient = factory.getObject();  
        }  
        return JestClient;  
    }  
    
	
}
