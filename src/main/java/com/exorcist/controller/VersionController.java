package com.exorcist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exorcist.mapper.VersionMapper;
import com.exorcist.pojo.Version;



@RestController
@RequestMapping("/api/version")
public class VersionController {

    @Autowired
    private VersionMapper versionMapper;

    @GetMapping("/getLatestVersion")
    public Version getAllVersionInfo() {
        return versionMapper.selectAll();
    }

}