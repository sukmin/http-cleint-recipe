package com.naver.msdt.httpclient.recipe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by john on 2016-11-04.
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "ok";
    }

}
