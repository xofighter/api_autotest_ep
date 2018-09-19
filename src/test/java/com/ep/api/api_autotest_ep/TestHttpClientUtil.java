package com.ep.api.api_autotest_ep;

import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * @author xu_bob
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ep.api.utils.ExcelUtil;
import com.ep.httpclientutil.HttpClientUtil;
import com.ep.httpclientutil.builder.HCB;
import com.ep.httpclientutil.common.HttpConfig;
import com.ep.httpclientutil.common.SSLs.SSLProtocolVersion;
import com.ep.httpclientutil.exception.HttpProcessException;

public class TestHttpClientUtil {




	private static final Map<String, Object> paramsToMap = null;




	public static void main(String[] args) throws Exception {

		String result = null; //响应
		Map<String, Object> map = new HashMap<String, Object>(); 

		Object urlLink = null;	//url
		String params = null;	//入参
		String requestmethod = null;	//请求方式
		JSONObject paramsToJson = new JSONObject();	//入参转为json格式
//		Map<String, Object> paramsToMap = null;	//入参转为Map格式
		
		ExcelUtil eu = new ExcelUtil("case/api_testdata 2.xlsx");
		List<Map<String, Object>> lms = eu.readSheet(0);
		System.out.println("开始了………………………………………………");
		System.out.println("lms的长度："+lms.size());
		for(int i=0; i<lms.size(); i++){
			for (Map.Entry<String, Object> entry : lms.get(i).entrySet()) {
				if (entry.getKey().equals("url")) {
					urlLink = (String) entry.getValue();	
				} else if (entry.getKey().toString().equals("param")) {
					params = (String) entry.getValue();	
				}else if (entry.getKey().equals("method")) {
					requestmethod = (String)entry.getValue();
				}
			}
		}
		System.out.println("url: " + urlLink);
		System.out.println("入参 :  "+ params);
		System.out.println("请求方式 :  "+ requestmethod);
		System.out.println("结束了………………………………………………");
//		System.out.println("字符串解析成json开始了………………………………………………");
//		JSONObject json = JSONObject.parseObject(params);
//        System.out.println(JSONObject.toJSONString(json, true));
//        System.out.println("字符串解析成Json结束！………………………………………………");
        System.out.println("字符串解析成Map开始了………………………………………………");
        paramsToMap = (Map)JSON.parse(params);  
        for (Object map2 : paramsToMap.entrySet()){  
            System.out.println(((Map.Entry)map2).getKey()+"     " + ((Map.Entry)map2).getValue());  
        }
        System.out.println("字符串解析成Map结束！………………………………………………");
	}
//		String url = "http://abc.e-ports.com/apic/searchEPAccounts/";

		//最简单的使用：
//		String html = HttpClientUtil.get(HttpConfig.custom().url(url).client(HCB.custom().sslpv(SSLProtocolVersion.TLSv1_2).ssl().build()));
//		System.out.println(html);
		
		//---------------------------------
		//			【详细说明】
		//--------------------------------
		
		//插件式配置Header（各种header信息、自定义header）
//		Header[] headers 	= HttpHeader.custom()
//											.userAgent("javacl")
//											.other("customer", "自定义")
//											.build();
		
		//插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
		HCB hcb 				= HCB.custom()
											.timeout(1000) 		//超时
											.pool(100, 10)    	//启用连接池，每个路由最大创建10个链接，总连接数限制为100个
											.sslpv(SSLProtocolVersion.TLSv1_2) 	//可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
											.ssl()  			   		//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
											.retry(5)					//重试5次
											;
		
		HttpClient client = hcb.build();

		private String urlLink;






		
//		Map<String, Object> map2 = new HashMap<String, Object>();
//		map2.put("sortBy", "score");
//		map2.put("page", "1");
//		map2.put("PageSize", "10");
//		map2.put("searchKey", "lovely");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("action", "searchEPAccounts");
//		map.put("request",map2);
		
		
//		String str = "{\"action\":\"login\",\"request\":{\"username\":\"xu.bob@e-ports.com\",\"password\":\"password\",\"rememberMe\":false}}";
//	    JSONObject  jsonObject = JSONObject.parseObject(str);
	    //json对象转Map
//	    map = (Map<String,Object>)jsonObject;
//		System.out.println("map:"+map);
		
		//插件式配置请求参数（网址、请求参数、编码、client）
		HttpConfig config = HttpConfig.custom()
//											.headers(headers)	//设置headers，不需要时则无需设置
											.url(urlLink)					//设置请求的url
											.map(paramsToMap)			//设置请求参数，没有则无需设置
											.encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
											.client(client)														//如果只是简单使用，无需设置，会自动获取默认的一个client对象
											//.inenc("utf-8") 													//设置请求编码，如果请求返回一直，不需要再单独设置
											//.inenc("utf-8")													//设置返回编码，如果请求返回一直，不需要再单独设置
											//.json("json字符串")												//json方式请求的话，就不用设置map方法，当然二者可以共用。
											//.context(HttpCookies.custom().getContext()) 		//设置cookie，用于完成携带cookie的操作
											//.out(new FileOutputStream("保存地址"))			 	//下载的话，设置这个方法,否则不要设置
											//.files(new String[]{"d:/1.txt","d:/2.txt"})					//上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
											;

		
		
		
		//使用方式：
//		String result1 = HttpClientUtil.get(config);		//get请求
//		System.out.println("r1:"+result1);
		if(requestmethod.equals("Post")) {
			result = HttpClientUtil.post(config);	//post请求
		}
		System.out.println("r2:"+result2);

	}

}
