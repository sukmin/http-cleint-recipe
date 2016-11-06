package com.naver.msdt.httpclient.recipe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by john on 2016-11-04.
 */
@RestController
@RequestMapping(path="/simple")
public class SimpleTestController {

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "ok";
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, String[]> post(HttpServletRequest request) {
        return request.getParameterMap();
    }

}
