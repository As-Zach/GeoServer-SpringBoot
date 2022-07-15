package com.zach.services;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 8095)
public class Fileurl {

    @PostMapping("/testfile")
    public String Findfile(@RequestBody String input){
        String url = "1111";
        url = input;
        System.out.println(url);
        return url;
    }
}
