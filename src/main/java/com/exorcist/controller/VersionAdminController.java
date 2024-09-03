package com.exorcist.controller;

import com.exorcist.dto.version.VersionConfigDTO;
import com.exorcist.dto.version.VersionDTO;
import com.exorcist.exception.ResultInfo;
import com.exorcist.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@Tag(name="游戏版本控制模块")
@RestController
@RequestMapping("/api/versionAdmin")
public class VersionAdminController {

    @Autowired
    private VersionService versionService;

    @Operation(
            summary = "录入完整包",
            description = "将最新版本的完整包信息录入数据库。接口接收有关完整包的版本信息、下载地址及相关数据，并将这些信息存储到系统中以便于后续的查询和管理。"
    )
    @PostMapping("/addGameVersion")
    public ResultInfo addGameVersion(@RequestBody @Valid VersionDTO versionDTO) {
        return versionService.addGameVersion(versionDTO);
    }

    @Operation(
            summary = "录入游戏客户端",
            description = "将最新版本的游戏客户端信息录入数据库。接口接收客户端的版本信息、下载地址以及其他相关数据，并将这些信息存储到系统中以供后续查询和管理。"
    )
    @PostMapping("/addGameLauncher")
    public ResultInfo addGameLauncher(@RequestBody @Valid VersionDTO versionDTO){
        return versionService.addGameLauncher(versionDTO);
    }

    @Operation(
            summary = "录入游戏补丁",
            description = "将最新版本的游戏补丁信息录入数据库。接口接收有关补丁的版本信息、下载地址及相关数据，并将这些信息存储到系统中以便于后续的查询和管理。"
    )
    @PostMapping("/addGamePatch")
    public ResultInfo addGamePatch(@RequestBody @Valid VersionDTO versionDTO) {
        return versionService.addGamePatch(versionDTO);
    }

    @Operation(
            summary = "版本信息配置",
            description = "设置客户端、启动器的配置信息，用于前端确认最新版本。"
    )
    @PostMapping("/updateLatestLauncherConfig")
    public ResultInfo updateConfig(@RequestBody @Valid VersionConfigDTO versionConfigDTO) {
        return versionService.updateConfig(versionConfigDTO);
    }

    @Operation(
            summary = "补丁标记",
            description = "输入补丁号、标记补丁状态,版本号请同个API申请获取"
    )

    @PostMapping("/updatedPatch")
    public ResultInfo updatedPatch(@RequestBody @Valid VersionDTO versionDTO) {
        return versionService.updatedPatch(versionDTO);
    }

    @Operation(
            summary = "补丁删除",
            description = "从数据库中真正删除指定版本的信息。接口接收版本号并直接从数据库中删除对应记录。"
    )
    @PostMapping("/permanentDelete/{versionNumber}")
    public ResultInfo permanentDelete(@PathVariable String versionNumber) {
        return versionService.permanentPatchDelete(versionNumber);
    }

}
