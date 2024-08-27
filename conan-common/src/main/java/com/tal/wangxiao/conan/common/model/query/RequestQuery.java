package com.tal.wangxiao.conan.common.model.query;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * 流量查询类，用于从ES中获取请求流量
 * @author mtx
 */
@Data
@Builder
public class RequestQuery {
    String domain;

    String api;

    Integer method;

    Integer count;

    LocalDate dateFrom;

    LocalDate dateTo;

     String index;


}
