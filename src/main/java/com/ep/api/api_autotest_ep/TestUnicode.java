package com.ep.api.api_autotest_ep;

import java.io.UnsupportedEncodingException;

public class TestUnicode{

    public static void main(String[] args) throws UnsupportedEncodingException,NumberFormatException {
        String s = "简介";
        System.out.println(s+" --的unicode编码是："+gbEncoding(s));
        String s2 = "\u6253\u5f00\u4e0b\u9762\u8fd9\u4e2a\u9875\u9762\uff0c\u8fdb\u9a8c\u8bc1\u7801\u5f00\u53d1\u8005\u7684\u7fa4\uff0c\u7fa4\u6587\u4ef6\u91cc\u6709\u83b7\u53d6apiKey\u7684\u65b9\u6cd5";
        System.out.println(s2 + " --转换成中文是："+decodeUnicode(s2));
        
        //System.out.println(gbEncoding(s) + " --转换成中文是："+decodeUnicode("\\u7b80\\u4ecb"));
    }
    
    /*
     * 中文转unicode编码
     */
    //中文转Unicode
    public static String gbEncoding(final String gbString) {   //gbString = "测试"
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";   
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {   
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
              if (hexB.length() <= 2) {   
                  hexB = "00" + hexB;   
             }   
             unicodeBytes = unicodeBytes + "\\u" + hexB;   
        }   
        System.out.println("unicodeBytes is: " + unicodeBytes);   
        return unicodeBytes;   
    }

    /*
     * unicode编码转中文
     */
    //Unicode转中文
    public static String decodeUnicode(final String dataStr) {   
       int start = 0;   
       int end = 0;   
       final StringBuffer buffer = new StringBuffer();   
       while (start > -1) {   
           end = dataStr.indexOf("\\u", start + 2);   
           String charStr = "";   
           if (end == -1) {   
               charStr = dataStr.substring(start + 2, dataStr.length());   
           } else {   
               charStr = dataStr.substring(start + 2, end);   
           }   
           char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。   
           buffer.append(new Character(letter).toString());   
           start = end;   
       }   
       return buffer.toString();   
    }

    
}