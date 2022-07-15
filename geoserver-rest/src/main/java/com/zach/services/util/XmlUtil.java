package com.zach.services.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;


/**
 * <p>
 *  xml解析工具类
 * </p>
 *
 * @author liugh
 * @since 2018/4/3
 */
public class XmlUtil {

    private static String GEO_SERVER_PATH = ResourceBundle.getBundle("constant").getString("geoServer-dir");

    private static String BUNDLE_URL = ResourceBundle.getBundle("constant").getString("geoServer-url");

    private static String XML_ELEMENT_NAME="latLonBoundingBox";
    public static void main(String[] args)throws Exception {
        getMapUrl("1522723368940","unitMap");
    }

    //获取图集发布地址
    public static String  getMapUrl(String layerId,String workspace)throws Exception{
        File file =new File(GEO_SERVER_PATH+File.separator+workspace);
        String[] fileList = file.list();
        StringBuilder mapUrl = new StringBuilder();
        mapUrl.append(BUNDLE_URL+workspace)
                .append("/wms?service=WMS&version=1.1.0&request=GetMap&layers=").append(workspace+":"+layerId).append("&styles=&bbox=");
        if(!ComUtil.isEmpty1(fileList)){
            for (String fileName:fileList) {
                if(fileName.equals(layerId)){
                    String []  coordinates = readXMLDocument(layerId,workspace);
                    mapUrl.append(coordinates[0]+","+coordinates[2]+","+coordinates[1]+","+coordinates[3]).append("&width=768&height=437&srs=").append(coordinates[4]);
                }
            }
        }else{
            return null;
        }
        return  mapUrl.toString();
    }

    private static String []  readXMLDocument(String layerId, String workspace){
        File file = new File(GEO_SERVER_PATH+File.separator+workspace+
                File.separator+layerId+File.separator+layerId+File.separator+"featuretype.xml");
        if (!file.exists()) {
            try {
                throw new IOException("Can't find the path");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //创建SAXReader对象
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            //读取文件 转换成Document
            document = saxReader.read(file);
            //获取根节点元素对象  遍历当前节点下的所有节点
            for (Iterator iter = document.getRootElement().elementIterator(); iter.hasNext();){
                //获取节点
                Element e1 = (Element) iter.next();

                //如果过节点的名称等于beanName那么继续进入循环读取beanName节点下的所有节点
                if(e1.getName().equalsIgnoreCase(XML_ELEMENT_NAME)){
                    String [] ss = new String[5];
                    int i =0;
                    //遍历beanName当前节点下的所有节点
                    for (Iterator iter1 = e1.elementIterator(); iter1.hasNext();){
                        Element e2 = (Element) iter1.next();
                        ss[i]= e2.getStringValue();
                        i++;
                    }
                    return ss;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
