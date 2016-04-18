package cc.geekie.wanjuanwu.dao;

import cc.geekie.wanjuanwu.domain.DetailBook;

public interface BookDetailDao {
	DetailBook getDetailBook();
	void getDetailDoc(String bookId);
}
