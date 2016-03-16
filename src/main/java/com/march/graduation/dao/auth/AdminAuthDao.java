
package com.march.graduation.dao.auth;

import com.google.common.base.Preconditions;
import com.march.graduation.base.dao.BaseDao;
import com.march.graduation.model.SqlParamBuilder;
import com.march.graduation.model.auth.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class AdminAuthDao extends BaseDao {

    private static final String [] PRIMARY_KEY = new String[] { "id" };

    // TABLE ADMIN_USER
    //private static final String TABLE_USER = " admin_user ";
    private static final String TABLE_USER = " manager_user ";

    private static final String TABLE_USER_COLUMNS = " id, login_name, user_name, c_time ";

    private static final String SQL_QUERY_USER_BY_LOGIN = "SELECT " + TABLE_USER_COLUMNS + " FROM " + TABLE_USER + " WHERE login_name = :loginName";

    private static final String SQL_QUERY_USER_BY_ID = "SELECT " + TABLE_USER_COLUMNS + " FROM " + TABLE_USER + " WHERE id = :id";

    private static final String SQL_QUERY_USERS_COUNT = "SELECT COUNT(1) FROM " + TABLE_USER + " WHERE 1 = 1 ";

    private static final String SQL_QUERY_USERS = "SELECT " + TABLE_USER_COLUMNS + " FROM " + TABLE_USER + " WHERE 1 = 1 ";

    private static final String SQL_INSERT_USER = "INSERT INTO " + TABLE_USER + " (user_name, login_name, login_password) VALUES (:userName, :loginName, :password)";

    private static final String SQL_UPDATE_USER = "UPDATE " + TABLE_USER + " SET user_name = :userName, login_name = :loginName WHERE id = :id";

    private static final BeanPropertyRowMapper<AdminUser> ROW_MAPPER_USER = new BeanPropertyRowMapper<AdminUser>(AdminUser.class);

    // TABLE ADMIN_ROLE
    private static final String TABLE_ROLE = " admin_role ";

    private static final String TABLE_ROLE_COLUMNS = " id, role_name, remarks, valid, u_time ";

    private static final String SQL_QUERY_ROLES =  "SELECT " + TABLE_ROLE_COLUMNS + " FROM " + TABLE_ROLE + " WHERE 1 = 1 ";

    private static final String SQL_INSERT_ROLE = "INSERT INTO " + TABLE_ROLE + " (role_name, remarks, valid) VALUES (:roleName, :remarks, :valid)";

    private static final String SQL_QUERY_ROLE_BY_ROLE_ID =  "SELECT " + TABLE_ROLE_COLUMNS + " FROM " + TABLE_ROLE + " WHERE id = :id ";

    private static final String SQL_UPDATE_ROLE = "UPDATE " + TABLE_ROLE + " SET role_name = :roleName, remarks = :remarks, valid = :valid, u_time = NOW() WHERE id = :id";

    private static final BeanPropertyRowMapper<AdminRole> ROW_MAPPER_ROLE = new BeanPropertyRowMapper<AdminRole>(AdminRole.class);

    // TABLE ADMIN_MODULE
    private static final String TABLE_MODULE = " admin_module ";

    private static final String TABLE_MODULE_COLUMNS = " id, module_name, remarks, parent_id, ordered, link, valid, c_time, u_time ";

    private static final String SQL_QUERY_MODULES = "SELECT " + TABLE_MODULE_COLUMNS + " FROM " + TABLE_MODULE + " WHERE 1 = 1 ";

    private static final String SQL_INSERT_MODULE = "INSERT INTO " + TABLE_MODULE + " (module_name, remarks, parent_id, ordered, link, valid) VALUES (:moduleName, :remarks, :parentId, :ordered, :link, :valid)";

    private static final String SQL_UPDATE_MODULE = "UPDATE " + TABLE_MODULE + " SET module_name = :moduleName, remarks = :remarks, parent_id = :parentId, ordered = :ordered, link = :link, valid = :valid, u_time = NOW() WHERE id = :id";

    private static final BeanPropertyRowMapper<AdminModule> ROW_MAPPER_MODULE = new BeanPropertyRowMapper<AdminModule>(AdminModule.class);

    // TABLE ADMIN_RESOURCE
    private static final String TABLE_RESOURCE = " admin_resource ";

    private static final String TABLE_RESOURCE_COLUMNS = " id, module_id, name, remarks, valid, link, c_time, u_time ";

    private static final String SQL_QUERY_RESOURCE_BY_MODULE = "SELECT " + TABLE_RESOURCE_COLUMNS + " FROM " + TABLE_RESOURCE + " WHERE module_id = :moduleId";

    private static final String SQL_INSERT_RESOURCE = "INSERT INTO " + TABLE_RESOURCE + " (module_id, name, remarks, valid, link) VALUES (:moduleId, :name, :remarks, :valid, :link)";

    private static final String SQL_QUERY_RESOURCE_BY_ID = "SELECT " + TABLE_RESOURCE_COLUMNS + " FROM " + TABLE_RESOURCE + " WHERE id = :id";

    private static final String SQL_UPDATE_RESOURCE = "UPDATE " + TABLE_RESOURCE + " SET module_id = :moduleId, name = :name, remarks = :remarks, valid = :valid, link = :link, u_time = NOW() WHERE id = :id";

    private static final String SQL_QUERY_ALL_VALID_RESOURCE = "SELECT " + TABLE_RESOURCE_COLUMNS + " FROM " + TABLE_RESOURCE + " WHERE valid ";

    private static final BeanPropertyRowMapper<AdminResource> ROW_MAPPER_RESOURCE = new BeanPropertyRowMapper<AdminResource>(AdminResource.class);

    // TABLE ADMIN_USER_ROLE
    private static final String TABLE_USER_ROLE = " admin_user_role ";

    private static final String TABLE_USER_ROLE_COLUMNS = " id, user_id, role_id ";

    private static final String SQL_QUERY_USER_ROLES = "SELECT " + TABLE_USER_ROLE_COLUMNS + " FROM " + TABLE_USER_ROLE + " WHERE user_id = :userId";

    private static final String SQL_DELETE_USER_ROLE = "DELETE FROM " + TABLE_USER_ROLE + " WHERE user_id = :userId AND role_id IN (:roleIds)";

    private static final String SQL_INSERT_USER_ROLE = "INSERT INTO " + TABLE_USER_ROLE + " (user_id, role_id) VALUES (:userId, :roleId)";

    private static final BeanPropertyRowMapper<AdminUserRole> ROW_MAPPER_USER_ROLE = new BeanPropertyRowMapper<AdminUserRole>(AdminUserRole.class);

    // TABLE ADMIN_ROLE_AUTH
    private static final String TABLE_ROLE_AUTH = " admin_role_auth ";

    private static final String SQL_DELETE_ROLE_RESOURCE = "DELETE FROM " + TABLE_ROLE_AUTH + " WHERE role_id = :roleId AND resource_id IN (:resourceIds)";

    private static final String SQL_INSERT_ROLE_RESOURCE = "INSERT INTO " + TABLE_ROLE_AUTH + " (role_id, resource_id) VALUES (:roleId, :resourceId)";

    // OTHER
    private static final String SQL_QUERY_USER_ROLE_MODULE = "SELECT DISTINCT m.id, m.module_name, m.parent_id, m.ordered, m.link FROM " + TABLE_USER + " u, "
            + TABLE_USER_ROLE + " ur, " + TABLE_ROLE_AUTH + "ra, " + TABLE_RESOURCE + " r, " + TABLE_MODULE
            + " m WHERE ur.role_id = ra.role_id and ur.user_id = u.id and ra.resource_id = r.id and r.module_id = m.id and m.valid and u.login_name = :loginName order by m.ordered";

    static {
        ROW_MAPPER_USER.setPrimitivesDefaultedForNullValue(true);
        ROW_MAPPER_ROLE.setPrimitivesDefaultedForNullValue(true);
        ROW_MAPPER_MODULE.setPrimitivesDefaultedForNullValue(true);
    }

    /**
     * 根据用户名(登录名,qtalk名称) 查询
     *
     * @param loginName
     * @return
     */
    public AdminUser queryUserByLoginName(String loginName) {
        List<AdminUser> userList = this.getNamedParameterJdbcTemplate().query(SQL_QUERY_USER_BY_LOGIN,
                Collections.singletonMap("loginName", loginName), ROW_MAPPER_USER);

        if (CollectionUtils.isNotEmpty(userList)) {
            return userList.get(0);
        }

        return null;
    }

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    public AdminUser queryUserById(int id) {
        List<AdminUser> userList = this.getNamedParameterJdbcTemplate().query(SQL_QUERY_USER_BY_ID,
                Collections.singletonMap("id", id), ROW_MAPPER_USER);

        if (CollectionUtils.isNotEmpty(userList)) {
            return userList.get(0);
        }

        return null;
    }

    public boolean updateUser(AdminUser adminUser) {
        Preconditions.checkNotNull(adminUser, "updateUser parameter adminUser must be not null");
        if (adminUser.getId() <= 0) {
            return false;
        }
        return getNamedParameterJdbcTemplate().update(SQL_UPDATE_USER, new BeanPropertySqlParameterSource(adminUser)) == 1;
    }

    /**
     * 查询用户
     *
     * @param queryAdminUsersParam
     * @return
     */
    public int queryUsersCount(QueryAdminUsersParam queryAdminUsersParam) {
        Preconditions.checkNotNull(queryAdminUsersParam, "queryAdminUsersParam must be not null");

        SqlParamBuilder sqlParamBuilder = SqlParamBuilder.newBuilder();
        sqlParamBuilder.addSqlPrefix(SQL_QUERY_USERS_COUNT);

        buildQueryUserCondition(queryAdminUsersParam, sqlParamBuilder);

        return this.getNamedParameterJdbcTemplate().queryForInt(sqlParamBuilder.getSql(), sqlParamBuilder.getParamMap());
    }

    /**
     * 查询用户
     *
     * @param queryAdminUsersParam
     * @return
     */
    public List<AdminUser> queryUsers(QueryAdminUsersParam queryAdminUsersParam) {
        Preconditions.checkNotNull(queryAdminUsersParam, "queryAdminUsersParam must be not null");

        SqlParamBuilder sqlParamBuilder = SqlParamBuilder.newBuilder();
        sqlParamBuilder.addSqlPrefix(SQL_QUERY_USERS);

        buildQueryUserCondition(queryAdminUsersParam, sqlParamBuilder);

        sqlParamBuilder.addSqlConditon(" ORDER BY id ASC ");
        sqlParamBuilder.addSqlConditon(" OFFSET :offset", "offset", queryAdminUsersParam.getPagingOffset());
        sqlParamBuilder.addSqlConditon(" LIMIT :limit", "limit", queryAdminUsersParam.getPageSize());

        return getNamedParameterJdbcTemplate().query(sqlParamBuilder.getSql(), sqlParamBuilder.getParamMap(), ROW_MAPPER_USER);
    }

    /**
     * 查询用户的构造条件语句
     *
     * @param queryAdminUsersParam
     * @param sqlParamBuilder
     * @return
     */
    private SqlParamBuilder buildQueryUserCondition(QueryAdminUsersParam queryAdminUsersParam, SqlParamBuilder sqlParamBuilder) {
        if (StringUtils.isNotBlank(queryAdminUsersParam.getUserName())) {
            sqlParamBuilder.addSqlConditon(" AND user_name LIKE :username", "username", "%" + queryAdminUsersParam.getUserName() + "%");
        }

        if (StringUtils.isNotBlank(queryAdminUsersParam.getLoginName())) {
            sqlParamBuilder.addSqlConditon(" AND login_name LIKE :loginName", "loginName",  "%" + queryAdminUsersParam.getLoginName() + "%");
        }

        return sqlParamBuilder;
    }

    /**
     * 查询用户的所有角色
     *
     * @param userId
     * @return
     */
    public List<AdminUserRole> queryUserRoles(int userId) {
        return this.getNamedParameterJdbcTemplate().query(SQL_QUERY_USER_ROLES, Collections.singletonMap("userId", userId), ROW_MAPPER_USER_ROLE);
    }

    /**
     * 新增用户
     *
     * @param adminUser
     * @return
     */
    public int insertUser(AdminUser adminUser) {
        Preconditions.checkNotNull(adminUser, "insertUser parameter adminUser must be not null");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getNamedParameterJdbcTemplate().update(SQL_INSERT_USER, new BeanPropertySqlParameterSource(adminUser), keyHolder, PRIMARY_KEY);
        return keyHolder.getKey().intValue();
    }

    /**
     * 查询角色
     * @param queryAdminRolesParam
     * @return
     */
    public List<AdminRole> queryRoles(QueryAdminRolesParam queryAdminRolesParam) {
        Preconditions.checkNotNull(queryAdminRolesParam, "queryAdminRolesParam must be not null");

        SqlParamBuilder sqlParamBuilder = SqlParamBuilder.newBuilder();
        sqlParamBuilder.addSqlPrefix(SQL_QUERY_ROLES);

        if (StringUtils.isNotBlank(queryAdminRolesParam.getRoleName())) {
            sqlParamBuilder.addSqlConditon(" AND role_name LIKE :rolename", "rolename", "%" + queryAdminRolesParam.getRoleName() + "%");
        }

        if (queryAdminRolesParam.getValid() == QueryAdminRolesParam.VALID_TRUE) {
            sqlParamBuilder.addSqlConditon(" AND valid");
        } else if (queryAdminRolesParam.getValid() == QueryAdminRolesParam.VALID_FALSE) {
            sqlParamBuilder.addSqlConditon(" AND valid = false");
        }

        sqlParamBuilder.addSqlConditon(" ORDER BY id ASC ");

        return this.getNamedParameterJdbcTemplate().query(sqlParamBuilder.getSql(), sqlParamBuilder.getParamMap(), ROW_MAPPER_ROLE);
    }

    /**
     * 新增角色
     *
     * @param adminRole
     * @return
     */
    public int insertAdminRole(AdminRole adminRole) {
        Preconditions.checkNotNull(adminRole, "insertAdminRole parameter adminRole must be not null");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getNamedParameterJdbcTemplate().update(SQL_INSERT_ROLE, new BeanPropertySqlParameterSource(adminRole), keyHolder, PRIMARY_KEY);
        return keyHolder.getKey().intValue();
    }

    /**
     * 查询角色
     *
     * @param roleId
     * @return
     */
    public AdminRole queryAdminRoleByRoleId(int roleId) {
        List<AdminRole> roleList = this.getNamedParameterJdbcTemplate().query(SQL_QUERY_ROLE_BY_ROLE_ID, Collections.singletonMap("id", roleId), ROW_MAPPER_ROLE);
        if (CollectionUtils.isNotEmpty(roleList)) {
            return roleList.get(0);
        }

        return null;
    }

    /**
     * 更新角色
     *
     * @param adminRole
     * @return
     */
    public boolean updateRole(AdminRole adminRole) {
        return getNamedParameterJdbcTemplate().update(SQL_UPDATE_ROLE, new BeanPropertySqlParameterSource(adminRole)) == 1;
    }

    /**
     * 查询模块
     *
     * @param queryAdminModuleParam
     * @return
     */
    public List<AdminModule> queryModules(QueryAdminModuleParam queryAdminModuleParam) {
        Preconditions.checkNotNull(queryAdminModuleParam, "queryAdminModuleParam must be not null");

        SqlParamBuilder sqlParamBuilder = SqlParamBuilder.newBuilder();
        sqlParamBuilder.addSqlPrefix(SQL_QUERY_MODULES);

        if (StringUtils.isNotBlank(queryAdminModuleParam.getModuleName())) {
            sqlParamBuilder.addSqlConditon(" AND module_name LIKE :moduleName", "moduleName", "%" + queryAdminModuleParam.getModuleName() + "%");
        }

        if (CollectionUtils.isNotEmpty(queryAdminModuleParam.getModelIdList())) {
            sqlParamBuilder.addSqlConditon(" AND id IN (:moduleIdList)", "moduleIdList", queryAdminModuleParam.getModelIdList());
        }

        if (queryAdminModuleParam.getValid() == QueryAdminModuleParam.VALID_TRUE) {
            sqlParamBuilder.addSqlConditon(" AND valid");
        } else if (queryAdminModuleParam.getValid() == QueryAdminModuleParam.VALID_FALSE) {
            sqlParamBuilder.addSqlConditon(" AND valid = false");
        }

        if (queryAdminModuleParam.getParentId() >= 0) {
            sqlParamBuilder.addSqlConditon(" AND parent_id = :parentId", "parentId", queryAdminModuleParam.getParentId());
        }

        sqlParamBuilder.addSqlConditon(" ORDER BY parent_id, ordered");
        return this.getNamedParameterJdbcTemplate().query(sqlParamBuilder.getSql(), sqlParamBuilder.getParamMap(), ROW_MAPPER_MODULE);
    }

    /**
     * 新增模块
     *
     * @param module
     * @return
     */
    public int insertModule(AdminModule module) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getNamedParameterJdbcTemplate().update(SQL_INSERT_MODULE, new BeanPropertySqlParameterSource(module), keyHolder, PRIMARY_KEY);
        return keyHolder.getKey().intValue();
    }

    /**
     * 修改模块
     *
     * @param module
     * @return
     */
    public boolean updateModule(AdminModule module) {
        return getNamedParameterJdbcTemplate().update(SQL_UPDATE_MODULE, new BeanPropertySqlParameterSource(module)) == 1;
    }

    /**
     * 根据模块查询其所有的资源
     *
     * @param moduleId
     * @return
     */
    public List<AdminResource> queryResourceByModule(int moduleId) {
        return getNamedParameterJdbcTemplate().query(SQL_QUERY_RESOURCE_BY_MODULE, Collections.singletonMap("moduleId", moduleId), ROW_MAPPER_RESOURCE);
    }

    /**
     * 新增资源
     *
     * @param adminResource
     * @return
     */
    public int insertAdminResource(AdminResource adminResource) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getNamedParameterJdbcTemplate().update(SQL_INSERT_RESOURCE, new BeanPropertySqlParameterSource(adminResource), keyHolder, PRIMARY_KEY);
        return keyHolder.getKey().intValue();
    }

    /**
     *
     * 根据资源ID查询模块的资源
     *
     * @param id
     * @return
     */
    public AdminResource queryResourceById(int id) {
        List<AdminResource> resourceList = getNamedParameterJdbcTemplate().query(SQL_QUERY_RESOURCE_BY_ID, Collections.singletonMap("id", id), ROW_MAPPER_RESOURCE);
        if (CollectionUtils.isNotEmpty(resourceList)) {
            return resourceList.get(0);
        }

        return null;
    }

    /**
     * 修改资源
     *
     * @param resource
     * @return
     */
    public boolean updateResource(AdminResource resource) {
        int result = getNamedParameterJdbcTemplate().update(SQL_UPDATE_RESOURCE, new BeanPropertySqlParameterSource(resource));
        return result == 1;
    }

    /**
     * 查询所有拥有URI权限的角色
     *
     * @param uri
     * @return
     */
    public List<Integer> queryRoleByUri(String uri) {
        String sql = "SELECT DISTINCT ra.role_id FROM admin_role_auth ra, admin_resource r WHERE r.valid AND ra.resource_id = r.id AND r.link = :uri";
        return getNamedParameterJdbcTemplate().queryForList(sql, Collections.singletonMap("uri", uri), Integer.class);
    }

    /**
     * 查询角色的资源
     *
     * @param roleId
     * @return
     */
    public List<AdminResource> queryRoleResources(int roleId) {
        String sql = "SELECT r.id, r.module_id, r.name, r.valid, r.link FROM admin_role_auth ra, admin_resource r WHERE ra.resource_id = r.id AND ra.role_id = :roleId";
        return getNamedParameterJdbcTemplate().query(sql, Collections.singletonMap("roleId", roleId), ROW_MAPPER_RESOURCE);
    }

    /**
     * 查询所有有效的资源
     *
     * @return
     */
    public List<AdminResource> queryAllValidResource() {
        return getNamedParameterJdbcTemplate().query(SQL_QUERY_ALL_VALID_RESOURCE, Collections.EMPTY_MAP, ROW_MAPPER_RESOURCE);
    }

    /**
     * 删除角色的资源
     *
     * @param roleId
     * @param resourceIds
     */
    public void deleteRoleResource(int roleId, List<Integer> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return ;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roleId", roleId);
        paramMap.put("resourceIds", resourceIds);
        getNamedParameterJdbcTemplate().update(SQL_DELETE_ROLE_RESOURCE, paramMap);
    }

    /**
     * 新增角色资源
     *
     * @param roleId
     * @param resourceIds
     */
    public void insertRoleResource(int roleId, List<Integer> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return ;
        }

        Map [] batchValues = new Map[resourceIds.size()];
        for (int i = 0; i < resourceIds.size(); i++) {
            batchValues[i] = new HashMap<String, Object>();
            batchValues[i].put("roleId", roleId);
            batchValues[i].put("resourceId", resourceIds.get(i));

        }

        getNamedParameterJdbcTemplate().batchUpdate(SQL_INSERT_ROLE_RESOURCE, batchValues);
    }

    /**
     * 删除用户角色
     *
     * @param userId
     * @param roleIds
     */
    public void deleteUserRole(int userId, List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return ;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("roleIds", roleIds);
        getNamedParameterJdbcTemplate().update(SQL_DELETE_USER_ROLE, paramMap);
    }

    /**
     * 新建用户角色
     *
     * @param userId
     * @param roleIds
     */
    public void insertUserRole(int userId, List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return ;
        }

        Map [] batchValues = new Map[roleIds.size()];
        for (int i = 0; i < roleIds.size(); i++) {
            batchValues[i] = new HashMap<String, Object>();
            batchValues[i].put("userId", userId);
            batchValues[i].put("roleId", roleIds.get(i));

        }

        getNamedParameterJdbcTemplate().batchUpdate(SQL_INSERT_USER_ROLE, batchValues);
    }

    /**
     * 查询用户拥有权限的模块
     *
     * @param loginName
     * @return
     */
    public List<AdminModule> queryUserRoleModule(String loginName) {
        return getNamedParameterJdbcTemplate().query(SQL_QUERY_USER_ROLE_MODULE, Collections.singletonMap("loginName", loginName), ROW_MAPPER_MODULE);
    }
}

