package org.ixming.android.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来设置Activity或者View中声明的View变量；
 * <br/>
 * 运行时注入，不必重复编写获取、赋值代码。
 * <br/>
 * 
 * @author Yin Yong
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
	
	/**
	 * 要获取的相应View的ID值
	 */
    int id();

    
    /**
     * 此属性，主要是为了总的View结构层次下，存在多个ID一样的项，而父控件有所不同，用以互相区分。
     * <p>
     * 如果没有上述的情况可以不予设置。
     * </p>
     */
    int parentId() default 0;
    
}
