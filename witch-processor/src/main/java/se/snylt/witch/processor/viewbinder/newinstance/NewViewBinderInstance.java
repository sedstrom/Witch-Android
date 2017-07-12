package se.snylt.witch.processor.viewbinder.newinstance;

import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.TypeUtils;
import se.snylt.witch.processor.viewbinder.TypeSpecModule;

import static se.snylt.witch.processor.TypeUtils.VIEW_BINDER;


public class NewViewBinderInstance implements TypeSpecModule {

    private final int viewId;

    public NewViewBinderInstance(int viewId) {
        this.viewId = viewId;
    }

    @Override
    public TypeSpec.Builder builder() {
        return  TypeSpec.anonymousClassBuilder("$L", viewId)
                .addSuperinterface(VIEW_BINDER)
                .addField(TypeUtils.BINDER, "binder", Modifier.PROTECTED);

    }
}
