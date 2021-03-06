package com.haixia.util;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

import com.haixia.controller.UserController;
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;

public class UserUtil {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	private User user;
	
	@Resource
	private IUserService userService;
	
	public UserUtil(User u){
		user = u;
	}
	
	public UserUtil() {
		
	}
	
	public boolean hasRole(String ckRole) {
		for (Role role : user.getRoles()) {
			if(ckRole.equals(role.getRoleName().toString())) {
				logger.info("UserUtil hasRole:"+role.getRoleName().toString());
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPermissiom(String ckPermission) {
		for (Role role : user.getRoles()) {
			logger.info("Role:");
			for (Permission permission : role.getPermissions()) {
				if (ckPermission.equals(permission.getPermissionCode())) {
					logger.info("---Permission:"+permission.getPermissionCode());
					return true;
				}
			}
		}
		return false;
	}
	
	public String checkLoginUser(String sid,Collection<Session> sessions) {
		logger.info("sid:"+sid);
		String decodeText = Base64.decodeToString(sid);
		logger.info("decodesid:"+decodeText);
		String[] splitstr=decodeText.split("&");
		String uid = Base64.decodeToString(splitstr[0]);
		logger.info("uid:"+uid);
		
		String userName=null;
		for(Session session:sessions){
			System.out.println("登录ip:"+session.getHost());
			System.out.println("登录id:"+session.getId());
			System.out.println("登录用户"+session.getAttribute("currentUser"));
			System.out.println("最后操作日期:"+session.getLastAccessTime());
			if(uid.equals(session.getId().toString())) {
				userName = session.getAttribute("currentUser").toString();
				logger.info("currentUser"+userName);
				break;
			}
		}
		return userName;
	}
}
