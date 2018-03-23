package se.snylt.witch.processor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

class SupportedAnnotations {

    interface HasViewId {
        Integer getViewId(Element element);
        Class<? extends Annotation> getClazz();
    }

    // BindTargetMethod
    public final static class BindData implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindTargetMethod";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindData.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindData.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Bind
    final static class Bind implements HasViewId {
        final static String name = "se.snylt.witch.annotations.Bind";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.Bind.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.Bind.class;
        }
    }

    // Data
    final static class Data {
        final static String name = "se.snylt.witch.annotations.Data";
    }

    // BindWhen
    final static class BindWhen {
        final static String name = "se.snylt.witch.annotations.BindWhen";
    }

    // BindNull
    final static class BindNull {
        final static String name = "se.snylt.witch.annotations.BindNull";
    }

    // All annotations that bind to a view id
    final static HasViewId[] ALL_BIND_VIEW = new HasViewId[] {
            new BindData(),
            new Bind()
    };
    
}
