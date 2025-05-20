package com.lakshan.imagUpload.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing/api")
public class TestController {
    @GetMapping("/getTest")//testing/api/getTest
    public String getTest() {

        return "getTestController Call";
    }
}
