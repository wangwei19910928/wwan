package com.vv.utils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.util.Base64Utils;

public class SystemUtil {
	
	
	public static String uriDecode(String data){
		try {
			return URLDecoder.decode(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String uriEncode(String data){
		return URLEncoder.encode(data);
	}
	
	public static byte[] base64Decode(String data){
		return Base64Utils.decodeFromString(data);
	}
	
	public static String base64Encode(byte[] data){
		return Base64Utils.encodeToString(data);
		
	}
}
