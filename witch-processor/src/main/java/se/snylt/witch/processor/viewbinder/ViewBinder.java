package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.processor.WitchException;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.viewbinder.getbinder.GetBinder;
import se.snylt.witch.processor.viewbinder.getbinder.GetTargetMethodBinder;
import se.snylt.witch.processor.viewbinder.getdata.GetData;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.newinstance.NewViewBinderInstance;

public class ViewBinder {

    private TypeSpecModule newInstance;

    private final MethodSpecModule getView;

    private final MethodSpecModule setView;

    private final GetData getData;

    private final MethodSpecModule getBinder;

    private final MethodSpecModule isDirty;

    ViewBinder(
            TypeSpecModule newInstance,
            MethodSpecModule getView,
            MethodSpecModule setView,
            GetData getData,
            MethodSpecModule getBinder,
            MethodSpecModule isDirty) {
        this.newInstance = newInstance;
        this.getView = getView;
        this.setView = setView;
        this.getData = getData;
        this.getBinder = getBinder;
        this.isDirty = isDirty;
    }

    public TypeSpec newInstance() {
        return newInstance.builder()
                .addMethod(getView.create())
                .addMethod(setView.create())
                .addMethod(getData.create())
                .addMethod(getBinder.create())
                .addMethod(isDirty.create())
                .build();
    }

    public String getValueName() {
        return getData.getValueAccessor().accessPropertyString();
    }

    public String getAccessValue() {
        return getData.describeValue();
    }

    public static class Builder {

        private int viewId;

        private String propertyName;

        private GetViewHolderView getView;

        private MethodSpecModule setView;

        private GetData getData;

        private GetBinder getBinder;

        private IsDirty isDirty;

        private IsDirty defaultDirty;

        private TypeName targetTypeName;

        public Builder(TypeName targetTypeName) {
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

        public Builder setGetBinder(GetBinder getBinder) {
            this.getBinder = getBinder;
            return this;
        }

        public Builder setIsDirty(IsDirty isDirty) {
            this.isDirty = isDirty;
            return this;
        }

        public boolean isDirtySet() {
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
                    getBinder,
                    isDirty);
        }

        private TypeSpecModule newInstance() {
            return new NewViewBinderInstance(
                viewId,
                    getView.getViewTypeName(),
                    getView.getViewHolderTypeName(),
                    targetTypeName,
                    getData.getDataTypeName()
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
            if (getData != null && getBinder != null && getBinder instanceof GetTargetMethodBinder) {
                if (!typeUtils.types().isAssignable(getData.getDataTypeMirror(), getBinder.getDataTypeMirror())) {
                    throw WitchException.incompatibleDataTypes(getData.getElement(), getBinder.getElement());
                }
            }
        }
    }

}
