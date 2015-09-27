package com.gontuseries.hellocontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
 
public class HelloController extends AbstractController{
 
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		String urlStr = "http://api.openweathermap.org/data/2.5/weather?zip=94040,us";
		URL url = new URL(urlStr);
		System.out.println("Connected");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() != 200) {
		    throw new IOException(conn.getResponseMessage());
		  }
		BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    sb.append(line);
			  }
			  rd.close();
			  System.out.println(sb);
			  conn.disconnect();
			  
		ModelAndView modelandview = new ModelAndView("HelloPage");
		modelandview.addObject("welcomeMessage", sb);
		
		return modelandview;
	}
}