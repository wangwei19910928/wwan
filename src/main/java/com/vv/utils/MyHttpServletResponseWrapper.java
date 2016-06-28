package com.vv.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper{
	
	private ByteArrayOutputStream buffer=null;
    private ServletOutputStream out=null;
    private PrintWriter writer=null;
    
    public MyHttpServletResponseWrapper(HttpServletResponse resp) throws IOException{
        super(resp);
        buffer=new ByteArrayOutputStream();//�����洢���ݵ���
        out=new WapperedOutputStream(buffer);
        writer=new PrintWriter(new OutputStreamWriter(buffer,this.getCharacterEncoding()));
    }
    //���ظ����ȡoutputstream�ķ���
    @Override
    public ServletOutputStream getOutputStream()throws IOException{
        return out;
    }
    //���ظ����ȡwriter�ķ���
    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException{
        return writer;
    }
    //���ظ����ȡflushBuffer�ķ���
    @Override
    public void flushBuffer()throws IOException{
        if(out!=null){
            out.flush();
        }
        if(writer!=null){
            writer.flush();
        }
    }
    @Override
    public void reset(){
        buffer.reset();
    }
    public byte[] getResponseData()throws IOException{
        flushBuffer();//��out��writer�е�����ǿ�������WapperedResponse��buffer���棬����ȡ��������
        return buffer.toByteArray();
    }
    
    //�ڲ��࣬��ServletOutputStream���а�װ
    private class WapperedOutputStream extends ServletOutputStream{
        private ByteArrayOutputStream bos=null;
        public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException{
            bos=stream;
        }
        @Override
        public void write(int b) throws IOException{
            bos.write(b);
        }
		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public void setWriteListener(WriteListener writeListener) {
			// TODO Auto-generated method stub
			
		}
    }
}
