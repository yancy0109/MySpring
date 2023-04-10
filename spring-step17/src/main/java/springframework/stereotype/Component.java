package springframework.stereotype;


import java.lang.annotation.*;

/**
 * 自动注入容器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {


    String value() default "";

}
