package com.exorcist.service;

import com.exorcist.dto.version.VersionConfigDTO;
import com.exorcist.dto.version.VersionDTO;
import com.exorcist.exception.ResultInfo;

public interface VersionService
{
    ResultInfo getPatchList();

    ResultInfo getLatestLauncher();

    ResultInfo getLatestGame();

    ResultInfo addGameVersion(VersionDTO versionDTO);

    ResultInfo addGameLauncher(VersionDTO versionDTO);

    ResultInfo addGamePatch(VersionDTO versionDTO);

    ResultInfo updatedPatch(VersionDTO versionDTO);

    ResultInfo permanentPatchDelete(String versionNumber);

    ResultInfo updateConfig(VersionConfigDTO versionConfigDTO);
}
