package com.exorcist.mapper;


import com.exorcist.dto.version.VersionConfigDTO;
import com.exorcist.pojo.VersionConfigPojo;

public interface VersionConfigMapper {

    VersionConfigPojo getLatestLauncher();

    VersionConfigPojo getLatestGame();

    void updateConfig(VersionConfigPojo versionConfigPojo);

}
