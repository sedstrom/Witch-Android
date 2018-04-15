package se.snylt.witch.processor.viewbinder.getview;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_VIEW;

public interface GetView extends MethodSpecModule {

    TypeName getViewTypeName();

    TypeName getViewHolderTypeName();
}
