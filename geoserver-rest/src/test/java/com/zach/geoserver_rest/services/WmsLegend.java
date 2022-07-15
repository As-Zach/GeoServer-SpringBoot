package com.zach.geoserver_rest.services;

import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/16.
 */
@WebServlet(description = "wms legend", urlPatterns =  {"/legend"})
public class WmsLegend extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "http://192.168.0.117:8081/geoserver";
        try {
            GeoServerRESTReader reader = new GeoServerRESTReader(url, "admin", "geoserver");
            String workspace = "guotu";
            RESTLayer restLayer = reader.getLayer(workspace, "csght");
            String styleName = restLayer.getDefaultStyle();
            String sld = reader.getSLD(styleName);
            if(sld!=null){
                StringReader sr = new StringReader(sld);
                InputSource is = new InputSource(sr);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = (Document) builder.parse(is);
                NodeList nodeNames = doc.getElementsByTagName("sld:Name");
                NodeList nodeTitles = doc.getElementsByTagName("ogc:Literal");
                NodeList nodeFields = doc.getElementsByTagName("ogc:PropertyName");
                Map<String, Object> rules = new HashMap<String, Object>();
                List<Map<String, Object>> legends = new ArrayList<Map<String, Object>>();
                rules.put("field", nodeFields.item(0).getTextContent().toLowerCase());
                for(int i=0;i<nodeTitles.getLength();i++){
                    Node name = nodeNames.item(i+2);
                    Node title = nodeTitles.item(i);
                    Map<String, Object> legend = new HashMap<String, Object>();
                    legend.put("title",title.getTextContent());
                    legend.put("rule",name.getTextContent());
                    legends.add(legend);
                }
                rules.put("rules",legends);
                JSON json = JSONObject.fromObject(rules);
                response.setContentType("text/html;charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println(json);
                out.flush();
                out.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    public static void main(String[] args) {
        String url = "http://192.168.0.117:8081/geoserver";
        try {
            GeoServerRESTReader reader = new GeoServerRESTReader(url, "admin", "geoserver");
            String workspace = "guotu";
            RESTLayer restLayer = reader.getLayer(workspace, "csght");
            String styleName = restLayer.getDefaultStyle();
            String sld = reader.getSLD(styleName);
            if(sld!=null){
                StringReader sr = new StringReader(sld);
                InputSource is = new InputSource(sr);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = (Document) builder.parse(is);
                NodeList nodeNames = doc.getElementsByTagName("sld:Name");
                NodeList nodeTitles = doc.getElementsByTagName("ogc:Literal");
                NodeList nodeFields = doc.getElementsByTagName("ogc:PropertyName");
                Map<String, Object> rules = new HashMap<String, Object>();
                List<Map<String, Object>> legends = new ArrayList<Map<String, Object>>();
                rules.put("field", nodeFields.item(0).getTextContent().toLowerCase());
                for(int i=0;i<nodeTitles.getLength();i++){
                    Node name = nodeNames.item(i+2);
                    Node title = nodeTitles.item(i);
                    Map<String, Object> legend = new HashMap<String, Object>();
                    legend.put("title",title.getTextContent());
                    legend.put("rule",name.getTextContent());
                    legends.add(legend);
                }
                rules.put("rules",legends);
                JSON json = JSONObject.fromObject(rules);
                System.out.println(json);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}