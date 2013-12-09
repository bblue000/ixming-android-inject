package org.ixming.android.inject.core;

import java.lang.reflect.Field;

import org.ixming.android.inject.annotation.ViewInject;

import android.view.View;

/**
 * 此处提供监听器的运行时注入，运行时获取，给target中的成员变量并赋值。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public interface IListenerInjector {

	/**
	 * 实现动态获取并注入监听器。
	 * @return 如果field中存在{@link ViewInject}该标注，则返回TRUE。
	 */
	boolean injectView(Object target, View view, Field field);
	
}
