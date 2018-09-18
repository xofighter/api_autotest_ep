package com.ep.api.api_autotest_ep;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.ep.api.utils.BeanToMapUtil;
import com.ep.api.utils.ExcelUtil;
import com.ep.data.beans.ApiDataBean;

public class TestExcelUtil {
  @Test
  public void f() throws Exception {
	  ExcelUtil eu = new ExcelUtil("case/api_testdata 2.xlsx");
//	  List<List<String>> lts = eu.readFirstSheet();
//	  System.out.println(lts);
//	  Map<String, Object> apidata = BeanToMapUtil.convertBean2Map(new ApiDataBean());
	//遍历map中的键和值
//	  for (Map.Entry<String, Object> entry : apidata.entrySet()) { 
//		  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
//	  }
//	  for(List<String> lt:lts) {
//		  
//		  System.out.println(lt);  
//	  }
	  
	  List<Map<String,Object>> lms = eu.readSheet(0) ;
	  for (Map<String,Object> mm : lms) { 
		  for (Map.Entry<String, Object> entry : mm.entrySet()) { 
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		  } 
	  }
	  
  }
}