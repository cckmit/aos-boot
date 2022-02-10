package ink.aos.boot.oapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import ink.aos.boot.oapi.crypto.RSAUtils;
import ink.aos.boot.oapi.exception.OapiException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenApiHandle {

    private static final String REDIS_KEY_RSA_INNER_PRIVATE = "aos_openapi:rsa_key_inner_private";
    private static final String REDIS_KEY_RSA_OUTER_PUBLIC = "aos_openapi:rsa_key_outter_public";

    public final ObjectMapper objectMapper;

    public OpenApiHandle(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
//
//    public void setAosPrivate(String sysCode, String privateKey) {
//        redisTemplate.boundHashOps(REDIS_KEY_RSA_INNER_PRIVATE).put(sysCode, privateKey);
//    }
//
//    public void setSysPublic(String sysCode, String publicKey) {
//        redisTemplate.boundHashOps(REDIS_KEY_RSA_OUTER_PUBLIC).put(sysCode, publicKey);
//    }

//    /**
//     * 请求加密
//     *
//     * @param sysCode   系统标识
//     * @param sourceReq 源请求
//     * @return 密文返回
//     */
//    public ApiReq encryptReq(String sysCode, SourceReq sourceReq) {
//        String privateKey = (String) redisTemplate.boundHashOps(REDIS_KEY_RSA_INNER_PRIVATE).get(sysCode);
//        String publicKey = (String) redisTemplate.boundHashOps(REDIS_KEY_RSA_OUTER_PUBLIC).get(sysCode);
//        String encryptData = null;
//        String sign = null;
//        long timestamp = System.currentTimeMillis();
//        try {
//            if (sourceReq.getData() != null) {
//                encryptData = RSAUtils.encryptByPublicKey(objectMapper.writeValueAsString(sourceReq.getData()), publicKey);
//            }
//            String verifyData = signParamResp(sysCode, sourceReq.getOrderNo(), timestamp, encryptData); // 签名的数据源
//            sign = RSAUtils.sign(verifyData, privateKey);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new OpenapiException("加密异常：" + e.getMessage());
//        }
//        ApiReq apiReq = new ApiReq();
//        apiReq.setOrderNo(sourceReq.getOrderNo());
//        apiReq.setData(encryptData);
//        apiReq.setSign(sign);
//        apiReq.setTimestamp(timestamp);
//        apiReq.setFileBase64(sourceReq.getFileBase64());
//        apiReq.setSysCode(sysCode);
//        return apiReq;
//    }

    /**
     * 接收请求参数解密
     *
     * @param apiReq 请求密文
     * @param cla    类
     * @return 源请求
     */
    public SourceReq<?> decryptReq(String privateKey, String publicKey, ApiReq apiReq, Class<?> cla) {
        Object o;
        {
            // 验证
            boolean flag;
            try {
                String verifyData = signParam(apiReq.getSysCode(), apiReq.getOrderNo(), apiReq.getTimestamp(), apiReq.getData());
                flag = RSAUtils.verify(verifyData, publicKey, apiReq.getSign());
                log.debug("STEP 1 : 验证签名 {}", flag);
                if (!flag) {
                    throw new OapiException("验证签名失败");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new OapiException("验证签名异常" + e.getMessage());
            }

        }
        {
            // 解析
            try {
                String data = RSAUtils.decryptByPrivateKey(apiReq.getData(), privateKey);
                log.debug("STEP 2 : 解析 {}", data);
                o = objectMapper.readValue(data, cla);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new OapiException("解析异常：" + e.getMessage());
            }
        }
        SourceReq<Object> sourceReq = new SourceReq<>();
        sourceReq.setSysCode(apiReq.getSysCode());
        sourceReq.setOrderNo(apiReq.getOrderNo());
        sourceReq.setData(o);
        return sourceReq;
    }

    /**
     * 响应加密
     *
     * @param sysCode    系统标识
     * @param sourceResp 源返回
     * @return 密文返回
     */
    public ApiResp encryptResp(String sysCode, String privateKey, String publicKey, SourceResp sourceResp) throws Exception {
        String encryptData = null;
        String sign = null;
        long timestamp = System.currentTimeMillis();
        if (sourceResp.getData() != null) {
            encryptData = RSAUtils.encryptByPublicKey(objectMapper.writeValueAsString(sourceResp.getData()), publicKey);
        }
        String verifyData = signParam(sysCode, sourceResp.getOrderNo(), timestamp, encryptData); // 签名的数据源
        sign = RSAUtils.sign(verifyData, privateKey);

        ApiResp apiResp = new ApiResp();
        apiResp.setOrderNo(sourceResp.getOrderNo());
        apiResp.setCode(sourceResp.getCode());
        apiResp.setData(encryptData);
        apiResp.setSign(sign);
        apiResp.setTimestamp(timestamp);
        apiResp.setFileBase64(sourceResp.getFileBase64());
        apiResp.setMessage(sourceResp.getMessage());
        return apiResp;
    }

    /**
     * 回调参数加密
     *
     * @param sysCode   系统标识
     * @param sourceReq 源参数
     * @return 密文返回
     */
    public CallbackReq encryptCallbackReq(String sysCode, String privateKey, String publicKey, CallbackSourceReq sourceReq) throws Exception {
        String encryptData = null;
        String sign = null;
        long timestamp = System.currentTimeMillis();
        if (sourceReq.getData() != null) {
            encryptData = RSAUtils.encryptByPublicKey(objectMapper.writeValueAsString(sourceReq), publicKey);
        }
        String verifyData = signParam(sysCode, sourceReq.getOrderNo(), timestamp, encryptData); // 签名的数据源
        sign = RSAUtils.sign(verifyData, privateKey);

        CallbackReq callbackReq = new CallbackReq();
        callbackReq.setOrderNo(sourceReq.getOrderNo());
        callbackReq.setCode(sourceReq.getCode());
        callbackReq.setData(encryptData);
        callbackReq.setSign(sign);
        callbackReq.setTimestamp(timestamp);
        callbackReq.setFileBase64(sourceReq.getFileBase64());
        callbackReq.setMessage(sourceReq.getMessage());
        return callbackReq;
    }

    private String signParam(String sysCode, String orderNo, long timestamp, String encryptData) {
        return sysCode + orderNo + timestamp + (encryptData == null ? "" : encryptData);
    }
}
