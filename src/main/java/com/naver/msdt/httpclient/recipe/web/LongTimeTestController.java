package com.naver.msdt.httpclient.recipe.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by john on 2016-11-05.
 */
@RestController
@RequestMapping("/longtime")
public class LongTimeTestController {

    @RequestMapping(method = RequestMethod.GET)
    public String get() throws InterruptedException {
        Thread.sleep(5000);
        return "ok";
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, String[]> post(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(5000);
        return request.getParameterMap();
    }

}
