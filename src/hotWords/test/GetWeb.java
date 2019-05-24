package hotWords.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.hadoop.io.Text;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import hotWords.bean.Message;
import hotWords.dao.MessageDaoImpl;

public class GetWeb {
public static String httpRequest(String requestUrl) {  
        
        StringBuffer buffer = null;  
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;
  
        try {  
            // 建立get请求
            URL url = new URL(requestUrl);  
            httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
  
            // 获取输入流  
            inputStream = httpUrlConn.getInputStream();  
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            bufferedReader = new BufferedReader(inputStreamReader);  
  
            // 从输入流读取结果
            buffer = new StringBuffer();  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  finally {
            // 释放资源
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrlConn != null){
                httpUrlConn.disconnect();  
            }
        }
        return buffer.toString();  
    } 
	
	
	public static void main(String[] args) {
		Message message=null;
        MessageDaoImpl messageDaoImpl=new MessageDaoImpl();
        String htmlStr = null; 
        for (int i = 1; i < 10; i++) { 
        	htmlStr=httpRequest("https://www.csdn.net/nav/newarticles");
        	JXDocument Document = new JXDocument(htmlStr);  
            message=new Message();
            if (htmlStr.indexOf("blog-content-box") > 0) {  //判断是否有文章内容
                try {  
                	System.out.println("执行了");
                	
                    String leixing = Document  
                            .sel("//div[@class='article-title-box']/span[1]/text()")  //博客类型(转载或原创)
                            .get(0).toString(); 
                    
                    String tag="";
                    if(htmlStr.indexOf("tags-box artic-tag-box")>0) {
                    	tag=Document  
                                .sel("//span[@class='tags-box artic-tag-box']/a[1]/text()")  //博客标签
                                .get(0).toString();
                    }
                    
                    String fenlei="";
                    if(htmlStr.indexOf("tags-box space")>0) {
                    	fenlei = Document  
                                .sel("//div[@class='tags-box space']/a[1]/text()")  //个人分类
                                .get(0).toString(); 
                    }
                    
                    String laiyuan = Document  
                            .sel("//a[@class='follow-nickName']/text()")  //文章来源
                            .get(0).toString(); 
                    
                    String biaoti = Document  
                            .sel("//h1[@class='title-article']/text()").get(0) //标题 
                            .toString();  
                    
                    String shijian = Document  
                            .sel("//span[@class='time']/text()")  
                            .get(0).toString();   						//发布时间
                    
                    String yuedushu = Document  
                          .sel("//span[@class='read-count']/text()")  //阅读数
                          .get(0).toString().replaceAll("阅读数：", "");
                    
                    String pinglunshu = Document  
                          .sel("//a[@title='写评论']/p[1]/text()")  //评论数
                          .get(0).toString();
                    if(pinglunshu.equals("")) {
                    	pinglunshu ="0";
                    }
                    
                    String dianzanshu = Document  
                            .sel("//button[@title='点赞']/p[1]/text()")  //点赞数
                            .get(0).toString();
                    
                    String content = Document  
                            .sel("//div[@class='word float-left']/span[1]/a[1]/text()")  //文章热词
                            .get(0).toString()+","+Document  
                            .sel("//div[@class='word float-left']/span[2]/a[1]/text()")  //文章热词
                            .get(0).toString()+","+Document  
                            .sel("//div[@class='word float-left']/span[3]/a[1]/text()")  //文章热词
                            .get(0).toString()+","+Document  
                            .sel("//div[@class='word float-left']/span[4]/a[1]/text()")  //文章热词
                            .get(0).toString()+","+Document  
                            .sel("//div[@class='word float-left']/span[5]/a[1]/text()")  //文章热词
                            .get(0).toString(); 
                    
                    String web= Document  
                            .sel("//link[@rel='canonical']/@href")  //链接
                            .get(0).toString();
                    
                    String dataout = leixing + "\t" +tag+ "\t" +fenlei+"\t"+ laiyuan + "\t"+biaoti+"\t"  
                                +  shijian + "\t" + yuedushu +"\t" +pinglunshu +"\t"
                                + dianzanshu +"\t" +content+"\t"+web;
                    message.setLeixing(leixing);
                    message.setTag(tag);
                    message.setFenlei(fenlei);
                    message.setLaiyuan(laiyuan);
                    message.setBiaoti(biaoti);
                    message.setShijian(shijian);
                    message.setYuedushu(Integer.parseInt(yuedushu));
                    message.setPinglunshu(Integer.parseInt(pinglunshu));
                    message.setDianzanshu(Integer.parseInt(dianzanshu));
                    message.setContent(content);
                    message.setWeb(web);
//                    messageDaoImpl.addMessage(message);
                    
                    System.out.println(dataout);  
     

                } catch (XpathSyntaxErrorException | NoSuchAxisException | NoSuchFunctionException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }
            }  
        }  
        
	}
}
