package com.vv.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vv.aes.Aes;
import com.vv.aes.AesCtr;
import com.vv.aes.AesEnum;
import com.vv.utils.SystemUtil;


@Controller
public class LoginController {
	
	@RequestMapping(value="login",method = RequestMethod.POST)
	public String login(String _data) throws UnsupportedEncodingException{
		String uriData = SystemUtil.uriDecode(_data);
		System.out.println(uriData);
		byte[] base64Data = SystemUtil.base64Decode(uriData);
		for (byte b : base64Data) {
			System.out.print(parseByte2HexStr(b));
		}
		byte[] ascData = AesCtr.decode(base64Data, "FE7A45426AFF5D14E52897E134F5CC33".getBytes(), AesEnum.KEY256);
		System.out.println();
		System.out.println("-------------------");
		System.out.print(new String(ascData,"utf-8"));
		return "";
	}
	
	
	@RequestMapping("/test")
	@ResponseBody
	public Map<String, Object> test(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("encryptionKey", "FE7A45426AFF5D14E52897E134F5CC33");
		map.put("CLID", "08E4049FF24E4DD8C0E345398ACFB9EE");
		map.put("_s", "08E4049FF24E4DD8C0E345398ACFB9EE1398902400");
		map.put("OS", "iOS");
		return map;
	}
	
	@RequestMapping("/entry")
	@ResponseBody
	public String entry(String xing,String ming,String _d,String pid){
		System.out.println(xing);
		System.out.println(ming);
		System.out.println(_d);
		System.out.println(pid);
		return "";
	}
	
	public  String parseByte2HexStr(byte buf) {
		StringBuffer sb = new StringBuffer();
		String hex = Integer.toHexString(buf & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		sb.append(hex.toUpperCase());
		return sb.toString();
	}
	/*
	public static void main(String[] args) throws UnsupportedEncodingException{
		String a = "{\"CLID\":\"08E4049FF24E4DD8C0E345398ACFB9EE\",\"_s\":\"08E4049FF24E4DD8C0E345398ACFB9EE1398902400\",\"OS\":\"iOS\"}";
		byte[] aesData = AesCtr.encode(a.getBytes("utf-8"), "FE7A45426AFF5D14E52897E134F5CC33".getBytes(), AesEnum.KEY256);
		String baseData = SystemUtil.base64Encode(aesData);
		System.out.println(baseData);
		String uriData = SystemUtil.uriEncode(baseData);
		System.out.println(uriData);
	}*/
	
//	public static void main(String[] args) throws UnsupportedEncodingException {
//		String a = "pjDsfoV6sU4rkpv/7hyWQIbUF4ojkAJZFIGp2tWuSKoeMS0j1KMIHY+3DKcn6/VDn/HQQCylJx1BfoPTJ4BN7cZ6ZS3hh35geOnM33A6pGfqE7tpfLVszQ5yA5LRPlF9anFDax1/FMu/PTnJszLz4g==";
//		byte[] base  =SystemUtil.base64Decode(a);
//		byte[] aa = AesCtr.decode(base, "FE7A45426AFF5D14E52897E134F5CC33".getBytes("utf-8"), AesEnum.KEY256);
//		
//		System.out.println(new String(aa,"utf-8"));
//		
//	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(1);
		System.out.println(list);
		Collections.sort(list, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2.compareTo(o1);
			}
			
		});
		
		System.out.println(list);
	}
}
