package cc.geekie.wanjuanwu.dao.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cc.geekie.wanjuanwu.dao.SearchBookDao;
import cc.geekie.wanjuanwu.domain.SearchBook;
import cc.geekie.wanjuanwu.domain.SearchInfo;
import cc.geekie.wanjuanwu.exception.WrongPageException;
import cc.geekie.wanjuanwu.util.HttpUtil;

public class SearchBookDaoImpl implements SearchBookDao {
	private Document doc;
	private int bookNum;
	private int bookNumPerPage;
	private int pageNum;
	private String currentPage;
	@Override
	public void searchEngine(String searchContent, String searchCriteria, String page) throws WrongPageException {
		
		currentPage = page;
		String queryString = "cmdACT=simple.list" + 
				 "&RDID=ANONYMOUS" + 
				 "&ORGLIB=SCUT" + 
			     "&VAL1=" + searchContent + 
			  	 "&PAGE=" + page +
				 "&FIELD1=" + searchCriteria;
		try {
			String resultHtml = HttpUtil.httpPost("http://202.38.232.10/opac/servlet/opac.go", queryString);
//			System.out.println(queryString);
//			System.out.println(resultHtml);
			doc = Jsoup.parse(resultHtml);
//			getSearchInfo();
//			getBooksOnPage(Integer.parseInt(page));
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<SearchBook> searchEngineAdvance(String searchContent, String searchCriteria, String page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchInfo getSearchInfo() throws WrongPageException {
		Elements pTag = doc.getElementsByTag("p");
		Elements fontTag = null;
		try {
			fontTag = pTag.last().getElementsByTag("font");
		} catch (NullPointerException e) {
			throw new WrongPageException("发生了缺页异常");
		}
		bookNum = Integer.parseInt(fontTag.get(1).text());
		bookNumPerPage = Integer.parseInt(fontTag.get(2).text());
		pageNum = (int) Math.ceil((double) bookNum / bookNumPerPage);
		
		SearchInfo searchInfo = new SearchInfo(pageNum, bookNum, bookNumPerPage);
		return searchInfo;
	}
	
	
	public List<SearchBook> getResultBook() {
		List<SearchBook> resultBookLists = new ArrayList<SearchBook>();
		if (bookNum == 0 || Integer.parseInt(currentPage) > pageNum) return null;
		Elements elements = doc.getElementsByTag("tr");
		elements.remove(0);
		for (Element element : elements) {

			Elements tdElems = element.getElementsByTag("td");
			String title = tdElems.get(1).text();
			String author = tdElems.get(2).text();
			String publisher = tdElems.get(3).text();
			String isbn = tdElems.get(4).text().replaceAll("\\W","");
			String pubdate = tdElems.get(5).text();
			String searchNum = tdElems.get(6).text();
			String type = tdElems.get(7).text();
			String bookId = tdElems.get(1).select("a").first().attr("href").replaceAll("\\D", "");
			SearchBook book = new SearchBook(title, author, publisher, isbn, pubdate, searchNum, type, bookId);
			resultBookLists.add(book);
		}
		return resultBookLists;
	}


}




















