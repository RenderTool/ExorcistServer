package com.exorcist.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @Schema(description = "用户邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "用户手机号码", example = "13800138000")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "用户密码", example = "123456")
    private String password;
}
