package com.exorcist.controller;

import com.exorcist.exception.ResultInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exorcist.service.VersionService;

@Tag(name="游戏版本获取模块")
@RestController
@RequestMapping("/api/version")
public class VersionController {

    @Autowired
    private VersionService VersionService;

    @Operation(summary = "获取游戏客户端", description = "获取最新版本的游戏启动器客户端地址，用于下载游戏内容")
    @GetMapping("/getLatestLauncher")
    public ResultInfo getLatestLauncher() {
        return VersionService.getLatestLauncher();
    }

    @Operation(summary = "获取游戏完整包", description = "获取最新完整包压缩文件地址")
    @GetMapping("/getLatestGameZip")
    public ResultInfo getLatestGame() {
        return VersionService.getLatestGame();
    }

    @Operation(summary = "获取游戏补丁", description = "获取游戏补丁列表")
    @GetMapping("/getPatchList")
    public ResultInfo getPatchList() {
        return VersionService.getPatchList();
    }
}