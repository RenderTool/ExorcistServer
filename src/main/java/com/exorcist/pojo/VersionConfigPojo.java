package com.exorcist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class VersionConfigPojo {
    @JsonIgnore//JSON字符串过滤
    private Long id;
    private String configKey;
    private String configValue;
    private String description;
    @JsonIgnore//JSON字符串过滤
    private String operator;
}
