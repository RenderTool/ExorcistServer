package com.exorcist.controller;

import com.exorcist.dto.version.VersionConfigDTO;
import com.exorcist.dto.version.VersionDTO;
import com.exorcist.exception.ResultInfo;
import com.exorcist.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
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
            summary = "录入完整包版本信息",
            description = "将最新版本的完整包信息录入数据库。接口接收有关完整包的版本信息、下载地址及相关数据，并将这些信息存储到系统中以便于后续的查询和管理。"
    )
    @PostMapping("/addGameVersion")
    public ResultInfo addGameVersion(@RequestBody @Valid VersionDTO versionDTO) {
        return versionService.addGameVersion(versionDTO);
    }

    @Operation(
            summary = "录入游戏客户端版本信息",
            description = "将最新版本的游戏客户端信息录入数据库。接口接收客户端的版本信息、下载地址以及其他相关数据，并将这些信息存储到系统中以供后续查询和管理。"
    )
    @PostMapping("/addGameLauncher")
    public ResultInfo addGameLauncher(@RequestBody @Valid VersionDTO versionDTO){
        return versionService.addGameLauncher(versionDTO);
    }

    @Operation(
            summary = "录入游戏补丁版本信息",
            description = "将最新版本的游戏补丁信息录入数据库。接口接收有关补丁的版本信息、下载地址及相关数据，并将这些信息存储到系统中以便于后续的查询和管理。"
    )
    @PostMapping("/addGamePatch")
    public ResultInfo addGamePatch(@RequestBody @Valid VersionDTO versionDTO) {
        return versionService.addGamePatch(versionDTO);
    }

    @Operation(
            summary = "配置游戏版本信息",
            description = "设置客户端、启动器的配置信息，用于前端确认最新版本。"
    )
    @PostMapping("/updateLatestLauncherConfig")
    public ResultInfo updateConfig(@RequestBody @Valid VersionConfigDTO versionConfigDTO) {
        return versionService.updateConfig(versionConfigDTO);
    }

    @Operation(
            summary = "标记启用补丁信息",
            description = "将指定版本的信息标记为删除状态。接口接收版本号并将对应记录的状态更新为已删除，以保留历史记录的完整性。"
    )
    @PostMapping("/markPatchAsDeleted/{versionNumber}")
    public ResultInfo markPatchAsDeprecated(@PathVariable String versionNumber) {
        return versionService.markPatchAsDeleted(versionNumber);
    }

    @Operation(
            summary = "删除补丁信息",
            description = "从数据库中真正删除指定版本的信息。接口接收版本号并直接从数据库中删除对应记录。"
    )
    @PostMapping("/permanentDelete/{versionNumber}")
    public ResultInfo permanentDelete(@PathVariable String versionNumber) {
        return versionService.permanentPatchDelete(versionNumber);
    }

}
