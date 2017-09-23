package com.thinkgem.jeesite.log;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.entity.Log;
import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.utils.Exceptions;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UserUtils;
import com.thinkgem.jeesite.sys.service.LogService;
import com.thinkgem.jeesite.sys.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by guolf on 17/9/23.
 */
public class LogUtils {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    private static LogService logService = SpringContextHolder.getBean(LogService.class);
    private static MenuService menuService = SpringContextHolder.getBean(MenuService.class);

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title){
        saveLog(request, null, null, title);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
        User user = UserUtils.getUser();
        if (user != null && user.getId() != null){
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(request.getParameterMap());
            log.setMethod(request.getMethod());
            // 异步保存日志
            new SaveLogThread(log, handler, ex).start();
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread{

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            // 获取日志标题
            if (StringUtils.isBlank(log.getTitle())){
                String permission = "";
                if (handler instanceof HandlerMethod){
                    Method m = ((HandlerMethod)handler).getMethod();
                    RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
                    permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
                }
                log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
            }
            // 如果有异常，设置异常信息
            log.setException(Exceptions.getStackTraceAsString(ex));
            // 如果无标题并无异常日志，则不保存信息
            if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
                return;
            }
            // 保存日志信息
            logService.save(log);
        }
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    public static String getMenuNamePath(String requestUri, String permission){
        String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
        Map<String, String> menuMap = menuService.getMenuMap();
        String menuNamePath = menuMap.get(href);
        if (menuNamePath == null) {
            for (String p : StringUtils.split(permission)) {
                menuNamePath = menuMap.get(p);
                if (StringUtils.isNotBlank(menuNamePath)) {
                    break;
                }
            }
            if (menuNamePath == null) {
                return "";
            }
        }
        return menuNamePath;
    }

}
