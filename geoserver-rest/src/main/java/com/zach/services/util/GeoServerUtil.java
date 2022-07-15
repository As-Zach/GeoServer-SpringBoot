package com.zach.services.util;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import org.apache.commons.httpclient.NameValuePair;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeoServerUtil {
    public static final String RESTURL;

    public static final String RESTUSER;

    public static final String RESTPW;

    public static final String GS_VERSION;

    public static java.net.URL URL;

    public static GeoServerRESTManager manager;

    public static GeoServerRESTReader reader;

    public static GeoServerRESTPublisher publisher;

    private static ResourceBundle bundle = ResourceBundle.getBundle("constant");

    //初始化用户名密码赋值,发布图集时会进行身份认证
    static {
        RESTURL = getenv("gsmgr_resturl", "http://localhost:18080/geoserver/");
        RESTUSER = getenv("gsmgr_restuser","admin");
        RESTPW = getenv("gsmgr_restpw", "geoserver");
        GS_VERSION = getenv("gsmgr_version", "2.11.2");
        try {
            URL = new URL(RESTURL);
            manager = new GeoServerRESTManager(URL, RESTUSER, RESTPW);
            reader = manager.getReader();
            publisher = manager.getPublisher();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //获取环境信息
    private static String getenv(String envName, String envDefault) {
        String env = System.getenv(envName);
        String prop = System.getProperty(envName, env);
        return prop != null ? prop : envDefault;
    }

    public  static boolean  publishShpAndReloadStore(String workspace,String zipFilePath,String storeName,String layerName,String styleType,String coordinateSystem) throws  Exception{
        //坐标系,判断是否为空
        if(ComUtil.isEmpty(coordinateSystem)){
            coordinateSystem=GeoServerRESTPublisher.DEFAULT_CRS;
        }
        //存在相应的工作区
        if(!reader.existsWorkspace(workspace)){
            publisher.createWorkspace(workspace);
        }
        boolean published;
        if(Constant.AtlasStyleType01.AtlasStyleType.equals(styleType)){
            published = publisher.publishShp(workspace, storeName, layerName, new File(zipFilePath),coordinateSystem,
                    new NameValuePair[]{new NameValuePair("charset", "GBK")});
        }else{
            //读取style文件
            String styleFile = bundle.getString("geoServer-dir")+"/"+styleType+".sld";
            File file  = new File(styleFile);
            String strStyle = "style"+styleType;
            //是否已经发布了改style
            if(!reader.existsStyle(workspace,strStyle)){
                publisher.publishStyleInWorkspace(workspace,file,strStyle);
            }
            //创建发布类,放入用户名密码和url
            GeoServerRESTPublisher geoServerRESTPublisher  =new GeoServerRESTPublisher(RESTURL,RESTUSER,RESTPW);
            //工作区      数据存储名称
            published =geoServerRESTPublisher.publishShp( workspace,  storeName, new NameValuePair[]{new NameValuePair("charset", "GBK")},
                    //图层名称               指定用于发布资源的方法
                    layerName, GeoServerRESTPublisher.UploadMethod.FILE,
                    //        zip图集的地址           坐标系         样式
                    new File(zipFilePath).toURI(),coordinateSystem, strStyle);
        }
        return published;
    }

    public static boolean unPublishShpAndReloadStore(String workspace,String storeName){
        return publisher.removeLayer(workspace,storeName);
    }


}
