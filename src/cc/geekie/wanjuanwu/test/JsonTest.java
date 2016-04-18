package cc.geekie.wanjuanwu.test;

import static org.junit.Assert.*;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import cc.geekie.wanjuanwu.domain.SearchBook;
import cc.geekie.wanjuanwu.domain.SearchInfo;
import cc.geekie.wanjuanwu.util.HttpUtil;

public class JsonTest {

	@Test
	public void test() {
		List<SearchBook> bookList = new ArrayList<>();
		bookList.add(new SearchBook("a1", "b", "c", "d", "e", "f", "g", "h"));
		bookList.add(new SearchBook("a2", "b", "c", "d", "e", "f", "g", "h"));
		bookList.add(new SearchBook("a3", "b", "c", "d", "e", "f", "g", "h"));
		bookList.add(new SearchBook("a4", "b", "c", "d", "e", "f", "g", "h"));
		bookList.add(new SearchBook("a5", "b", "c", "d", "e", "f", "g", "h"));
		
		SearchInfo info = new SearchInfo(30, 100, 20);
		
//		JSONArray jArray = new JSONArray();
//		jArray.put(bookList);
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("info", jObj.wrap(info));
			jObj.put("resultArray", jObj.wrap(bookList));
			
			System.out.println(jObj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void test1() {
		try {
			String json = HttpUtil.httpGet("https://api.douban.com/v2/book/isbn/:978-7-121-15883-4");
			JSONObject jObj = new JSONObject(json);
			jObj.getJSONArray("author");
			System.out.println(jObj.getJSONArray("uthor"));
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}















