/**
 * Copyright (c) 2017 ttpai.cn All Rights Reserved.
 */
package com.ttpai.test.email.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 文件操作
 * @author jinbiao.xing
 * @date 2017年10月20日 下午1:18:12
 * @version V1.0
 */
public class FileUtils {

	/**
	 *  工作空间
	 */
	private static String ECLIPSE_WORK_SPANCE = "E:\\workspace";

	/**
	 * git分支地址
	 */
	private static String BRANCH_URL = "\\.git\\logs\\refs\\remotes\\origin";

	/**
	 * @Title: getEclipseProjectNames
	 * @Description: 获得eclipse工作空间中公司的项目名
	 * @return List<String>
	 * @throws
	*/
	public static List<String> getEclipseProjectNames() {
		List<String> projects = new ArrayList<String>();

		File file = new File(ECLIPSE_WORK_SPANCE);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory() && tempList[i].getName().contains("ttp")) {
				System.out.println("获得公司项目：" + tempList[i].getName());
				projects.add(tempList[i].getName().trim());
			}
		}
		return projects;
	}

	public static List<String> getProjectNamesByGitBranch(String branchName) {
		List<String> projects = FileUtils.getEclipseProjectNames();
		List<String> branchProject = new ArrayList<String>();

		for (String name : projects) {
			File file = new File(ECLIPSE_WORK_SPANCE + "\\" + name + BRANCH_URL);
			File[] tempList = file.listFiles();

			if (tempList != null) {
				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].isFile() && tempList[i].getName().equals(branchName)) {
						System.out.println("获得分支项目：" + tempList[i].getName());
						branchProject.add(name);
						break;
					}
				}
			}
		}

		return branchProject;
	}

	public static void main(String[] args) {
		getProjectNamesByGitBranch("dev_xing_homePageRevision");
	}
}
