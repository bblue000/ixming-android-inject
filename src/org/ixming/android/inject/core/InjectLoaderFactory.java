package org.ixming.android.inject.core;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 根据不同参数类型获得BaseInjectLoader具体实现类。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public class InjectLoaderFactory {

	private InjectLoaderFactory() { }
	
	/**
	 * 针对Activity类获取相应的BaseInjectLoader实现类
	 */
	public static BaseInjectLoader createFrom(Activity context, Context localContext, Context themedContext) {
		return new ActivityInjectLoader(context, localContext, themedContext);
	}

	/**
	 * 针对View和资源的Container类（含有View及资源变量的任意具体类），获取相应的BaseInjectLoader实现类
	 */
	public static BaseInjectLoader createFrom(View view, Context localContext, Context themedContext) {
		return new ViewInjectLoader(view, localContext, themedContext);
	}
}
