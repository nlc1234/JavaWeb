package hotWords.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hotWords.bean.Explain;
import hotWords.util.DBUtil;
import hotWords.util.KeyWord;

public class LoadDaoImpl {
	public List<Explain> load() {		//
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public List<Explain> loadTop() {		//
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		int i=1;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()&&i<=40) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
				i++;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public List<Explain> loadTopType(String type) {		//
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination where fenlei="+"'"+type+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		int i=1;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()&&i<=40) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
				i++;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public List<Explain> loadTopType2(String type) {		//
		Connection connection=DBUtil.getConnection();
		String sql="select * from LastNews where fenlei="+"'"+type+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		int i=1;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()&&i<=40) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
				i++;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public int loadTypeNum1(String type) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination where fenlei="+"'"+type+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		int n=0;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				n++;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return n;
	}
	public String loadTypeExplain(String type) {
		KeyWord keyWord=new KeyWord();
		
		if(type.equals("云计算/大数据"))
			type="云计算";
		if(type.equals("游戏开发"))
			type="手机游戏开发";
		@SuppressWarnings("static-access")
		String html1=keyWord.httpRequest("https://baike.baidu.com/item/"+type);//百度百科查询关键词的解释
        @SuppressWarnings("static-access")
		String explaination=(keyWord.GetExplain(html1));
		return explaination;
	}
	
	public int loadTypeNum2(String type) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from LastNews where fenlei="+"'"+type+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		int n=0;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				n++;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return n;
	}
	
	public List<Explain> load_type(String type) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination where fenlei="+"'"+type+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public List<Explain> load_type2(String type) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from LastNews where fenlei="+"'"+type+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public List<Explain> load_value(String value) {	  //根据数值查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination where article like "+"'%"+value+"%'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	public List<Explain> load_word(String word) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from Explaination where words= "+"'"+word+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setNum(resultSet.getInt("num"));
				explain.setArticle(resultSet.getString("article"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
	
	public Explain load_word2(String word) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select * from LastNews where words= "+"'"+word+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				explain=new Explain();
				explain.setWords(resultSet.getString("words"));
				explain.setTuijian(resultSet.getString("tuijian"));
				explain.setExplain(resultSet.getString("explain"));
				explain.setFenlei(resultSet.getString("fenlei"));
				explain.setXiangguan(resultSet.getString("xiangguan"));
//				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explain;
	}
	
	@SuppressWarnings("resource")
	public List<Explain> load_word_article(String word) {	  //根据类型查询
		Connection connection=DBUtil.getConnection();
		String sql="select article from Explaination where words= "+"'"+word+"'";
		String sql2="select * from Explaination where article=?";
		String sql3=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Explain explain=null;
		String article=null;
		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				article=(resultSet.getString("article"));
			}
			
			preparedStatement= connection.prepareStatement(sql2);
			preparedStatement.setString(1, article);
//			preparedStatement.executeUpdate();
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(!resultSet.getString("words").equals(word)) {
					explain=new Explain();
					explain.setWords(resultSet.getString("words"));
					explain.setNum(resultSet.getInt("num"));
					explain.setArticle(resultSet.getString("article"));
					explain.setTuijian(resultSet.getString("tuijian"));
					explain.setExplain(resultSet.getString("explain"));
					explain.setFenlei(resultSet.getString("fenlei"));
					explain.setXiangguan(resultSet.getString("xiangguan"));
					explains.add(explain);
				}
				
			}
			
//			for(Explain e:explains) {
//				sql3="update Explaination set xiangguan=xiangguan+? where words="+"'"+word+"'";
//				preparedStatement= connection.prepareStatement(sql3);
//				preparedStatement.setString(1, e.getWords()+"相关热词");
//				preparedStatement.executeUpdate();
//			}
		}catch (Exception e) {
			// TODO: handle exception)
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explains;
	}
//	public static void main(String[] args) {
//		LoadDaoImpl loadDaoImpl=new LoadDaoImpl();
//		List<Explain> explains=loadDaoImpl.load();
//		for(Explain e:explains) {
//			loadDaoImpl.load_word_article(e.getWords());
//		}
//	}
	public String load_explain(String word) {	  //根据word查询
		Connection connection=DBUtil.getConnection();
		String sql="select explain from Explaination where words= "+"'"+word+"'";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String explain=null;
//		Explain explain=null; 
//		List<Explain> explains=new ArrayList<>();
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
//				explain=new Explain();
				explain=(resultSet.getString("explain"));
//				explains.add(explain);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			DBUtil.close(resultSet);
			DBUtil.close(preparedStatement);
			DBUtil.close(connection);
		}
		return explain;
	}

}
