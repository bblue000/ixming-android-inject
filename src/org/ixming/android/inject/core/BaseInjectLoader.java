package org.ixming.android.inject.core;

import java.lang.reflect.Field;

import org.ixming.android.inject.annotation.ResInject;
import org.ixming.android.inject.annotation.ViewInject;
import org.ixming.android.inject.themed.ThemedResInject;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * 动态加载的基类，现阶段暂时是加载Res资源和成员变量View
 * @author Yin Yong
 * @version 1.0
 */
public abstract class BaseInjectLoader implements IResInjector, IViewInjector {
	
	final String TAG = BaseInjectLoader.class.getSimpleName();
	
	private ResLoader mResLoader;
	private Context mLocalContext;
	private Context mThemedContext;
	private SparseArray<View> mCache;
	
	protected BaseInjectLoader(Context localContext, Context themedContext) {
		mLocalContext = localContext;
		mThemedContext = themedContext;
	}

	protected View findViewById(int id, int parentId) {
		View parentView = getFromCache(parentId);
		if (null == parentView) {
			parentView = findViewById(parentId);
		}
		if (null == parentView) {
			throw new NullPointerException("cannot find a view by (parentId = " + parentId + ")");
		}
		checkCache();
		mCache.put(parentId, parentView);
		return parentView.findViewById(id);
	}

	/**
	 * 实现根据ID获取View的逻辑（不通的实现有不同的方式获取子View）
	 */
	protected abstract View findViewById(int id) ;
	
	@Override
	public boolean injectView(Object target, Field field) {
		ViewInject viewInject = field.getAnnotation(ViewInject.class);
		// if annotation is not present, return false
    	if (null == viewInject) {
    		return false;
    	}
    	final int parentId = viewInject.parentId();
    	final int id = viewInject.id();
    	final Object view;
    	if (parentId > 0) {
    		view = findViewById(id, parentId);
    	} else {
    		view = findViewById(id);
    	}
    	if (null != view) {
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
    		
    	}
    	// anyway return true
		return true;
	}

	@Override
	public boolean injectRes(Object target, Field field) {
		ResInject resInject = field.getAnnotation(ResInject.class);
		// if annotation is not present, return false
		if (null == resInject) {
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
		} else {
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
		}
        // anyway return true
		return true;
	}

	private void checkResLoader() {
		if (null == mResLoader) {
			mResLoader = new ResLoader();
		}
	}

	private void checkCache() {
		if (null == mCache) {
			mCache = new SparseArray<View>();
		}
	}
	
	private View getFromCache(int parentId) {
		if (null == mCache) {
			return null;
		}
		return mCache.get(parentId, null);
	}
}
