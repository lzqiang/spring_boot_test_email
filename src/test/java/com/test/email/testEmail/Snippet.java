/**
 * Copyright (c) 2017 ttpai.cn All Rights Reserved.
 */
package com.test.email.testEmail;

import org.junit.Test;

import com.ttpai.test.email.util.EMailSendUtil;
import com.ttpai.test.email.vo.MailInfo;

/** 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author jinbiao.xing
* @date 2017年10月23日 下午6:57:51 
* @version V1.0 
*/
public class Snippet {
	@Test
    public void send(){
        String mail = "jinbiao.xing@ttpai.cn"; //发送对象的邮箱
        String title = "我有一句话跟你说";
        String content = "<div>你不在学校吗？</div><br/><hr/><div>记得28号来学校</div>";
        MailInfo info = new MailInfo();
        info.setToAddress(mail);
        info.setSubject(title);
        info.setContent(content);
        try {
        } catch (Exception e) {
            System.out.print("'"+title+"'的邮件发送失败！");
            e.printStackTrace();
        }
    }
}

