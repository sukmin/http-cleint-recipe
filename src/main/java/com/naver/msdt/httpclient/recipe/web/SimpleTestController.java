package com.naver.msdt.httpclient.recipe.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(path = "/simple")
public class SimpleTestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(SimpleTestController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "ok";
	}

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String[]> post(HttpServletRequest request) {
		LOGGER.info("this post");
		return request.getParameterMap();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, String[]> put(HttpServletRequest request) {
		LOGGER.info("this put");
		return request.getParameterMap();
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Map<String, String[]> delete(HttpServletRequest request) {
		LOGGER.info("this delete");
		return request.getParameterMap();
	}

}
