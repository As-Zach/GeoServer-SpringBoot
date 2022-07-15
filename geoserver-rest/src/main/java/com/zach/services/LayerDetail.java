package com.zach.services;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import lombok.Data;
import org.apache.log4j.BasicConfigurator;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 8095)
public class LayerDetail {

    @Data
    public static class Bounds {
        private String SRS;
        private String NameSpace;
        private double MinX;
        private double MinY;
        private double MaxX;
        private double MaxY;


        public String getNameSpace() {
            return NameSpace;
        }

        public void setNameSpace(String nameSpace) {
            this.NameSpace = nameSpace;
        }
        public String getSRS() {
            return SRS;
        }

        public void setSRS(String SRS) {
            this.SRS = SRS;
        }

        public double getMinX() {
            return MinX;
        }

        public void setMinX(double minX) {
            MinX = minX;
        }

        public double getMinY() {
            return MinY;
        }

        public void setMinY(double minY) {
            MinY = minY;
        }

        public double getMaxX() {
            return MaxX;
        }

        public void setMaxX(double maxX) {
            MaxX = maxX;
        }

        public double getMaxY() {
            return MaxY;
        }

        public void setMaxY(double maxY) {
            MaxY = maxY;
        }
    }
    @PostMapping("/layerdetail")
    public Bounds getAll(@RequestBody String input) throws MalformedURLException {
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        input = input.substring(0,input.length()-1);
        RESTLayer restLayer = manager.getReader().getLayer(input);

        Bounds bounds = new Bounds();
        bounds.setSRS(manager.getReader().getResource(restLayer).getSRS());
        bounds.setNameSpace(manager.getReader().getResource(restLayer).getNameSpace());
        bounds.setMinX(manager.getReader().getResource(restLayer).getMinX());
        bounds.setMinY(manager.getReader().getResource(restLayer).getMinY());
        bounds.setMaxX(manager.getReader().getResource(restLayer).getMaxX());
        bounds.setMaxY(manager.getReader().getResource(restLayer).getMaxY());
        return bounds;
    }

    @GetMapping("/layernames")
    public static List<String> test01() throws MalformedURLException {
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";

        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        RESTLayerList restStore = manager.getReader().getLayers();
        System.out.println(restStore.getNames());
        return restStore.getNames();
    }
    public static void main(String[] args) throws MalformedURLException {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境(spring boot中无需添加)
        //List<String> test = test01();
    }
}
