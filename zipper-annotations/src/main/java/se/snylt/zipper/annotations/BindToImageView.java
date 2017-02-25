package se.snylt.zipper.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static se.snylt.zipper.annotations.ViewProperties.ImageView.IMG_RESOURCE;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface BindToImageView {

    @IdRes int id();

    String set() default IMG_RESOURCE;
}
