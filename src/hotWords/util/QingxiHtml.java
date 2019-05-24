package hotWords.util;

import java.io.IOException;  
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import hotWords.bean.Message;
import hotWords.dao.MessageDaoImpl;

import org.apache.hadoop.fs.Path;

  
public class QingxiHtml {  
    public static class doMapper extends Mapper<Object, Text,Text, Text> {  
        public static final IntWritable one = new IntWritable(1);  
        public static Text word = new Text();  
        Message message=null;
        MessageDaoImpl messageDaoImpl=new MessageDaoImpl();
        protected void map(Object key, Text value, Context context)  
                throws IOException, InterruptedException {  
            String htmlStr = value.toString();  
//            System.out.println("网页:"+htmlStr);//有内容
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
                    messageDaoImpl.addMessage(message);
                    
                    System.out.println(dataout);  
                    Text oneLines = new Text(dataout);  
                    context.write(oneLines, new Text(""));   
  
                } catch (XpathSyntaxErrorException | NoSuchAxisException | NoSuchFunctionException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }
            }  
        }  
    }  
  
//    public static void main(String[] args) 
    public static void clean()throws IOException,  
            ClassNotFoundException, InterruptedException {  
        Job job = Job.getInstance();  
        job.setJobName("QingxiHtml");  
        job.setJarByClass(QingxiHtml.class);  
        job.setMapperClass(doMapper.class);  
  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(Text.class);  
        job.setInputFormatClass(FileInput.class);  
        Path in = new Path("hdfs://192.168.21.128:9000/work/hotWords");  
        Path out = new Path("hdfs://192.168.21.128:9000/work/hotWordsOut");  
        FileInputFormat.addInputPath(job, in);  
        FileOutputFormat.setOutputPath(job, out);  
        job.waitForCompletion(true);  
        System.out.println("zx");
    }   
}
