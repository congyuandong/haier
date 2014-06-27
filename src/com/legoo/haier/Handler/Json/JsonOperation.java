package com.legoo.haier.Handler.Json;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.legoo.haier.Application.HaierSettings;
import com.legoo.haier.Extension.FormatUtils;

/**
 * @class Json Operation
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public class JsonOperation 
{
	public static JsonHandler get(String url, JsonHandler jsonHandler, boolean needOriginal)
	{
		jsonHandler.setError(JsonHandler.ERROR_NONE);
		try
		{
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setConnectTimeout(HaierSettings.NETWORK_CONNECT_TIMEOUT);
			conn.setReadTimeout(HaierSettings.NETWORK_READ_TIMEOUT);
	
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream inputStream = conn.getInputStream();
				String content = FormatUtils.convertInputStreamToString(inputStream);
				
				if (needOriginal)
				{
					jsonHandler.setOriginal(content);
				}
				
				jsonHandler.parse(content);
				
				inputStream.close();
				inputStream = null;
			}
			else
			{
				jsonHandler.setError(JsonHandler.ERROR_CONNECTION);
		    }
			
			conn.disconnect();
			conn = null;
			url = null;
		}
		catch (Exception e) 
		{
			jsonHandler.setError(JsonHandler.ERROR_CONNECTION);
		}
		return jsonHandler;
	}
	
	public static JsonHandler get(String url, JsonHandler xmlHandler)
	{
		return get(url, xmlHandler, false);
	}
	
	
	public static JsonHandler post(String url, List<NameValuePair> pairs, JsonHandler jsonHandler)
	{
		jsonHandler.setError(JsonHandler.ERROR_NONE);
		try 
		{
			HttpPost httpRequest = new HttpPost(url);
		    httpRequest.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));  
		    HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
		    
		    if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
		    {
		    	jsonHandler.setError(JsonHandler.ERROR_CONNECTION);
		    }
		    
	        if(jsonHandler.getError() == JsonHandler.ERROR_NONE)
	        {
			    String content = EntityUtils.toString(httpResponse.getEntity());
			    
			    jsonHandler.parse(content);
	        }
	        
	        pairs = null;
		    httpRequest = null;
		    httpResponse = null;
		} 
		catch (Exception e) 
		{
			jsonHandler.setError(JsonHandler.ERROR_CONNECTION);
		}
		return jsonHandler;
	}
}
