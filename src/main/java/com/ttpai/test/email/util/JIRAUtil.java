package com.ttpai.test.email.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ttpai.test.email.vo.JIRAVO;

public class JIRAUtil {

	private static HttpResponse cookieLogin(String cookie, String cookie2, String url) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);

		httpGet.setHeader("Cookie", "JSESSIONID=" + cookie + ";" + "NSC_kjbpxv-iuuq=" + cookie2);// 注意分号
		HttpResponse response = new DefaultHttpClient().execute(httpGet);

		logger.info("cookie登陆成功。");
		return response;
	}

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	private static String loginUrl = "http://jira.ttpai.cn/login.jsp";
	private static String userName = "zhaoqiang.liu";
	private static String passWord = "12345678a";
	private static String login = "登陆";

	private static HttpResponse userAndPwdLogin(String url) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient(); // 构建一个Client
		HttpPost post = new HttpPost(loginUrl); // 构建一个POST请求
		// 构建表单参数
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("os_username", userName));
		formParams.add(new BasicNameValuePair("os_password", passWord));
		formParams.add(new BasicNameValuePair("os_cookie", "true"));
		formParams.add(new BasicNameValuePair("login", login));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");// 将表单参数转化为“实体”
		post.setEntity(entity); // 将“实体“设置到POST请求里

		HttpResponse response = client.execute(post);// 提交POST请求

		if (response.getStatusLine().getStatusCode() == 200) {
			List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();// 获取Cookie

			if (cookies != null && cookies.size() >= 2) {
				cookie = cookies.get(0).getValue();
				cookie2 = cookies.get(1).getValue();
				Map<String, String> keyValueMap = new HashMap<String, String>();
				//更新配置文件
				keyValueMap.put("JSESSIONID", cookie);
				keyValueMap.put("NSC_kjbpxv-iuuq", cookie2);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DAY_OF_MONTH, 5);
				Date cookieExpiryDate = cookies.get(0).getExpiryDate()==null? cal.getTime():cookies.get(0).getExpiryDate();
				keyValueMap.put("COOKIE_EXPIRY_DATE", simpleDateFormat.format(cookieExpiryDate));
				PropertyUtils.updateProperties("cookies.properties", keyValueMap);
				logger.error("用户名密码登陆成功");
				return cookieLogin(cookie, cookie2, url);
			}

		} else {
			logger.error("用户名密码登陆JIRA失败");
		}

		return null;
	}

	private static String cookie = PropertyUtils.getProperty("cookies.properties", "JSESSIONID");

	private static String COOKIE_EXPIRY_DATE = PropertyUtils.getProperty("cookies.properties", "COOKIE_EXPIRY_DATE");

	private static String cookie2 = PropertyUtils.getProperty("cookies.properties", "NSC_kjbpxv-iuuq");

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		String content = getJira("http://jira.ttpai.cn/browse/INVITATION-877");

		Document containerDoc = Jsoup.parse(content);
		System.out.println(containerDoc);
		String title =  containerDoc.getElementById("summary-val").text();

		System.out.println(title);
	}

	public static JIRAVO getJiraInfo(String url) {
		JIRAVO vo = new JIRAVO();

		if(StringUtils.isNotEmpty(url)) {
			String content = getJira(url);

			Document containerDoc = Jsoup.parse(content);
			String title =  containerDoc.getElementById("summary-val").text();

			vo.setUrl(url);
			vo.setTitle(title);
		}
		return vo;
	}

	public static String getJira(String url) {
		String htmlContent = "";
		try {
			HttpResponse response = null;
			response = userAndPwdLogin(url);

	/*		if(simpleDateFormat.parse(COOKIE_EXPIRY_DATE).after(new Date())) {
				response = cookieLogin(cookie, cookie2, url);
			} else {
			}*/
			if(response != null) {
				HttpEntity result2 = response.getEntity();
				htmlContent = EntityUtils.toString(result2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return htmlContent;
	}
}