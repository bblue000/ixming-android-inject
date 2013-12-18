package org.ixming.android.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来设置View的单击事件，编写代码时，只需要在该View的声明变量上加入此注解即可
 * （使用此注解时需要注意，该变量必须同时具备{@link ViewInject} Annotation）。
 * 
 * <p>
 * 如果需要设置点击事件的View并不需要在Activity或者载体类中声明为变量，则可以使用
 * {@link OnClickMethodInject}作为替代。
 * </p>
 * 
 * <p>
 * 运行时注入，不必重复编写获取、赋值代码。
 * </p>
 * @see {@link OnClickMethodInject}
 * 
 * @author Yin Yong
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewOnClickInject {
	
	/**
	 * 单击事件触发时，调用的方法。
	 * 
	 * <p>
	 * 注意：大多数情况下，需要保证本方法没有参数；<br/>
	 * 或者第一个参数为View时，会自动将当前Annotation注解的对象传入。<br/>
	 * 其他情况下，所有参数使用null赋值。
	 * </p>
	 */
	String methodName();
	
}
