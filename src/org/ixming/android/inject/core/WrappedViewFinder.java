package org.ixming.android.inject.core;

import android.util.SparseArray;
import android.view.View;

class WrappedViewFinder {

	// 提供查找View的接口
	private IViewFinder mViewFinder;
	
	private final int NONE_PARENT_KEY = 0;
	// 这些cache只是增加了引用，并没有创建太多小号内存的东西。
	private SparseArray<View> mParentViewCache;
	private SparseArray<SparseArray<View>> mChildViewCache;
	public WrappedViewFinder(IViewFinder viewFinder) {
		mViewFinder = viewFinder;
	}
	
	public View findViewById(int id, int parentId) {
		View view;
    	if (parentId > 0) {
    		// get parent view from cache first
    		// then get View from cache
    		view = findViewByParentId0(id, parentId);
    	} else {
    		view = findViewByParentId0(id, NONE_PARENT_KEY);
    	}
    	return view;
	}
	
	private View findParentViewById(int parentId) {
		View parentView = null;
		if (null != mParentViewCache) {
			parentView = mParentViewCache.get(parentId);
		}
		if (null != parentView) {
			return parentView;
		}
		parentView = findViewByParentId0(parentId, NONE_PARENT_KEY);
		if (null == parentView) {
			// 找不到parentId指定的View
			throw new NullPointerException("cannot find a view by (parentId = 0x"
					+ Integer.toHexString(parentId) + ")");
		}
		if (null == mParentViewCache) {
			mParentViewCache = new SparseArray<View>();
		}
		mParentViewCache.put(parentId, parentView);
		return parentView;
	}
	
	private View findViewByParentId0(int id, int parentId) {
		// 先从View缓存中获取
		View targetView = null;
		SparseArray<View> childViewCacheOfParent = null;
		if (null != mChildViewCache) {
			childViewCacheOfParent = mChildViewCache.get(parentId);
			if (null != childViewCacheOfParent) {
				targetView = childViewCacheOfParent.get(id);
			}
		}
		if (null != targetView) {
			return targetView;
		}
		if (parentId == NONE_PARENT_KEY) {
			// 再调用IViewFinder中的方法
			targetView = mViewFinder.findViewById(id);
			if (null == targetView) {
				// 找不到parentId指定的View
				throw new NullPointerException("cannot find a view by (id = 0x"
						+ Integer.toHexString(id) + ")");
			}
		} else {
			// 没有从缓存中找到target view
			// 尝试先找parent view
			View parentView = findParentViewById(parentId);
			// 从parent view中找target view
			targetView = parentView.findViewById(id);
			if (null == targetView) {
				// 找不到parentId指定的View
				throw new NullPointerException("cannot find a view by (id = 0x"
						+ Integer.toHexString(id) + ") "
						+ "from parent (parentId = "
						+ Integer.toHexString(parentId) + ")");
			}
		}
		// 将结果放入到缓存
		if (null == mChildViewCache) {
			mChildViewCache = new SparseArray<SparseArray<View>>();
		}
		if (null == childViewCacheOfParent) {
			childViewCacheOfParent = new SparseArray<View>();
			mChildViewCache.put(parentId, childViewCacheOfParent);
		}
		childViewCacheOfParent.put(id, targetView);
		return targetView;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (null != mParentViewCache) {
			mParentViewCache.clear();
			mParentViewCache = null;
		}
		if (null != mChildViewCache) {
			for (int i = 0; i < mChildViewCache.size(); i++) {
				SparseArray<View> sp = mChildViewCache.get(mChildViewCache.keyAt(i));
				if (null != sp) {
					sp.clear();
				}
			}
			mChildViewCache.clear();
			mChildViewCache = null;
		}
	}
	
}
