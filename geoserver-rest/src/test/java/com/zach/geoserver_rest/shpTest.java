package com.zach.geoserver_rest;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder;
import org.apache.log4j.BasicConfigurator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URI;
import java.net.URL;

@RestController
//@RequestMapping("/shp")
public class shpTest {
    @GetMapping("/shp")
    public String index(){
        return "shpTest !!!";
    }

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境
        //GeoServer连接配置
        String url = "http://localhost:8080/geoserver";
        String userName = "admin";
        String passWord = "geoserver";

        String workSpace;     //创建的工作区名称
        String storeName; //创建的数据存储名称
        //待发布shp数据路径与矢量数据包路径
        String filePath;
        String zipPath;
        File zipfile;

        //与GeoServer连接，进行管理
        GeoServerRESTManager manager = new GeoServerRESTManager(new URL(url), userName, passWord);
        GeoServerRESTPublisher publisher = manager.getPublisher();

        /*遍历文件夹路径下.tif文件*/
        String path = "F:\\Document\\Graduation Project\\MapData\\mapData_test\\Shp";		//要遍历的路径
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        for(File f : fs)
        {
            if(f.isFile())
            {
                if (f.getName().endsWith(".zip")) {
                    // 就输出该文件的绝对路径
                    System.out.println(f.getName().replaceAll("[.][^.]+$", ""));
                    workSpace = "Shp_"+ f.getName().replaceAll("[.][^.]+$", "");
                    //创建工作区
                    boolean createWorkSpace = publisher.createWorkspace(workSpace);
                    System.out.println("create ws " + createWorkSpace);

                    storeName = f.getName().replaceAll("[.][^.]+$", "");
                    zipPath = f.getAbsolutePath();
                    filePath = zipPath.replaceAll("[.][^.]+$", "");
                    zipfile = new File(zipPath);
                    URI resource = zipfile.toURI();


                    //shp字符集


                    //创建shp数据存储
                    GSShapefileDatastoreEncoder gsShapefileDatastoreEncoder= new GSShapefileDatastoreEncoder(storeName,new URL("file:" + path));
                    boolean createStore = manager.getStoreManager().create(workSpace, gsShapefileDatastoreEncoder);
                    System.out.println("create shp store :" + createStore);

                    //发布shp图层（如果没有目标数据存储则会先自动创建该命名数据存储再发布）
                    boolean publish = manager.getPublisher().publishShpCollection(workSpace, storeName, resource);
                    System.out.println("publish shp :" + publish);
                }

            }
        }
        //读取目标矢量数据存储
        //RESTDataStore restDataStore = manager.getReader().getDatastore(workSpace, storeName);
    }
}
