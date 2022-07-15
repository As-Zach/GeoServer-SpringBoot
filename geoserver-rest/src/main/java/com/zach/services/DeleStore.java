package com.zach.services;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.encoder.datastore.GSGeoTIFFDatastoreEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 8095)
public class DeleStore {
    @PostMapping("/delestore")
    public String Delete(@RequestBody String input) throws MalformedURLException {
        //GeoServer的连接配置
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";

        String store_name ; //待创建和发布图层的数据存储名称store
        String ws ;     //待创建和发布图层的工作区名称workspace
        //判断工作区（workspace）是否存在，不存在则创建
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        List<String> workspaces = manager.getReader().getWorkspaceNames();
        input = input.substring(0,input.length()-1);
        RESTLayer restLayer = manager.getReader().getLayer(input);
        ws = manager.getReader().getResource(restLayer).getNameSpace();
        String StoreName = manager.getReader().getResource(restLayer).getStoreName();
        store_name = StoreName.split(":")[1];
        if (!workspaces.contains(ws)) {
            System.out.println("workspace不存在,ws : " + ws );
            return String.valueOf(workspaces.contains(ws));
        } else {
            System.out.println("workspace已经存在了,ws :" + ws);
        }
        GSGeoTIFFDatastoreEncoder gsGeoTIFFDatastoreEncoder = new GSGeoTIFFDatastoreEncoder(store_name);
        gsGeoTIFFDatastoreEncoder.setWorkspaceName(ws);
        boolean deleStore = manager.getStoreManager().remove(ws,gsGeoTIFFDatastoreEncoder,true);
        System.out.println("remove store : " + deleStore);
        return String.valueOf(deleStore);
    }

    @GetMapping("/tetstttja")
    public String test(){
        return "retur";
    }
    public static void main(String[] args){
        // BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境(spring boot中无需添加)
    }
}
