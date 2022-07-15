package com.zach.services.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  HTTP请求工具类
 * </p>
 *
 * @author liugh
 * @since 2018/3/20
 */
public class HttpLayerPropertyUtil {

    /**
     * 获取图层所有属性
     * @param layerPreviewURL
     * @return
     */
    public static List<Map<String,String>> getLayerProperty(String layerPreviewURL){
        List<Map<String,String>>  retList = new ArrayList();
        try {
            Document doc = Jsoup.connect(layerPreviewURL).get();
            Elements select = doc.select("th");
            Elements select1 = doc.select("td");
            for (int i = 0; i < select.size(); i++) {
                HashMap<String,String> tempMap = new HashMap(16);
                tempMap.put(cutValue(select.get(i).toString()), cutValue(select1.get(i).toString()));
                retList.add(tempMap);
            }
            return retList;
        } catch (IOException e) {
            System.out.println("页面获取异常！");
            e.printStackTrace();
        }
        return null;
    }

    private static String cutValue(String s){
        String substring = s.substring(4,s.indexOf("</"));
        return substring;
    }
}
