package com.github.rxyor.carp.search.infrastructure.repository.user.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.github.rxyor.carp.search.SpringWithJUnit5IT;
import com.github.rxyor.carp.search.infrastructure.repository.user.dataobj.EsPermissionDO;
import com.github.rxyor.carp.search.infrastructure.repository.user.dataobj.EsRoleDO;
import com.github.rxyor.carp.search.infrastructure.repository.user.dataobj.EsUserDO;
import org.junit.jupiter.api.Test;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-15 13:04:27 v1.0
 */
class EsUserDAOTest extends SpringWithJUnit5IT {

    @Resource
    EsUserDAO esUserDAO;

    @Test
    void save() {
        EsUserDO esUserDO = new EsUserDO();
        esUserDO.setId(System.currentTimeMillis());
        esUserDO.setUsername("LY" + esUserDO.getId() % 1000);
        esUserDO.setPassword("123456");
        esUserDO.setPhone("15988886666");
        esUserDO.setEmail("ly@outlook.com");
        esUserDO.setNickname("阳如花");
        esUserDO.setAvatar("http://www.baidu.com/avatar.svg");
        esUserDO.setDisable(0);
        esUserDO.setLocked(0);
        esUserDO.setRemark("无");
        esUserDO.setCreateTime(new Date());
        esUserDO.setUpdateTime(new Date());
        List<EsRoleDO> roleDOList = new ArrayList<>();
        esUserDO.setRoleList(roleDOList);

        EsRoleDO esRoleDO = new EsRoleDO();
        esRoleDO.setId(System.currentTimeMillis());
        esRoleDO.setRoleCode("R" + esRoleDO.getId());
        esRoleDO.setRoleName("ADMIN_" + esRoleDO.getId() % 100000);
        esRoleDO.setRemark("无");
        esRoleDO.setDisable(0);
        esRoleDO.setCreateTime(new Date());
        esRoleDO.setUpdateTime(new Date());
        List<EsPermissionDO> permissionDOList = new ArrayList<>();
        esRoleDO.setPermissionList(permissionDOList);
        roleDOList.add(esRoleDO);

        EsPermissionDO esPermissionDO = new EsPermissionDO();
        esPermissionDO.setId(System.currentTimeMillis());
        esPermissionDO.setPermissionCode("P" + esPermissionDO.getId());
        esPermissionDO.setPermissionName("RESOURCE_" + esPermissionDO.getId() % 100000);
        esPermissionDO.setRemark("无");
        esPermissionDO.setDisable(0);
        esPermissionDO.setCreateTime(new Date());
        esPermissionDO.setUpdateTime(new Date());
        permissionDOList.add(esPermissionDO);

        esUserDAO.save(esUserDO);
    }
}