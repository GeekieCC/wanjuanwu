package cc.geekie.wanjuanwu.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cc.geekie.wanjuanwu.dao.BookDetailDao;
import cc.geekie.wanjuanwu.dao.impl.BookDetailDaoImpl;
import cc.geekie.wanjuanwu.domain.DetailBook;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class BookDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String bookId = request.getParameter("bookId");
		BookDetailDao bookDetailDao = new BookDetailDaoImpl();
		bookDetailDao.getDetailDoc(bookId);
		DetailBook detailBook = bookDetailDao.getDetailBook();
		JSONObject jObj = new JSONObject();
		PrintWriter out = response.getWriter();
		out.print(jObj.wrap(detailBook).toString());
		out.flush();
		out.close();
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new NotImplementedException();
	}

}
