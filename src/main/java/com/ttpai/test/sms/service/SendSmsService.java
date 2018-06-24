package com.ttpai.test.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ttpai.test.sms.util.HttpClientUtil;
import com.ttpai.test.sms.util.JsonUtil;
import com.ttpai.test.sms.util.ResultJson;

@Service
public class SendSmsService {
	private static final Logger logger = LoggerFactory.getLogger(SendSmsService.class);
	/**
	* @Description 人人车登录验证码
	* @date  2017年11月6日上午9:58:58
	* @author 刘赵强
	* @company www.ttpai.cn
	* @return
	 */
	public String rrcLoginCode(String phone){
		final String url = "https://www.renrenche.com/api/user/getsmscode?plog_id=58fb887d1a00c281f84e6d29e941566b&__jwtsignature=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3ZWJcL3d3dyIsImlhdCI6MTUwOTkzMzUxMywibmJmIjoxNTA5OTMzNTEzLCJleHAiOjE1MDk5MzUzMTN9.ZFg2ylJ3pFumMu_JQ5tWoKU7lKbmD9xkv6q96fCOVpc&channel=bd_other&city=sh&phone="+phone;
		ResultJson result = new ResultJson();
		try {
			String postResult = HttpClientUtil.post(url);
			result.setResult(postResult);
		} catch (Exception e) {
			result.setCode("500");
			result.setMessage("人人车登录验证码发送失败");
			logger.error(e.getMessage(),e);
		}
		return JsonUtil.toJSONString(result);
	}
}
