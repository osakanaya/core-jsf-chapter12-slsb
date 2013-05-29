package com.corejsf;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("user")
@SessionScoped
public class UserBean implements Serializable {

	private static final Logger logger = Logger.getLogger("com.corejsf");
	private static final long serialVersionUID = 1L;

	private String name;
	private String password;
	private int count;
	private boolean loggedIn;

	@EJB
	private CredentialManager cm;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCount() {
		return count;
	}
	
	public String login() {
		try {
			int oldCount = count;
			count = cm.checkCredential(name, password);
			loggedIn = count > oldCount;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "login failed", e);
			
			return "internalError";
		}
		
		if(loggedIn) {
			return "loginSuccess";
		} else {
			return "loginFailure";
		}
	}
	
	public String logout() {
		loggedIn = false;
		name = "";
		password = "";
		
		return "login";
	}
}
