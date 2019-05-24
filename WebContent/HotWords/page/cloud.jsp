
<%@page import="hotWords.bean.Explain"%>
<%@page import="java.util.List"%>
<%@page import="hotWords.dao.LoadDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字符云图</title>
</head>
<%
	request.setCharacterEncoding("UTF-8");
	LoadDaoImpl loadDaoImpl=new LoadDaoImpl();
	String type=null;
	List<Explain> explains=null;
	
	if(request.getParameter("type")==null)
		explains=loadDaoImpl.load();
	
	if(request.getParameter("type")!=null){
		type=request.getParameter("type");
		explains=loadDaoImpl.load_type(type);
	}
%>

    <script src="../js/echarts-all.js"></script>
<body>
			<form action="cloud.jsp" method="post">
    			<tr align="center">
<!--     				<td colspan="2"><h1>分类查询：</h1></td> -->
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
    		
    <div id="main" style="height:1000px;width:800px"></div>
    		
	 <script type="text/javascript">
                var myChart = echarts.init(document.getElementById('main'));
                

                function createRandomItemStyle() {
                    return {
                        normal: {
                            color: 'rgb(' + [
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)
                            ].join(',') + ')'
                        }
                    };
                }

                var option = {
                    title: {
//                         text: '热词字符云',
                        link: 'http://www.google.com/trends/hottrends'
                    },
                    tooltip: {
                        show: true
                    },
                    series: [{
                        name: '热词和频率',
                        type: 'wordCloud',
                        size: ['80%', '80%'],
                        textRotation : [0, 45, 90, -45],
                        textPadding: 0,
                        autoSize: {
                            enable: true,
                            minSize: 16
                        },
                        data: [
//                             {
//                                 name: "Sam S Club",
//                                 value: 1000000,
//                                 itemStyle: {
//                                     normal: {
//                                         color: 'black'
//                                     }
//                                 }
//                             },
							<%for(Explain e:explains){%>
                            {
                                name: "<%=e.getWords() %>",
                                value: <%=e.getNum()%>,
                                itemStyle: createRandomItemStyle(),
                                link: '<%="sheets.jsp"%>'
                            },
                            <%}%>
//                             {
//                                 name: "Amy Schumer",
//                                 value: 4386,
//                                 itemStyle: createRandomItemStyle()
//                             }
                        ]
                    }]
                };
                                    
                
      			myChart.setOption(option);
      			
      			myChart.on("click", function (param){  
              	  var word=param.name;
              	window.location.href="sheets.jsp?word="+word;
              });
      </script>
</body>
</html>