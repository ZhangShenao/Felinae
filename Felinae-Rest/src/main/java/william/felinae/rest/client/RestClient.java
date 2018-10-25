package william.felinae.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import william.felinae.json.util.JacksonUtil;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/12 15:48
 * @Description: Rest客户端,封装发送HTTP请求的操作
 */
@Component
public class RestClient {
    @Autowired
    @Qualifier("encodedRestTemplate")
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(RestClient.class);

    /**
     * 发送HTTP请求,获取响应
     * @param url 请求URL
     * @param requestJson 请求参数Json字符串
     * @param responseClass 返回的响应类型
     * @param requestMethod 请求方式(POST、GET、PUT、DELETE)
     * @return 响应对象
     */
    public <T> T queryForResponse(String url,String requestJson,Class<T> responseClass,RequestMethod requestMethod){
        ResponseEntity<String> responseEntity = null;
        switch (requestMethod){
            case GET: responseEntity = restTemplate.getForEntity(url,String.class); break;
            case POST: {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                HttpEntity<String> entity = new HttpEntity<>(requestJson,headers);
                responseEntity =  restTemplate.postForEntity(url,entity,String.class);
            } break;
        }

        if (responseEntity == null){
            logger.error("queryForResponse#queryForResponse(): Get Response Error !! URL: {},responseClass: {},requestMethod: {}",
                    url,responseClass.getName(),requestMethod.name());
            return null;
        }

        String respBody = responseEntity.getBody();
        try {
            return JacksonUtil.readValue(respBody,responseClass);
        } catch (Exception e) {
            logger.error("queryForResponse#queryForResponse(): Get Response Error !! URL: {},responseClass: {},requestMethod: {}",
                    url,responseClass.getName(),requestMethod.name());
            throw new RuntimeException(e);
        }
    }
}
