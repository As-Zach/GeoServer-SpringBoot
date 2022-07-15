package com.zach.geoserver_rest;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.encoder.datastore.GSGeoTIFFDatastoreEncoder;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class test01 {
    public static void main(String[] args) throws MalformedURLException {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境(spring boot中无需添加)

        //GeoServer的连接配置
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";

        String store_name; //待创建和发布图层的数据存储名称store
        String ws = "test01";     //待创建和发布图层的工作区名称workspace

        //判断工作区（workspace）是否存在，不存在则创建
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        List<String> workspaces = manager.getReader().getWorkspaceNames();
        if (!workspaces.contains(ws)) {
            System.out.println("workspace不存在,ws : " + ws );
        } else {
            System.out.println("workspace已经存在了,ws :" + ws);
        }
        store_name = "hebei";


            GSGeoTIFFDatastoreEncoder gsGeoTIFFDatastoreEncoder = new GSGeoTIFFDatastoreEncoder(store_name);
            gsGeoTIFFDatastoreEncoder.setWorkspaceName(ws);

            boolean deleStore = manager.getStoreManager().remove(ws,gsGeoTIFFDatastoreEncoder,true);

            System.out.println("remove store : " + deleStore);


    }
}
