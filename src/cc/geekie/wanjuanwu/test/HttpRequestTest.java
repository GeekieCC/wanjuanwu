package cc.geekie.wanjuanwu.test;

import static org.junit.Assert.*;

import java.net.SocketTimeoutException;

import org.junit.Test;

import cc.geekie.wanjuanwu.util.HttpRequest;
import cc.geekie.wanjuanwu.util.HttpUtil;

public class HttpRequestTest {

	@Test
	public void test() {
		//String response = HttpRequest.get("http://geekie.cc").body();
		
		
		String queryString = "cmdACT=simple.list" + 
				 "&RDID=ANONYMOUS" + 
				 "&ORGLIB=SCUT" + 
			     "&VAL1=" + "»À" + 
			  	 "&PAGE=" + "2" +
				 "&FIELD1=" + "TITLE";
		String response = HttpRequest.post("http://202.38.232.10/opac/servlet/opac.go").send("queryString").body();
		System.out.println("Response was: " + response);
	}
	
	@Test
	public void test1() {
		try {
			String result = HttpUtil.httpGet("http://geekie.cc");
			System.out.println(result);

		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		try {
			
			String queryString = "cmdACT=simple.list" + 
					 "&RDID=ANONYMOUS" + 
					 "&ORGLIB=SCUT" + 
				     "&VAL1=" + "»À" + 
				  	 "&PAGE=" + "0" +
					 "&FIELD1=" + "TITLE";
			String result = HttpUtil.httpPost("http://202.38.232.10/opac/servlet/opac.go", queryString);
			System.out.println(result);

		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void test3() {
		try {
			
			String queryString = "cmdACT=simple.list" + 
					 "&RDID=ANONYMOUS" + 
					 "&ORGLIB=SCUT" + 
				     "&VAL1=" + "»À" + 
				  	 "&PAGE=" + "0" +
					 "&FIELD1=" + "TITLE";
			String result = HttpUtil.httpPost("http://202.38.232.10/opac/servlet/opac.go", queryString);
			System.out.println(result);

		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
