package com.example.junitstudy.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMRespDTO<T> {
    private Integer code; //1 == success, -1 == fail
    private String msg;
    private T body;

    @Builder
    public CMRespDTO(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
