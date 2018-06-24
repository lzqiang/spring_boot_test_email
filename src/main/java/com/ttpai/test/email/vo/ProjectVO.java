/**
 * Copyright (c) 2017 ttpai.cn All Rights Reserved.
 */
package com.ttpai.test.email.vo;

/** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author jinbiao.xing
 * @date 2017年10月20日 下午2:58:59 
 * @version V1.0 
 */
public class ProjectVO {
	private String projectName;
	private String deployName;
	private String serverName;
	private String gitUrl;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDeployName() {
		return deployName;
	}
	public void setDeployName(String deployName) {
		this.deployName = deployName;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getGitUrl() {
		return gitUrl;
	}
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}
	
	
}
