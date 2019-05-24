package hotWords.test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.suggest.Suggester;
public class KeyWord {


public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("首次编译运行时，HanLP会自动构建词典缓存，请稍候……\n");
		//第一次运行会有文件找不到的错误但不影响运行，缓存完成后就不会再有了
//		System.out.println("标准分词：");
//		System.out.println(HanLP.segment("你好，欢迎使用HanLP！"));
//		System.out.println("\n");
//		java.util.List<Term> termList = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
//		System.out.println("NLP分词：");
//		System.out.println(termList);
//		System.out.println("\n");


		System.out.println("智能推荐：");
		getSegement();
		System.out.println("\n");


//		System.out.println("关键字提取：");
//		getMainIdea();
//		System.out.println("\n");
//
//
//		System.out.println("自动摘要：");
//		getZhaiYao();
//		System.out.println("\n");
//
//
//		System.out.println("短语提取：");
//		getDuanYu();
//		System.out.println("\n");
}


	/**
     * 智能推荐部分
     */
	public static void getSegement() {
		Suggester suggester = new Suggester();
		String[] titleArray = ("威廉王子发表演说 呼吁保护野生动物\n" + "发言《时代》年度人物最终入围名单出炉 普京马云入选\n" + "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n"
				+ "日本保密法将正式生效 日媒指其损害国民知情权\n" + "英报告说空气污染带来“公共健康危机”").split("\\n");
		for (String title : titleArray) {
			suggester.addSentence(title);
		}
		System.out.println(suggester.suggest("发言", 1)); // 语义
//		System.out.println(suggester.suggest("危机公共", 1)); // 字符
//		System.out.println(suggester.suggest("mayun", 1)); // 拼音
	}


	/**
     * 关键字提取
     */
	public static void getMainIdea() {
		// String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
	String content = "机器学习机器学习课程机器学习教程深度学习视频教程深度学习学习";
	java.util.List<String> keywordList = HanLP.extractKeyword(content, 200);
		System.out.println(keywordList);
	}


	/**
     * 自动摘要
     */
	public static void getZhaiYao() {
		String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n"
				+ "算法可以宽泛的分为三类，\n" + "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n"
				+ "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n"
				+ "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
		java.util.List<String> sentenceList = HanLP.extractSummary(document, 3);
		System.out.println(sentenceList);
	}


	/**
     * 短语提取
     */
	public static void getDuanYu() {
		String text = "机器学习,机器学习课程,机器学习教程,深度学习视频教程,深度学习学习";
		java.util.List<String> phraseList = HanLP.extractPhrase(text, 10);
		System.out.println(phraseList);
	}


}