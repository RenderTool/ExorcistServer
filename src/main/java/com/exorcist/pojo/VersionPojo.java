package com.exorcist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class VersionPojo {
    @JsonIgnore//JSON字符串过滤
    private Long id;
    private String versionNumber;
    private String fileName;
    private String md5;
    private String md5List;
    private String downloadUrl;
    private Boolean status;
    @JsonIgnore//JSON字符串过滤
    private String operator;
}
