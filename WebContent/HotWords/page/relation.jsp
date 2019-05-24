<%@page import="org.apache.commons.math3.analysis.function.Exp"%>
<%@page import="hotWords.dao.LoadDaoImpl"%>
<%@page import="hotWords.bean.Explain"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="javafx.print.Collation"%>
<%@page import="java.util.List"%>
<%-- <%@page import="ReadFile.ReadFile"%> --%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="../js/echarts.js"></script>

<title>热词关系图</title>
</head>
<% 
//热词是否来自同一个文章，是否属于同一个分类
	request.setCharacterEncoding("UTF-8");
	Explain explain=new Explain();
	LoadDaoImpl loadDaoImpl=new LoadDaoImpl();
	String type=null;
	List<Explain> explains=null;
	String typeName[]= {"人工智能","云计算/大数据","区块链","数据库","程序人生","游戏开发","研发管理","前端","移动开发","物联网","运维","计算机基础","编程语言","架构","音视频开发","安全","其他"};
	String typeNameAll="";
	for(String s:typeName){
		typeNameAll=typeNameAll+s+",";
	}
// 	System.out.println(typeNameAll);
	
	if(request.getParameter("type")==null)
		explains=loadDaoImpl.loadTop();
	
	if(request.getParameter("type")!=null){
		type=request.getParameter("type");
		explains=loadDaoImpl.loadTopType(type);
	}
	
	
	
%>
<body>
			<form action="relation.jsp" method="post">
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

<div class="page-container">
<div id="tbSecond" style="width:1500px;height: 1000px;"></div>
</div>


<script type="text/javascript">

