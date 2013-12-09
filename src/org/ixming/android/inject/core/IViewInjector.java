package org.ixming.android.inject.core;

import java.lang.reflect.Field;

import org.ixming.android.inject.annotation.ViewInject;


/**
 * 此处提供View的运行时注入，运行时获取，给target中的成员变量赋值。
 * 
 * @author Yin Yong
 * @version 1.0
 */
interface IViewInjector {
	
//	View findViewById(int id, int parentId);
//	
//	View findViewById(int id);
	
	/**
	 * 实现动态获取并注入View。
	 * @return 如果field中存在{@link ViewInject}该标注，则返回TRUE。
	 */
	boolean injectView(Object target, Field field);
}
