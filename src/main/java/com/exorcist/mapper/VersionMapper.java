package com.exorcist.mapper;

import com.exorcist.pojo.VersionPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface VersionMapper {

    VersionPojo selectGameVersionByVersionNumber(String versionNumber);

    VersionPojo selectGameLauncherByVersionNumber(String versionNumber);

    VersionPojo selectGamePatchByVersionNumber(String versionNumber);

    void addGameVersion(VersionPojo versionPojo);

    void addGameLauncher(VersionPojo versionPojo);

    void addGamePatch(VersionPojo versionPojo);

    void updatedPatch(VersionPojo versionPojo);

    void permanentPatchDelete(@Param("versionNumber") String versionNumber);

    List<VersionPojo> getPatchList();

}
