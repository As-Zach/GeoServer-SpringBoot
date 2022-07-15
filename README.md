# GeoServer二次开发之 REST自动部署

## REST架构规范

表现层状态转换简称为REST，是一种万维网的软件设计架构，可实现不同软件在网络中简易、高效的信息传递功能。REST是根基于HTTP协议之上而确定的一组属性和约束，通过识别客户端所发出的统一资源标识符和网络资源请求，将预先定义好的操作集合化，为用户在互联网中交互资源提供协助。此外用户亦可根据各自需求自定义操作集合，以满足不同场景、不同邻域的实际开发与应用。

REST设计架构的资源表现形式多为XML和HTML格式，并由URI指定资源，通过采用客户端-服务器结构、无状态模式、统一接口和分层系统对实际资源的请求进行严格规范，满足主流网络架构的设计原则。



## GeoServer REST在Github中的实例

GeoServer-Manager  https://github.com/geosolutions-it/geoserver-manager/wiki

## Maven 依赖

在Maven项目的pom.xml中配置依赖。
依赖库除了GeoServer-Manager以外，还需要GeoServer-Manager用到的一些外部依赖。

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.jjxliu.geoserver</groupId>
    <artifactId>geoserver_rest</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>7</source>
                    <target>7</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
    <packaging>pom</packaging>

    <name>geoserver_rest</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>

        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>jcl-over-slf4j</artifactId>
            <version>1.7.5</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.5</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.5</version>
        </dependency>
        <!--		<dependency>-->
        <!--			<groupId>it.geosolutions</groupId>-->
        <!--			<artifactId>geoserver-manager</artifactId>-->
        <!--			<version>1.7.0</version>-->
        <!--		</dependency>-->

        <dependency>
            <groupId>it.geosolutions</groupId>
            <artifactId>geoserver-manager</artifactId>
            <version>1.8-SNAPSHOT</version>
        </dependency>

        <!-- JSONObject对象依赖的jar包 开始 -->
        <dependency>
            <groupId>commons-beanutils</groupId>
            <artifactId>commons-beanutils</artifactId>
            <version>1.9.3</version>
        </dependency>
        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.1</version>
        </dependency>
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
            <version>2.6</version>
        </dependency>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.ezmorph</groupId>
            <artifactId>ezmorph</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>2.4</version>
            <classifier>jdk15</classifier>
            <!-- jdk版本 -->
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.0.1</version>
        </dependency>

        <!-- ant -->
        <dependency>
            <groupId>org.apache.ant</groupId>
            <artifactId>ant</artifactId>
            <version>1.9.7</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.13.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.dom4j/dom4j -->
        <dependency>
            <groupId>org.dom4j</groupId>
            <artifactId>dom4j</artifactId>
            <version>2.1.3</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.8.1</version>
        </dependency>
        <dependency>
            <groupId>it.geosolutions</groupId>
            <artifactId>geoserver-manager</artifactId>
            <version>1.8-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>it.geosolutions</groupId>
            <artifactId>geoserver-manager</artifactId>
            <version>1.8-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot</artifactId>
            <version>2.3.2.RELEASE</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
            <version>2.3.2.RELEASE</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.2.8.RELEASE</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>


    </dependencies>

    <repositories>
        <repository>
            <id>GeoSolutions</id>
            <name>for geoserver-manager</name>
            <url>http://maven.geo-solutions.it/</url>
        </repository>

    </repositories>


</project>
```

## 

## GeoServerRESTManager类

该类是GeoServer-Manager中REST主要调用GeoServer地图服务器接口，可实现地理数据的部署、删除、查询和更改等操作。具体可在Github中的[GeoServer-Manager](https://github.com/geosolutions-it/geoserver-manager)进行阅读。

```java
package it.geosolutions.geoserver.rest;

import it.geosolutions.geoserver.rest.manager.GeoServerRESTAbstractManager;
import it.geosolutions.geoserver.rest.manager.GeoServerRESTStoreManager;
import it.geosolutions.geoserver.rest.manager.GeoServerRESTStructuredGridCoverageReaderManager;
import it.geosolutions.geoserver.rest.manager.GeoServerRESTStyleManager;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <i>The</i> single entry point to all of geoserver-manager functionality.
 * 
 * Instance this one, and use getters to use different components. These are:
 * <ul>
 * <li>getReader() simple, high-level access methods.
 * <li>getPublisher() simple, high-level pubhish methods.
 * <li>get<i>Foo</i>Manager, full-fledged management of catalog objects.
 * </ul>
 * 
 * @author Oscar Fonts
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 */
public class GeoServerRESTManager extends GeoServerRESTAbstractManager {

