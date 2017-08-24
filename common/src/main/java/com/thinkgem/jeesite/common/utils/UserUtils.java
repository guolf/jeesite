package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.security.shiro.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

/**
 * Created by guolf on 17/8/22.
 */
public class UserUtils {

    public static Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static User getUser(){
        Principal principal = getPrincipal();
        if (principal!=null){
            User user = new User() ;//get(principal.getId());
            if (user != null){
                return user;
            }
            return new User();
        }
        return new User();
    }
}
