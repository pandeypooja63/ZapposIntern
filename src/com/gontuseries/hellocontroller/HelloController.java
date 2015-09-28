package com.gontuseries.hellocontroller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

 
@Controller
public class HelloController {
	@RequestMapping(value = "/Zappos_Proj.html" , method = RequestMethod.GET)
	protected ModelAndView get_data(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView modelandview = new ModelAndView("EnterDetails");
		modelandview.addObject("welcomeMessage","Enter City name and Country Code " );
		return modelandview;
	}
	
	@RequestMapping(value = "/fetch_data.html" , method = RequestMethod.POST)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response,@RequestParam Map<String,String> reqPar) throws Exception {
		String citycc = reqPar.get("cityncc");
		String urlStr = "http://api.openweathermap.org/data/2.5/weather?q="+ citycc;
		URL url = new URL(urlStr);
		System.out.println("Connected");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() != 200) {
		    throw new IOException(conn.getResponseMessage());
		  }
		BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder data = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    data.append(line);
			  }
			  rd.close();
			  JSONObject jsonObject = new JSONObject(data.toString());
			  System.out.println(jsonObject);

		  String Final="";
		  Final +="Weather data"+"<br>";
		  Final += "Latitude of the place:"+(Double) jsonObject.getJSONObject("coord").get("lat")+"<br>";
		  Final += "Longitude of the place: "+(Double) jsonObject.getJSONObject("coord").get("lon")+"<br>";
		  Final += "Temperature :"+(Double) jsonObject.getJSONObject("main").get("temp")+"<br>";
		  Final += "Maximum Temperature:"+(Double) jsonObject.getJSONObject("main").get("temp_min")+"<br>";
		  Final += "Minimum Temperature :"+(Double) jsonObject.getJSONObject("main").get("temp_max")+"<br>";
		  Final += "Pressure :"+(Integer) jsonObject.getJSONObject("main").get("pressure")+"<br>";
		  Final += "Humidity :"+(Integer) jsonObject.getJSONObject("main").get("humidity")+"</br>";
		  Final += "Wind Speed :"+(Double) jsonObject.getJSONObject("wind").get("speed")+"</br>";
		  System.out.println(Final);
		 
	
			   
	    conn.disconnect();
			  
		ModelAndView modelandview = new ModelAndView("HelloPage");
		modelandview.addObject("welcomeMessage",Final);
		
		return modelandview;
	}
}