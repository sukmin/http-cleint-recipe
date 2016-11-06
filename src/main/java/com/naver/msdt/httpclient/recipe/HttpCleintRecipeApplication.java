package com.naver.msdt.httpclient.recipe;

import com.naver.msdt.httpclient.recipe.myserver.MyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class HttpCleintRecipeApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(HttpCleintRecipeApplication.class, args);
	}

	@Autowired
	private MyServer myServer;


	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		myServer.start();
	}
}
