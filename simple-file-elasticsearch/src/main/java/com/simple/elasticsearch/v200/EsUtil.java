package com.simple.elasticsearch.v200;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class EsUtil {
	private static JestClient jestClient = EsFactory.getClient();
	/**
	 * 创建索引
	 * @param indexName
	 * @throws Exception
	 */
	public static void createIndex(String indexName) throws Exception {
		//Settings.Builder settingsBuilder = Settings.settingsBuilder();
		//settingsBuilder.put("number_of_shards",5);
		//settingsBuilder.put("number_of_replicas",1);
		//new CreateIndex.Builder("articles").settings(settingsBuilder.build().getAsMap()).build();
		JestResult result = jestClient.execute(new CreateIndex.Builder(indexName).build());
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("create index error: "+result.getErrorMessage());
		}
	}
	
	public static void insertOrUpdateDoc(String indexName,String type,Object o) throws Exception {
		long start = System.currentTimeMillis(); 
		Index index = null;
			index = new Index.Builder(o).index(indexName).type(type).build();
		JestResult result = jestClient.execute(index);
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("insertOrUpdate doc error: "+result.getErrorMessage());
		}
		long end = System.currentTimeMillis();  
		System.out.println("创建索引时间: 共用时间 -->> " + (end - start) + " 毫秒");  
	}
	
	public static void batchInsertOrUpdateDoc(String indexName,String type,List list) throws Exception {
		long start = System.currentTimeMillis();  
		Builder builder = new Bulk.Builder().defaultIndex(indexName).defaultType(type);
		for (int i = 0 ;i < list.size() ; i ++) {
			builder.addAction(new Index.Builder(list.get(i)).build());
		}
		Bulk bulk = builder.build();
		JestResult result = jestClient.execute(bulk); 
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("batchInsertOrUpdate doc error: "+result.getErrorMessage());
		}
		long end = System.currentTimeMillis();  
        System.out.println("批量创建索引时间:数据量是  " + list.size() + "记录,共用时间 -->> " + (end - start) + " 毫秒");  
	}
	
	public static void deleteDoc(String indexName,String type,String indexid) throws IOException {
		long start = System.currentTimeMillis();
		JestResult result = jestClient.execute(new Delete.Builder(indexid).index(indexName).type(type).build());
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("delete doc error: "+result.getErrorMessage());
		}
		long end = System.currentTimeMillis();  
        System.out.println("删除索引时间:,共用时间 -->> " + (end - start) + " 毫秒"); 
	}
	
	//@JestId
	public static void batchDeleteDoc(String indexName,String type,List<String> indexIdlist) throws Exception {
		long start = System.currentTimeMillis();  
		Builder builder = new Bulk.Builder().defaultIndex(indexName).defaultType(type);
		for (int i = 0 ;i < indexIdlist.size() ; i ++) {
			builder.addAction(new Delete.Builder(indexIdlist.get(i)).index(indexName).type(type).build());
		}
		Bulk bulk = builder.build();
		JestResult result = jestClient.execute(bulk); 
		if (result != null && !result.isSucceeded()) {
			 throw new RuntimeException("batchInsertOrUpdate doc error: "+result.getErrorMessage());
		}
		long end = System.currentTimeMillis();  
        System.out.println("批量删除索引时间:数据量是  " + indexIdlist.size() + "记录,共用时间 -->> " + (end - start) + " 毫秒");  
	}
	
	public static <T> List<T> searchList(String indexName,String type,QueryBuilder builder ,Class<T> classType) throws IOException {
		long start = System.currentTimeMillis();  
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
		searchSourceBuilder.query(builder);
		Search search = new Search.Builder(searchSourceBuilder.toString())
		                                // multiple index or types can be added.
		                                .addIndex(indexName)
		                                //.addIndex("tweet")
		                                .addType(type)
		                                .build();
		SearchResult searchResult = jestClient.execute(search);
		//List<SearchResult.Hit<T, Void>> hits = searchResult.getHits(classType);
		// or
		List<T> articles = searchResult.getSourceAsObjectList(classType);
		long end = System.currentTimeMillis();  
        System.out.println("批量查询时间:数据量是  " + articles.size() + "记录,共用时间 -->> " + (end - start) + " 毫秒");  
		return articles;
		
		
		
	}
	
	public static <T> T searchById(String indexName,String type,String indexId,Class<T> classType) throws IOException {
		long start = System.currentTimeMillis(); 
		Get get = new Get.Builder(indexName, indexId).type(type).build();
		JestResult result = jestClient.execute(get);
		T article = result.getSourceAsObject(classType);
		long end = System.currentTimeMillis();  
        System.out.println("查询时间:,共用时间 -->> " + (end - start) + " 毫秒"); 
		return article;
	}
	
	
	public static void main(String[] args) {
		try {
			//createIndex("testindex1");
			
			Test t = new Test();
			t.setTestId("6");
			t.setName("哈哈ssss");
			t.setDesc("哈哈是个好同志ssss");
			
			//insertOrUpdateDoc("testindex1","employ",t);
			
			List<Test> list = new ArrayList<Test>();
			Test t0 = new Test();
			t0.setTestId("1");
			t0.setName("金果ssss");
			t0.setDesc("金果是个好同志sss");
			list.add(t0);
			
			Test t1 = new Test();
			t1.setTestId("2");
			t1.setName("方圆ss");
			t1.setDesc("方圆是个好同志ss");
			list.add(t1);
			
			Test t2 = new Test();
			t2.setTestId("3");
			t2.setName("武鹏ss");
			t2.setDesc("武鹏是个好同志ss");
			list.add(t2);
			//batchInsertOrUpdateDoc("testindex1","employ",list);
			
			//deleteDoc("testindex1","employ","6");
			List<String> idlist = new ArrayList<String>();
			idlist.add("1");
			idlist.add("2");
			idlist.add("3");
			//batchDeleteDoc("testindex1","employ", idlist);
			Test st = searchById("testindex1","employ", "6", Test.class);
			System.out.println(st.getTestId()+">>"+st.getName()+">>"+st.getDesc());
			
			
			searchList("testindex1","employ", builder, Test.class);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
