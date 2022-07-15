package com.zach.geoserver_rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;


public class AppTest 
{
   
	public static void main(String[] args) throws MalformedURLException {
		
		//geoserver连接配置
		String url = "http://localhost:8000/geoserver" ;
		String username = "admin" ; 
		String passwd = "geoserver" ;
		
		//postgis连接配置
		String postgisHost = "localhost" ;
		int postgisPort = 6666 ;
		String postgisUser = "lyf" ;
		String postgisPassword = "lyf" ;
		String postgisDatabase = "lyf" ;
		
		String ws = "lyf" ; //待创建和发布图层的workspace
		String store_name = "lyf" ; //数据库连接要创建的store
		String table_name = "lyf" ; // 数据库要发布的表名称,后面图层名称和表名保持一致
		
		URL u = new URL(url);
		
		
		GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
		
		GeoServerRESTPublisher publisher = manager.getPublisher() ;
		//创建一个workspace
		List<String> workspaces = manager.getReader().getWorkspaceNames();
		if(!workspaces.contains(ws)){
			boolean createws = publisher.createWorkspace(ws);
			
			System.out.println("create ws : " + createws);
		}else {
			System.out.println("workspace已经存在了,ws :" + ws);
		}

	
		
		//store 包含和workspace一致，一个ws一个连接即可
		RESTDataStore restStore = manager.getReader().getDatastore(ws, store_name);
		
		if(restStore == null){
		
			
			GSPostGISDatastoreEncoder store = new GSPostGISDatastoreEncoder(store_name);
            store.setHost(postgisHost);//设置url
            store.setPort(postgisPort);//设置端口
            store.setUser(postgisUser);// 数据库的用户名
            store.setPassword(postgisPassword);// 数据库的密码
            store.setDatabase(postgisDatabase);// 那个数据库;
            store.setSchema("public"); //当前先默认使用public这个schema
            store.setConnectionTimeout(20);// 超时设置
            //store.setName(schema);
            store.setMaxConnections(20); // 最大连接数
            store.setMinConnections(1);		// 最小连接数
            store.setExposePrimaryKeys(true);
            boolean createStore = manager.getStoreManager().create(ws, store);
			
            System.out.println("create store : " + createStore);
            
		} else {
			System.out.println("数据store已经发布过了,store:" + store_name);
		}
		
		//发布
		
		RESTLayer layer = manager.getReader().getLayer(ws, table_name);
		
		if(layer == null){
			GSFeatureTypeEncoder pds = new GSFeatureTypeEncoder();
			pds.setTitle(table_name);
			pds.setName(table_name);
			pds.setSRS("EPSG:4326");
			  
			GSLayerEncoder layerEncoder = new GSLayerEncoder();
			
			boolean publish = manager.getPublisher().publishDBLayer(ws, store_name,  pds, layerEncoder);
			
			System.out.println("publish : " + publish);
		}else {
			System.out.println("表已经发布过了,table:" + table_name);
		}
	
		
	}
	
}
