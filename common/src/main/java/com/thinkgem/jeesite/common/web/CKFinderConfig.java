/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.web;

import com.ckfinder.connector.configuration.Configuration;
import com.ckfinder.connector.data.AccessControlLevel;
import com.ckfinder.connector.utils.AccessControlUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.Principal;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

/**
 * CKFinder配置
 * @author ThinkGem
 * @version 2014-06-25
 */
public class CKFinderConfig extends Configuration {

	public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);  
    }
	
	@Override
    protected Configuration createConfigurationInstance() {
		Principal principal =  UserUtils.getPrincipal();
		if (principal == null){
			return new CKFinderConfig(this.servletConf);
		}
		boolean isView = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:view");
		boolean isUpload = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:upload");
		boolean isEdit = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:edit");
		AccessControlLevel alc = this.getAccessConrolLevels().get(0);
		alc.setFolderView(isView);
		alc.setFolderCreate(isEdit);
		alc.setFolderRename(isEdit);
		alc.setFolderDelete(isEdit);
		alc.setFileView(isView);
		alc.setFileUpload(isUpload);
		alc.setFileRename(isEdit);
		alc.setFileDelete(isEdit);
		AccessControlUtil.getInstance(this).loadACLConfig();
		try {
			this.baseURL = FileUtils.path(Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL + principal + "/");
			this.baseDir = FileUtils.path(Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + principal + "/");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new CKFinderConfig(this.servletConf);
    }

    @Override  
    public boolean checkAuthentication(final HttpServletRequest request) {
        return UserUtils.getPrincipal()!=null;
    }

}
