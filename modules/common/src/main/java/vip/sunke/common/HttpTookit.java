package vip.sunke.common;

import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.Map;


public final class HttpTookit {


    private static Logger log = Logger.getLogger(HttpTookit.class);


    private static String doTryGet(String url, String queryString, Map<String, String> headMap) throws Exception {

        if (queryString != null && !"".equals(queryString))
            if (-1 == url.indexOf("?")) {
                url += "?" + queryString;
            } else {
                url += "&" + queryString;
            }


        HttpGet httpGet = new HttpGet(url);

        if (headMap != null) {
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());


            }

        }

        return HttpClientUtil.doHttpRequest(httpGet, "utf-8");


    }

    public static String doGetHeadMap(String url, String queryString, Map<String, String> headMap) {

        int tryCount = Const.TRY_MAX_COUNT;
        while (tryCount-- > 0) {
            try {
                return doTryGet(url, queryString, headMap);
            } catch (Exception e) {
                log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
            }
        }

        return "";


    }

    public static String doGet(String url, String queryString) {

        int tryCount = Const.TRY_MAX_COUNT;
        String res="";
        while (tryCount-- > 0) {
            try {


                res= doTryGet(url, queryString, null);

                if(!"".equals(res)){
                    return res;
                }
            } catch (Exception e) {
                log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
            }
        }

        return "";


    }


    public static String doPostJson(String strURL, String paramsJson) {
        int tryCount = Const.TRY_MAX_COUNT;


        while (tryCount-- > 0) {
            try {
                return doTryPostJson(strURL, paramsJson);

            } catch (Exception e) {
                log.error("执行HTTP Post json请求" + strURL + "时，发生异常！", e);

            }
        }


        return "";

    }


    private static String doTryPostJson(String strURL, String paramsJson) throws Exception {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(strURL);// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            //  connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "utf-8"); // utf-8编码
            out.append(paramsJson);
            out.flush();
            out.close();
            InputStream is = connection.getInputStream();
            // 读取请求内容
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return URLDecoder.decode(sb.toString(), "utf-8");

        } catch (IOException e) {
            throw e;

        } finally {
            try {
                connection.disconnect();
            } catch (Exception e) {

            }
        }
        // return ""; // 自定义错误信息*/


    }


    /**
     * @param url
     * @param params
     * @param code
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String code) {

        int tryCount = Const.TRY_MAX_COUNT;

        while (tryCount-- > 0) {
            try {
                return doTryPost(url, params, code, null);
            } catch (Exception e) {
                log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
            }


        }

        return "";


    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headMap) {

        int tryCount = Const.TRY_MAX_COUNT;

        while (tryCount-- > 0) {
            try {

                String result = doTryPost(url, params, "utf-8", headMap);
                if (StringUtil.isNotEmpty(result))
                    return result;

            } catch (Exception e) {
                log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
            }


        }

        return "";


    }


    /**
     * @param url
     * @param params
     * @param code
     * @return
     */
    private static String doTryPost(String url, Map<String, String> params, String code, Map<String, String> headMap) throws Exception {


        return HttpClientUtil.doHttpRequest(url, params, code, headMap);


    }


    /**
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, String> params) {

        return doPost(url, params, "utf-8");

      /*  com.sk.common.util.https.http.HttpClient client = new com.sk.common.util.https.http.HttpClient();

        PostParameter[] pps =null;

        if(params!=null ){
            pps = new PostParameter[params.size()];
            Iterator iter = params.entrySet().iterator();
            int i = 0;
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                PostParameter pp = new PostParameter((String) entry.getKey(), (String) entry.getValue());
                pps[i] = pp;
                i++;
            }
        }

//		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        Response response = null;




        try {
            response = client.post(url, pps, false, null);
            return response.asString();
        } catch (Exception e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
//			method.releaseConnection();
        }*/

        //   return "";
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

//		String url="http://yzm.mb345.com/ws/BatchSend2.aspx";
//		Map<String, String> params =new HashMap<String, String>();
//		params.put("CorpID", "CDLK00064");
//		params.put("Pwd", "00064@");
//		params.put("Mobile", "15990084484");
//
//		params.put("Content",EncodeUtil.encodeStr("您正在绑定银行帐号，手机验证码:803617，有效期120秒，客服热线4008805740。【泓信在线】"));
//
//		System.out.println(HttpTookit.doPost(url, params,"GBK"));


    }


    public static String sendGet(String url) {


        HttpGet getMethod = new HttpGet(url);


        getMethod.setHeader("Content-type",
                "text/html;charset=utf-8");
        try {

            return HttpClientUtil.doHttpRequest(getMethod, "utf-8");
        } catch (Exception e) {


        }

        return null;

       /* String msg = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
                    url).openConnection();
            msg = creatConnection(url, httpURLConnection);
        } catch (IOException io) {
            log.error("http close" + io);
        }
        System.out.println("信息发送情况1：" + msg);
        return msg;*/
    }

    private static String creatConnection(String url,
                                          HttpURLConnection httpURLConnection) {
        String msg = "";
        try {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            httpURLConnection = (HttpURLConnection) new URL(url)
                    .openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "text/html;charset=utf-8");
            msg = receiveMessage(httpURLConnection);
        } catch (IOException io) {
            io.printStackTrace();
            log.error("Http Connect to :" + url + " " + "IOFail!");
        } catch (Exception ex) {
            log.error("Http Connect to :" + url + " " + "Failed" + ex);
        } finally {
            closeConnection(httpURLConnection);
        }
        return msg;
    }

    private static void closeConnection(HttpURLConnection httpURLConnection) {
        try {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        } catch (Exception localException) {
        }
    }

    private static String receiveMessage(HttpURLConnection httpURLConnection) {
        String responseBody = null;
        try {
            InputStream httpIn = httpURLConnection.getInputStream();
            if (httpIn != null) {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                byte tempByte;
                while (-1 != (tempByte = (byte) httpIn.read())) {
                    byte tempByte1 = 0;
                    byteOut.write(tempByte1);
                }
                responseBody = new String(byteOut.toByteArray(), "utf-8");
            }
        } catch (IOException ioe) {
            log.error("Http Connect tosss :" + ioe.getLocalizedMessage() + " "
                    + "IOEFail!");
            return null;
        }
        return responseBody;
    }

    public static String getRealIpAddr(HttpServletRequest request) {
        try {

            String ip = getIpAddr(request);
            //  System.out.println("ip:"+ip);
            return ip;


            /*String ip = request.getHeader("X-Real-IP");
            System.out.println("ip:"+ip);


            if ((ip == null) || (ip.length() == 0)
                    || ("unknown".equalsIgnoreCase(ip))) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if ((ip == null) || (ip.length() == 0)
                    || ("unknown".equalsIgnoreCase(ip))) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if ((ip == null) || (ip.length() == 0)
                    || ("unknown".equalsIgnoreCase(ip))) {
                ip = request.getRemoteAddr();
            }
            return ip;*/
        } catch (Exception e) {

            e.printStackTrace();
            return getHostAddress();
        }


    }

    public static String getHostAddress() {
        try {
            InetAddress ipaddr = InetAddress.getLocalHost();
            return ipaddr.getHostAddress();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return "127.0.0.1";

    }


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }


    public static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";
    }


    /**
     * 发送json包
     *
     * @param url
     * @param param json字符串
     * @return
     * @throws Exception
     */
    public static String sendPostFromJson(String url, String param) throws Exception {


        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            new Exception("发送 POST 请求出现异常！" + e);

        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;


    }
}
