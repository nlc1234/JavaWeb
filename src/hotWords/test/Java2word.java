package hotWords.test;

import java.awt.Color;    
import java.io.FileNotFoundException;    
import java.io.FileOutputStream;
import java.util.List;

import com.lowagie.text.Document;    
import com.lowagie.text.DocumentException;    
import com.lowagie.text.Font;    
import com.lowagie.text.PageSize;    
import com.lowagie.text.Paragraph;    
import com.lowagie.text.rtf.RtfWriter2;

import hotWords.bean.Explain;
import hotWords.dao.LoadDaoImpl;  
/**   
  * 创建word文档 步骤:    
  * 1,建立文档    
  * 2,创建一个书写器    
  * 3,打开文档    
  * 4,向文档中写入数据    
  * 5,关闭文档   
  */   
 public class Java2word {    
    
	  public Java2word() {    
	  }    
	    
	  /**   
	   * @param args   
	   */   
	  public static void main(String[] args) {   
		  creatDoc();
	  }
	  
	public static void creatDoc() {
		String path="E:\\DevelopmentTools\\Eclipse\\EclipseFile\\HotWords\\nlc.doc";
		String s[]= {"1","2","3","4","5","6"};
		int i=0;
		
		Explain explain=new Explain();
		LoadDaoImpl loadDaoImpl=new LoadDaoImpl();
		List<Explain> explains=loadDaoImpl.load();
		
		//设置纸张的大小  
		  Document document = new Document(PageSize.A4);   
		  try {    
				//创建word文档
			   RtfWriter2.getInstance(document,new FileOutputStream(path)); 
			   
			   //打开文档
			   document.open();    
			      
			   for(Explain e:explains) {
				   
				 //创建段落
				  Paragraph p = new Paragraph(e.getWords()+":"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.BOLD, new Color(0, 0, 0)) );   
				  //设置段落为居中对齐
				  p.setAlignment(Paragraph.ALIGN_CENTER);  
				  //写入段落
			      document.add(p);   
			   }
			  
		      
		      //关流
		      document.close(); 
		      System.out.println("end");
		  } catch (FileNotFoundException e) {    
		   e.printStackTrace();    
		  } catch (DocumentException e) {    
		   e.printStackTrace();    
		  } 
	}    
 }   