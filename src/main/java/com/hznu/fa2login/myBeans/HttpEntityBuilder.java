package com.hznu.fa2login.myBeans;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

/**
 * 构造http传输类型
 *
 * @author wheelchen
 * @date 2018-05-17 20:40:13
 */
public interface HttpEntityBuilder {

    HttpEntity<MultiValueMap<String, Object>> build();


}
