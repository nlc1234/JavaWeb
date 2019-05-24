package hotWords.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.suggest.Suggester;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import hotWords.bean.Explain;
import hotWords.bean.Message;
import hotWords.dao.MessageDaoImpl;
import oracle.net.aso.m;
public class KeyWord {
	
public static void main(String[] args) throws NoSuchAxisException, NoSuchFunctionException, XpathSyntaxErrorException {
		// TODO Auto-generated method stub
	
	MessageDaoImpl messageDaoImpl=new MessageDaoImpl();
	KeyWord keyWord=new KeyWord();
//		System.out.println("关键字提取中：");
//		getMainIdea(); 
		
		//根据添加的文章的标题和热词进行分类
//		messageDaoImpl.sort();
	
//		System.out.println("智能推荐：");
//		getSegement();
	keyWord.setNews("小黄车");
		System.out.println("结束");
}

	public void setNews(String word) throws NoSuchAxisException, NoSuchFunctionException, XpathSyntaxErrorException {
		 List<Explain> explains=new ArrayList<Explain>();
//			String url="http://www.donews.com/search/search_word.html?keyword="+word;
		 	String url="https://so.csdn.net/so/search/s.do?q="+word+"&t=%20&u=";
			String html=httpRequest(url);
	          
	        MessageDaoImpl messageDaoImpl=new MessageDaoImpl();
	        String tuijian="";
	        String xiangguan="";
	        String article="";
	        String web="";
	        
	        String html1=httpRequest("https://baike.baidu.com/item/"+word);//百度百科查询关键词的解释
	        String explaination=(GetExplain(html1));
	        
	        for(int i=1;i<=3;i++) {
	        	JXDocument Document = new JXDocument(html);
	        	try {
	        		article=Document  //标题
		                      .sel("//div[@class='limit_width']/a[1]/allText()")  
		                      .get(i).toString(); 
	        		web = Document       //链接
	  	                    .sel("//div[@class='limit_width']/a/@href")  
	  	                    .get(i).toString(); 
	        		
	        		xiangguan=Document       //相关热词
	  	                    .sel("//div[@class='relation-search csdn-tracking-statistics']/ul/li[1]/a/text()")  
	  	                    .get(0).toString() +"相关热词"+Document       //相关热词
	  	                    .sel("//div[@class='relation-search csdn-tracking-statistics']/ul/li[2]/a/text()")  
	  	                    .get(0).toString() +"相关热词"+Document       //相关热词
	  	                    .sel("//div[@class='relation-search csdn-tracking-statistics']/ul/li[3]/a/text()")  
	  	                    .get(0).toString(); 
	        		
	        		tuijian=tuijian+"文章推荐"+article+"地址"+web;
	        		
//	        		System.out.println(tuijian);
	        		
				} catch (Exception e) {
					// TODO: handle exception
						continue;
				}
	        }
	        
	        messageDaoImpl.insertLastNews(word,tuijian,explaination,xiangguan);
	        
	        messageDaoImpl.sort2(word, article);
			return;
		}
	/**
     * 关键字提取
     */
	public static void getMainIdea() {
		MessageDaoImpl messageDaoImpl=new MessageDaoImpl();
		List<Message> messages=messageDaoImpl.getKeyWord();
		List<String> keywordList=new ArrayList<String>();
		String content=null;
		
		for(Message m:messages) {   //从hotWords表中依次遍历，将所有热词统计
			
			content=m.getTag()+","+m.getFenlei()+","+m.getBiaoti();
			System.out.println(content);
			keywordList = HanLP.extractKeyword(content, 25); //分析出关键词
			
			for(String k:keywordList) {
				
				String html=httpRequest("https://baike.baidu.com/item/"+k);//百度百科查询关键词的解释
				String explain=GetExplain(html);		//查询关键词的解释
				
				if(messageDaoImpl.loadWord(k)==null) {   //若该名称已存在
					messageDaoImpl.insertKeyWord(m,k,0,explain);//标题 网页地址 关键字 num 解释
				}
				else {
					messageDaoImpl.insertKeyWord(m,k,1,explain);
				}
			}
		}
		
	}
	
	/**
	     * 智能推荐部分
	     */
		public static void getSegement() {
			MessageDaoImpl messageDaoImpl=new MessageDaoImpl();
			List<Message> messages=messageDaoImpl.getKeyWord();   //获取所有文章标题
			List<Message> messages2=messageDaoImpl.getHotWord();   //获取所有热词
			
			Suggester suggester = new Suggester();
			
			for (Message m : messages) {      //有文章网址,存储所有网址
				suggester.addSentence(m.getBiaoti());
			}
			
			for(Message m2:messages2 ) {
//				suggester.suggest(m.getHotWord(), 3); //根据关键词查找最相关的三篇文章
				for(String s:suggester.suggest(m2.getHotWord(), 3)) {
					String web=messageDaoImpl.loadWeb(s);
//					System.out.println("热词："+m2.getHotWord());
//					System.out.println("文章"+s);
//					System.out.println("地址："+web);
					messageDaoImpl.updataTuiJian(m2.getHotWord(), s,web);
				}
			}
			
		}
	
		
	//名词解释 获取网页
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
	public static String GetExplain(String htmlStr) {
		JXDocument Document = new JXDocument(htmlStr); 
		String message=null;
		if (htmlStr.indexOf("lemma-summary") > 0) {  
            try {  
                message = Document  
                		.sel("//meta[@name='description']/@content")
                        .get(0).toString(); 

            } catch (XpathSyntaxErrorException | NoSuchAxisException | NoSuchFunctionException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }
        } 
		return message;
	}
	

}