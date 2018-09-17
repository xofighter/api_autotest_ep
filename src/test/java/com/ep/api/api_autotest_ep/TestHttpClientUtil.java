package com.ep.api.api_autotest_ep;

/**
 * @author xu_bob
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import com.alibaba.fastjson.JSONObject;
import com.ep.httpclientutil.HttpClientUtil;
import com.ep.httpclientutil.builder.HCB;
import com.ep.httpclientutil.common.HttpConfig;
import com.ep.httpclientutil.common.HttpHeader;
import com.ep.httpclientutil.common.SSLs.SSLProtocolVersion;
import com.ep.httpclientutil.exception.HttpProcessException;

public class TestHttpClientUtil {

	public static void main(String[] args) throws HttpProcessException{

		String url = "http://abc.e-ports.com/apic/searchEPAccounts/";

		//最简单的使用：
		String html = HttpClientUtil.get(HttpConfig.custom().url(url).client(HCB.custom().sslpv(SSLProtocolVersion.TLSv1_2).ssl().build()));
		System.out.println(html);
		
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
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("sortBy", "score");
		map2.put("page", "1");
		map2.put("PageSize", "10");
		map2.put("searchKey", "lovely");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action", "searchEPAccounts");
		map.put("request",map2);

		
//		String str = "{\"action\":\"login\",\"request\":{\"username\":\"xu.bob@e-ports.com\",\"password\":\"password\",\"rememberMe\":false}}";
//	    JSONObject  jsonObject = JSONObject.parseObject(str);
	    //json对象转Map
//	    map = (Map<String,Object>)jsonObject;
		System.out.println("map:"+map);
		
		//插件式配置请求参数（网址、请求参数、编码、client）
		HttpConfig config = HttpConfig.custom()
//											.headers(headers)	//设置headers，不需要时则无需设置
											.url(url)					//设置请求的url
											.map(map)			//设置请求参数，没有则无需设置
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
		String result2 = HttpClientUtil.post(config);	//post请求
		System.out.println("r2:"+result2);

	}

}
