package se.snylt.zipper.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

public class ViewHolderFactory {

    private static TypeName viewType = ClassName.get("android.view", "View");

    public static TypeSpec toJava(List<BindToViewActions> bindToViewActionses, ClassName viewHolderClassName) {
        TypeSpec.Builder viewHolder = TypeSpec.classBuilder(viewHolderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (BindToViewActions bindToViewActions: bindToViewActionses) {
            viewHolder.addField(viewType, bindToViewActions.value.getSimpleName().toString(), Modifier.PUBLIC);
        }
        return viewHolder.build();
    }
}
