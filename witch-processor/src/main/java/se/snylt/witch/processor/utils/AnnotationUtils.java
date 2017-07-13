package se.snylt.witch.processor.utils;

import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

public class AnnotationUtils {

    public static TypeName getOnBindToRecyclerViewAdapterClass(Element bindAction) {
        TypeMirror bindClass;
        try {
            bindAction.getAnnotation(se.snylt.witch.annotations.BindToRecyclerView.class).adapter();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }

    public static TypeName getOnBindToViewPagerAdapterClass(Element bindAction) {
        TypeMirror bindClass;
        try {
            bindAction.getAnnotation(se.snylt.witch.annotations.BindToViewPager.class).adapter();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }


    public static TypeMirror getOnBindTypeMirror(Element action) {
        TypeMirror bindClass;
        try {
            action.getAnnotation(se.snylt.witch.annotations.OnBind.class).value();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return bindClass;
    }

    public static List<? extends TypeMirror> getOnBindEachTypeMirrors(Element action) {
        List<? extends TypeMirror> bindClasses;
        try {
            action.getAnnotation(se.snylt.witch.annotations.OnBindEach.class).value();
            return null;
        } catch (MirroredTypesException mte) {
            bindClasses = mte.getTypeMirrors();
        }
        return bindClasses;
    }

    public static TypeName getOnBindToViewClass(Element action) {
        TypeMirror bindClass;
        try {
            action.getAnnotation(se.snylt.witch.annotations.BindToView.class).view();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }
}
