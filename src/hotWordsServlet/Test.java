package hotWordsServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import hotWords.bean.Explain;
import hotWords.dao.LoadDaoImpl;
import hotWords.util.KeyWord;
import hotWords.util.CreateWord1;

/**
 * Servlet implementation class Test
 */
@WebServlet("*.action")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {// TODO Auto-generated method stub
			request.setCharacterEncoding("utf-8");  	
			response.setContentType("text/html;charset=utf-8");  
			String actionUrl = request.getServletPath(); // 获取用户请求的路径
			System.out.println("请求地址："+actionUrl);
			
//	        response.setHeader("Access-Control-Allow-Origin", "*");   
//	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");  
	        LoadDaoImpl loadDaoImpl=new LoadDaoImpl();
	        KeyWord keyWord1=new KeyWord();
	        
	        if (actionUrl.equals("/load.action")) {
				List<Explain> explains=loadDaoImpl.loadTop();
				JSONArray jsonArray = new JSONArray();
				for (Explain e:explains) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("word", e.getWords());
					map.put("num", e.getNum());
					map.put("explain", e.getExplain());
					map.put("type", e.getFenlei());
					map.put("article", e.getArticle());
					map.put("tuijian", e.getTuijian());
					map.put("xiangguan", e.getXiangguan());
					JSONObject danci = new JSONObject(map);
					jsonArray.put(danci);
				}
				response.getWriter().print(jsonArray.toString());
			}
	        
	        if (actionUrl.equals("/loadType.action")) {
				String type=request.getParameter("type");
				List<Explain> explains=loadDaoImpl.loadTopType(type);
				JSONArray jsonArray = new JSONArray();
				for (Explain e:explains) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("word", e.getWords());
					map.put("num", e.getNum());
					map.put("explain", e.getExplain());
					map.put("type", e.getFenlei());
					map.put("article", e.getArticle());
					map.put("tuijian", e.getTuijian());
					map.put("xiangguan", e.getXiangguan());
					JSONObject danci = new JSONObject(map);
					jsonArray.put(danci);
				}
				response.getWriter().print(jsonArray.toString());
			}
	        
	        if (actionUrl.equals("/loadTypeNew.action")) {
				String type=request.getParameter("type");
				List<Explain> explains=loadDaoImpl.loadTopType2(type);
				JSONArray jsonArray = new JSONArray();
				for (Explain e:explains) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("word", e.getWords());
					map.put("num", e.getNum());
					map.put("explain", e.getExplain());
					map.put("type", e.getFenlei());
					map.put("article", e.getArticle());
					map.put("tuijian", e.getTuijian());
					map.put("xiangguan", e.getXiangguan());
					JSONObject danci = new JSONObject(map);
					jsonArray.put(danci);
				}
				response.getWriter().print(jsonArray.toString());
			}
	        
	        if (actionUrl.equals("/loadType2.action")) {      //分类查询  最新和旧新闻
				String type=request.getParameter("type");
				List<Explain> explains=loadDaoImpl.load_type(type);
				List<Explain> explains2=loadDaoImpl.load_type2(type);
				int n1=loadDaoImpl.loadTypeNum1(type);
				int n2=loadDaoImpl.loadTypeNum2(type);
				String explain=loadDaoImpl.loadTypeExplain(type);
				JSONArray jsonArray = new JSONArray();
//				for (Explain e:explains) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("num1", n1);
					map.put("num2", n2);
					map.put("type", type);
					map.put("explain", explain);
					JSONObject danci = new JSONObject(map);
					jsonArray.put(danci);
//				}
				response.getWriter().print(jsonArray.toString());
			}
	        
	        if (actionUrl.equals("/loadWord.action")) {
				String word=request.getParameter("word");
				List<Explain> explains=loadDaoImpl.load_word(word);
				JSONArray jsonArray = new JSONArray();
				for (Explain e:explains) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("word", e.getWords());
					map.put("num", e.getNum());
					map.put("explain", e.getExplain());
					map.put("type", e.getFenlei());
					map.put("article", e.getArticle());
					map.put("tuijian", e.getTuijian());
					map.put("xiangguan", e.getXiangguan());
					JSONObject danci = new JSONObject(map);
					jsonArray.put(danci);
				}
				response.getWriter().print(jsonArray.toString());
			}
	        
	        if (actionUrl.equals("/loadKeyWord.action")) {
				String keyWord=request.getParameter("keyWord");
				System.out.println("keyWord"+keyWord);
				List<Explain> explains=loadDaoImpl.load_value(keyWord);
				JSONArray jsonArray = new JSONArray();
				for (Explain e:explains) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("word", e.getWords());
					map.put("num", e.getNum());
					map.put("explain", e.getExplain());
					map.put("type", e.getFenlei());
					map.put("article", e.getArticle());
					map.put("tuijian", e.getTuijian());
					map.put("xiangguan", e.getXiangguan());
					JSONObject danci = new JSONObject(map);
					jsonArray.put(danci);
				}
				response.getWriter().print(jsonArray.toString());
			}
	        
	        if (actionUrl.equals("/getNews.action")) {
				String word=request.getParameter("word");
				System.out.println("word:"+word);
				try {
					keyWord1.setNews(word);
					Explain e = loadDaoImpl.load_word2(word);
					JSONArray jsonArray = new JSONArray();
//					for (Explain e:explains) {
						System.out.println("article:"+e.getTuijian());
						String tuijian[]=e.getTuijian().split("文章推荐");
						
						String xiangguan="";
						String str2[]=e.getXiangguan().split("相关热词");
						for(String s:str2) {
							xiangguan=xiangguan+s;
						}
						for(int i=1;i<tuijian.length;i++) {
							String str1[]=tuijian[i].split("地址");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("word",word);
							map.put("article", str1[0]);
							map.put("web", str1[1]);
							map.put("explain", e.getExplain());
							map.put("fenlei", e.getFenlei());
							
							
							map.put("xiangguan", xiangguan);
							JSONObject danci = new JSONObject(map);
							jsonArray.put(danci);
						}
//					}
					response.getWriter().print(jsonArray.toString());
				} catch (NoSuchAxisException | NoSuchFunctionException | XpathSyntaxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
	        
	        if (actionUrl.equals("/createWord.action")) {
	        	CreateWord1 createWord1=new CreateWord1();
	        	createWord1.creatDoc();
			}
			
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
