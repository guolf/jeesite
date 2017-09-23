/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.entity.*;
import com.thinkgem.jeesite.common.security.shiro.Principal;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.sys.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 用户工具类
 *
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

    private static UserService userService = SpringContextHolder.getBean(UserService.class);
    private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);
    private static MenuService menuService = SpringContextHolder.getBean(MenuService.class);
    private static AreaService areaService = SpringContextHolder.getBean(AreaService.class);
    private static OfficeService officeService = SpringContextHolder.getBean(OfficeService.class);

    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

    public static final String CACHE_AUTH_INFO = "authInfo";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id) {
        return userService.get(id);
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     */
    public static User getByLoginName(String loginName) {
        User user = userService.getByLoginName(loginName);
        if (user == null) {
            return null;
        }
        user.setRoleList(roleService.findList(new Role(user)));
        return user;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(User user) {
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRoleList() {
        return roleService.getRoleList(getUser());
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        return menuService.findByUser(getUser());
    }

    /**
     * 获取当前用户授权的区域
     *
     * @return
     */
    public static List<Area> getAreaList() {
        List<Area> areaList = areaService.findList(new Area());
        return areaList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    @Cacheable(value = CACHE_OFFICE_LIST)
    public static List<Office> getOfficeList() {
        User user = getUser();
        if (user.isAdmin()) {
            return officeService.findList(new Office());
        } else {
            Office office = new Office();
            office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
            return officeService.findList(office);
        }
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    @Cacheable(value = CACHE_OFFICE_ALL_LIST)
    public static List<Office> getOfficeAllList() {
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = officeService.findList(new Office());
        }
        return officeList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
//			subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

}