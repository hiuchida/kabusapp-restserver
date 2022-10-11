package util;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * HTTP操作に関するユーティリティクラス。
 */
public class HttpUtil {

	/**
	 * エンコーディング：UTF-8。
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * JSON文字列をPOSTする。
	 * 
	 * @param uri  送信先URI。
	 * @param data JSON文字列。
	 * @return HTTPレスポンスステータス。
	 * @throws IOException
	 */
	public static String postJson(String uri, String data) throws IOException {
		StringEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);
		String ret = "";
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(entity);
			try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
				StatusLine status = response.getStatusLine();
				ret = status.toString();
				write(System.out, response);
			} finally {
			}
		} finally {
		}
		return ret;
	}

	/**
	 * HTTPレスポンスを出力する。
	 * 
	 * @param os       出力ストリーム。
	 * @param response HTTPレスポンス。
	 * @throws IOException
	 */
	private static void write(OutputStream os, CloseableHttpResponse response) throws IOException {
		StatusLine status = response.getStatusLine();
		Header typeHeader = response.getLastHeader("Content-Type");
		String type = typeHeader != null ? typeHeader.getValue() : "";
		Header dispositionHeader = response.getLastHeader("Content-Disposition");
		String disposition = dispositionHeader != null ? dispositionHeader.getValue() : "";
		HttpEntity entity = response.getEntity();
		os.write((status.toString() + "\r\n").getBytes(UTF8));
		os.write(("Content-Type=" + type + "\r\n").getBytes(UTF8));
		os.write(("Content-Disposition=" + disposition + "\r\n").getBytes(UTF8));
		entity.writeTo(os);
		os.write("\r\n".getBytes(UTF8));
	}

}
