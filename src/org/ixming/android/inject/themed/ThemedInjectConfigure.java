package org.ixming.android.inject.themed;

import org.ixming.android.inject.InjectConfigure;

/**
 * 配置那些需要使用动态注入。
 * <p>
 * 默认情况下，全部支持。
 * </p>
 * @author Yin Yong
 * @version 1.0
 */
public class ThemedInjectConfigure extends InjectConfigure {

	@Override
	public String toString() {
		return "ThemedInjectConfigure [isInjectReses()=" + isInjectReses()
				+ ", isInjectViews()=" + isInjectViews() + "]";
	}
}
