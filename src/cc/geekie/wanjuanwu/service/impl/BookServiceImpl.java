package cc.geekie.wanjuanwu.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cc.geekie.wanjuanwu.domain.BorrowBook;
import cc.geekie.wanjuanwu.service.BookService;

public class BookServiceImpl implements BookService {

	private static final String MAIN_PAGE_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=mylibrary.login";
	private static final String LOAN_PAGE_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=loan.list";
	private List<BorrowBook> bookLists;
	private HttpClient httpClient;
	private String bookHtml;
	@Override
	public List<BorrowBook> getBorrowedBookList() {
		
		// 登录成功
		bookLists = new ArrayList<BorrowBook>();
		Document doc = Jsoup.parse(bookHtml);
		Elements elements = doc.getElementsByTag("tr");
		elements.remove(0);
		for (Element element : elements) {
			Elements es = element.getElementsByTag("td");
			String barcode = es.get(1).text();
			String titleAndAuthor = es.get(2).text();
			int index1 = titleAndAuthor.indexOf('/');
			String title = titleAndAuthor.substring(0, index1);
			String author = titleAndAuthor.substring(index1 + 1);
			String volume = es.get(3).text();
			String libraryName = es.get(4).text();
			String libraryLocation = es.get(5).text();
			String borrowDay = es.get(6).text();
			String returnDay = es.get(7).text();
			String borrowedTimeAndMaxBorrowTime = es.get(8).text();
			int index2 = borrowedTimeAndMaxBorrowTime.indexOf('/');
			int borrowedTime = Integer.parseInt(borrowedTimeAndMaxBorrowTime.substring(0, index2));
			int maxBorrowTime = Integer.parseInt(borrowedTimeAndMaxBorrowTime.substring(index2 + 1));
			boolean isExpired = es.get(9).text().equals("是") ? true : false;
			String renewLink = "";
			if (borrowedTime < maxBorrowTime) {
				renewLink = "http://202.38.232.10/opac" + es.get(10).select("a").first().attr("href").substring(2);
			}
			String detailLink = "http://202.38.232.10/opac" + 
					es.get(2).select("a").first().attr("href").substring(2); //新增
			String find = "bookid=";
			String s1 = detailLink.substring(detailLink.indexOf(find) + find.length());
			String bookId = s1.substring(0, s1.indexOf('&'));
			BorrowBook book = new BorrowBook(barcode, title, author, 
					volume, libraryName, libraryLocation, borrowDay, 
					returnDay, borrowedTime, maxBorrowTime, isExpired, renewLink, detailLink, bookId);		
			bookLists.add(book);
		}
		return bookLists;
		
	}

	@Override
	public boolean login(String username, String passwd) {
		NameValuePair[] nameValuePairs = {
				new NameValuePair("userid", username),
				new NameValuePair("passwd", passwd) };
		PostMethod postMethod = new PostMethod(MAIN_PAGE_URL);
		postMethod.setRequestBody(nameValuePairs);
				
		httpClient = new HttpClient();
//		HttpClient httpClient = HttpClientSingle.getInstance();
		bookHtml = "";
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return (bookHtml.contains("个人资料")) ? true : false;

	}

	@Override
	public boolean renew(String bookId) {
		String renewLink = "";
		for (BorrowBook book : bookLists) {
			if (bookId.equals(book.getBookId())) {
				renewLink = book.getRenewLink();
				break;
			}
		}
		if ("".equals(renewLink)) return false;
		GetMethod getMethod = new GetMethod(renewLink);
		try {
			httpClient.executeMethod(getMethod);
			GetMethod getMethodAfter = new GetMethod(LOAN_PAGE_URL);
			httpClient.executeMethod(getMethodAfter);
			BufferedReader br = new BufferedReader(new InputStreamReader(getMethodAfter.getResponseBodyAsStream(), "utf-8"));
			String bookHtmlAfter = "";
			String readLine = "";
			while ((readLine = br.readLine()) != null) {
				bookHtmlAfter += readLine;
			}
			br.close();
			if (!bookHtmlAfter.equals(bookHtml)) {
				bookHtml = bookHtmlAfter;
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
















