package cc.geekie.wanjuanwu.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import cc.geekie.wanjuanwu.domain.BorrowBook;
import cc.geekie.wanjuanwu.service.BookService;
import cc.geekie.wanjuanwu.service.impl.BookServiceImpl;

public class BorrowBookTest {
	private static final String MAIN_PAGE_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=mylibrary.login";
	private static final String LOAN_PAGE_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=loan.list";
	private HttpClient httpClient;
	@Test
	public void test() {
		NameValuePair[] nameValuePairs = {
				new NameValuePair("userid", "201421003124"),
				new NameValuePair("passwd", "ziqian930209") };
		PostMethod postMethod = new PostMethod(MAIN_PAGE_URL);
		postMethod.setRequestBody(nameValuePairs);
				
		httpClient = new HttpClient();
		String bookHtml = "";
		try {
			httpClient.executeMethod(postMethod);
			
			GetMethod getMethod = new GetMethod(LOAN_PAGE_URL);
			httpClient.executeMethod(getMethod);
			BufferedReader br = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "utf-8"));
			bookHtml = "";
			String readLine = "";
			while ((readLine = br.readLine()) != null) {
				bookHtml += readLine;
			}
			br.close();
			
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		boolean isLogined = (bookHtml.contains("个人资料")) ? true : false;
		System.out.println(isLogined);
	}
	
//	@Test
//	public void test1() {
//		BookService bs = new BookServiceImpl();
//		List<BorrowBook> bookList = bs.getBorrowedBookList("201420101043", "725462");
////		System.out.println(bookList);
//		for (BorrowBook book : bookList) {
//			System.out.println(book.toString());
//		}
//	}

}





















