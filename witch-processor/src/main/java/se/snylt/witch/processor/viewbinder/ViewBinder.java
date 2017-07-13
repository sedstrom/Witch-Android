package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.getbinder.GetBinder;

public class ViewBinder {

    private TypeSpecModule newInstance;

    private final MethodSpecModule getView;

    private final MethodSpecModule setView;

    private final MethodSpecModule getValue;

    private final MethodSpecModule getBinder;

    private final MethodSpecModule isDirty;

    ViewBinder(
            TypeSpecModule newInstance,
            MethodSpecModule getView,
            MethodSpecModule setView,
            MethodSpecModule getValue,
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

    public static class Builder {

        private TypeSpecModule newInstance;

        private MethodSpecModule getView;

        private MethodSpecModule setView;

        private MethodSpecModule getValue;

        private GetBinder getBinder;

        private MethodSpecModule isDirty;

        private PropertyAccessor valueAccessor;

        private TypeName targetTypeName;

        public Builder(TypeName targetTypeName) {
            this.targetTypeName = targetTypeName;
        }

        public Builder setNewInstance(TypeSpecModule newInstance) {
            this.newInstance = newInstance;
            return this;
        }

        public Builder setValueAccessor(PropertyAccessor valueAccessor) {
            this.valueAccessor = valueAccessor;
            return this;
        }

        public Builder setGetView(MethodSpecModule getView) {
            this.getView = getView;
            return this;
        }

        public Builder setSetView(MethodSpecModule setView) {
            this.setView = setView;
            return this;
        }

        public Builder setGetValue(MethodSpecModule getValue) {
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

        public PropertyAccessor getValueAccessor() {
            return valueAccessor;
        }

        public ViewBinder build() {
            return new ViewBinder(
                    newInstance,
                    getView,
                    setView,
                    getValue,
                    getBinder,
                    isDirty);
        }

        public void addOnBind(OnBind onBind) {
            getBinder.addOnBind(onBind);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof ViewBinder.Builder) {
                return ((ViewBinder.Builder) obj).getValueAccessor().equals(getValueAccessor());
            }
            if(obj instanceof PropertyAccessor) {
                return obj.equals(getValueAccessor());
            }
            return super.equals(obj);
        }

        public TypeName getTargetTypeName() {
            return targetTypeName;
        }
    }

}
