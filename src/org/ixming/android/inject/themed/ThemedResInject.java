package org.ixming.android.inject.themed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ixming.android.inject.ResTargetType;

/**
 * 用来设置Activity或者View中声明的一些资源对象；
 * <br/>
 * 运行时注入，不必重复编写获取、赋值代码。
 * <br/>
 * @author Yin Yong
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThemedResInject {
	
	/**
	 * res/ 文件夹中的相应ID的名称，如R.string.app_name中的app_name
	 */
    String name();

    /**
     * 给获取出来的资源指定赋值的类型。
     * <p>
     * 比如，当获取string类型的配置文件时，该值就设置为{@link ResTargetType#String}；
     * 同为string类型，如果是需要保留格式的文本，该值就设置为{@link ResTargetType#Text}；
     * </p>
     * @see {@link ResTargetType}
     */
    ResTargetType type();
}
