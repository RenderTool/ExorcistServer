package com.exorcist.pojo;

import lombok.Data;

@Data
public class Version {
    private Long id;
    private String versionNumber;
    private String fileName;
    private String md5;
    private String downloadUrl;
    private String createdDate;
}
