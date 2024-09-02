package com.exorcist.dto.version;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VersionDTO {

    @Schema(description = "版本号", example = "2024_08_15_05_25")
    @NotBlank(message = "版本号不能为空")
    private String versionNumber;

    @Schema(description = "文件名", example = "full_2024_08_15_05_25.zip")
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @Schema(description = "MD5校验码", example = "878998d87a74819c0519401a5f3fa1cf")
    @NotBlank(message = "Md5校验码不能为空")
    private String md5;

    @Schema(description = "Md5清单文件下载地址", example = "http://localhost/md5list.xml")
    private String md5list;

    @Schema(description = "下载地址", example = "http://localhost/full_2024_08_15_05_25.zip")
    private String downloadUrl;

}


