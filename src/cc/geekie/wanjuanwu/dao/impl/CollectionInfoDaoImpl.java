package cc.geekie.wanjuanwu.dao.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cc.geekie.wanjuanwu.dao.CollectionInfoDao;
import cc.geekie.wanjuanwu.domain.CollectInfo;
import cc.geekie.wanjuanwu.util.HttpUtil;

public class CollectionInfoDaoImpl implements CollectionInfoDao {

	@Override
	public int getCollectionInfo(String bookId) {
		String url = BookDetailDaoImpl.BOOK_DETAIL_URL + bookId;
		Document doc = null;
		try {
			String html = HttpUtil.httpGet(url);
			doc = Jsoup.parse(html);
			List<CollectInfo> collectInfoList = getCollectList(doc);
			return getInfoHelper(collectInfoList);
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	private int getInfoHelper(List<CollectInfo> collectInfoList) {
		boolean north = false;
		boolean south = false;
		for (CollectInfo info : collectInfoList) {
			String location = info.getLocation();
			String detailLocation = info.getDetailLocation();
			String status = info.getStatus();
			if (!location.contains("停") && !detailLocation.contains("停") && status.contains("在馆")) {
				if (location.contains("北") || detailLocation.contains("北")) north = true;
				if (location.contains("南") || detailLocation.contains("南")) south = true;
			}
		}
		if (!north && !south) return CollectInfo.BOTH_NOT;
		if (north && south) return CollectInfo.BOTH_YES;
		if (north && !south) return CollectInfo.NORTH_ONLY;
		if (!north && south) return CollectInfo.SOUTH_ONLY;
		
		return CollectInfo.UNKNOWN;
	}

	private List<CollectInfo> getCollectList(Document doc) {
		List<CollectInfo> collectInfo = new ArrayList<>();
		Elements tableElements = doc.getElementsByTag("tbody");
		Elements trElements = tableElements.last().getElementsByTag("tr");
		trElements.remove(0);
		for (Element tr : trElements) {
			Elements es = tr.getElementsByTag("td");
			String location = es.get(0).text();
			String detailLocation = es.get(1).text();
			String status = es.get(5).text();
			CollectInfo collect = new CollectInfo(location, detailLocation, status);
			collectInfo.add(collect);
		}

		return collectInfo;
	}

}
