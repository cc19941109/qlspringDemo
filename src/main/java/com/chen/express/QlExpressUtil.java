package com.chen.express;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.chen.service.impl.UserService;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

@Component
public class QlExpressUtil implements ApplicationContextAware {

	private static ExpressRunner runner;
	static {
		runner = new ExpressRunner();
	}
	private static boolean isInitialRunner = false;

	private ApplicationContext applicationContext;// spring上下文

	public QlExpressUtil() {
	}

	public QlExpressUtil(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 *
	 * @param statement
	 *            执行语句
	 * @param context
	 *            上下文
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object execute(String statement, Map<String, Object> context) throws Exception {

		if (applicationContext == null) {
			System.err.println("expressionServiceImpl get bean applicationContext is null");
		} else {
			System.err.println("expressionServiceImpl get bean applicationContext is not  null");

		}
		initRunner(runner);
		IExpressContext expressContext = new QLExpressContext(context, applicationContext);
		statement = initStatement(statement);
		return runner.execute(statement, expressContext, null, true, false);
	}

	public Object coreExecute(QLExpressContext expressContext, String statement) {

		if (applicationContext == null) {
			System.err.println(" applicationContext is null");
		} else {
			System.err.println(" applicationContext initialzed...");
		}

		initRunner(runner);
		statement = initStatement(statement);

		expressContext.setApplicationContext(applicationContext);

		try {
			Object value = this.runner.execute(statement, expressContext, null, true, true);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("expression exception ....");
		}
		return null;

	}

	/**
	 * 在此处把一些中文符号替换成英文符号
	 * 
	 * @param statement
	 * @return
	 */
	private String initStatement(String statement) {
		return statement.replace("（", "(").replace("）", ")").replace("；", ";").replace("，", ",").replace("“", "\"")
				.replace("”", "\"");
	}

	private void initRunner(ExpressRunner runner) {
		if (isInitialRunner == true) {
			return;
		}
		synchronized (runner) {
			if (isInitialRunner == true) {
				return;
			}
			try {
				// 在此加入预定义函数
				runner.addFunctionOfClassMethod("find", UserService.class.getName(), "findIDs",
						new Class[]{Integer.class}, null);
				
			} catch (Exception e) {
				throw new RuntimeException("初始化失败表达式", e);
			}
		}
		isInitialRunner = true;
	}


	
	public void setApplicationContext(ApplicationContext aContext) throws BeansException {
		applicationContext = aContext;
	}
}
