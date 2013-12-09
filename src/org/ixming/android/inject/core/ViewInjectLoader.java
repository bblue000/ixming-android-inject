package org.ixming.android.inject.core;

import android.content.Context;
import android.view.View;

/**
 * 基于RootView的一个动态注入
 * @author Yin Yong
 * @version 1.0
 */
class ViewInjectLoader extends BaseInjectLoader {

	private View mRootView;
	public ViewInjectLoader(View rootView, Context localContext, Context themedContext) {
		super(localContext, themedContext);
		this.mRootView = rootView;
	}

	@Override
	protected View findViewById(int id) {
		return mRootView.findViewById(id);
	}

}
