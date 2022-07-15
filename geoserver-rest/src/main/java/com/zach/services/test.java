package com.zach.services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
//@RequestMapping("/shp")

@CrossOrigin(origins = "*",maxAge = 8095)

public class test {
    @GetMapping("/test")
    public String index() {
        return "Test !!!";
    }

    public static void main(String[] args){
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
                    System.out.println(f.getAbsolutePath());
                    System.out.println(f.getAbsolutePath().replaceAll("[.][^.]+$", ""));
                }
            }
/*            else if (f.isDirectory()){
                System.out.println(f.getAbsolutePath());
            }*/
        }
    }
}
