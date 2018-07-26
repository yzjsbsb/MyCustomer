package cn.com.hxx.fakewaterfall.MyAnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by apple on 2018/7/26.
 */
//@Target:指明该类型的注解可以注解的程序元素的范围;Method代表该注解针对方法
@Target(ElementType.METHOD)

//指明了该Annotation被保留的时间长短。RetentionPolicy取值为SOURCE,CLASS,RUNTIME。
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnotation {     //该注解对作用是用来对button进行排序

    /***
     * 注解方法返回值类型限定为：基本类型、String、Enums、Annotation或者是这些类型的数组；
       注解方法可以有默认值；default后跟默认值
     * @return
     */
    int order() default 0;
}
