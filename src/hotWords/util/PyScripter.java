package hotWords.util;

import com.sun.corba.se.spi.monitoring.StatisticsAccumulator;

import us.codecraft.webmagic.Page;  
import us.codecraft.webmagic.Site;  
import us.codecraft.webmagic.Spider;  
import us.codecraft.webmagic.pipeline.FilePipeline;  
import us.codecraft.webmagic.processor.PageProcessor;  
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;  
public class PyScripter implements PageProcessor {  
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);  
    public void process(Page page) {  
        page.putField("allhtml", page.getHtml().toString());  
        String urlstr = null;  
        for (int i = 1; i < 10; i++) {  //https://news.cnblogs.com/n/page/"+i+"/"
            urlstr = "https://www.csdn.net/nav/newarticles";  
            page.addTargetRequest(urlstr);  
            page.addTargetRequests(page.getHtml().links()  //https://news.cnblogs.com/n/\\w+.\\w+/
                    .regex("(https://blog.csdn.net/\\w+/article/details/\\w+)").all());  
        }  
    }  
    public Site getSite() {  
        return site;  
    }  
//    public static void main(String[] args)
    public static void pyScripter(){  
        Spider.create(new PyScripter())  //https://news.cnblogs.com/
                .addUrl("https://www.csdn.net/nav/newarticles")  
                .addPipeline(new FilePipeline("E:\\DevelopmentTools\\Eclipse\\EclipseFile\\爬虫\\CSDN"))  
                .setScheduler(new FileCacheQueueScheduler("E:\\DevelopmentTools\\Eclipse\\EclipseFile\\爬虫\\CSDN"))  
                .thread(5)  
                .run();  
        System.out.println("zx");
    }  
}  