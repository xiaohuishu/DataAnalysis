package com.march.graduation.service.auth;

import com.march.graduation.model.ListResult;
import com.march.graduation.model.auth.*;

import java.util.List;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public interface IAdminAuthService {

    /**
     * 查询管理平台用户
     *
     * @param queryParam
     * @return
     */
    ListResult<AdminUser> queryAdminUsers(QueryAdminUsersParam queryParam);

    /**
     * 根据名字全部匹配查询
     *
     * @param loginName
     * @return
     */
    AdminUser getUserByLoginName(String loginName);

    /**
     * 根据ID查询
     *
     * @param userId
     * @return
     */
    AdminUser getUserById(int userId);

    /**
     * 更新用户信息
     *
     * @param adminUser
     * @return
     */
    boolean updateUser(AdminUser adminUser);

    /**
     * 获取用户的所有角色
     *
     * @param userId
     * @return
     */
    List<AdminUserRole> getUserRoles(int userId);

    /**
     * 获取拥有URI权限的所有角色
     *
     * @param uri
     * @return
     */
    List<Integer> getRoleByUri(String uri);

    /**
     * 新增用户
     *
     * @param adminUser
     * @return
     */
    int addUser(AdminUser adminUser);

    /**
     * 查询管理平台角色
     *
     * @param queryParam
     * @return
     */
    List<AdminRole> queryAdminRoles(QueryAdminRolesParam queryParam);

    /**
     * 新增角色
     *
     * @param adminRole
     * @return
     */
    int addAdminRole(AdminRole adminRole);

    /**
     * 查询角色
     *
     * @param roleId
     * @return
     */
    AdminRole getAdminRoleByRoleId(int roleId);

    /**
     * 修改角色
     *
     * @param adminRole
     * @return
     */
    boolean updateRole(AdminRole adminRole);

    /**
     * 查询模块
     *
     * @param queryParam
     * @return
     */
    List<AdminModule> getModules(QueryAdminModuleParam queryParam);

    /**
     * 新增模块
     *
     * @param module
     * @return
     */
    int addModule(AdminModule module);

    /**
     * 修改模块
     *
     * @param module
     * @return
     */
    boolean updateModule(AdminModule module);

    /**
     * 查询模块的所有资源
     *
     * @param moduleId
     * @return
     */
    List<AdminResource> getResources(int moduleId);

    /**
     * 新增模块的资源
     *
     * @param resource
     * @return
     */
    int addAdminResource(AdminResource resource);

    /**
     * 根据资源ID查询
     *
     * @param id
     * @return
     */
    AdminResource getResourceById(int id);

    /**
     * 修改资源
     *
     * @param resource
     * @return
     */
    boolean updateResource(AdminResource resource);

    /**
     * 获取角色有权限的资源
     *
     * @param roleId
     * @return
     */
    List<AdminResource> getRoleResources(int roleId);

    /**
     * 所有有效的资源
     *
     * @return
     */
    List<AdminResource> getAllValidResources();

    /**
     * 删除角色资源
     *
     * @param roleId
     * @param resourceIds
     */
    void deleteRoleResource(int roleId, List<Integer> resourceIds);

    /**
     * 添加角色资源
     *
     * @param roleId
     * @param resourceIds
     */
    void addRoleResource(int roleId, List<Integer> resourceIds);

    /**
     * 删除用户角色
     *
     * @param userId
     * @param roleIds
     */
    void deleteUserRole(int userId, List<Integer> roleIds);

    /**
     * 新建用户角色
     *
     * @param userId
     * @param roleIds
     */
    void addUserRole(int userId, List<Integer> roleIds);

    /**
     * 获取用户角色的模块
     *
     * @param loginName
     * @return
     */
    List<AdminModule> getUserRoleModule(String loginName);
}
