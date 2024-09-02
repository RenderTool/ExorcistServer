package com.exorcist.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordDTO {

    @Schema(description = "用户手机号码", example = "13800138000")
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^\\d{10,15}$", message = "手机号格式不正确")
    private String mobile;

    @Schema(description = "旧密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20个字符之间")
    private String oldPassword;

    @Schema(description = "新密码", example = "234567")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20个字符之间")
    private String newPassword;

}
