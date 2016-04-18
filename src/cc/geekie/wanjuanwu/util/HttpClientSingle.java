package cc.geekie.wanjuanwu.util;

import org.apache.commons.httpclient.HttpClient;

public class HttpClientSingle {
	private static HttpClient client;
	public HttpClientSingle() {
		
	}
	public static HttpClient getInstance() {
		if (client == null) {
			client = new HttpClient();
		}
		return client;
	}
}
