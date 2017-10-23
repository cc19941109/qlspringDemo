package com.chen.express;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;

import com.ql.util.express.IExpressContext;

public class QLExpressContext extends HashMap<String, Object> implements IExpressContext<String, Object> {

	private static final long serialVersionUID = 1L;

	private ApplicationContext context;

	 public QLExpressContext(ApplicationContext aContext){  
	     this.context = aContext;  
	    }  
	
	public QLExpressContext(Map<String, Object> aProperties, ApplicationContext aContext) {
		super(aProperties);
		this.context = aContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext){
		this.context = applicationContext;
	}

	/**
	 * 抽象方法：根据名称从属性列表中提取属性值
	 */
	public Object get(Object name) {
		Object result = null;
		result = super.get(name);
		try {
			String sname = (String) name;
			System.err.println("try to get "+sname+" ......loading.....");
			if (result == null && this.context != null && this.context.containsBean((String) name)) {
				// 如果在Spring容器中包含bean，则返回String的Bean
				result = this.context.getBean((String) name);
			}
		} catch (Exception e) {
			throw new RuntimeException("表达式获取对象失败",e);
		}
		return result;
	}

	public Object put(String name, Object object) {

		return super.put(name, object);
	}

}