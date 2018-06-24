<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="jsp_tag.jsp" %>
<!DOCTYPE html>

<html>
<head>
<title>提测工具</title>

<style type="text/css">
	#project {
		border:1px solid #aaa;
		width: 300px;
		height: 100%;
		float: left;
	}
	#message {
		border:1px solid #ddd;
		width: calc(100% - 310px);;
		height: 100%;
		float: right;
	}

	#deployName {
		border:1px solid #000;
		width: 33%;
		height: 100%;
		float: left;
	}
	#serverName {
		border:1px solid #c4e;
		width: 33%;
		height: 100%;
		float: left;
	}
	#gitName {
		border:1px solid #5ab;
		width: 33%;
		height: 100%;
		float: left;
	}
	.ml5 {
		margin-left: 5px;
	}
	.w250 {
		width: 250px;
	}
</style>

</head>
<body>

<div id="project">
	<h1>项目名</h1>
	<c:forEach items="${projects}" var="project">
		<input class="names" type="checkbox" name="names" value="${project}"/><span>${project }<br></span>
	</c:forEach>

	<br><br>
	<input type="checkbox" name="unCheck"/>不显示选中<br><br><br>
	<input type="text" name="gitName" class="branchName ml5 w250" placeholder="分支名"/>
	<input type="text" name="jiraUrl" class="jira ml5 w250" placeholder="JIRA"/><br><br>
	<button type="button" class="branchButton ml5">分支项目</button>
	<button type="button" class="sendButton ml5">发邮件</button><br>

</div>
<div id="message">
	<div id="serverName">
		<h1>服务名</h1>
	</div>
	<div id="deployName">
		<h1>部署名</h1>
	</div>
	<div id="gitName">
		<h1>GIT地址</h1>
	</div>
</div>

</body>

<script type="text/javascript">
	$(function(){
    	$(".names").click(function(){
    		var name = $(this).val();
    		if($(this).is(':checked')) {
	    		$.ajax({
	    			url: "projectDetail",
	    			type:'POST', //GET
	    		    data:{
	    		        name:name
	    		    },
	    		    dataType:'json',
	    			success: function(data){
	    				var deployName = "<span class='"+ name +"'>"+data.deployName+"<br></span>";
	    				var serverName = "<span class='"+ name +"'>"+data.serverName+"<br></span>";
	    				var gitName = "<span class='"+ name +"'>"+data.gitUrl+"<br></span>";
	    				$("#deployName").append(deployName);
	    				$("#serverName").append(serverName);
	    				$("#gitName").append(gitName);
	   	      		}
	    		});
    		} else {
    			$("#deployName > ."+name).remove();
    			$("#serverName > ."+name).remove();
    			$("#gitName > ."+name).remove();
    		}

   		});


    	$("input[name='unCheck']").click(function(){
    		if($(this).is(':checked')) {
    			$(".names").each(function(i, element){
    				if($(element).is(':checked')) {
    					$(element).hide();
    					$(element).next("span").hide();
    				}
  				});
    		} else {
    			$(".names").each(function(i, element){
   					$(element).show();
					$(element).next("span").show();
  				});
    		}

    	});

    	$(".branchButton").click(function(){
    		var name = $(".branchName").val();
    		$.ajax({
    			url: "/projectsByBranch",
    			type:'POST', //GET
    		    data:{
    		    	branchName:name
    		    },
    		    dataType:'json',
    			success: function(data){
    				//清空信息
    				$("#serverName").html("<h1>服务名</h1>");
    				$("#deployName").html("<h1>部署名</h1>");
    				$("#gitName").html("<h1>GIT地址</h1>");

    				$(".names").each(function(i, element){
    					if(this.checked){
    						this.checked=false;
   						}
   	                });

    				for (var i=0;i<data.length;i++) {
    					var projectName = data[i].projectName;
    					var deployName = "<span class='"+ projectName +"'>"+data[i].deployName+"<br></span>";
	    				var serverName = "<span class='"+ projectName +"'>"+data[i].serverName+"<br></span>";
	    				var gitName = "<span class='"+ projectName +"'>"+data[i].gitUrl+"<br></span>";
	    				$("#deployName").append(deployName);
	    				$("#serverName").append(serverName);
	    				$("#gitName").append(gitName);
	    				//选中项目名
	    				$(".names").each(function(i, element){
	    					if(this.value == projectName){
	    						this.checked=true;
	   						}
	   	                });
    				}
   	      		}
    		});
    	});

    	$(".sendButton").click(function(){
    		var name = $(".branchName").val();
    		var url = $(".jira").val();
    		var projectNames = $('.names:checked').map(function(){return this.value}).get().join(',');
    		alert(projectNames);
    		$.ajax({
    			url: "/sendEmail",
    			type:'POST', //GET
    		    data:{
    		    	branchName:name,jiraUrl:url,names:projectNames
    		    },
    		    dataType:'json',
    			success: function(data){
    				if(data) {
    					alert("邮件已发送");
    				}
   	      		}
    		});
    	});
  	})

</script>
</html>