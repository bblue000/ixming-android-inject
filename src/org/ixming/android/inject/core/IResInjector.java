package org.ixming.android.inject.core;

import java.lang.reflect.Field;

import org.ixming.android.inject.annotation.ResInject;

/**
 * 此处提供除了layout之外的资源文件的运行时注入，运行时获取资源，给target中的成员变量并赋值。
 * 
 * <p>
 * <strong>注意：</strong>
 * 该工具的确提供了图片的加载，但是获取到的图片没有规避OOM的操作；<br/>
 * 此处可以用于加载单个的，相对小的图片；<br/>
 * 如果需要加载大型图片，或者需要自定义/获取图片局部，推荐使用框架中的“图片加载工具”。
 * </p>
 * 
 * @author Yin Yong
 * @version 1.0
 */
interface IResInjector {

	/**
	 * 实现动态获取并注入资源。
	 * @return 如果field中存在{@link ResInject}该标注，则返回TRUE。
	 */
	boolean injectRes(Object target, Field field);
	
}
