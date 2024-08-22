package com.exorcist.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigInteger;

@Data
@Schema(description = "用户实体类")
public class User {

    @Schema(description = "用户ID", example = "1")
    private BigInteger id;

    @Schema(description = "用户名称", example = "张三")
    private String name;

    @Schema(description = "用户密码", example = "123456")
    private String password;

    @Schema(description = "密码加密的盐值", example = "randomSalt")
    private String salt;

    @Schema(description = "用户邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "用户手机号码", example = "13800138000")
    private String mobile;

    @Schema(description = "用户状态（true为激活，false为禁用）", example = "true")
    private Boolean status;

    @Schema(description = "部门ID", example = "100")
    private BigInteger dept_id;

    @Schema(description = "创建人", example = "admin")
    private String create_by;

    @Schema(description = "创建时间", example = "2023-08-21 10:00:00")
    private String create_time;
}
