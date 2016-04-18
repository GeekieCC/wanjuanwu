package cc.geekie.wanjuanwu.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.geekie.wanjuanwu.dao.CollectionInfoDao;
import cc.geekie.wanjuanwu.dao.impl.CollectionInfoDaoImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class CheckCollectionInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String bookId = request.getParameter("bookId");
		CollectionInfoDao dao = new CollectionInfoDaoImpl();
		int result = dao.getCollectionInfo(bookId);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		throw new NotImplementedException();
	}

}
