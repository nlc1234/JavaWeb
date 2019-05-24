package hotWords.dao;
import java.awt.Color;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.http11.filters.VoidInputFilter;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.sun.crypto.provider.RSACipher;

import hotWords.bean.Explain;
import hotWords.bean.Message;
import hotWords.util.DBUtil;
import hotWords.util.PyScripter;
import hotWords.util.QingxiHtml;

public class MessageDaoImpl {
	
	public void refresh() {       //重新生成数据
		
		PyScripter getWords=new PyScripter();
//		getWords.pyScripter();   //爬取网站到本地
		
		QingxiHtml qingxiHtml=new QingxiHtml();
		try {
			qingxiHtml.clean();    //清晰数据并保存在数据库中 
		} catch (ClassNotFoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMessage(Message message) {    //清洗数据后添加到数据库
		Connection connection=DBUtil.getConnection();
		String sql="insert into hotWords(leixing,tag,fenlei,laiyuan,biaoti,shijian,yuedushu,pinglunshu,dianzanshu,reci,web) values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, message.getLeixing());
			preparedStatement.setString(2, message.getTag());
			preparedStatement.setString(3, message.getFenlei());
			preparedStatement.setString(4, message.getLaiyuan());
			preparedStatement.setString(5, message.getBiaoti());
			preparedStatement.setString(6, message.getShijian());
			preparedStatement.setInt(7, message.getYuedushu());
			preparedStatement.setInt(8, message.getPinglunshu());
			preparedStatement.setInt(9, message.getDianzanshu());
			preparedStatement.setString(10, message.getContent());//热词
			preparedStatement.setString(11, message.getWeb());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return;
	}
	public String loadWord(String keyWord) {		//查看该热词是否存在或重复
		Connection connection=DBUtil.getConnection();
		String sql="select words from Explaination where words="+"'"+keyWord+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String word=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				word=resultSet.getString("words");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return word;
	}
	
	public String loadWeb(String article) {		//根据文章标题查询网址
		Connection connection=DBUtil.getConnection();
		String sql="select web from hotWords where biaoti="+"'"+article+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String web=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				web=resultSet.getString("web");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return web;
	}
	
	
	public void insertKeyWord(Message message,String keyWord,int n,String explain) {   //向Explaination中添加
		Connection connection=DBUtil.getConnection();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		if(n==0) {
			String sql="insert into Explaination(words,num,article,explain) values(?,?,?,?)";
			try {
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1, keyWord);
				preparedStatement.setInt(2, 1);
				preparedStatement.setString(3, message.getBiaoti()+" 地址"+message.getWeb());//标题和网页地址
				preparedStatement.setString(4, explain); //解释
				preparedStatement.executeUpdate();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				DBUtil.close(resultSet);
				DBUtil.close(preparedStatement);
				DBUtil.close(connection);
			}
		}
		else {
			String sql="update Explaination set num=num+1 where words="+"'"+keyWord+"'";
			try {
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.executeUpdate();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				DBUtil.close(resultSet);
				DBUtil.close(preparedStatement);
				DBUtil.close(connection);
			}
		}
		return;
	}
	
	public void insertLastNews(String word,String tuijian,String explain,String xiangguan) {   //向Explaination中添加
		Connection connection=DBUtil.getConnection();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
	
		String sql="insert into LastNews(words,tuijian,explain,xiangguan) values(?,?,?,?)";
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, word);
			preparedStatement.setString(2, tuijian);
			preparedStatement.setString(3, explain);//标题和网页地址
			preparedStatement.setString(4, xiangguan); //解释
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return;
	}
	
