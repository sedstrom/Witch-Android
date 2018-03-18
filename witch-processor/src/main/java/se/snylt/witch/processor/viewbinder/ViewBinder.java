package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;

import se.snylt.witch.processor.WitchException;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.viewbinder.bind.Bind;
import se.snylt.witch.processor.viewbinder.bind.BindTargetMethod;
import se.snylt.witch.processor.viewbinder.getdata.GetData;
import se.snylt.witch.processor.viewbinder.getdata.GetTargetData;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.newinstance.NewViewBinderInstance;

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

    public String getAccessValue() {
        return getData.describeData();
    }

    public static class Builder {

        private int viewId;

        private String propertyName;

        private GetViewHolderView getView;

        private MethodSpecModule setView;

        private GetData getData;

        private Bind bind;

        private IsDirty isDirty;

        private IsDirty defaultDirty;

        private TypeName targetTypeName;

        private Element target;

        public Builder(Element target, TypeName targetTypeName) {
            this.target = target;
            this.targetTypeName = targetTypeName;
            isDirty = new IsDirtyIfNotEquals(targetTypeName);
            defaultDirty = isDirty;
        }

        public Builder setPropertyName(String propertyName) {
            this.propertyName = propertyName;
            return this;
        }

        public Builder setGetView(GetViewHolderView getView) {
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

        public ViewBinder build() throws WitchException {
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

        public void validate(TypeUtils typeUtils) throws WitchException {
            // Incompatible data types
            if (getData != null && getData instanceof GetTargetData && bind != null && bind instanceof BindTargetMethod) {
                GetTargetData getTargetData = (GetTargetData) getData;
                BindTargetMethod bindTarget = (BindTargetMethod) bind;
                if (!typeUtils.types().isAssignable(getTargetData.getDataTypeMirror(), bindTarget.getDataTypeMirror())) {
                    throw WitchException.incompatibleDataTypes(getTargetData.getElement(), bindTarget.getElement());
                }
            }
        }

        public Element getTarget() {
            return target;
        }

        public GetViewHolderView getGetView() {
            return getView;
        }
    }

}
