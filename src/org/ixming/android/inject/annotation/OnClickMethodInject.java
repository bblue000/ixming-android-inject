package org.ixming.android.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 设置点击事件的View并不需要在Activity或者载体类中声明为变量，则可以对方法添加该注解。
 * </p>
 * <p>
 * 运行时注入，不必重复编写获取、赋值代码。
 * </p>
 * @author Yin Yong
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClickMethodInject {
	
	/**
	 * 指定View的ID，当该View单击事件触发时，调用的方法。
	 * 
	 * <p>
	 * 注意：大多数情况下，需要保证本方法没有参数；<br/>
	 * 或者第一个参数为View时（当且仅当为View时），会自动将当前Annotation注解中相应ID的View对象传入。<br/>
	 * 其他情况下，所有参数使用null赋值。
	 * </p>
	 */
	int id();
	
	/**
     * 此属性，主要是为了RootView结构层次下，存在多个ID一样的项，
     * 而其父控件有所不同，因此需要设置其父控件的ID用以互相区分。
     * <p>
     * 如果没有上述的情况可以不予设置。
     * </p>
     * <p>
     * <strong>设计时请注意：</strong>
     * View的结构层次是不可预知的，但是View的层数是应该严格限制的；<br/>
     * 如果一个界面中（除了ListView等特殊情况）存在多个ID相同，parentId又相同的项，
     * 我们认为是无法想象，也不予理解。
     * </p>
     */
	int parentId() default 0;
	
}
