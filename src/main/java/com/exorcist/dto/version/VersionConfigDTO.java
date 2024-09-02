package com.exorcist.dto.version;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VersionConfigDTO {

    @Schema(description = "配置项名称", example = "sys_version_launcher")
    @NotBlank(message = "配置项名称不能为空")
    private String configKey;

    @Schema(description = "版本ID", example = "launcher-v0.0.1")
    @NotBlank(message = "版本ID不能为空")
    private String configValue;

    @Schema(description = "描述", example = "最新客户端启动器安装包版本号versionNumber")
    private String description;

}
