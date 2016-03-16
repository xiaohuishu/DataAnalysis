package com.march.graduation.service.auth.impl;

import com.march.graduation.dao.auth.AdminAuthDao;
import com.march.graduation.model.ListResult;
import com.march.graduation.model.auth.*;
import com.march.graduation.service.auth.IAdminAuthService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class AdminAuthServiceImpl implements IAdminAuthService, InitializingBean {

    private AdminAuthDao adminAuthDao;

    @Override
    public ListResult<AdminUser> queryAdminUsers(QueryAdminUsersParam queryParam) {
        ListResult<AdminUser> result = new ListResult<AdminUser>();

        int total = this.adminAuthDao.queryUsersCount(queryParam);
        result.setTotal(total);

        if (total == 0 || total < queryParam.getPagingOffset()) {
            return result;
        }

        List<AdminUser> userList = this.adminAuthDao.queryUsers(queryParam);
        result.setResultList(userList);

        return result;
    }

    @Override
    public AdminUser getUserByLoginName(String loginName) {
        return this.adminAuthDao.queryUserByLoginName(loginName);
    }

    @Override
    public AdminUser getUserById(int userId) {
        return this.adminAuthDao.queryUserById(userId);
    }

    @Override
    public boolean updateUser(AdminUser adminUser) {
        try {
            return this.adminAuthDao.updateUser(adminUser);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<AdminUserRole> getUserRoles(int userId) {
        return this.adminAuthDao.queryUserRoles(userId);
    }

    @Override
    public List<Integer> getRoleByUri(String uri) {
        return this.adminAuthDao.queryRoleByUri(uri);
    }

    @Override
    public int addUser(AdminUser adminUser) {
        try {
            return this.adminAuthDao.insertUser(adminUser);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<AdminRole> queryAdminRoles(QueryAdminRolesParam queryParam) {

        List<AdminRole> roleList = this.adminAuthDao.queryRoles(queryParam);

        return roleList;
    }

    @Override
    public int addAdminRole(AdminRole adminRole) {
        return this.adminAuthDao.insertAdminRole(adminRole);
    }

    @Override
    public AdminRole getAdminRoleByRoleId(int roleId) {
        return this.adminAuthDao.queryAdminRoleByRoleId(roleId);
    }

    @Override
    public boolean updateRole(AdminRole adminRole) {
        return this.adminAuthDao.updateRole(adminRole);
    }

    @Override
    public List<AdminModule> getModules(QueryAdminModuleParam queryParam) {
        List<AdminModule> moduleList = this.adminAuthDao.queryModules(queryParam);

        return moduleList;
    }

    @Override
    public int addModule(AdminModule module) {
        return this.adminAuthDao.insertModule(module);
    }

    @Override
    public boolean updateModule(AdminModule module) {
        return this.adminAuthDao.updateModule(module);
    }

    @Override
    public List<AdminResource> getResources(int moduleId) {
        return this.adminAuthDao.queryResourceByModule(moduleId);
    }

    @Override
    public int addAdminResource(AdminResource resource) {
        return this.adminAuthDao.insertAdminResource(resource);
    }

    @Override
    public AdminResource getResourceById(int id) {
        return this.adminAuthDao.queryResourceById(id);
    }

    @Override
    public boolean updateResource(AdminResource resource) {
        return this.adminAuthDao.updateResource(resource);
    }

    @Override
    public List<AdminResource> getRoleResources(int roleId) {
        return this.adminAuthDao.queryRoleResources(roleId);
    }

    @Override
    public List<AdminResource> getAllValidResources() {
        return this.adminAuthDao.queryAllValidResource();
    }

    @Override
    public void deleteRoleResource(int roleId, List<Integer> resourceIds) {
        this.adminAuthDao.deleteRoleResource(roleId, resourceIds);
    }

    @Override
    public void addRoleResource(int roleId, List<Integer> resourceIds) {
        this.adminAuthDao.insertRoleResource(roleId, resourceIds);
    }

    @Override
    public void deleteUserRole(int userId, List<Integer> roleIds) {
        this.adminAuthDao.deleteUserRole(userId, roleIds);
    }

    @Override
    public void addUserRole(int userId, List<Integer> roleIds) {
        this.adminAuthDao.insertUserRole(userId, roleIds);
    }

    @Override
    public List<AdminModule> getUserRoleModule(String loginName) {
        return this.adminAuthDao.queryUserRoleModule(loginName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(adminAuthDao, "AdminUserServiceImpl.adminAuthDao must not be null!");
    }

    public void setAdminAuthDao(AdminAuthDao adminAuthDao) {
        this.adminAuthDao = adminAuthDao;
    }
}
