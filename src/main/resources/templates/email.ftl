<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>表格</title>
<style type="text/css">
body{
	font-size:18px;
	color:#000;
	font-family:Arial, Helvetica, sans-serif;
	font-stretch:
	
	}
</style>
</head>

<body>
<table width="1130" border="1" align="center" cellpadding="0" cellspacing="0" >

<!-------------------------------------------------------第一段---------------------------->

  <tr>
    <td width="90" rowspan="2" style="text-align: center">环境信息</td>
    <td width="180" bgcolor="#bdd7ee" style="text-align: center">部署环境</td>
    <td width="455" colspan="2" bgcolor="#bdd7ee" style="text-align: center">部署项目</td>
    <td width="400" bgcolor="#bdd7ee" style="text-align: center">服务名称</td>   
  </tr>
  <tr>
    <td style="text-align: center">测试环境</td>
    <td colspan="2" style="text-align: center">
    	<#list projectVOs as ps>
    		${ps.deployName}<br/>
    	</#list>
    </td>
   	 <td style="text-align: center">
   	 	<#list projectVOs as ps>
    		${ps.serverName}<br/>
    	</#list>
	</td>   
  </tr>
<!-------------------------------------------------------第二段---------------------------->
  <tr>
    <td width="90" rowspan="2" style="text-align: center" >人员信息</td>
    <td width="180" bgcolor="#bdd7ee" style="text-align: center">产品经理</td>
    <td width="227" bgcolor="#bdd7ee" style="text-align: center">前端工程师</td>
    <td width="227" bgcolor="#bdd7ee" style="text-align: center">后端工程师</td>
    <td width="400" bgcolor="#bdd7ee" style="text-align: center">测试工程师</td>   
  </tr>
  <tr>
    <td style="text-align: center"></td>
    <td style="text-align: center"><br/>
    </td>
    <td style="text-align: center">${developName}</td>
   	 <td style="text-align: center">	</td>   
  </tr>
 
  <!-------------------------------------------------------第三段---------------------------->
  <tr>
    <td width="90" rowspan="2" style="text-align: center">业务信息</td>
    <td width="180" bgcolor="#bdd7ee" style="text-align: center">数据库（有、无）</td>
    <td  width="455" colspan="2" bgcolor="#bdd7ee" style="text-align: center">配置文件(有、无)</td>
    <td width="400" bgcolor="#bdd7ee" style="text-align: center">发布顺序</td>   
  </tr>
  <tr>
    <td style="text-align: center">无</td>
    <td colspan="2" style="text-align:center;color:#F00;">有</td>
    <td style="text-align:center;">无</td>   
  </tr>
 <!-------------------------------------------------------表格段---------------------------->
  <tr>
    <td  colspan="5"  bgcolor="#ededed">上线功能及对分支名</td>
  </tr>
  <tr>
    <td colspan="5">
    [功能] ${jira.title}

	<p>
	  <#list projectVOs as ps>
		${ps.deployName}<#if ps_has_next>/</#if>
		<#if !ps_has_next>：${branchName}</#if>
	  </#list>
	</p>
	</td>
  </tr>
  <tr style="text-align: left">
    <td colspan="5" align="left" bgcolor="#ededed">功能描述</td>
  </tr>
  <tr>
    <td colspan="5">
    ${jira.title}。详见jira。
    </td>
  </tr>
  
  <tr>
  	<td colspan="5" bgcolor="#ededed">git地址（必填）</td>
  </tr>
  <tr>
    <td colspan="5" >
    	<#list projectVOs as ps>
    		${ps.gitUrl}<br/>
    	</#list>
    </td>
  </tr>

  <tr>
    <td colspan="5" bgcolor="#ededed">jira地址（必填）</td>
  </tr>
 
  <tr>
    <td colspan="5">
		${jira.url}
    </td>
  </tr>
  <tr>
    <td colspan="5" bgcolor="#ededed">影响到的项目或模块</td>
  </tr>
  <tr>
  <td colspan="5">
  	<#list projectVOs as ps>
		${ps.deployName}<#if ps_has_next>/</#if>
	</#list>
  </td>
  </tr>
  <tr>
    <td colspan="5" bgcolor="#ededed">测试方法及测试功能点</td>
  </tr>
  <tr>
    <td colspan="5">
    	</br>
	</td>
  </tr>
   <tr>
    <td colspan="5" bgcolor="#ededed">后端代码是否己经合并到master（必填）</td>
  </tr>
  <tr>
    <td colspan="5">无</td>
  </tr>
  <tr>
    <td colspan="5" bgcolor="#ededed">数据库</td>
  </tr>
  <tr>
    <td colspan="5">无 </td>
  </tr>

 
  <tr>
    <td colspan="5" bgcolor="#ededed">配置文件（格式说明：项目名称请使用【】、文件名称请使用红色加粗字体）</td>
  </tr>
  <tr>
    <td colspan="5">
    	<strong>【schedule】<span style="color:#F00"> schedule.properties</span>文件，新增内容如下</strong>
        <p>app.domain.name.addr=<a href="http://pubapi.ttpai.cn/v1.0/mobile/push?appid=10010">
        http://pubapi.ttpai.cn/v1.0/mobile/push?appid=10010</a></p>
	</td>
  </tr>
  <tr>
    <td colspan="5" bgcolor="#ededed">其他操作</td>
  </tr>
  <tr>
    <td colspan="5" style="color:#F00">
    无<br/>
    </td>
  </tr>
  <tr>
    <td colspan="5" bgcolor="#ededed">后端代码是否更新</td>
  </tr>
  <tr>
    <td colspan="5">是</td>
  </tr>
  <tr>
    <td colspan="5" bgcolor="#ededed">前端代码是否更新</td>
  </tr>
  <tr>
    <td colspan="5">无</td>
  </tr>
  <tr bgcolor="#ededed">
    <td colspan="5">代码更新后操作</td>
  </tr>
  <tr>
    <td colspan="5">无</td>
  </tr>
  <tr>
    <td colspan="5" style="color:#F00">
    注:<br/>
	①17:30以后的上线邮件必须有蒋总邮件特批，否则不予处理！<br/>                    
	②同时发布多个项目时，请写明发布顺序！<br/>
	③请确认上线条件已成熟，SA将根据邮件进行上线，如有特殊说明请告知！<br/>
	</td>
  </tr>
</table>
</body>
</html>
