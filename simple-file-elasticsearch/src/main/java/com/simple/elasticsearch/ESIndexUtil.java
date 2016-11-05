package com.simple.elasticsearch;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.alibaba.fastjson.JSON;

public class ESIndexUtil {
	private static JestClient jestClient = ServerClient.jestClient();  
	
	/**
	 * 创建索引
	 * @param indexName
	 * @throws Exception
	 */
	public static void createIndex(String indexName) throws Exception {
		CreateIndex createIndex = new CreateIndex(indexName);  
		JestResult result = jestClient.execute(createIndex);
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("create index error: "+result.getErrorMessage());
		}
	}
	
	public static void insertOrUpdateDoc(String indexName,String type,Object o) throws Exception {
		Index.Builder builder = new Index.Builder(o);
		Index index = builder.index(indexName).type(type).build();
		JestResult result = jestClient.execute(index);
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("insertOrUpdate doc error: "+result.getErrorMessage());
		}
	}
	
	public static void batchInsertOrUpdateDoc(String indexName,String type,List<Object> list) throws Exception {
		Bulk bulk = new Bulk(indexName, type); 
		for (int i = 0 ; i < list.size() ; i ++) {
			bulk.addIndex(new Index.Builder(list.get(i)).build()); 
		}
		JestResult result = jestClient.execute(bulk); 
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("batchInsertOrUpdate doc error: "+result.getErrorMessage());
		}
	}
	
	public static void deleteDoc(String indexName,String type,String indexId) throws Exception {
		Delete.Builder builder = new Delete.Builder(indexId);
		Delete delete = builder.index(indexName).type(type).build();
		JestResult result = jestClient.execute(delete);
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("delete doc error: "+result.getErrorMessage());
		}
	}
	
	 
	
	
	
	/** 
     * 创建es news索引 
     */  
    public static void builderSearchIndex() {  
        int num = 10000;  
        long start = System.currentTimeMillis();  
        try {  
            // 如果索引存在,删除索引  
            DeleteIndex deleteIndex = new DeleteIndex("testindex","test");  
            jestClient.execute(deleteIndex);  
            // 创建索引  
            CreateIndex createIndex = new CreateIndex("testindex");  
            jestClient.execute(createIndex);  
            // Bulk 两个参数1:索引名称2:类型名称(用文章(article)做类型名称)  
            Bulk bulk = new Bulk("testindex", "test");  
            // 添加添加100万条假数据去服务端(ES)  
            for (int i = 0; i < num; i++) {  
            	Map map = new HashMap();
            	map.put("id", i+1);
            	map.put("title", "hahhahahha_"+i);
            	map.put("desc", "desc_"+i);
                bulk.addIndex(new Index.Builder(map).build());  
            }  
            jestClient.execute(bulk); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        long end = System.currentTimeMillis();  
        System.out.println("创建索引时间:数据量是  " + num + "记录,共用时间 -->> " + (end - start) + " 毫秒");  
    }  
    
    /** 
     * 搜索新闻 
     *  
     * @param param 
     * @return 
     */  
    public static List searchsNews(String param) {  
        try {  
            long start = System.currentTimeMillis();  
            QueryBuilder queryBuilder = QueryBuilders.queryString(param);  
            Search search = new Search(Search.createQueryWithBuilder(queryBuilder.toString()));  
            search.addIndex("testindex");  
            search.addType("test");  
            JestResult result = jestClient.execute(search);  
            long end = System.currentTimeMillis();  
            System.out.println("在100万条记录中,搜索新闻,共用时间 -->> " + (end - start) + " 毫秒");  
            return result.getSourceAsObjectList(Map.class);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    public static void main(String[] args)throws Exception {
    	builderSearchIndex();
    	String param = "个人";  
        List news = searchsNews(param);  
        System.out.println("id   标题                                           内容");  
        for (int i = 0; i < news.size(); i++) {  
            Map article = (Map) news.get(i);  
            System.out.println(article.get("id") + "   " + article.get("title") + "   " + article.get("desc"));  
        }  
    }
    
}
