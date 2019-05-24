
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
    <script src="../js/echarts-all.js"></script>
<body>		
    <div id="main" style="height:700px;"></div>		
    
	 <script type="text/javascript">
                var myChart = echarts.init(document.getElementById('main'));
                
                var option = {
                    tooltip: {},
                    series: [ {
                        type: 'wordCloud',
                        gridSize: 2,
                        sizeRange: [12, 50],
                        rotationRange: [-90, 90],
                        shape: 'pentagon',
                        width: 600,
                        height: 400,
                        textStyle: {
                            normal: {
                                color: function () {
                                    return 'rgb(' + [
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160)
                                    ].join(',') + ')';
                                }
                            },
                            emphasis: {
                                shadowBlur: 10,
                                shadowColor: '#333'
                            }
                        },
                        data: [
                            {
                                name: 'Sam S Club',
                                value: 10000,
                                textStyle: {
                                    normal: {
                                        color: 'black'
                                    },
                                    emphasis: {
                                        color: 'red'
                                    }
                                }
                            },
                            {
                                name: 'Home',
                                value: 965
                            },
                            {
                                name: 'Johnny Depp',
                                value: 847
                            },
                            {
                                name: 'Lena Dunham',
                                value: 582
                            },
                            {
                                name: 'Point Break',
                                value: 265
                            }
                        ]
                    } ]
                };

                          
      			myChart.setOption(option);
      			
      			myChart.on("click", function (param){  
              	  var word=param.name;
              	window.location.href="sheets.jsp?word="+word;
              });
      </script>
</body>
</html>