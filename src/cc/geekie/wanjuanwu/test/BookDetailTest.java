package cc.geekie.wanjuanwu.test;

import static org.junit.Assert.*;

import java.net.SocketTimeoutException;

import org.json.JSONObject;
import org.junit.Test;

import cc.geekie.wanjuanwu.dao.BookDetailDao;
import cc.geekie.wanjuanwu.dao.impl.BookDetailDaoImpl;
import cc.geekie.wanjuanwu.domain.DetailBook;
import cc.geekie.wanjuanwu.util.HttpUtil;

public class BookDetailTest {

	@Test
	public void test() {
		String BOOK_DETAIL_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=query.bookdetail&bookid=";
		String url = BOOK_DETAIL_URL + 1105664;
		try {
			String html = HttpUtil.httpGet(url);
			
			System.out.println(html);
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test1() {
		BookDetailDao bookDetailDao = new BookDetailDaoImpl();
		bookDetailDao.getDetailDoc("1779");
		DetailBook detailBook = bookDetailDao.getDetailBook();
		JSONObject jObj = new JSONObject();
		System.out.println(jObj.wrap(detailBook).toString());
	}

}











