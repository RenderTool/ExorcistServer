package com.exorcist.pojo;

import lombok.Data;

@Data
public class VersionPojo {
    private Long id;
    private String versionNumber;
    private String fileName;
    private String md5;
    private String md5list;
    private String downloadUrl;
    private Boolean status;
    private String operator;
}
