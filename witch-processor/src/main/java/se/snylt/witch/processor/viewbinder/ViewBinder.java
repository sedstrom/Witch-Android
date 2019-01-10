package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

import se.snylt.witch.processor.WitchException;
import se.snylt.witch.processor.utils.ProcessorUtils;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.viewbinder.bind.Bind;
import se.snylt.witch.processor.viewbinder.bind.BindTargetMethod;
import se.snylt.witch.processor.viewbinder.getdata.GetData;
import se.snylt.witch.processor.viewbinder.getdata.GetNoData;
import se.snylt.witch.processor.viewbinder.getdata.GetTargetData;
import se.snylt.witch.processor.viewbinder.getview.GetView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.newinstance.NewViewBinderInstance;

import static se.snylt.witch.annotations.Bind.NO_ID;

public class ViewBinder {

    private TypeSpecModule newInstance;

    private final MethodSpecModule getView;

    private final MethodSpecModule setView;

    private final GetData getData;

    private final MethodSpecModule isDirty;

    private final MethodSpecModule bind;

    ViewBinder(
            TypeSpecModule newInstance,
            MethodSpecModule getView,
            MethodSpecModule setView,
            GetData getData,
            MethodSpecModule isDirty,
            MethodSpecModule bind) {
        this.newInstance = newInstance;
        this.getView = getView;
        this.setView = setView;
        this.getData = getData;
        this.isDirty = isDirty;
        this.bind = bind;
    }

    public TypeSpec newInstance() {
        return newInstance.builder()
                .addMethod(getView.create())
                .addMethod(setView.create())
                .addMethod(getData.create())
                .addMethod(isDirty.create())
                .addMethod(bind.create())
                .build();
    }

    public String getDataName() {
        return getData.getDataName();
    }

    public String getAccessData() {
        return getData.describeData();
    }

    public static class Builder {

        private int viewId;

        private String propertyName;

        private final Class<? extends Annotation> annotation;

        private GetView getView;

        private MethodSpecModule setView;

        private GetData getData;

        private Bind bind;

        private IsDirty isDirty;

        private IsDirty defaultDirty;

        private TypeName targetTypeName;

        private Element targetElement;

        private Element bindElement;

        public Builder(Class<? extends Annotation> annotation, Element targetElement, Element bindElement, TypeName targetTypeName) {
            this.annotation = annotation;
            this.targetElement = targetElement;
            this.bindElement = bindElement;
            this.targetTypeName = targetTypeName;
            isDirty = new IsDirtyIfNotEquals(targetTypeName);
            defaultDirty = isDirty;
        }

        public Builder setPropertyName(String propertyName) {
            this.propertyName = propertyName;
            return this;
        }

        public Builder setGetView(GetView getView) {
            this.getView = getView;
            return this;
        }

        public Builder setSetView(MethodSpecModule setView) {
            this.setView = setView;
            return this;
        }

        public Builder setGetData(GetData getData) {
            this.getData = getData;
            return this;
        }

        public Builder setIsDirty(IsDirty isDirty) {
            this.isDirty = isDirty;
            return this;
        }

        public Builder setBind(Bind bind) {
            this.bind = bind;
            return this;
        }

        public boolean isIsDirtySet() {
            return isDirty != null && isDirty != defaultDirty;
        }

        public IsDirty getIsDirty() {
            return isDirty;
        }

        public Bind getBind() {
            return bind;
        }

        public ViewBinder build() {
            return new ViewBinder(
                    newInstance(),
                    getView,
                    setView,
                    getData,
                    isDirty,
                    bind);
        }

        private TypeSpecModule newInstance() {
            return new NewViewBinderInstance(
                    viewId,
                    getView.getViewTypeName(),
                    getView.getViewHolderTypeName(),
                    targetTypeName,
                    bind.getDataTypeName()
            );
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof ViewBinder.Builder) {
                if (propertyName != null && ((Builder) obj).propertyName != null) {
                    return propertyName.equals(((Builder) obj).propertyName);
                }
            }

            if(obj instanceof String) {
                if (propertyName != null) {
                    return propertyName.equals(obj);
                }
            }
            return false;
        }

        public TypeName getTargetTypeName() {
            return targetTypeName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public Builder setViewId(int viewId) {
            this.viewId = viewId;
            return this;
        }

        public String getViewHolderViewName() {
            return String.format("_%d", viewId);
        }

        public void validate(TypeUtils typeUtils) throws WitchException {
            if (bind != null && bind instanceof BindTargetMethod) {

                BindTargetMethod bindTarget = (BindTargetMethod) bind;

                if (bindTarget.getBindMethod().hasDataParameter()) {
                    ProcessorUtils.BindMethod method = bindTarget.getBindMethod();

                    // Missing data
                    if(getData == null || getData instanceof GetNoData) {
                        throw WitchException.bindMethodMissingData(bindTarget.getElement(), method.getDataParameterName());
                    }

                    // Incompatible data types
                    if(getData instanceof GetTargetData) {
                        GetTargetData getTargetData = (GetTargetData) getData;
                        if (!typeUtils.types().isAssignable(getTargetData.getDataTypeMirror(), method.getDataTypeMirror())) {
                            throw WitchException.incompatibleDataTypes(getTargetData.getElement(), bindTarget.getElement());
                        }
                    }
                }

                // View but no id
                if (bindTarget.getBindMethod().hasViewParameter() && viewId == NO_ID) {
                    throw WitchException.bindMethodWithViewMissingId(bindElement);
                }

                // Id but no view
                if (!bindTarget.getBindMethod().hasViewParameter() && viewId != NO_ID) {
                    throw WitchException.bindMethodWithIdMissingView(bindElement);
                }
            }
        }

        public Element getTargetElement() {
            return targetElement;
        }

        public Element getBindElement() {
            return bindElement;
        }

        public GetView getGetView() {
            return getView;
        }

        public Class<? extends Annotation> getAnnotation() {
            return annotation;
        }
    }

}
