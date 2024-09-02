package com.exorcist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserPojo {
    private Long id;
    private String name;
    @JsonIgnore//JSON字符串过滤
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Boolean status;
    private String create_time;
}
