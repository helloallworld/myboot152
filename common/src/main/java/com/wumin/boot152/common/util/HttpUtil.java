package com.wumin.boot152.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * http请求工具类，默认字符集为UTF-8
 * 
 * @author zhujianbo
 * @date 2018-8-9
 */
public class HttpUtil {
	private final static Log logger = LogFactory.getLog(HttpUtil.class);
	/**
	 * 默认字符集
	 */
	private static final String defaultCharset = "UTF-8";

	/**
	 * 请求失败重试次数
	 */
	private static final int retryCount = 1;

	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param url
	 * @param content
	 * @param charset
	 * @return String
	 */
	public static final String sendViaPost(String url, String content, String charset) {
		if (StringUtils.isNotBlank(url) && url.toUpperCase().startsWith("HTTPS")) {
			return HttpUtil.sendHttpsRequestByPost(url, content, charset);
		} else {
			return HttpUtil.post(url, content, null,charset);
		}
	}

	// 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	private final static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		@Override
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= retryCount) {
				// Do not retry if over max retry count
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// Retry if the server dropped connection on us
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};
	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private final static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		@Override
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? defaultCharset : EntityUtils
						.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * Get方式提交,URL中包含查询参数, 格式：http://www.g.cn?search=p&name=s.....
	 * 
	 * @param url 提交地址
	 * @return 响应消息
	 */
	public static String get(String url) {
		return get(url, null, null,null);
	}

	/**
	 * Get方式提交,URL中包含查询参数, 格式：http://www.g.cn?search=p&name=s.....
	 * 
	 * @param url 提交地址
	 * @return 响应消息
	 */
	public static String get(String url, int connectTimeout, int soTimeout) {
		return get(url, null, null, connectTimeout, soTimeout);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url 提交地址
	 * @param params 查询参数集, 键/值对
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params,Map<String, String> headerParams) {
		return get(url, params, headerParams);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url 提交地址
	 * @param params 查询参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params, String charset, int connectTimeout, int soTimeout) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? defaultCharset : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
					: (url.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset, connectTimeout, soTimeout);
		logger.info("Get请求,url:" + url);
		HttpGet hg = new HttpGet(url);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseStr = "-11,客户端连接协议错误";
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url 提交地址
	 * @param params 查询参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params, Map<String,String> headerMap,String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? defaultCharset : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
					: (url.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		logger.info("Get请求,url:" + url);
		HttpGet hg = new HttpGet(url);

		//设置header
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			hg.addHeader(entry.getKey(), (String)entry.getValue());
		}

		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseStr = "-11,客户端连接协议错误";
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url 提交地址
	 * @param content 提交内容
	 * @return 响应消息
	 */
	public static String post(String url, String content) {
		return post(url, content, null,null);
	}

	/**
	 * Post方式提交
	 * 
	 * @param url 请求地址
	 * @param content 请求内容，可为空
	 * @param charset 字符集
	 * @return
	 */
	public static String post(String url, String content, Map<String,String> headerMap,String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		String responseStr = null;

		if (charset == null) {
			charset = defaultCharset;
		}

		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		ByteArrayEntity entity = null;
		HttpPost hp = null;

		try {
			hp = new HttpPost(url);
			if (!StringUtils.isBlank(content)) {
				entity = new ByteArrayEntity(content.getBytes(charset));
				hp.setEntity(entity);
			}

			//设置header
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				hp.addHeader(entry.getKey(), (String)entry.getValue());
			}

			responseStr = httpclient.execute(hp, responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交
	 * 
	 * @param url 请求地址
	 * @param content 请求内容，可为空
	 * @param charset 字符集
	 * @param connectTimeout 连接超时ms
	 * @param soTimeout 读取超时ms
	 * @return
	 */
	public static String post(String url, String content, String charset, int connectTimeout, int soTimeout) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		String responseStr = null;

		if (charset == null) {
			charset = defaultCharset;
		}

		DefaultHttpClient httpclient = getDefaultHttpClient(charset, connectTimeout, soTimeout);
		ByteArrayEntity entity = null;
		HttpPost hp = null;

		try {
			hp = new HttpPost(url);
			if (!StringUtils.isBlank(content)) {
				entity = new ByteArrayEntity(content.getBytes(charset));
				hp.setEntity(entity);
			}
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url 提交地址
	 * @param params 提交参数集, 键/值对
	 * @return 响应消息
	 */
	public static String post(String url, Map<String, String> params) {
		return post(url, params, null,null);
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url 提交地址
	 * @param params 提交参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static String post(String url,Map<String, String> params, Map<String, String> headerMap,String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		if (charset == null) {
			charset = defaultCharset;
		}

		// 发送请求，得到响应
		String responseStr = null;

		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (StringUtils.isBlank(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			responseStr = "-13,不支持的编码集";
		}
		HttpPost hp = new HttpPost(url);
		hp.setEntity(formEntity);

		//设置header
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			hp.addHeader(entry.getKey(), (String)entry.getValue());
		}

		try {
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseStr = "-11,客户端连接协议错误";
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @param charset 参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? defaultCharset : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		// 超时时间设置
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 30); // 连接超时30s
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 3); // 读取超时3min
		return httpclient;
	}

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @param charset 编码
	 * @param connectTimeout 连接超时时间
	 * @param soTimeout 读取数据超时时间
	 * @return
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset, int connectTimeout, int soTimeout) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? defaultCharset : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		// 超时时间设置
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout); // 连接超时30s
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout); // 读取超时3min
		return httpclient;
	}

	/**
	 * 释放HttpClient连接
	 * 
	 * @param hrb 请求对象
	 * @param httpclient client对象
	 */
	private static void abortConnection(final HttpRequestBase hrb, final HttpClient httpclient) {
		if (hrb != null) {
			hrb.abort();
		}
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param paramsMap 参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private static List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), String.valueOf(map.getValue())));
		}
		return params;
	}

	/**
	 * invokeGetService(这里用一句话描述这个方法的作用)
	 * 
	 * @param @param url
	 * @param @param charsetName
	 * @param @return
	 * @return String
	 * @Exception 异常对象
	 */
	public static String invokeGetService(String url, String charsetName, int timeoutms) {
		String resStr = "";
		HttpClient httpclient = null;
		// logger.info("request url : " + url);
		try {
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setIntParameter("http.socket.timeout", timeoutms);

			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream is = resEntity.getContent();
				resStr = convertStreamToString(is, charsetName);
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			// HttpClient的实例不再需要时，降低连接，管理器关闭，以确保立即释放所有系统资源
			if (httpclient != null)
				httpclient.getConnectionManager().shutdown();
		}
		// logger.debug("interface return result: " + resStr);
		return resStr;
	}

	public static String convertStreamToString(InputStream is, String charsetName) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the
		 * BufferedReader return null which means there's no more data to read. Each line will appended to a
		 * StringBuilder and returned as String.
		 */
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
		return sb.toString();
	}

	/**
	 * 发送HTTPS POST请求
	 * 
	 * @param 要访问的HTTPS地址,POST访问的参数Map对象
	 * @return 返回响应值
	 * */
	public static final String sendHttpsRequestByPost(String url, String content, String encode) {
		String responseContent = "-12,IO操作异常";
		;
		HttpClient httpClient = getDefaultHttpClient(encode);
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}

			@Override
			public void verify(String arg0, SSLSocket arg1) throws IOException {
			}

			@Override
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
			}

			@Override
			public void verify(String arg0, X509Certificate arg1) throws SSLException {
			}
		};
		HttpPost httpPost = null;
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			httpPost = new HttpPost(url);
			if (!StringUtils.isBlank(content)) {
				ByteArrayEntity entity = new ByteArrayEntity(content.getBytes(encode));
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, encode);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseContent = "-11,客户端连接协议错误";
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			abortConnection(httpPost, httpClient);
		}
		return responseContent;
	}

	/**
	 * 
	 * @param url 地址
	 * @param xml 内容
	 * @param charser 内容的编码
	 * @param contentType 请求头中的contentType
	 * @return
	 */
	public String PostWithXML(String url, String xml, String charset, String contentType) {
		String responseStr = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, contentType);

		ByteArrayEntity entity = null;
		try {
			entity = new ByteArrayEntity(xml.getBytes(charset));
			httpPost.setEntity(entity);
			responseStr = httpClient.execute(httpPost, responseHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseStr = e.getMessage();
		}
		return responseStr;
	}

	public String PostWithBytes(String url, byte[] bytes, String charset) {
		if (StringUtils.isBlank(url) || bytes == null) {
			logger.error("HttpUtil.PostWithBytes():url或者字节数组为空。");
			return null;
		}
		if (charset == null) {
			charset = defaultCharset;
		}
		String responseStr = null;
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		ByteArrayEntity entity = null;
		HttpPost hp = null;

		try {
			hp = new HttpPost(url);
			entity = new ByteArrayEntity(bytes);
			hp.setEntity(entity);
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (IOException e) {
			logger.error("HttpUtil.PostWithBytes()异常：", e);
			responseStr = null;
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * 发送HTTPS POST请求
	 * 
	 * @param 要访问的HTTPS地址,POST访问的参数Map对象
	 * @return 返回响应值
	 * */
	public static final String postHttpsJSON(String url, String content, String encode) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		if (encode == null) {
			encode = defaultCharset;
		}

		String responseContent = "-12,IO操作异常";

		HttpClient httpClient = getDefaultHttpClient(encode);
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}

			@Override
			public void verify(String arg0, SSLSocket arg1) throws IOException {
			}

			@Override
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
			}

			@Override
			public void verify(String arg0, X509Certificate arg1) throws SSLException {
			}
		};
		HttpPost httpPost = null;
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			httpPost = new HttpPost(url);

			httpPost.addHeader("Content-type", "application/json; charset=" + encode);
			httpPost.setHeader("Accept", "application/json");

			if (!StringUtils.isBlank(content)) {
				ByteArrayEntity entity = new ByteArrayEntity(content.getBytes(encode));
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, encode);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseContent = "-11,客户端连接协议错误";
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			abortConnection(httpPost, httpClient);
		}
		return responseContent;
	}

	/**
	 * Post方式提交
	 * 
	 * @param url 请求地址
	 * @param content 请求内容，可为空
	 * @param charset 字符集
	 * @return
	 */
	public static String postJSON(String url, String content, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		String responseStr = null;

		if (charset == null) {
			charset = defaultCharset;
		}

		DefaultHttpClient httpclient = getDefaultHttpClient(charset);

		ByteArrayEntity entity = null;
		HttpPost hp = null;

		try {
			hp = new HttpPost(url);
			hp.addHeader("Content-type", "application/json; charset=" + charset);
			hp.setHeader("Accept", "application/json");

			if (!StringUtils.isBlank(content)) {
				entity = new ByteArrayEntity(content.getBytes(charset));
				hp.setEntity(entity);
			}
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}


	public static void main(String args[]){
		/*Map<String,String> contentParams = new HashMap<String,String>();

		contentParams.put("orgCode","0000001");
		contentParams.put("orgName","组织机构1");



		Long timestamp = DateUtils.getCurrentTimeMillis();
		String appKey = "aaa.key";

		Map<String,String> signParams = new HashMap<String,String>();
		signParams.putAll(contentParams);
		signParams.put("appKey",appKey);
		signParams.put("timestamp",String.valueOf(timestamp));

		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("appKey",appKey);
		headerMap.put("timestamp",String.valueOf(timestamp));
		headerMap.put("Content-type", "application/json; charset=UTF-8");
		headerMap.put("Accept", "application/json");
		String sign = null;
		try {
			sign = SignUtil.sign(signParams,"5810B50A084419630624EF74643D1A0D");
		} catch (Exception e) {
			e.printStackTrace();
		}
		headerMap.put("sign",sign);


		String url = "http://localhost:9090/access/requirePlan/save";
		String content = JSONObject.toJSONString(contentParams);
		String response = HttpUtil.post(url,content,headerMap,"UTF-8");

		System.out.print("response==="+response);*/



		Map<String,String> contentParams = new HashMap<String,String>();
		contentParams.put("id","0000001");


		Long timestamp = DateUtils.getCurrentTimeMillis();
		String appKey = "aaa.key";

		Map<String,Object> signParams = new HashMap<String,Object>();
		signParams.putAll(contentParams);
		signParams.put("appKey",appKey);
		signParams.put("timestamp",String.valueOf(timestamp));

		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("appKey",appKey);
		headerMap.put("timestamp",String.valueOf(timestamp));
		String sign = null;
		try {
			sign = SignUtil.sign(signParams,"5810B50A084419630624EF74643D1A0D");
		} catch (Exception e) {
			e.printStackTrace();
		}
		headerMap.put("sign",sign);


		String url = "http://localhost:9090/access/requirePlan/view";
		String response = HttpUtil.post(url,contentParams,headerMap,"UTF-8");

		System.out.print("response==="+response);
	}
}
