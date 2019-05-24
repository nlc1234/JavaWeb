package hotWords.util;

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
 public class CreateWord1 {     
//	  public static void main(String[] args) {  
//		  CreateWord1 createWord1=new CreateWord1();
//		  createWord1.creatDoc();
//	  }
	  
	public void creatDoc() {
//		String path="E:\\DevelopmentTools\\Eclipse\\EclipseFile\\HotWords\\热词Word.doc";
		String path="E:\\DevelopmentTools\\Eclipse\\EclipseFile\\HotWords\\CSDN热词.doc";
		
		Explain explain=new Explain();
		LoadDaoImpl loadDaoImpl=new LoadDaoImpl();

		List<Explain> ai=loadDaoImpl.load_type("人工智能");
		List<Explain> cloud=loadDaoImpl.load_type("云计算/大数据");
		List<Explain> chain=loadDaoImpl.load_type("区块链");
		List<Explain> db=loadDaoImpl.load_type("数据库");
		List<Explain> career=loadDaoImpl.load_type("程序人生");
		List<Explain> game=loadDaoImpl.load_type("游戏开发");
		List<Explain> engineering=loadDaoImpl.load_type("研发管理");
		List<Explain> web=loadDaoImpl.load_type("前端");
		List<Explain> mobile=loadDaoImpl.load_type("移动开发");
		List<Explain> iot=loadDaoImpl.load_type("物联网");
		List<Explain> ops=loadDaoImpl.load_type("运维");
		List<Explain> fund=loadDaoImpl.load_type("计算机基础");
		List<Explain> lang=loadDaoImpl.load_type("编程语言");
		List<Explain> arch=loadDaoImpl.load_type("架构");
		List<Explain> avi=loadDaoImpl.load_type("音视频开发");
		List<Explain> sec=loadDaoImpl.load_type("安全");
		List<Explain> other=loadDaoImpl.load_type("其他");
		
		
		//设置纸张的大小  
		  Document document = new Document(PageSize.A4);   
		  try {    
				//创建word文档
			   RtfWriter2.getInstance(document,new FileOutputStream(path)); 
			   
			   //打开文档
			   document.open();   
			      
			   Paragraph article = new Paragraph("\n\n\n\n信息化领域热词分类分析及解释"+"\n",new Font(Font.NORMAL, 36, Font.BOLD, new Color(0, 0, 0)) );    
			   Paragraph message = new Paragraph("\n\n\n\n\n\n\n\n\n\n班级：信1605-1班\n"+"学号：20163415\n"+"姓名：钮立超\n"+"辅导教师：王建民\n\n\n\n\n\n\n\n",
					   new Font(Font.NORMAL, 20, Font.BOLD, new Color(0, 0, 0)) );    
			   article.setAlignment(Paragraph.ALIGN_CENTER);  
			   message.setAlignment(Paragraph.ALIGN_CENTER);  
			   document.add(article); 
			   document.add(message); 
			   
			   int i1=1;
			   Paragraph p1 = new Paragraph("一,人工智能"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p1); 
			   for(Explain e:ai) {
				 //创建段落
				  Paragraph p = new Paragraph("1."+i1+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
				  //设置段落为居中对齐
//				  p.setAlignment(Paragraph.ALIGN_CENTER);  
				  //写入段落
			      document.add(p);   
			      i1++;
			   }
			   
			   int i2=1;
			   Paragraph p2 = new Paragraph("\n"+"二,云计算/大数据"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p2); 
			   for(Explain e:cloud) {
				  Paragraph p = new Paragraph("2."+i2+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i2++;
			   }
			   
			   int i3=1;
			   Paragraph p3 = new Paragraph("\n"+"三,区块链"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p3); 
			   for(Explain e:chain) {
				  Paragraph p = new Paragraph("3."+i3+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i3++;
			   }
			   
			   int i4=1;
			   Paragraph p4 = new Paragraph("\n"+"四,数据库"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p4); 
			   for(Explain e:db) {
				  Paragraph p = new Paragraph("4."+i4+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i4++;
			   }
			   
			   int i5=1;
			   Paragraph p5 = new Paragraph("\n"+"五,程序人生"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p5); 
			   for(Explain e:career) {
				  Paragraph p = new Paragraph("5."+i5+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i5++;
			   }
			   
			   int i6=1;
			   Paragraph p6 = new Paragraph("\n"+"六,游戏开发"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p4); 
			   for(Explain e:game) {
				  Paragraph p = new Paragraph("6."+i6+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i6++;
			   }
			   
			   int i7=1;
			   Paragraph p7 = new Paragraph("\n"+"七,研发管理"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p7); 
			   for(Explain e:engineering) {
				  Paragraph p = new Paragraph("7."+i7+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i7++;
			   }
			   
			   int i8=1;
			   Paragraph p8 = new Paragraph("\n"+"八,前端"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p8); 
			   for(Explain e:web) {
				  Paragraph p = new Paragraph("8."+i8+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i8++;
			   }
			   
			   int i9=1;
			   Paragraph p9 = new Paragraph("\n"+"九,移动开发"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p9); 
			   for(Explain e:mobile) {
				  Paragraph p = new Paragraph("9."+i9+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i9++;
			   }
			   
			   int i10=1;
			   Paragraph p10 = new Paragraph("\n"+"十,物联网"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p8); 
			   for(Explain e:iot) {
				  Paragraph p = new Paragraph("10."+i10+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i10++;
			   }
			   
			   int i11=1;
			   Paragraph p11 = new Paragraph("\n"+"十一,运维"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p11); 
			   for(Explain e:ops) {
				  Paragraph p = new Paragraph("11."+i11+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i11++;
			   }
			   
			   int i12=1;
			   Paragraph p12 = new Paragraph("\n"+"十二,计算机基础"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p12); 
			   for(Explain e:fund) {
				  Paragraph p = new Paragraph("12."+i12+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i12++;
			   }
			   
			   int i13=1;
			   Paragraph p13 = new Paragraph("\n"+"十三,编程语言"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p13); 
			   for(Explain e:lang) {
				  Paragraph p = new Paragraph("13."+i13+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i13++;
			   }
			   
			   int i14=1;
			   Paragraph p14 = new Paragraph("\n"+"十四,架构"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p13); 
			   for(Explain e:arch) {
				  Paragraph p = new Paragraph("14."+i14+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i14++;
			   }
			   
			   int i15=1;
			   Paragraph p15 = new Paragraph("\n"+"十五,音视频开发"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p15); 
			   for(Explain e:avi) {
				  Paragraph p = new Paragraph("15."+i15+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i15++;
			   }
			   
			   int i16=1;
			   Paragraph p16 = new Paragraph("\n"+"十六,安全"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p16); 
			   for(Explain e:sec) {
				  Paragraph p = new Paragraph("16."+i16+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i16++;
			   }
			   
			   //其他
			   int i17=1;
			   Paragraph p17 = new Paragraph("\n"+"十七,其他"+"\n",new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0)) );    
			   document.add(p17); 
			   for(Explain e:other) {
				  Paragraph p = new Paragraph("17."+i17+" "+e.getWords()+"\n\t"+e.getExplain()+"\n",new Font(Font.NORMAL, 10, Font.NORMAL, new Color(0, 0, 0)) );   
			      document.add(p);   
			      i17++;
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