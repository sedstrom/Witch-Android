package se.snylt.zipper.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static se.snylt.zipper.ViewProperties.EditText.TEXT;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface BindToEditText {

    @IdRes int id();

    String set() default TEXT;
}
