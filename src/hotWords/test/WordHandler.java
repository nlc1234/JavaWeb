package hotWords.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class WordHandler {
	private Configuration configuration = null;
	Log logger = LogFactory.getLog(WordHandler.class);

	public WordHandler() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	private Template getTemplate(String templatePath, String templateName) throws IOException {
		configuration.setClassForTemplateLoading(this.getClass(), templatePath);
		Template t = null;
		t = configuration.getTemplate(templateName);
		t.setEncoding("UTF-8");
		return t;
	}

	@SuppressWarnings("rawtypes")
	public void write(String templatePath, String templateName, Map dataMap, Writer out)   {
		try {
			Template t = getTemplate(templatePath, templateName);
			t.process(dataMap, out);
		} catch (IOException e) {
			logger.error(e);
//			throw new WordHandlerException(e);
		} catch (TemplateException e) {
			logger.error(e);
//			throw new WordHandlerException(e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e);
//				throw new WordHandlerException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("content", "这是基于freemarker导出成word格式。包含图片");
		map.put("userName", "二土");
		WordHandler handler = new WordHandler();
		Writer out = new OutputStreamWriter(new FileOutputStream("E:\\DevelopmentTools\\Eclipse\\EclipseFile\\HotWords\\test.doc"),"UTF-8");
		
//		handler.write("/test/com/sinoprof/tiger/doc", "hotWords.xml", map, out);
		handler.write("/WebContent/HotWords", "hotWords.xml", map, out);
	}
}
