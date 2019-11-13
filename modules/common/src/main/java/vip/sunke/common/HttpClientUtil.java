package vip.sunke.common;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6.
 */
public class HttpClientUtil {

    public final static String REQUEST_HEADER = "x-forwarded-for";
    /**
     * 日志处理类
     */
    private static final Log log = LogFactory.getLog(HttpClientUtil.class);
    // 读取超时
    private final static int SOCKET_TIMEOUT = 10000;
    // 连接超时
    private final static int CONNECTION_TIMEOUT = 10000;
    // 每个HOST的最大连接数量
    private final static int MAX_CONN_PRE_HOST = 20;
    // 连接池的最大连接数
    private final static int MAX_CONN = 60;
    // 连接池
    private final static HttpConnectionManager httpConnectionManager;

    static {
        httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(CONNECTION_TIMEOUT);
        params.setSoTimeout(SOCKET_TIMEOUT);
        params.setDefaultMaxConnectionsPerHost(MAX_CONN_PRE_HOST);
        params.setMaxTotalConnections(MAX_CONN);
    }

    private static org.apache.commons.httpclient.NameValuePair[] getNameValuePairToArr(Map<String, String> parasMap) {
        if (parasMap == null)
            return null;
        int size = parasMap.size();
        org.apache.commons.httpclient.NameValuePair[] pairs = new org.apache.commons.httpclient.NameValuePair[size];
        int i = 0;
        for (Map.Entry<String, String> parasEntry : parasMap.entrySet()) {
            String parasName = parasEntry.getKey();
            String parasValue = parasEntry.getValue();
            pairs[i] = new org.apache.commons.httpclient.NameValuePair(parasName, parasValue);
            i++;
        }
        return pairs;

    }

    public static String doHttpRequest(String url, Map<String, String> cloneParamMap, String code) throws Exception {


        return doHttpRequest(url, cloneParamMap, code, null);
    }

    /**
     * @param url
     * @param cloneParamMap
     * @param code
     * @return
     * @throws Exception
     */

    public static String doHttpRequest(String url, Map<String, String> cloneParamMap, String code, Map<String, String> headMap) throws Exception {


        HttpPost httpPost = new HttpPost(url);

        if (headMap != null) {
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());

            }


        }

        List<NameValuePair> listNVP = new ArrayList<NameValuePair>();
        if (cloneParamMap != null) {
            for (String key : cloneParamMap.keySet()) {
                listNVP.add(new BasicNameValuePair(key, cloneParamMap.get(key).toString()));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(listNVP, "UTF-8");
        log.info("创建请求httpPost-URL={},params={}" + url + listNVP);
        httpPost.setEntity(entity);

        return doHttpRequest(httpPost, code, code);

      /*  PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestBody(getNameValuePairToArr(cloneParamMap));
        return doHttpRequest(postMethod, code, code);*/

    }

    /**
     * @param post
     * @param sendCode   发送数据的编码
     * @param acceptCode 接收数据时编码
     * @return
     * @throws Exception
     */

    public static String doHttpRequest(HttpPost post, String sendCode, String acceptCode) throws Exception {

        if (post == null)
            return "";

        if (post.getHeaders("Content-type") == null)
            post.setHeader("Content-type",
                    "application/x-www-form-urlencoded; charset=" + sendCode);


        return doHttpRequest(post, acceptCode);
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }


    public static String doHttpRequest(HttpRequestBase post, String code) {


        for (int i = 0; i < 3; i++) {
            try {
                return doOneHttpRequest(post, code);
            } catch (Exception e) {

            }


        }

        return "";


    }


    /**
     * 发送主要方法,异常捕获
     *
     * @param post
     * @param code
     * @return
     */
    public static String doOneHttpRequest(HttpRequestBase post, String code) throws Exception {


        CloseableHttpResponse httpResponse;
        CloseableHttpClient httpClient = createSSLClientDefault();


        //resetRequestHeader(httpClient, "10.0.23.178");
        // 设置读取超时时间(单位毫秒)
        // httpClient.getParams().setParameter("http.socket.timeout",socket_timeout);
        // 设置连接超时时间(单位毫秒)
        // httpClient.getParams().setParameter("http.connection.timeout",connection_timeout);
        // httpClient.getParams().setParameter("http.connection-manager.timeout",100000000L);
        BufferedReader in = null;
        String resultString = "";

        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());

        try {

            httpResponse = httpClient.execute(post);
            HttpEntity httpEntity = httpResponse.getEntity();



            if (httpEntity != null) {
                String jsObject = EntityUtils.toString(httpEntity, "UTF-8");
                return jsObject;
            } else {
                return null;
            }
        } catch (SocketTimeoutException e) {


            log.error("连接超时" + e.toString());

            throw e;

            // throw new CustomException(
            // TmallConstants.TmallExceptionType.CONNECTION_EXCEPTION,
            // "连接超时:" + e.getMessage(), e);
            //    resultString = returnError("连接超时");
        } catch (HttpException e) {
            log.error("读取外部服务器数据失败" + e.toString());
            throw e;
            // throw new CustomException(
            // TmallConstants.TmallExceptionType.SOCKET_EXCEPTION,
            // "读取天猫服务器数据失败:" + e.getMessage(), e);
            //   resultString = returnError("读取外部服务器数据失败");
        } catch (UnknownHostException e) {
            log.error("请求的主机地址无效" + e.toString());
            throw e;
            // throw new CustomException(
            // TmallConstants.TmallExceptionType.CONNECTION_EXCEPTION,
            // "请求的主机地址无效：" + e.getMessage(), e);
            //   resultString = returnError("请求的主机地址无效");
        } catch (IOException e) {
            log.error("向外部接口发送数据失败" + e.toString());
            throw e;
            // throw new CustomException(
            // TmallConstants.TmallExceptionType.INNER_EXCEPTION,
            // "向天猫发送数据失败", e);
            //  resultString = returnError("向外部接口发送数据失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            post.releaseConnection();
        }

    }

    /**
     * 设置一下返回错误的通用提示,可以自定义格式.
     *
     * @param reason
     * @return
     */
    public static String returnError(String reason) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
        buffer.append("<Response>");
        buffer.append("<Success>false</Success>");
        buffer.append("<reason>");
        buffer.append(reason);
        buffer.append("</reason>");
        buffer.append("</Response>");
        return buffer.toString();
    }

    /**
     * 将客户IP写入请求头
     * 这个设置可以伪装IP请求,注意使用
     *
     * @param client
     * @param ip
     * @return
     */
    public static void resetRequestHeader(HttpClient client, String ip) {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header(REQUEST_HEADER, ip));
        client.getHostConfiguration().getParams().setParameter(
                "http.default-headers", headers);
    }


    public static void main(String[] args) throws Exception {


    }


}
