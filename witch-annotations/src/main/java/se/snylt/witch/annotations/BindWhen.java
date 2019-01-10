package se.snylt.witch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface BindWhen {

    public final static String ALWAYS = "always";

    public final static String NOT_SAME = "notSame";

    public final static String NOT_EQUALS = "notEquals";

    public final static String ONCE = "once";

    String value();
}
