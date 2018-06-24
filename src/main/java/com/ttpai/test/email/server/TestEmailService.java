/**
 * Copyright (c) 2017 ttpai.cn All Rights Reserved.
 */
package com.ttpai.test.email.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ttpai.test.email.util.FileUtils;
import com.ttpai.test.email.util.PropertyUtils;
import com.ttpai.test.email.vo.ProjectVO;


/** 
 * @Description: 提测邮件服务类
 * @author jinbiao.xing
 * @date 2017年10月20日 上午9:51:58 
 * @version V1.0 
 */
@Service
public class TestEmailService {
	
	/** 
	 * @Title: getProjects 
	 * @Description: 获取所有工作区的项目名
	 * @return List<String>
	 * @throws 
	*/
	public List<String> getProjects() {
		return FileUtils.getEclipseProjectNames();
	}
	
	public List<ProjectVO> getProjectVOs(List<String> projects) {
		List<ProjectVO> projectVOs = new ArrayList<ProjectVO>();

		if(projects != null && projects.size()>0) {
			for (String p : projects) {
				projectVOs.add(getProjectVODetail(p));
			}
		}
		return projectVOs;
	}
	
	public List<ProjectVO> getProjectVOsByBranch(String branchName) {
		
		List<String> projectNames = FileUtils.getProjectNamesByGitBranch(branchName);
		
		List<ProjectVO> projectVOs = new ArrayList<ProjectVO>();

		for (String p : projectNames) {
			projectVOs.add(getProjectVODetail(p));
		}
		return projectVOs;
	}
	
	public ProjectVO getProjectVODetail(String name) {
		String message = PropertyUtils.getProperty("projects.properties", name);
		ProjectVO vo = new ProjectVO();
		if (message != null && message.length() > 0) {
			vo.setProjectName(name);
			
			String[] voStr = message.split(",");
			
			vo.setDeployName(voStr[0]);
			vo.setServerName(voStr[1]);
			vo.setGitUrl(voStr[2]);
		}
		return vo;
	}
}
