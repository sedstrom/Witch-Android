package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.getbinder.GetBinder;
import se.snylt.witch.processor.viewbinder.getvalue.GetValue;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.newinstance.NewViewBinderInstance;

public class ViewBinder {

    private TypeSpecModule newInstance;

    private final MethodSpecModule getView;

    private final MethodSpecModule setView;

    private final GetValue getValue;

    private final MethodSpecModule getBinder;

    private final MethodSpecModule isDirty;

    ViewBinder(
            TypeSpecModule newInstance,
            MethodSpecModule getView,
            MethodSpecModule setView,
            GetValue getValue,
            MethodSpecModule getBinder,
            MethodSpecModule isDirty) {
        this.newInstance = newInstance;
        this.getView = getView;
        this.setView = setView;
        this.getValue = getValue;
        this.getBinder = getBinder;
        this.isDirty = isDirty;
    }

    public TypeSpec newInstance() {
        return newInstance.builder()
                .addMethod(getView.create())
                .addMethod(setView.create())
                .addMethod(getValue.create())
                .addMethod(getBinder.create())
                .addMethod(isDirty.create())
                .build();
    }

    public String getValueName() {
        return getValue.getValueAccessor().accessPropertyString();
    }

    public String getAccessValue() {
        return getValue.describeValue();
    }

    public static class Builder {

        private int viewId;

        private String propertyName;

        private GetViewHolderView getView;

        private MethodSpecModule setView;

        private GetValue getValue;

        private GetBinder getBinder;

        private MethodSpecModule isDirty;

        private TypeName targetTypeName;

        public Builder(TypeName targetTypeName) {
            this.targetTypeName = targetTypeName;
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

        public Builder setGetValue(GetValue getValue) {
            this.getValue = getValue;
            return this;
        }

        public Builder setGetBinder(GetBinder getBinder) {
            this.getBinder = getBinder;
            return this;
        }

        public Builder setIsDirty(MethodSpecModule isDirty) {
            this.isDirty = isDirty;
            return this;
        }

        public ViewBinder build() {
            return new ViewBinder(
                    newInstance(),
                    getView,
                    setView,
                    getValue,
                    getBinder,
                    isDirty);
        }

        private TypeSpecModule newInstance() {
            return new NewViewBinderInstance(
                viewId,
                    getView.getViewTypeName(),
                    getView.getViewHolderTypeName(),
                    targetTypeName,
                    getValue.getValueTypeName()
            );
        }

        public void addOnBind(OnBind onBind) {
            getBinder.addOnBind(onBind);
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
    }

}