	public void updataTuiJian(String hotWord,String article,String web) {   //向Explaination中添加推荐文章
		Connection connection=DBUtil.getConnection();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
			String sql="update Explaination set tuijian=tuijian+? where words=?";
			try {
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1, " 文章推荐"+article+" 地址"+web);
				preparedStatement.setString(2, hotWord);
				preparedStatement.executeUpdate();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				DBUtil.close(resultSet);
				DBUtil.close(preparedStatement);
				DBUtil.close(connection);
			}
		return;
	}
	
	public List<Message> getKeyWord() {                    //从hotWords表中获取字符串 来分析得到关键词
		Connection connection=DBUtil.getConnection();
		String sql="select * from hotWords";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		List<Message> messages=new ArrayList<Message>();;
		Message message=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				message=new Message();
				message.setBiaoti(resultSet.getString("biaoti"));
				message.setTag(resultSet.getString("tag"));
				message.setFenlei(resultSet.getString("fenlei"));
				message.setWeb(resultSet.getString("web"));
				messages.add(message);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return messages;
	}
	public List<Message> getHotWord() {                    //从hotWords表中获取字符串 来分析得到热词
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		List<Message> messages=new ArrayList<Message>();;
		Message message=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				message=new Message();
				message.setHotWord(resultSet.getString("words"));
				messages.add(message);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return messages;
	}
	
	
	
	public void sort() {      //给热词分类
		String ai[]= {"机器","深度","神经","机械","AI","人工智能","ai","智能"};  //人工智能
		String cloud[]={"Docker","Spring","Ubuntu","大数据","数据","云计算","云","虚拟机","Spark","Cloud","Hadoop","CentOs","HDFS","Mapreduce","计算"};  //云计算/大数据
		String chain[]={"区块链","比特币","Block","Chain","链"};  //区块链
		String db[]={"Oracle","sql","数据库","Redis","Mango","Hbase","Table","table","表"};  //数据库
		String career[]={"总结","思考","程序人生","个人成长"};  //程序人生
		String game[]={"游戏","Unity","unity"};  //游戏开发
		String engineering[]={"管理","研发","工程","项目"};  //研发管理
		String web[]={"网","html","jsp","JavaScript","CSS","JSON","javaweb"};  //前端
		String mobile[]={"移动","手机","Android","iOS","android"};  //移动开发
		String iot[]={"物联","传感器","物联网"};  //物联网
		String ops[]={"运维"};  //运维
		String fund[]={"计算机","语法","语义","缀","排序","指令","算法","数据结构","win","网络","HTTP","操作系统","Http"};  //计算机基础
		String lang[]={"c++","Java","Python","python","C#","编程","Eclipse","WinForm","代码","C语言","java"};  //编程语言
		String arch[]={"架构","框架","搭建"};  						//架构
		String avi[]={"视频","OpenCV","opencv","matlab","FFmpeg","语音"};  //音视频开发
		String sec[]={"火墙","安全","加密","漏洞","密码","隐私","bug"};  //安全
		
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		List<Message> messages=new ArrayList<Message>();;
		Message message=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				message=new Message();
				message.setHotWord(resultSet.getString("words"));
				message.setBiaoti(resultSet.getString("article"));
				messages.add(message);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		
		connection=DBUtil.getConnection();
		sql="update Explaination set fenlei=? where words=?";
		preparedStatement=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			for(Message m:messages) {
				String s=m.getHotWord()+m.getBiaoti();
//				System.out.println(s);
				
				for(String s1:ai) {
					if(s.contains(s1)) {
//						System.out.println("人工智能");
						preparedStatement.setString(1, "人工智能");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:cloud) {
					if(s.contains(s1)) {
//						System.out.println("云计算 大数据");
						preparedStatement.setString(1, "云计算/大数据");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:chain) {
					if(s.contains(s1)) {
						preparedStatement.setString(1, "区块链");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:db) {
					if(s.contains(s1)) {
//						System.out.println("数据库");
						preparedStatement.setString(1, "数据库");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:career) {
					if(s.contains(s1)) {
//						System.out.println("程序人生");
						preparedStatement.setString(1, "程序人生");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:game) {
					if(s.contains(s1)) {
//						System.out.println("游戏开发");
						preparedStatement.setString(1, "游戏开发");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:engineering) {
					if(s.contains(s1)) {
//						System.out.println("研发管理");
						preparedStatement.setString(1, "研发管理");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:web) {
					if(s.contains(s1)) {
//						System.out.println("前端");
						preparedStatement.setString(1, "前端");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:mobile) {
					if(s.contains(s1)) {
//						System.out.println("移动开发");
						preparedStatement.setString(1, "移动开发");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:iot) {
					if(s.contains(s1)) {
//						System.out.println("物联网");
						preparedStatement.setString(1, "物联网");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:ops) {
					if(s.contains(s1)) {
//						System.out.println("运维");
						preparedStatement.setString(1, "运维");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:fund) {
					if(s.contains(s1)) {
//						System.out.println("计算机基础");
						preparedStatement.setString(1, "计算机基础");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:lang) {
					if(s.contains(s1)) {
//						System.out.println("编程语言");
						preparedStatement.setString(1, "编程语言");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:arch) {
					if(s.contains(s1)) {
//						System.out.println("架构");
						preparedStatement.setString(1, "架构");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				
				for(String s1:avi) {
					if(s.contains(s1)) {
//						System.out.println("音视频开发");
						preparedStatement.setString(1, "音视频开发");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:sec) {
					if(s.contains(s1)) {
//						System.out.println("安全");
						preparedStatement.setString(1, "安全");
						preparedStatement.setString(2, m.getHotWord());
						preparedStatement.executeUpdate();
					}
				}
				
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		
	}
	
	public void sort2(String word,String tuijian) {      //给热词分类
		String ai[]= {"机器","深度","神经","机械","AI","人工智能","ai","智能"};  //人工智能
		String cloud[]={"Docker","Spring","Ubuntu","大数据","数据","云计算","云","虚拟机","Spark","Cloud","Hadoop","CentOs","HDFS","Mapreduce","计算"};  //云计算/大数据
		String chain[]={"区块链","比特币","Block","Chain","链"};  //区块链
		String db[]={"Oracle","sql","数据库","Redis","Mango","Hbase","Table","table","表"};  //数据库
		String career[]={"总结","思考","程序人生","个人成长"};  //程序人生
		String game[]={"游戏","Unity","unity"};  //游戏开发
		String engineering[]={"管理","研发","工程","项目"};  //研发管理
		String web[]={"网","html","jsp","JavaScript","CSS","JSON","javaweb"};  //前端
		String mobile[]={"移动","手机","Android","iOS","android"};  //移动开发
		String iot[]={"物联","传感器","物联网"};  //物联网
		String ops[]={"运维"};  //运维
		String fund[]={"计算机","语法","语义","缀","排序","指令","算法","数据结构","win","网络","HTTP","操作系统","Http"};  //计算机基础
		String lang[]={"c++","Java","Python","python","C#","编程","Eclipse","WinForm","代码","C语言","java"};  //编程语言
		String arch[]={"架构","框架","搭建"};  						//架构
		String avi[]={"视频","OpenCV","opencv","matlab","FFmpeg","语音"};  //音视频开发
		String sec[]={"火墙","安全","加密","漏洞","密码","隐私","bug"};  //安全
		
		Connection connection=DBUtil.getConnection();
		String sql="update LastNews set fenlei=? where words=?";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		List<Message> messages=new ArrayList<Message>();;
		Message message=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
//			for(Message m:messages) {
				String s=word+tuijian;
				
				for(String s1:ai) {
					if(s.contains(s1)) {
//						System.out.println("人工智能");
						preparedStatement.setString(1, "人工智能");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:cloud) {
					if(s.contains(s1)) {
//						System.out.println("云计算 大数据");
						preparedStatement.setString(1, "云计算/大数据");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:chain) {
					if(s.contains(s1)) {
						preparedStatement.setString(1, "区块链");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:db) {
					if(s.contains(s1)) {
//						System.out.println("数据库");
						preparedStatement.setString(1, "数据库");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:career) {
					if(s.contains(s1)) {
//						System.out.println("程序人生");
						preparedStatement.setString(1, "程序人生");
						preparedStatement.setString(2,word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:game) {
					if(s.contains(s1)) {
//						System.out.println("游戏开发");
						preparedStatement.setString(1, "游戏开发");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:engineering) {
					if(s.contains(s1)) {
//						System.out.println("研发管理");
						preparedStatement.setString(1, "研发管理");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:web) {
					if(s.contains(s1)) {
//						System.out.println("前端");
						preparedStatement.setString(1, "前端");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:mobile) {
					if(s.contains(s1)) {
//						System.out.println("移动开发");
						preparedStatement.setString(1, "移动开发");
						preparedStatement.setString(2,word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:iot) {
					if(s.contains(s1)) {
//						System.out.println("物联网");
						preparedStatement.setString(1, "物联网");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:ops) {
					if(s.contains(s1)) {
//						System.out.println("运维");
						preparedStatement.setString(1, "运维");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:fund) {
					if(s.contains(s1)) {
//						System.out.println("计算机基础");
						preparedStatement.setString(1, "计算机基础");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:lang) {
					if(s.contains(s1)) {
//						System.out.println("编程语言");
						preparedStatement.setString(1, "编程语言");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:arch) {
					if(s.contains(s1)) {
//						System.out.println("架构");
						preparedStatement.setString(1, "架构");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				
				for(String s1:avi) {
					if(s.contains(s1)) {
//						System.out.println("音视频开发");
						preparedStatement.setString(1, "音视频开发");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				for(String s1:sec) {
					if(s.contains(s1)) {
//						System.out.println("安全");
						preparedStatement.setString(1, "安全");
						preparedStatement.setString(2, word);
						preparedStatement.executeUpdate();
					}
				}
				
				sql="update LastNews set fenlei='其他' where fenlei is null";
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.executeUpdate();
				
//			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		
	}
	
}
