package org.ixming.android.inject.themed;

import java.lang.reflect.Field;

import org.ixming.android.inject.core.BaseInjectLoader;
import org.ixming.android.inject.core.InjectLoaderFactory;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 本包是对{@link org.ixming.android.inject}包的扩展。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public class ThemedInjectorUtils {

	final String TAG = ThemedInjectorUtils.class.getSimpleName();

	public static ThemedInjectorUtils defaultInstance() {
		return new ThemedInjectorUtils();
	}

	public static ThemedInjectorUtils instanceBuildFrom(
			ThemedInjectConfigure configure) {
		return new ThemedInjectorUtils(configure);
	}

	private ThemedInjectConfigure mConfigure;

	protected ThemedInjectorUtils() {
		this(new ThemedInjectConfigure());
	}

	protected ThemedInjectorUtils(ThemedInjectConfigure configure) {
		mConfigure = configure;
	}

	/**
	 * 针对具体的Activity继承类的处理
	 * 
	 * @param context
	 *            既是被动注入的对象，又提供本地Context
	 * @param themedContext
	 *            带有主题风格的Context
	 */
	public void inject(Activity context, Context themedContext) {
		inject(context, context.getApplicationContext(), themedContext);
	}

	/**
	 * 针对具体的Activity继承类的处理
	 * 
	 * @param context
	 *            只是被动注入的对象
	 * @param localContext
	 *            提供本地Context
	 * @param themedContext
	 *            带有主题风格的Context
	 */
	public void inject(Activity context, Context localContext,
			Context themedContext) {
		BaseInjectLoader baseLoader = InjectLoaderFactory.createFrom(context,
				localContext, themedContext);
		innerInject(context, baseLoader);
	}

	/**
	 * 如果在一个继承自View的子类内部使用该方式注入，则调用此方法；
	 * <p>
	 * 该方法调用{@link #inject(View, Context)}，用View.getContext()填充第二个参数；
	 * </p>
	 * 
	 * <p>
	 * 如果调用的资源不在View内部的Context中，请选择使用{@link #inject(View, Context)}
	 * </p>
	 * 
	 * @param view
	 *            被动注入的对象，又提供Context
	 * @param themedContext
	 *            带有主题风格的Context
	 * 
	 * @see {@link #inject(View, Context)}
	 */
	public void inject(View view, Context themedContext) {
		inject(view, view.getContext(), themedContext);
	}

	/**
	 * 如果在一个继承自View的子类内部使用该方式注入，则调用此方法；
	 * <p>
	 * 该方法调用{@link #inject(View, Context)}，用View.getContext()填充第二个参数；
	 * </p>
	 * 
	 * <p>
	 * 如果调用的资源不在View内部的Context中，请选择使用{@link #inject(View, Context)}
	 * </p>
	 * 
	 * @param view
	 *            只是被动注入的对象
	 * @param localContext
	 *            提供本地Context
	 * @param themedContext
	 *            带有主题风格的Context
	 * 
	 * @see {@link #inject(View, Context)}
	 */
	public void inject(View view, Context localContext, Context themedContext) {
		BaseInjectLoader baseLoader = InjectLoaderFactory.createFrom(view,
				localContext, themedContext);
		innerInject(view, baseLoader);
	}

	/**
	 * 需要动态注入的具体实例——该方法针对的是非继承自Activity或者View的其他类型，比如Fragment
	 * 
	 * @param target
	 *            目标类实例，具体用于加载资源文件
	 * @param contentView
	 *            该实例对象中的根View，并提供一个本地的Context对象
	 * @param themedContext
	 *            提供一个定义风格的Context对象
	 */
	public void inject(Object target, View contentView, Context themedContext) {
		inject(target, contentView, contentView.getContext(), themedContext);
	}

	/**
	 * 需要动态注入的具体实例——该方法针对的是非继承自Activity或者View的其他类型，比如Fragment
	 * 
	 * @param target
	 *            目标类实例，具体用于加载资源文件
	 * @param contentView
	 *            该实例对象中的根View
	 * @param localContext
	 *            提供一个本地的Context对象
	 * @param themedContext
	 *            提供一个定义风格的Context对象
	 */
	public void inject(Object target, View contentView, Context localContext,
			Context themedContext) {
		BaseInjectLoader baseLoader = InjectLoaderFactory.createFrom(
				contentView, localContext, themedContext);
		innerInject(target, baseLoader);
	}

	private void innerInject(Object target, BaseInjectLoader baseLoader) {
		// inject object
		Field[] fields = target.getClass().getDeclaredFields();
		if (null == fields || fields.length == 0) {
			return;
		}
		for (Field field : fields) {
			if (mConfigure.isInjectReses()) {
				if (baseLoader.injectRes(target, field)) {
					continue;
				}
			}
			if (mConfigure.isInjectViews()) {
				if (baseLoader.injectView(target, field)) {
					continue;
				}
			}
		}
	}
}
