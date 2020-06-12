<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="cn.tedu.nybike.entity.DateInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>小时骑行数量</title>
<style type="text/css">
	div{
		border: 1px solid gray;
	}
	#part1{
		height: 150px;
	}
	#part2{
		height:300px;
		background-color: orange;
	}
	#part1 .title{
		font-size: 20px;
		font-weight: bolder;
	}
	#p1_d1{
		height: 25px;
		background-color: red;
	}
	#p1_d2{
		height: 96px;
		overflow: auto;
		background-color: #C38E8A;
	}
	#p1_d3{
		height: 23px;
		background-color: yellow;
	}
	.p1_d2_t1_td1{
		width:90px;
		border: 1px solid gray;
	}
	.p1_d2_t1_td2{
		border: 1px solid gray;
	}
</style>
</head>
<body>
	<div id="part1">
		<div id="p1_d1">
			<span class="title">日期列表</span>
		</div >
		<div id="p1_d2">
			<table id="p1_d2_t1">
			<%
			//从request中获取Servelet发来的数据  
			//request的数据默认是object对象
			DateInfoVO vo = (DateInfoVO)request.getAttribute("dateInfoVO");
			Map<String,List<String>> map = vo.getDateInfoMap();
			//遍历数据   ->生成tr和td的内容
			for(Entry<String,List<String>> entry:map.entrySet()){
				/* "<tr>"+
						"<td class='p1_d2_t1_td1'>[date_year]</td>"+
						"<td class='p1_d2_t1_td2'>[date_chk]</td>"+
						"</tr>" */
				out.write("<tr>");
				out.write("<td class='p1_d2_t1_td1'>"+entry.getKey()+"</td>");
				out.write("<td class='p1_d2_t1_td2'>");
				for(String str:entry.getValue()){
					/* "<input type='checkbox'>"+dateArray[index]+" " */
					out.write("<input type='checkbox'>"+str+" ");
				}
				out.write("</td></tr>");
			}
			%>
			</table>
		</div>
		<div id="p1_d3">
		<button id="btn_search">查询</button>
		</div>
	</div>
	<div id="part2"></div>
</body>
</html>