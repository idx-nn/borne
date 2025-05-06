package nn.iamj.borne.modules.command.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMeta {

    String rights() default "";
    boolean consoleExecute() default true;

}
