package com.hbb.video;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import com.baidu.translate.demo.TransApi;

public class VideoMain {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "";
    private static final String SECURITY_KEY = "";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        System.out.println( "----------------------------------------");
        LineNumberReader  lnrO = null;
	
		String path = "/Users/HopeBayBridge/Documents/SubtitleTranslation/src/";    // 修改文件路径
		String desPath = path +"video.en.srt";  // 翻译完成的目标语言
		String srcLanguage = "zh";              // 源语言，母语  代码缩写
		String desLanguage = "en";              // 目标语言   代码缩写
		try {
			lnrO =  new LineNumberReader(new InputStreamReader(new FileInputStream(path + "video.zh-cn.srt"),"utf-8"));
		
			String bO="";
	
			StringBuffer sb = new StringBuffer();
			try {
				while((bO = lnrO.readLine())!=null ){
				
					if(Pattern.matches("^-?\\d+(\\.\\d+)?$", bO) || bO.contains("-->") || bO.trim().equals("") || null == bO.trim()) {	
						sb.append(bO+"\r\n");
						System.out.println(bO);  // 这句可以删除
				    }else {
				    	String temp = bO.replace(bO, api.getTransResult(bO, srcLanguage, desLanguage));
				    	String finalRes = temp.substring( temp.lastIndexOf("\"dst\":\"")+7, temp.lastIndexOf("\""));
				    	sb.append(finalRes.replace("\\", "") +"\r\n");
				    	System.out.println(finalRes.replace("\\", ""));  // 这句可以删除
				    }

				}
				
				System.out.println(sb.toString());   // 这句可以删除
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(desPath),"utf-8"));
				bw.write(sb.toString());
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(null != lnrO) {
					lnrO.close();
				}			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

}
