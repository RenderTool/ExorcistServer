package com.exorcist.service.impl;

import com.exorcist.dto.version.VersionConfigDTO;
import com.exorcist.dto.version.VersionDTO;
import com.exorcist.exception.ResultInfo;
import com.exorcist.mapper.VersionConfigMapper;
import com.exorcist.mapper.VersionMapper;
import com.exorcist.pojo.VersionConfigPojo;
import com.exorcist.pojo.VersionPojo;
import com.exorcist.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService
{

    @Autowired
    private VersionMapper versionMapper;

    @Autowired
    private VersionConfigMapper versionConfigMapper;

    public  VersionPojo versionPojoTransfer(VersionDTO versionDTO){
        VersionPojo versionPojo = new VersionPojo();
        versionPojo.setVersionNumber(versionDTO.getVersionNumber());
        versionPojo.setFileName(versionDTO.getFileName());
        versionPojo.setMd5(versionDTO.getMd5());
        versionPojo.setMd5List(versionDTO.getMd5list());
        versionPojo.setDownloadUrl(versionDTO.getDownloadUrl());
        versionPojo.setOperator("admin");
        //TODO 获取用户信息然后设置operator
        return versionPojo;
    }

    public  VersionConfigPojo versionConfigPojoTransfer(VersionConfigDTO versionConfigDTO){
        VersionConfigPojo versionConfigPojo = new VersionConfigPojo();
        versionConfigPojo.setConfigKey(versionConfigDTO.getConfigKey());
        versionConfigPojo.setConfigValue(versionConfigDTO.getConfigValue());
        versionConfigPojo.setDescription(versionConfigDTO.getDescription());
        versionConfigPojo.setOperator("admin");
        //TODO 获取用户信息然后设置operator
        return versionConfigPojo;
    }

    @Override
    public ResultInfo addGameVersion(VersionDTO versionDTO) {

        if(versionMapper.selectGameVersionByVersionNumber(versionDTO.getVersionNumber()) != null)
        {
            return ResultInfo.error("版本号已经存在");
        }
        versionMapper.addGameVersion(versionPojoTransfer(versionDTO));
        return ResultInfo.success();
    }

    @Override
    public ResultInfo addGameLauncher(VersionDTO versionDTO) {

        if(versionMapper.selectGameLauncherByVersionNumber(versionDTO.getVersionNumber())!= null)
        {
            return ResultInfo.error("重复的启动器版本");
        }
        versionMapper.addGameLauncher(versionPojoTransfer(versionDTO));
        return ResultInfo.success();
    }

    @Override
    public ResultInfo addGamePatch(VersionDTO versionDTO) {

        if(versionMapper.selectGamePatchByVersionNumber(versionDTO.getVersionNumber())!= null)
        {
            return ResultInfo.error("版本补丁已经存在");
        }
        versionMapper.addGamePatch(versionPojoTransfer(versionDTO));
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updatedPatch(VersionDTO versionDTO) {
        if(versionMapper.selectGamePatchByVersionNumber(versionDTO.getVersionNumber()) == null)
        {
            return ResultInfo.error("补丁不存在");
        }
        versionMapper.updatedPatch(versionPojoTransfer(versionDTO));
        return ResultInfo.success();
    }

    @Override
    public ResultInfo permanentPatchDelete(String versionNumber) {
        if(versionMapper.selectGamePatchByVersionNumber(versionNumber) == null)
        {
            return ResultInfo.error("补丁不存在");
        }
        versionMapper.permanentPatchDelete(versionNumber);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateConfig(VersionConfigDTO versionConfigDTO) {
        versionConfigMapper.updateConfig(versionConfigPojoTransfer(versionConfigDTO));
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getLatestLauncher() {
        return ResultInfo.success(versionConfigMapper.getLatestLauncher());
    }

    @Override
    public ResultInfo getLatestGame() {
        return ResultInfo.success(versionConfigMapper.getLatestGame());
    }

    @Override
    public ResultInfo getPatchList() {
        List<VersionPojo> versionPojoList = versionMapper.getPatchList();
        return ResultInfo.success(versionPojoList);
    }

}
