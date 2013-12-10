package jiangk.log.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlf4j {
	
	public static Logger logger = LoggerFactory.getLogger(TestSlf4j.class); 
	
	public static void main(String[] args)
	{
		//logger.info("hello world");
		//Class clazz = Class.class;
		//System.out.println(clazz.getClass().toString());
		ClassLoader systemClassLoader = TestSlf4j.class.getClassLoader();
		if(systemClassLoader==null)
			System.out.println("null");
		else
			System.out.println(systemClassLoader.toString());
		
		System.out.println(systemClassLoader.getParent().toString());
		System.out.println(systemClassLoader.getParent().getParent().toString());
	}
}
