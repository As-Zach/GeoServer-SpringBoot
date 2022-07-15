package com.zach.geoserver_rest;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import org.apache.log4j.BasicConfigurator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 8095)
public class test {
    @GetMapping("/testtt")
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

    public static class Bounds {
        private String SRS;
        private double MinX;
        private double MinY;
        private double MaxX;
        private double MaxY;

    }

    @GetMapping("/testtttt")
    public List<Bounds> getAll() throws MalformedURLException {
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        RESTLayer restLayer = manager.getReader().getLayer("test:beijing");
        //List<Double> detail = new ArrayList<>();
        Bounds bounds = new Bounds();
        bounds.SRS=manager.getReader().getResource(restLayer).getCRS();
        bounds.MinX=manager.getReader().getResource(restLayer).getMinX();
        bounds.MinY=manager.getReader().getResource(restLayer).getMinY();
        bounds.MaxX=manager.getReader().getResource(restLayer).getMaxX();
        bounds.MaxY=manager.getReader().getResource(restLayer).getMaxY();
        return (List<Bounds>) bounds;
    }

    public static void main(String[] args) throws MalformedURLException {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境(spring boot中无需添加)
        //List<String> test = test01();
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        RESTLayer restLayer = manager.getReader().getLayer("test:beijing");
        //List<Double> detail = new ArrayList<>();
        Bounds bounds = new Bounds();
        bounds.SRS=manager.getReader().getResource(restLayer).getCRS();
        bounds.MinX=manager.getReader().getResource(restLayer).getMinX();
        bounds.MinY=manager.getReader().getResource(restLayer).getMinY();
        bounds.MaxX=manager.getReader().getResource(restLayer).getMaxX();
        bounds.MaxY=manager.getReader().getResource(restLayer).getMaxY();
        //System.out.println(manager.getReader().getResource(restLayer).getMinX());
//        detail.add(manager.getReader().getResource(restLayer).getMinX());
//        detail.add(manager.getReader().getResource(restLayer).getMinY());
//        detail.add(manager.getReader().getResource(restLayer).getMaxX());
//        detail.add(manager.getReader().getResource(restLayer).getMaxY());
        System.out.println(bounds);
    }
}
