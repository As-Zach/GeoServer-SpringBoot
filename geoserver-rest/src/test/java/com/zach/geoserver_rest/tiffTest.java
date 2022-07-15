package com.zach.geoserver_rest;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.encoder.datastore.GSGeoTIFFDatastoreEncoder;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.net.URL;
import java.util.List;

public class tiffTest {
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境
        //GeoServer的连接配置
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";

        String ws = "test01";     //待创建和发布图层的工作区名称workspace
        //String store_name = "tiffTest"; //待创建和发布图层的数据存储名称store

        //判断工作区（workspace）是否存在，不存在则创建
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        GeoServerRESTPublisher publisher = manager.getPublisher();
        List<String> workspaces = manager.getReader().getWorkspaceNames();
        if (!workspaces.contains(ws)) {
            boolean createws = publisher.createWorkspace(ws);
            System.out.println("create ws : " + createws);
        } else {
            System.out.println("workspace已经存在了,ws :" + ws);
        }

        //判断数据存储（datastore）是否已经存在，不存在则创建
        //String fileName = "F:\\Document\\Graduation Project\\MapData\\Tiff\\beijing.tif";


        /*遍历文件夹路径下tiff文件*/
        String path = "F:\\Document\\Graduation Project\\MapData\\Tiff";		//要遍历的路径
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中

        String store_name; //待创建和发布图层的数据存储名称store
        String fileName;
        for(File f : fs)
        {
            if(f.isFile())
            {
                if (f.getName().endsWith(".tif")) {
                    // 就输出该文件的绝对路径
                    //System.out.println(f.getAbsolutePath());
                    store_name = f.getName().replaceAll("[.][^.]+$", "");
                    fileName = f.getAbsolutePath();
                    RESTDataStore restStore = manager.getReader().getDatastore(ws, store_name);
                    if (restStore == null) {
                        GSGeoTIFFDatastoreEncoder gsGeoTIFFDatastoreEncoder = new GSGeoTIFFDatastoreEncoder(store_name);
                        gsGeoTIFFDatastoreEncoder.setWorkspaceName(ws);
                        gsGeoTIFFDatastoreEncoder.setUrl(new URL("file:" + fileName));
                        boolean createStore = manager.getStoreManager().create(ws, gsGeoTIFFDatastoreEncoder);
                        System.out.println("create store (TIFF文件创建状态) : " + createStore);
                        boolean publish = manager.getPublisher().publishGeoTIFF(ws, store_name, new File(fileName));
                        System.out.println("publish (TIFF文件发布状态) : " + publish);
                        //System.out.println(f.getName() + " : 发布成功！");
                    } else {
                        System.out.println("数据存储已经存在了,store:" + store_name);
                    }
                }
            }
        }

        //验证tiff文件是否全部上传至GeoServer
        /*for (File f : fs){
            if(f.isFile()) {
                if (f.getName().endsWith(".tif")) {
                    store_name = f.getName().replaceAll("[.][^.]+$", "");
                    RESTDataStore restStore = manager.getReader().getDatastore(ws, store_name);
                    if (restStore != null){
                        System.out.println(f.getName() + " : 发布成功！");
                    }
                    else {
                        System.out.println("[warn] "+f.getName() + " : 发布失败！");
                    }
                }
            }
        }*/
    }
}