var tbSecond = echarts.init(document.getElementById("tbSecond"));
option={
		backgroundColor: '#ffffff',	// 背景颜色
	    title: {                    // 图表标题
// 	        text: "关系图展示",           // 标题文本
	        //left : '3%',                    // 标题距离左侧边距
	        //top : '3%',                     // 标题距顶部边距
			textStyle : {                       // 标题样式
				color : '#000',                     // 标题字体颜色
				fontSize : '30',                    // 标题字体大小
			}
	    },
	    tooltip: {                  // 提示框的配置
	        formatter: function(param) {
	            if (param.dataType === 'edge') {
	                //return param.data.category + ': ' + param.data.target;
	                return param.data.target;
	            }
	            //return param.data.category + ': ' + param.data.name;
	            return param.data.name;
	        }
	    },
	    
	    series: [{
	        type: "graph",          // 系列类型:关系图
	        top: '10%',             // 图表距离容器顶部的距离
	        roam: true,             // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
	        focusNodeAdjacency: true,   // 是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。[ default: false ]
	                force: {                // 力引导布局相关的配置项，力引导布局是模拟弹簧电荷模型在每两个节点之间添加一个斥力，每条边的两个节点之间添加一个引力，每次迭代节点会在各个斥力和引力的作用下移动位置，多次迭代后节点会静止在一个受力平衡的位置，达到整个模型的能量最小化。
	                                // 力引导布局的结果有良好的对称性和局部聚合性，也比较美观。
	            repulsion: 1000,            // [ default: 50 ]节点之间的斥力因子(关系对象之间的距离)。支持设置成数组表达斥力的范围，此时不同大小的值会线性映射到不同的斥力。值越大则斥力越大
	            edgeLength: [150, 100]      // [ default: 30 ]边的两个节点之间的距离(关系对象连接线两端对象的距离,会根据关系对象值得大小来判断距离的大小)，
	                                        // 这个距离也会受 repulsion。支持设置成数组表达边长的范围，此时不同大小的值会线性映射到不同的长度。值越小则长度越长。如下示例:
	                                        // 值最大的边长度会趋向于 10，值最小的边长度会趋向于 50      edgeLength: [10, 50]
	        },
	        layout: "force",            // 图的布局。[ default: 'none' ]
	                                    // 'none' 不采用任何布局，使用节点中提供的 x， y 作为节点的位置。
	                                    // 'circular' 采用环形布局;'force' 采用力引导布局.
	        // 标记的图形
	        //symbol: "path://M19.300,3.300 L253.300,3.300 C262.136,3.300 269.300,10.463 269.300,19.300 L269.300,21.300 C269.300,30.137 262.136,37.300 253.300,37.300 L19.300,37.300 C10.463,37.300 3.300,30.137 3.300,21.300 L3.300,19.300 C3.300,10.463 10.463,3.300 19.300,3.300 Z",
	        symbol: 'circle',
	        lineStyle: {            // 关系边的公用线条样式。其中 lineStyle.color 支持设置为'source'或者'target'特殊值，此时边会自动取源节点或目标节点的颜色作为自己的颜色。
	            normal: {
	                color: '#000',          // 线的颜色[ default: '#aaa' ]
	                width: 1,               // 线宽[ default: 1 ]
	                type: 'solid',          // 线的类型[ default: solid ]   'dashed'    'dotted'
	                opacity: 0.5,           // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。[ default: 0.5 ]
	                curveness: 0            // 边的曲度，支持从 0 到 1 的值，值越大曲度越大。[ default: 0 ]
	            }
	        },
	        label: {                // 关系对象上的标签
	            normal: {
	                show: true,                 // 是否显示标签
	                position: "inside",         // 标签位置:'top''left''right''bottom''inside''insideLeft''insideRight''insideTop''insideBottom''insideTopLeft''insideBottomLeft''insideTopRight''insideBottomRight'
	                textStyle: {                // 文本样式
	                    fontSize: 16
	                }
	            }
	        },
	        edgeLabel: {                // 连接两个关系对象的线上的标签
	            normal: {
	                show: true,
	                textStyle: {                
	                    fontSize: 14
	                },
	                formatter: function(param) {        // 标签内容
	                    return param.data.category;
	                }
	            }
	        },
	        data: [
	        	
	        <%
		        if(type==null){   //展示所有类别
		        for(String s:typeName){
	        %>	
            {
            	name: "<%=s %>",
	            draggable: true,                // 节点是否可拖拽，只在使用力引导布局的时候有用。
	            symbolSize: [100,100],         // 关系图节点标记的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
	            itemStyle: {
	            	color: '#00BBFF'	
	            }
            },
           	<%
           		}
           	}
           	%>
           	
           	<%
	        if(type!=null){    //展示一个类别
	        %>	
	        {
	        	name: "<%=type %>",
	            draggable: true,                // 节点是否可拖拽，只在使用力引导布局的时候有用。
	            symbolSize: [100,100],         // 关系图节点标记的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
	            itemStyle: {
	            	color: '#00BBFF'	
	            }
	        },
	       	<%
	       	}
	       	%>
           	
            <%
            	for(Explain e:explains){          //展示所有热词
            		if(!typeNameAll.contains(e.getWords())){
             %>
	        {
	        	name: "<%=e.getWords() %>",
	            draggable: true,                // 节点是否可拖拽，只在使用力引导布局的时候有用。
	            symbolSize: [30,30],         // 关系图节点标记的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
	            itemStyle: {
	            	color: '#FF8C00'
	            }
	        },
        	<%		
            		}
            	}
         	%> 
        	
	        ],
	        categories: [],
	        links: [
					<%
					for(Explain e:explains){
					%>
		                 {
		                     source: '<%=e.getWords() %>',
		                     target: '<%=e.getFenlei() %>',
		                     category:'类别'
		                 },
	                 <%
					}
	                 %>
	                 
	                 
	             	<%
	    	        if(type==null){    //展示所有类别
	    	        %>	
	                 <%
						for(Explain e:explains){
							List<Explain> explains2=loadDaoImpl.load_word_article(e.getWords());//查询与该关键词来源相同的文章(可以返回文章的链接),返回该文章产生的所有热
							for(Explain e1:explains2){
						%>
			                 {
			                     source: '<%=e.getWords() %>',
			                     target: '<%=e1.getWords() %>',
			                     category:'相同来源'
			                 },
		                 <%
							}
						}
	    	        }
		                 %>
		                 
		                 <%
			    	        if(type!=null){    //展示所有类别
			    	        %>	
			                 <%
								for(Explain e:explains){
									List<Explain> explains2=loadDaoImpl.load_word_article(e.getWords());//查询与该关键词来源相同的文章(可以返回文章的链接),返回该文章产生的所有热
									for(Explain e1:explains2){
								%>
					                 {
					                     source: '<%=e.getWords() %>',
					                     target: '<%=e1.getWords() %>',
					                     category:'相同来源'
					                 },
				                 <%
									}
								}
			    	        }
				                 %>
	
	        ]
	    }],
	    
	    animationEasingUpdate: "quinticInOut",          // 数据更新动画的缓动效果。[ default: cubicOut ]    "quinticInOut"
	    animationDurationUpdate: 100                    // 数据更新动画的时长。[ default: 300 ]
     }; 
tbSecond.setOption(option);
</script>

<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<!--/此乃百度统计代码，请自行删除-->
</body>
</html>