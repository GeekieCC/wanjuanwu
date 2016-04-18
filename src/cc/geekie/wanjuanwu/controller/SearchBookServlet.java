package cc.geekie.wanjuanwu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cc.geekie.wanjuanwu.dao.SearchBookDao;
import cc.geekie.wanjuanwu.dao.impl.SearchBookDaoImpl;
import cc.geekie.wanjuanwu.domain.SearchBook;
import cc.geekie.wanjuanwu.domain.SearchInfo;
import cc.geekie.wanjuanwu.exception.WrongPageException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//request.setCharacterEncoding("ISO-8859-1");
		String searchContent = request.getParameter("searchContent");
		searchContent = new String(searchContent.getBytes("iso8859-1"),"UTF-8");
		String searchCriteria = request.getParameter("searchCriteria");
		String page = request.getParameter("page");
		//String needBorrowInfo = request.getParameter("needBorrowInfo");
		List<SearchBook> searchBookList;
		SearchBookDao searchBookDao = new SearchBookDaoImpl();
		SearchInfo searchInfo;
		JSONObject jObj = new JSONObject();
		try {
//			if ("true".equals(needBorrowInfo)) {
//				searchBookDao.searchEngineAdvance(searchContent, searchCriteria, page);
//			} else {
				searchBookDao.searchEngine(searchContent, searchCriteria, page);
//			}

			searchInfo = searchBookDao.getSearchInfo();
			jObj.put("searchInfo", jObj.wrap(searchInfo));
			if (searchInfo.getBookNum() != 0) {
				searchBookList = searchBookDao.getResultBook();
				jObj.put("bookList", jObj.wrap(searchBookList));
			}
		} catch (WrongPageException e) {
			
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		PrintWriter out = response.getWriter();
		out.print(jObj.toString());
		out.flush();
		out.close();
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new NotImplementedException();
	}

}
