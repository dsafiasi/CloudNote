package zihong.wu.cloudnote.common.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.util.RequestUtils;
import zihong.wu.cloudnote.common.util.ResponseUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 接口转发 表单格式
 */

@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class FormController {
    @Resource
    HttpServletRequest request;

    @Autowired
    RestTemplate restTemplate;

    // TODO 转发前校验JWT和用户权限

    /**
     * 表单形式的GET请求
     * @return
     */
    @GetMapping
    public ResponseEntity get(){
        Map<String,String> map = RequestUtils.getParam(request);
        ResponseEntity responseEntity ;
        try {
            String url = RequestUtils.getUrl(map,request);
            if(map == null){
                responseEntity = restTemplate.getForEntity(url, String.class);
            }else{
                responseEntity = restTemplate.getForEntity(url, String.class,map);
            }
            RequestUtils.successLog(request,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,responseEntity);
        }
        return responseEntity;
    }


    /**
     * 表单形式的 POST 请求
     * @return
     */
    @PostMapping
    public ResponseEntity post(){
        Map<String,String> map = RequestUtils.getParam(request);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(RequestUtils.getUrl(map,request),null,String.class,map);
            RequestUtils.successLog(request,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,responseEntity);
        }
        return responseEntity;
    }


    /**
     * 表单形式的 PUT  请求
     * @return
     */
    @PutMapping
    public ResponseEntity put(){
        Map<String,String> map = RequestUtils.getParam(request);
        ResponseEntity responseEntity = Response.ok();
        try {
            restTemplate.put(RequestUtils.getUrl(map,request),null,map);
            RequestUtils.successLog(request,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,responseEntity);
        }
        return responseEntity;
    }

    /**
     * 表单形式的 DELETE 请求
     * restTemplate.delete()方法没有返回值，因此在delete操作时无法知道执行状态
     * @return
     */
    @DeleteMapping
    public ResponseEntity delete(){
        Map<String,String> map = RequestUtils.getParam(request);
        ResponseEntity responseEntity = Response.ok();
        try {
            restTemplate.delete(RequestUtils.getUrl(map,request),map);
            RequestUtils.successLog(request,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,responseEntity);
        }
        return responseEntity;

    }

}
