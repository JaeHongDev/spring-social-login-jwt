package com.artisan.springsocialloginjwt.restdocs;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleDocsController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
