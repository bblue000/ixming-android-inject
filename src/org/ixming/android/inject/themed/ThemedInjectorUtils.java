package org.ixming.android.inject.themed;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.ixming.android.inject.core.BaseInjectLoader;
import org.ixming.android.inject.core.InjectLoaderFactory;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 本包是对{@link org.ixming.android.inject}包的扩展。
 * <p>
 * 跨应用的程序，比如皮肤的一种设计方式就是安装一系列不能够直接打开的应用包，
 * 这些包中含有与皮肤相关的资源（图片或者颜色或者尺寸等等...）；
 * <br/>
 * 当应用需要根据当前皮肤设置获取资源时，可以考虑使用此包中的工具。
 * </p>
 * @author Yin Yong
 * @version 1.0
 */
public class ThemedInjectorUtils {

	final String TAG = ThemedInjectorUtils.class.getSimpleName();

	private static ThemedInjectorUtils mSingleton = null;
	
	/**
	 * 当应用想要以单例模式处理——所有用到本工具的地方，都配用同一套ThemedInjectConfigure设置
	 */
	public static synchronized ThemedInjectorUtils buildAsSingleton(ThemedInjectConfigure configure) {
		if (null == mSingleton) {
			mSingleton = new ThemedInjectorUtils(configure);
		}
		return mSingleton;
	}
	/**
	 * 默认所有项都支持的实例。
	 * 
	 * <p>
	 * Tips:当没有通过{@link ThemedInjectorUtils#buildAsSingleton(ThemedInjectConfigure)}设置客户端单例时，
	 * 回创建一个新对象；<br/>
	 * 当作为单例创建后，始终返回单例对象。
	 * </p>
	 */
	public static ThemedInjectorUtils defaultInstance() {
		return new ThemedInjectorUtils();
	}

	/**
	 * 根据客户端自定义的configure设置获得相应支持的实例
	 * 
	 * <p>
	 * <strong><i>Tips:</i></strong><br/>
	 * 当没有通过{@link ThemedInjectorUtils#buildAsSingleton(ThemedInjectConfigure)}设置客户端单例时，
	 * 回创建一个新对象；<br/>
	 * 当作为单例创建后，始终返回单例对象。（configure is ignored）
	 * </p>
	 */
	public static ThemedInjectorUtils instanceBuildFrom(
			ThemedInjectConfigure configure) {
		return new ThemedInjectorUtils(configure);
	}

	private final ThemedInjectConfigure mConfigure;

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
		boolean isInjectReses = mConfigure.isInjectReses();
		boolean isInjectViews = mConfigure.isInjectViews();
		boolean isInjectOnClickMethods = mConfigure.isInjectOnClickMethods();
		// inject object
		if (isInjectReses || isInjectViews) Inject1: {
			Field[] fields = target.getClass().getDeclaredFields();
			if (null == fields || fields.length == 0) {
				break Inject1;
			}
			for (Field field : fields) {
				if (isInjectReses && baseLoader.injectThemedRes(target, field)) {
					continue;
				}
				// next
				if (isInjectViews && baseLoader.injectView(target, field)) {
					continue;
				}
				// to be continued
			}
		}
		
		if (isInjectOnClickMethods) Inject2: {
			Method[] methods = target.getClass().getDeclaredMethods();
			if (null == methods || methods.length == 0) {
				break Inject2;
			}
			for (Method method : methods) {
				if (baseLoader.injectOnClickMethodListener(target, method)) {
					continue;
				}
			}
		}
		
		// force GC
		System.gc();
	}
}
