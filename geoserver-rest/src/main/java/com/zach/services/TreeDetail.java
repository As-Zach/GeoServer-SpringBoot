package com.zach.services;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import lombok.Data;
import org.apache.log4j.BasicConfigurator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 8095)
public class TreeDetail {

    @Data
    public static class Tree{
        private String id;
        private String text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Tree(String id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    @GetMapping("/treelist")
    public static List<Tree> test01() throws MalformedURLException {
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";
        List list = new ArrayList();
        list.add(new Tree("00","GeoServer"));
        list.add(new Tree("000","elevation"));
        list.add(new Tree("001","shp"));
        list.add(new Tree("002","map"));
        list.add(new Tree("003","cite"));
        //if (!list.contains())
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        RESTLayerList restStore = manager.getReader().getLayers();
        List<String> names = restStore.getNames();
        list.add(new Tree("0011","China"));
        list.add(new Tree("0012","Russia"));
        for (int i=0;i<names.size();i++){
            String[] arr = names.get(i).split(":");
            String ws = arr[0];
            String layer = arr[1];
            if (ws.equals("elevation"))
                list.add(new Tree("0000",layer));
            else if (ws.equals("shp")){
                if (layer.substring(0,5).equals("china")){

                    list.add(new Tree("00110",layer));
                }else if (layer.substring(0,6).equals("taiwan")){

                    list.add(new Tree("00121",layer));
                }else
                list.add(new Tree("0010",layer));
            }

            else if (ws.equals("map"))
                list.add(new Tree("0020",layer));
            else if (ws.equals("cite"))
                list.add(new Tree("0030",layer));
            System.out.println(ws +"  "+layer);
        }
        System.out.println(names);
//        System.out.println(list);
        return list;
    }

    public static void main(String[] args) throws MalformedURLException {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境(spring boot中无需添加)
        test01();
    }
}
