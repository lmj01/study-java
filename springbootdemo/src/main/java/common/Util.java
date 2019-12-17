package common;

import org.springframework.http.HttpHeaders;

public class Util {

	public static HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
    	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    	headers.add("Pragma", "no-cache");
    	headers.add("Expires", "0");
    	headers.add("charset", "utf-8");
    	return headers;
	}
}
