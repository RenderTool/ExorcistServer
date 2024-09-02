package com.exorcist.pojo;

import lombok.Data;

@Data
public class VersionConfigPojo {
    private Long id;
    private String configKey;
    private String configValue;
    private String description;
    private String operator;
}
