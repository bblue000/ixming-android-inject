package org.ixming.android.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.ixming.android.inject.core.BaseInjectLoader;
import org.ixming.android.inject.core.InjectLoaderFactory;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 此处提供除了layout之外的资源文件的运行时注入，运行时获取资源，给target中的成员变量并赋值。
 * 
 * <p>
 * <strong>注意：</strong> 该工具的确提供了图片的加载，但是获取到的图片没有规避OOM的操作；<br/>
 * 此处可以用于加载单个的，相对小的图片；<br/>
 * 如果需要加载大型图片，或者需要自定义/获取局部图片，推荐使用框架中的“图片加载工具”。
 * </p>
 * 
 * @author Yin Yong
 * @version 1.0
 */
public class InjectorUtils {

	final String TAG = InjectorUtils.class.getSimpleName();

	private static InjectorUtils mSingleton = null;
	/**
	 * 当应用想要以单例模式处理——所有用到本工具的地方，都配用同一套InjectConfigure设置
	 */
	public static synchronized InjectorUtils buildAsSingleton(InjectConfigure configure) {
		if (null == mSingleton) {
			mSingleton = new InjectorUtils(configure);
		}
		return mSingleton;
	}
	
	/**
	 * 默认所有项都支持的实例。
	 * 
	 * <p>
	 * Tips:当没有通过{@link InjectorUtils#buildAsSingleton(InjectConfigure)}设置客户端单例时，
	 * 回创建一个新对象；<br/>
	 * 当作为单例创建后，始终返回单例对象。
	 * </p>
	 */
	public static InjectorUtils defaultInstance() {
		return null == mSingleton ? new InjectorUtils(new InjectConfigure()) : mSingleton;
	}

	/**
	 * 根据客户端自定义的configure设置获得相应支持的实例
	 */
	public static InjectorUtils instanceBuildFrom(InjectConfigure configure) {
		return new InjectorUtils(configure);
	}
	
	private final InjectConfigure mConfigure;
	InjectorUtils() {
		this(new InjectConfigure());
	}

	InjectorUtils(InjectConfigure configure) {
		if (null == configure) {
			// new default
			configure = new InjectConfigure();
		}
		mConfigure = configure;
	}

	/**
	 * 针对具体的Activity继承类的处理
	 * 
	 * @param context
	 *            既是被动注入的对象，又提供Context
	 */
	public void inject(Activity context) {
		BaseInjectLoader baseLoader = InjectLoaderFactory.createFrom(context,
				context, null);
		innerInject(context, mConfigure, baseLoader);
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
	 *            既是被动注入的对象，又提供Context
	 *            
	 * @see {@link #inject(View, Context)}
	 */
	public void inject(View view) {
		inject(view, view.getContext());
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
	 *            被动注入的对象
	 * @param localContext
	 *            提供Context
	 *            
	 * @see {@link #inject(View, Context)}
	 */
	public void inject(View view, Context localContext) {
		BaseInjectLoader baseLoader = InjectLoaderFactory.createFrom(view,
				localContext, null);
		innerInject(view, mConfigure, baseLoader);
	}

	/**
	 * 需要动态注入的具体实例——该方法针对的是非继承自Activity或者View的其他类型，比如Fragment
	 * 
	 * @param target
	 *            目标类实例，具体用于加载资源文件
	 * @param contentView
	 *            该实例对象中的根View，并提供一个Context对象
	 */
	public void inject(Object target, View contentView) {
		inject(target, contentView, contentView.getContext());
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
	 */
	public void inject(Object target, View contentView, Context localContext) {
		BaseInjectLoader baseLoader = InjectLoaderFactory.createFrom(
				contentView, localContext, null);
		innerInject(target, mConfigure, baseLoader);
	}

	private void innerInject(Object target, InjectConfigure config, BaseInjectLoader baseLoader) {
		boolean isInjectReses = config.isInjectReses();
		boolean isInjectViews = config.isInjectViews();
		boolean isInjectOnClickMethods = config.isInjectOnClickMethods();
		// inject object
		if (isInjectReses || isInjectViews) Inject1: {
			Field[] fields = target.getClass().getDeclaredFields();
			if (null == fields || fields.length == 0) {
				break Inject1;
			}
			for (Field field : fields) {
				if (isInjectViews && baseLoader.injectView(target, field)) {
					continue;
				}
				// next
				if (isInjectReses && baseLoader.injectRes(target, field)) {
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
					continue ;
				}
			}
		}
		// force GC
		System.gc();
	}
}