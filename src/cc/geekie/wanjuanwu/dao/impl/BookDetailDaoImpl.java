package cc.geekie.wanjuanwu.dao.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cc.geekie.wanjuanwu.dao.BookDetailDao;
import cc.geekie.wanjuanwu.domain.CollectInfo;
import cc.geekie.wanjuanwu.domain.DetailBook;
import cc.geekie.wanjuanwu.util.HttpUtil;

public class BookDetailDaoImpl implements BookDetailDao {
	
	public static final String BOOK_DETAIL_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=query.bookdetail&bookid=";
	private static final String DOUBAN_BASE_URL = "https://api.douban.com/v2/book/isbn/:";
	
	private Document doc;
	
	public void getDetailDoc(String bookId) {
		
		String url = BOOK_DETAIL_URL + bookId;
		try {
			String html = HttpUtil.httpGet(url);
			doc = Jsoup.parse(html);
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public DetailBook getDetailBook() {
		DetailBook detailBook = new DetailBook();;
		// 1.从图书详情页获取馆藏信息和索书号
		List<CollectInfo> collectInfo = new ArrayList<>();
		String searchNum = "";
		Elements tableElements = doc.getElementsByTag("tbody");
		Elements trElements = tableElements.last().getElementsByTag("tr");
		trElements.remove(0);
		for (Element tr : trElements) {
			Elements es = tr.getElementsByTag("td");
			String location = es.get(0).text();
			String detailLocation = es.get(1).text();
			searchNum = es.get(2).text();
			String status = es.get(5).text();
			CollectInfo collect = new CollectInfo(location, detailLocation, status);
			collectInfo.add(collect);
		}
		detailBook.setSearchNum(searchNum);
		detailBook.setCollectInfo(collectInfo);
		// 2。从豆瓣API获取其他信息
		String isbn = getIsbn();
		String url = DOUBAN_BASE_URL + isbn;
		try {
			String doubanHtml = HttpUtil.httpGet(url);
//			System.out.println(doubanHtml);
			JSONObject jObj = new JSONObject(doubanHtml);
			String publisher = jObj.getString("publisher");
			String pubdate = jObj.getString("pubdate");
			String authorIntro = jObj.getString("author_intro");
			String summary = jObj.getString("summary");
			String catalog = jObj.getString("catalog");
			String pages = jObj.getString("pages");
			String price = jObj.getString("price");
			String pictureUrl = jObj.getJSONObject("images").getString("large");
			detailBook.setDoubanExist(true);
			detailBook.setPublisher(publisher);
			detailBook.setPubdate(pubdate);
			detailBook.setAuthorIntro(authorIntro);
			detailBook.setSummary(summary);
			detailBook.setCatalog(catalog);
			detailBook.setPages(pages);
			detailBook.setPrice(price);
			detailBook.setPictureUrl(pictureUrl);
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// 豆瓣不存在此图书时，抛出此异常
			detailBook.setDoubanExist(false);
			//e.printStackTrace();
		}
		
		return detailBook;
	}

	private String getIsbn() {
		String isbn = "";
		Elements elements = doc.getElementsContainingOwnText("价格:CNY");
		if (elements.size() > 0) {
			String targetString = elements.get(0).text();
			int index = targetString.indexOf("价格");
			isbn = targetString.substring(0, index).replaceAll("\\W","");
		}
		return isbn;

	}

}
