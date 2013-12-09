package org.ixming.android.inject.core;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 基于Activity的一个动态注入
 * @author Yin Yong
 * @version 1.0
 */
class ActivityInjectLoader extends BaseInjectLoader {

	private Activity mContext;
	public ActivityInjectLoader(Activity context, Context localContext, Context themedContext) {
		super(localContext, themedContext);
		this.mContext = context;
	}

	@Override
	protected View findViewById(int id) {
		return mContext.findViewById(id);
	}
}
