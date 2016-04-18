package cc.geekie.wanjuanwu.service;

import java.util.List;

import cc.geekie.wanjuanwu.domain.BorrowBook;

public interface BookService {
	List<BorrowBook> getBorrowedBookList();

	boolean login(String username, String passwd);

	boolean renew(String renewLink);
}
