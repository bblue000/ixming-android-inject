package org.ixming.android.inject.core;

import java.lang.reflect.Method;

import org.ixming.android.inject.annotation.OnClickMethodInject;

/**
 * 
 * @author Yin Yong
 * @version 1.0
 */
public interface IOnClickListenerInjector {

//	/**
//	 * 实现动态获取并注入单击事件监听器。
//	 * @return 如果method中存在{@link ViewOnClickInject}该标注，则返回TRUE。
//	 */
//	boolean injectViewOnClickListener(Object target, Method method);
	
	/**
	 * 实现动态获取并注入单击事件监听器。
	 * @return 如果method中存在 {@link OnClickMethodInject} 标注，则返回TRUE。
	 */
	boolean injectOnClickMethodListener(Object target, Method method);
	
}
