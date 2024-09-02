package com.exorcist.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//统一响应结果
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> ResultInfo<E> success(E data) {
        return new ResultInfo<>(0, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static ResultInfo success() {
        return new ResultInfo(0, "操作成功", null);
    }
    //快速返回操作成功结果(全参数版)
    public static ResultInfo success(int value, String Msg, List<String> collect) {
        return new ResultInfo(value, Msg, collect);
    }

    public static ResultInfo error(String message) {
        return new ResultInfo(1, message, null);
    }
    public static ResultInfo error(int value, String badRequestMsg) {
        return new ResultInfo(value, badRequestMsg, null);
    }
    public static ResultInfo error(int value, String badRequestMsg,Exception data) {
        return new ResultInfo(value, badRequestMsg, data);
    }


}