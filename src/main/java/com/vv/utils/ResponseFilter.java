package com.vv.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.vv.aes.AesCtr;
import com.vv.aes.AesEnum;

public class ResponseFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		 HttpServletResponse resp=(HttpServletResponse)arg1;
        MyHttpServletResponseWrapper wapper=new MyHttpServletResponseWrapper(resp);
        HttpServletRequest req = decryption(arg0);
        arg2.doFilter(req, wapper);
        byte[] b1=wapper.getResponseData();
         //do something with b1 here
        //输出处理后的数据
        ServletOutputStream output=arg1.getOutputStream();
        output.write(encryption(b1));
        output.flush();
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	private byte[] encryption(byte[] data) throws UnsupportedEncodingException{
		//转String
		String dataStr = new String(data, "utf-8");
		//转map
		Map<String, Object> map = parseJSON2Map(dataStr);
		//获取加密key
		String key = map.get("encryptionKey").toString();
		map.remove("encryptionKey");
		//转json加密
		String jsonStr = JSONObject.fromObject(map).toString();
		//des base64
		return SystemUtil.base64Encode(AesCtr.encode(jsonStr.getBytes("utf-8"), key.getBytes("utf-8"), AesEnum.KEY256)).getBytes("utf-8");
	}
	
	
	private  Map<String, Object> parseJSON2Map(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(parseJSON2Map(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
    }
	
	
	private HttpServletRequest decryption(ServletRequest arg0){
		HttpServletRequest req = (HttpServletRequest) arg0;
		HashMap m=new HashMap(arg0.getParameterMap());
		req.getParameter("_d");
		System.out.println(req.getParameter("_d"));
		System.out.println(req.getParameter("pid"));
		
		m.put("xing", new String[]{"wang"});
		m.put("ming", new String[]{"wei"});
		
		MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(req, m);
		return myHttpServletRequest;
	}

}
