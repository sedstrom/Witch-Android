package se.snylt.witch.processor.utils;

import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.annotations.BindData;

public class AnnotationUtils {

    public static TypeName getBindDataViewType(Element action) {
        TypeMirror bindClass;
        try {
            action.getAnnotation(BindData.class).view();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }
}