    private final GeoServerRESTPublisher publisher;
    private final GeoServerRESTReader reader;

    private final GeoServerRESTStoreManager storeManager;
    private final GeoServerRESTStyleManager styleManager;
    
    private final GeoServerRESTStructuredGridCoverageReaderManager structuredGridCoverageReader;

    /**
     * Default constructor.
     * 
     * Indicates connection parameters to remote GeoServer instance.
     * 
     * @param restURL GeoServer REST API endpoint
     * @param username GeoServer REST API authorized username
     * @param password GeoServer REST API password for the former username
     * @throws MalformedURLException {@link GeoServerRESTAbstractManager#GeoServerRESTAbstractManager(URL, String, String)}
     * @throws IllegalArgumentException {@link GeoServerRESTAbstractManager#GeoServerRESTAbstractManager(URL, String, String)}
     */
    public GeoServerRESTManager(URL restURL, String username, String password)
            throws IllegalArgumentException {
        super(restURL, username, password);

        // Internal publisher and reader, provide simple access methods.
        publisher = new GeoServerRESTPublisher(restURL.toString(), username, password);
        reader = new GeoServerRESTReader(restURL, username, password);
        structuredGridCoverageReader = new GeoServerRESTStructuredGridCoverageReaderManager(restURL, username, password);
        storeManager = new GeoServerRESTStoreManager(restURL, gsuser, gspass);
        styleManager = new GeoServerRESTStyleManager(restURL, gsuser, gspass);
    }

    public GeoServerRESTPublisher getPublisher() {
        return publisher;
    }

    public GeoServerRESTReader getReader() {
        return reader;
    }

    public GeoServerRESTStoreManager getStoreManager() {
        return storeManager;
    }

    public GeoServerRESTStyleManager getStyleManager() {
        return styleManager;
    }

    public GeoServerRESTStructuredGridCoverageReaderManager getStructuredGridCoverageReader() {
        return structuredGridCoverageReader;
    }

}
```

## 自动部署栅格数据

主要以Tiff格式为例，默认以坐标系EPSG:4326进行发布。

运行思路：遍历指定文件夹路径下所需部署的Tiff格式栅格数据，调用GSGeoTIFFDatastoreEncoder类以实现自动部署功能。

```java
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
```

## 自动部署矢量数据

GeoServerRESTPublisher类中的publishShpCollection函数可实现矢量数据的部署，从资源语法中推断上传方法和 mime 类型，进而对多矢量数据的压缩文件进行解压与部署。

```Java
/**
 * Publish a collection of shapefiles.
 * <P>
 * Will automatically create the store and publish each shapefile as a layer.
 * 
 * @param workspace the name of the workspace to use
 * @param storeName the name of the store to create
 * @param resource the shapefile collection. It can be:
 *        <ul>
 *        <li>A path to a directory containing shapefiles in the server. <li>A local zip file containing shapefiles that will be uploaded. <li>A
 *        URL pointing to a shapefile collection in the wild web (not tested).
 *        </ul>
 * @return {@code true} if publication successful.
 * @throws FileNotFoundException if the specified zip file does not exist.
 */
public boolean publishShpCollection(String workspace, String storeName, URI resource)
```

运行思路：遍历指定文件夹路径下的所有zip格式的压缩文件，利用publishShpCollection函数实现部署功能

```java
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
        String path = "F:\\Document\\Graduation Project\\MapData\\mapData_test\\Shp";     //要遍历的路径
        File file = new File(path);       //获取其file对象
        File[] fs = file.listFiles();  //遍历path下的文件和目录，放在File数组中
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
```

以上代码均来自Github，亲测可实现自动部署功能。

若有不清楚的地方欢迎私信与留言，本人在基于GeoServer地图服务器的二次开发中走过不少弯路，希望能帮助到大家。

