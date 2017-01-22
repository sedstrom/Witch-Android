package se.snylt.zipper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import se.snylt.zipper.BindAction;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface OnBind {

    Class<? extends BindAction> value();
}
