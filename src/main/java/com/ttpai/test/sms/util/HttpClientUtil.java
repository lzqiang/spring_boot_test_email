package com.ttpai.test.sms.util;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @Description: TODO
 * @author: simon.JY
 * @date: 2015年8月12日 久兴信息技术(上海)有限公司
 */
public class HttpClientUtil {
	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private final static Integer CONNECTION_OUT_TIME = 2000;
	private final static Integer READ_OUT_TIME = 2000;
	public final static String URL_CHARSET = "UTF-8";
	public final static String GET = "GET";
	public final static String POST = "POST";

	/**
	 * GET请求
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		return executeMethod(GET, url, null, URL_CHARSET);
	}

	/**
	 * GET请求
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> param) throws Exception {
		if (null == url)
			return null;
		Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		String queryParams = "";
		while (iterator.hasNext()) {
			Map.Entry<String, String> kv = iterator.next();
			sb.append(kv.getKey()).append("=").append(kv.getValue()).append("&");
		}
		if (sb.length() > 0) {// 不为空的str需要去掉末尾的&
			queryParams = sb.substring(0, sb.length() - 1);
		}
		url = url + "?" + queryParams;
		return executeMethod(GET, url, null, URL_CHARSET);
	}

	/**
	 * POST提交
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String post(String url) throws Exception {
		return post(url, null);
	}

	/**
	 * POST提交
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map params) throws Exception {
		return executeMethod(POST, url, params, URL_CHARSET);
	}

	/**
	 * @param targetUrl
	 * @param params
	 * @return
	 * @throws Exception
	 * @Description: 发送一个post请求，获取返回的json格式字符串
	 * @author: simon.JY
	 * @date: 2015年8月12日 下午9:31:19
	 */
	public static String executeMethod(String methodType, String targetUrl, Map<String, Object> params, String encoding)
			throws Exception {
		if (null == targetUrl)
			return null;
		SimpleHttpConnectionManager connectionManager = new SimpleHttpConnectionManager(true);
		// 连接超时,单位毫秒
		connectionManager.getParams().setConnectionTimeout(CONNECTION_OUT_TIME);
		// 读取超时,单位毫秒
		connectionManager.getParams().setSoTimeout(READ_OUT_TIME);
		connectionManager.getParams().setTcpNoDelay(false);
		// 设置获取内容编码
		connectionManager.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
		HttpClient httpClient = new HttpClient(connectionManager);
		HttpMethod method = null;

		if (POST.equals(methodType)) {
			PostMethod pmethod = new PostMethod(targetUrl);
			// 判断参数是否为空
			if (params != null) {
				NameValuePair[] nameValuePairArr = new NameValuePair[params.size()];
				Set key = params.keySet();
				Iterator keyIte = key.iterator();
				int index = 0;
				while (keyIte.hasNext()) {
					Object paramName = keyIte.next();
					Object paramValue = params.get(paramName);
					if (paramName instanceof String && paramValue instanceof String) {
						NameValuePair pair = new NameValuePair((String) paramName, (String) paramValue);
						nameValuePairArr[index] = pair;
						index++;
					}
				}
				pmethod.addParameters(nameValuePairArr);

			}
			method = pmethod;
		} else {
			try {
				method = new GetMethod(new URL(targetUrl).toString());
			} catch (MalformedURLException e) {
				logger.error(e.toString());
			}
		}

		// 设置请求参数的编码
		method.getParams().setContentCharset(encoding);
		String resmethod = "";
		try {
			httpClient.executeMethod(method);
			// 获取返回的信息
			resmethod = method.getResponseBodyAsString();
		} catch (IOException e) {
			logger.error("http请求数据异常!请求[url:{}],[param:{}]。异常错误信息" + e.getMessage(), targetUrl, params);
		} finally {
			// 释放连接
			method.releaseConnection();
			// 关闭连接
			connectionManager.shutdown();
		}
		return resmethod;
	}
}
