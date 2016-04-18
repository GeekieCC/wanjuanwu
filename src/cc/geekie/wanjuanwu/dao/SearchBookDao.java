package cc.geekie.wanjuanwu.dao;

import java.util.List;

import cc.geekie.wanjuanwu.domain.SearchBook;
import cc.geekie.wanjuanwu.domain.SearchInfo;
import cc.geekie.wanjuanwu.exception.WrongPageException;

public interface SearchBookDao {
	void searchEngine(String searchContent, String searchCriteria, String page) throws WrongPageException;
	List<SearchBook> searchEngineAdvance(String searchContent, String searchCriteria, String page);
	SearchInfo getSearchInfo() throws WrongPageException;
	List<SearchBook> getResultBook();

}
