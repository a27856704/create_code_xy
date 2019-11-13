package vip.sunke.web.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.Map;

@Component
public class HttpUtil {


    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> httpGet(String url) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String result = null;

        try {
            result = restTemplate.getForObject(builder.build().encode(Charset.forName("UTF-8")).toUri(), String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        JSONObject json = JSONObject.parseObject(result);
        Map a = (Map) (json.get("data"));//.get("data");
        return a;
    }

    public JSONObject httpGetJsonObject(String url) {

        return JSONObject.parseObject(httpGetString(url));
    }

    public JSONArray httpGetJsonArray(String url) {

        return JSONArray.parseArray(httpGetString(url));
    }

    public String httpGetString(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url);


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String result = null;
        try {
            result = restTemplate.getForObject(builder.build().encode(Charset.forName("UTF-8")).toUri(), String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map httpPutReturnMap(String url, Object paras) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(paras.toString(), headers);
        ResponseEntity<Map> resp = restTemplate.exchange(url, HttpMethod.PUT, entity, Map.class);
        Map result = resp.getBody();
        return result;
    }

    public SkMap httpPut(String url, Object paras) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(paras.toString(), headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, SkMap.class).getBody();
    }

    public SkMap httpPost(String url, Object paras) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(paras.toString(), headers);
        SkMap result = restTemplate.postForEntity(url, entity, SkMap.class).getBody();
        return result;
    }


    public SkMap httpDelete(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        try {
            return restTemplate.exchange(builder.build().encode(Charset.forName("UTF-8")).toUri(), HttpMethod.DELETE, null, SkMap.class).getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject httpDeleteJson(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        try {
            return restTemplate.exchange(builder.build().encode(Charset.forName("UTF-8")).toUri(), HttpMethod.DELETE, null, JSONObject.class).getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject httpPutJson(String url, Object paras) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(paras.toString(), headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, JSONObject.class).getBody();
    }

    public JSONObject httpPostJson(String url, Object paras) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30000);
        httpRequestFactory.setConnectTimeout(30000);
        httpRequestFactory.setReadTimeout(30000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(paras.toString(), headers);
        JSONObject result = restTemplate.postForEntity(url, entity, JSONObject.class).getBody();
        return result;
    }

}
