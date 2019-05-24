
<%@page import="hotWords.util.CreateWord1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hotWords.bean.Explain"%>
<%@page import="hotWords.dao.LoadDaoImpl"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户表单信息</title>

<link rel="stylesheet" href="../css/style.css" />

</head>
<%
	request.setCharacterEncoding("UTF-8");
	int i=1;
	LoadDaoImpl loadDaoImpl=new LoadDaoImpl();
	List<Explain> explains=new ArrayList<>();
	String type=null;
	String value=null;
	String word=null;
	if(request.getParameter("type")==null&&request.getParameter("value")==null){
		explains=loadDaoImpl.load();
	}
	if(request.getParameter("type")!=null&&request.getParameter("value")==null){
		type=request.getParameter("type");
		explains=loadDaoImpl.load_type(type);
	}
	if(request.getParameter("type")==null&&request.getParameter("value")!=null){
		value=request.getParameter("value");
		explains=loadDaoImpl.load_value(value);
	}
	if(request.getParameter("word")!=null){
		word=request.getParameter("word");
		explains=loadDaoImpl.load_word(word);
	}
	if(request.getParameter("daochu")!=null){
		CreateWord1 createWord1=new CreateWord1();
		createWord1.creatDoc();
	}
	
%>
<body>

	<center>
		<table>
		<tr>
		 <form action="sheets.jsp" method="post">
    			<tr align="center">
    				<td colspan="2"><h1>分类查询：</h1></td>
    				<td colspan="6"> 
                  	 <select name="type"> 
                    	<option value="人工智能" selected>人工智能</option> 
                    	<option value="云计算/大数据">云计算/大数据</option> 
                    	<option value="区块链">区块链</option> 
                    	<option value="数据库">数据库</option> 
                    	<option value="程序人生">程序人生</option> 
                    	<option value="游戏开发">游戏开发</option> 
                    	<option value="研发管理">研发管理</option> 
                    	<option value="前端">前端</option> 
                    	<option value="物联网">物联网</option> 
                    	<option value="运维">运维</option> 
                    	<option value="计算机基础">计算机基础</option> 
                    	<option value="编程语言">编程语言</option> 
                    	<option value="架构">架构</option> 
                    	<option value="音视频开发">音视频开发</option> 
                    	<option value="其他">其他</option> 	
                   	 </select> 
        			</td>
    			<td colspan="">
			 		<input type="image"  src="../img/select.png" width="40" height="40">
				</td>
    			</tr>
    		</form>
    	</tr>
    		
    		<tr>
   			 <form action="sheets.jsp" method="post">
    			<tr align="center">
    				<td colspan="2"><h1>关键字查询：</h1></td>
    				<td colspan="6"> 
                   	 	<input type="text" name="value"/>
        			</td>
    			<td colspan="">
			 		<input type="image"  src="../img/select.png" width="40" height="40">
				</td>
    			</tr>
    		</form>
    		<a href="sheets.jsp?daochu=1"><h1 style="float:right">导出word</h1></a>
    		</tr>
    		</table>
    	
    	</center>
    	
    	
    	
<table cellpadding="0" cellspacing="0" border="0" id="table" class="sortable">	

    	<thead>
			<tr>
				<th><h3>编号</h3></th>
				<th><h3>热词</h3></th>
				<th><h3>出现频率</h3></th>
				<th><h3>所属分类</h3></th>
				<th><h3>相关热词</h3></th>
				<th><h3>热词来源</h3></th>
				<th><h3>文章推荐</h3></th>
<!-- 				<th colspan="2"><h3>操作</h3></th> -->
			</tr>
		</thead>
		
		<tbody>
			<%for(Explain c:explains){ %>
		<tr>
			<td><%=i %></td>
			<td><a href='#' onclick="fun1('<%=c.getWords() %>','<%=c.getExplain() %>')"><%=c.getWords() %></a></td>
			<td><%=c.getNum() %></td>
			<td><%=c.getFenlei() %></td>
			
			<%
			List<Explain> explains3=loadDaoImpl.load_word_article(c.getWords());
			%>
			<td>
			<%for(Explain c2:explains3){ %>
				<a href='#' onclick="fun1('<%=c2.getWords() %>','<%=c2.getExplain() %>')"><%=c2.getWords() %></a>
			<%} %>
			</td>
			
			<%
				String s1[]=c.getArticle().split("地址");
			%>
			<td><a href="<%=s1[1]%>"><%=s1[0] %></a></td>
			
			
			<%
				String articles[]=c.getTuijian().split("文章推荐");
// 			System.out.println(articles[1]+articles[2]+articles[3]);
				String article1[]=articles[1].split("地址");
				String article2[]=articles[2].split("地址");
				String article3[]=articles[3].split("地址");
			%>
			<td><a href="<%=article1[1]%>">推荐一</a> <a href="<%=article2[1]%>">推荐二</a> <a href="<%=article3[1]%>">推荐三</a></td>

<%-- 			<td><a href='#' onclick="fun1('<%=c.getWords() %>','<%=c.getExplain() %>')">查看热词解释</a></td> --%>

			<script> 
			function fun1(word,explain){
			    alert(word+"："+explain);
			}
			</script>
			
		</tr>
		<%
		i++;
 		}
 		%>
		</tbody>
  </table>
	<div id="controls">
		<div id="perpage">
			<select onchange="sorter.size(this.value)">
			<option value="5">5</option>
				<option value="10">10</option>
				<option value="20" selected="selected">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span>每页数量</span>
		</div>
		<div id="navigation"> 
			<img src="../img/first.gif" width="20" height="20" alt="First Page" onclick="sorter.move(-1,true)" />
			<img src="../img/previous.gif" width="20" height="20" alt="First Page" onclick="sorter.move(-1)" />
			<img src="../img/next.gif" width="20" height="20" alt="First Page" onclick="sorter.move(1)" />
			<img src="../img/last.gif" width="20" height="20" alt="Last Page" onclick="sorter.move(1,true)" />
		</div>
		<div id="text">共<%=i-1 %>条信息，当前页面<span id="currentpage"></span> / <span id="pagelimit"></span></div>
	</div>
	
<script type="text/javascript" src="../js/script.js"></script>
<script type="text/javascript">
var sorter = new TINY.table.sorter("sorter");
sorter.head = "head";
sorter.asc = "asc";
sorter.desc = "desc";
sorter.even = "evenrow";
sorter.odd = "oddrow";
sorter.evensel = "evenselected";
sorter.oddsel = "oddselected";
sorter.paginate = true;
sorter.currentid = "currentpage";
sorter.limitid = "pagelimit";
sorter.init("table",0);  //以某一列开始从大到小
</script>
</body>
</html>