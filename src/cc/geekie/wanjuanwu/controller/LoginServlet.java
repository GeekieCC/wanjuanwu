package cc.geekie.wanjuanwu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import cc.geekie.wanjuanwu.domain.BorrowBook;
import cc.geekie.wanjuanwu.service.BookService;
import cc.geekie.wanjuanwu.service.impl.BookServiceImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(true);
		BookService bs = (BookService) session.getAttribute("bookService");

		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		if ("login".equals(action)) {
			String username = request.getParameter("username");
			String passwd = request.getParameter("passwd");
			BookService bs2 = new BookServiceImpl();
			boolean isLogined = bs2.login(username, passwd);
			if (isLogined) {
				session.setAttribute("bookService", bs2);
			} else {
				session.setAttribute("bookService", null);
			}

			out.print(isLogined);
		} else if ("renew".equals(action) && bs != null) {
			String bookId = request.getParameter("bookId");
			//System.out.println(bookId);
			boolean isRenewed = bs.renew(bookId);
			out.print(isRenewed);
		} else if ("borrowed".equals(action) && bs != null) {
			List<BorrowBook> bookList = bs.getBorrowedBookList();
//			System.out.println(username);
//			System.out.println(passwd);
			JSONObject jObj = new JSONObject();
			out.write(jObj.wrap(bookList).toString());

		}
		out.flush();
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new NotImplementedException();
	}

}























