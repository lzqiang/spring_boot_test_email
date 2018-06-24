/**
 * Copyright (c) 2017 ttpai.cn All Rights Reserved.
 */
package com.ttpai.test.email.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.ttpai.test.email.server.TestEmailService;
import com.ttpai.test.email.util.EMailSendUtil;
import com.ttpai.test.email.util.JIRAUtil;
import com.ttpai.test.email.vo.MailInfo;
import com.ttpai.test.email.vo.ProjectVO;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jinbiao.xing
 * @date 2017年10月20日 上午9:48:43
 * @version V1.0
 */
@Controller
@RequestMapping("")
public class TestEmailController {
	@Autowired
	private TestEmailService testEmailService;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${developName}")
	private String developName;


	 @RequestMapping("")
	 public String getProjects(Model model) {
		 model.addAttribute("projects", testEmailService.getProjects());
		 return "testEmail";
    }

	 @RequestMapping("/sendEmail")
	 @ResponseBody
	 public boolean sendEmail(Model model, String[] names, String branchName, String jiraUrl) {
		 List<ProjectVO> vos = testEmailService.getProjectVOs(Arrays.asList(names));
		 Map<String, Object> map = new HashMap<String, Object>();

		 map.put("projectVOs", vos);
		 map.put("branchName", branchName);
		 map.put("developName", developName);
		 map.put("jira", JIRAUtil.getJiraInfo(jiraUrl));

		 this.sendEmail(map);
		 return true;
	 }

	 private void sendEmail(Map<String, Object> map) {
		 String mail = "zhaoqiang.liu@ttpai.cn"; // 发送对象的邮箱
		 MailInfo info = new MailInfo();
		 info.setToAddress(mail);
		 info.setSubject("测试提测邮件");

		 try {
			 EMailSendUtil.sendHtmlMail(info, freeMarkerConfigurer, "email.ftl", map);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }

	 @RequestMapping("/projectList")
	 @ResponseBody
	 public List<ProjectVO> getProjects(Model model, List<String> nams) {
		 return testEmailService.getProjectVOs(nams);
	 }

	 @RequestMapping("/projectDetail")
	 @ResponseBody
	 public ProjectVO getProjectDetail(Model model, String name) {
		 return testEmailService.getProjectVODetail(name);
	 }

	 @RequestMapping("/projectsByBranch")
	 @ResponseBody
	 public List<ProjectVO> getProjectsByBranch(Model model, String branchName) {
		 return testEmailService.getProjectVOsByBranch(branchName);
	 }
}
