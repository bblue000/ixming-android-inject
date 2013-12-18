package org.ixming.android.inject.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.ixming.android.inject.annotation.OnClickMethodInject;
import org.ixming.android.inject.annotation.ResInject;
import org.ixming.android.inject.annotation.ViewInject;
import org.ixming.android.inject.annotation.ViewOnClickInject;
import org.ixming.android.inject.themed.ThemedResInject;

import android.content.Context;
import android.view.View;

/**
 * 动态加载的基类，现阶段暂时是加载Res资源和成员变量View
 * 
 * @author Yin Yong
 * @version 1.0
 */
public abstract class BaseInjectLoader
implements IViewFinder, IResInjector, IViewInjector, IOnClickListenerInjector {
	
	final String TAG = BaseInjectLoader.class.getSimpleName();
	
	private ResLoader mResLoader;
	private Context mLocalContext;
	private Context mThemedContext;
	private WrappedViewFinder mWrappedViewFinder;
	private Map<String, View> mViewOnClickCache;
	
	protected BaseInjectLoader(Context localContext, Context themedContext) {
		mLocalContext = localContext;
		mThemedContext = themedContext;
		mWrappedViewFinder = new WrappedViewFinder(this);
	}

	/**
	 * 实现根据ID获取View的逻辑（不通的实现有不同的方式获取子View）
	 */
	public abstract View findViewById(int id) ;
	
	@Override
	public boolean injectView(Object target, Field field) {
		ViewInject viewInject = field.getAnnotation(ViewInject.class);
		// if annotation is not present, return false
    	if (null == viewInject) {
    		return false;
    	}
    	final int parentId = viewInject.parentId();
    	final int id = viewInject.id();
    	final View view = mWrappedViewFinder.findViewById(id, parentId);
		try {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
            field.set(target, view);
        } catch (Throwable e) {
            throw new RuntimeException("set value failed: target value type = " + field.getType()
            		+ ", result type = " + view.getClass()
            		+ ", details: " + e.getMessage());
        }
		
		// inject method-setter
		ViewOnClickInject viewOnClickInject = field.getAnnotation(ViewOnClickInject.class);
		if (null != viewOnClickInject) {
			if (null == mViewOnClickCache) {
				mViewOnClickCache = new HashMap<String, View>();
			}
			mViewOnClickCache.put(viewOnClickInject.methodName(), view);
		}
    	// anyway return true
		return true;
	}
	
	@Override
	public boolean injectOnClickMethodListener(final Object target, final Method method) {
		OnClickMethodInject onClickMethodInject = method.getAnnotation(OnClickMethodInject.class);
		if (null == onClickMethodInject) {
			return false;
		}
		int id = onClickMethodInject.id();
		int parentId = onClickMethodInject.parentId();
		View view = mWrappedViewFinder.findViewById(id, parentId);
		final Class<?>[] paramClasses = method.getParameterTypes();
		final Object[] params;
		// 尽量保证空参数或者只有一个View类型的参数接收当前的View，
		// 但是如果有基本数据类型的参数则会出现错误，抛出异常
		if (null == paramClasses || paramClasses.length == 0) {
			params = null;
		} else {
			if (paramClasses.length == 1 && View.class.isAssignableFrom(paramClasses[0])) {
				params = new Object[] { view };
			} else {
				// 所有的参数赋值null
				params = new Object[paramClasses.length];
			}
		}
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (!method.isAccessible()) {
						method.setAccessible(true);
	    			}
					method.invoke(target, params);
				} catch (Throwable e) {
					throw new RuntimeException("invoke onclick method failed: "
							+ "check the parameters of the method " + method.getName()
	                		+ ", be sure it's none-param or only a View-class param (we'd supply in code)"
	                		+ ", details: " + e.getMessage());
				}
			}
		});
		return true;
	}

	@Override
	public boolean injectRes(Object target, Field field) {
		ResInject resInject = field.getAnnotation(ResInject.class);
		// if annotation is not present, return false
		if (null == resInject) {
			return false;
		}
		checkResLoader();
		Object res = mResLoader.loadRes(resInject.type(), mLocalContext, resInject.id());
		if (null != res) {
        	try {
    			if (!field.isAccessible()) {
    				field.setAccessible(true);
    			}
                field.set(target, res);
            } catch (Throwable e) {
            	throw new RuntimeException("set value failed: target value type = " + field.getType()
                		+ ", and resTargetType(set) = " + resInject.type()
                		+ ", result type = " + res.getClass()
                		+ ", details: " + e.getMessage());
            }
        }
        // anyway return true
		return true;
	}
	
	@Override
	public boolean injectThemedRes(Object target, Field field) {
		// if annotation is not present, return false
		if (injectRes(target, field)) {
			return true;
		}
		ThemedResInject themedResInject = field.getAnnotation(ThemedResInject.class);
		if (null == themedResInject) {
			return false;
		}
		checkResLoader();
		Object res = mResLoader.loadThemedRes(themedResInject.type(), mThemedContext, themedResInject.name());
		if (null != res) {
        	try {
    			if (!field.isAccessible()) {
    				field.setAccessible(true);
    			}
                field.set(target, res);
            } catch (Throwable e) {
            	throw new RuntimeException("set value failed: target value type = " + field.getType()
                		+ ", and resTargetType(set) = " + themedResInject.type()
                		+ ", result type = " + res.getClass()
                		+ ", details: " + e.getMessage());
            }
        }
        // anyway return true
		return true;
	}
	

	private void checkResLoader() {
		if (null == mResLoader) {
			mResLoader = new ResLoader();
		}
	}

}
