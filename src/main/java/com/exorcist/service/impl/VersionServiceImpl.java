package com.exorcist.service.impl;

import com.exorcist.mapper.VersionMapper;
import com.exorcist.pojo.Version;
import com.exorcist.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionServiceImpl implements VersionService
{

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Version selectAll() {
        return versionMapper.selectAll();
    }

}
