package se.snylt.witch.processor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

public class SupportedAnnotations {

    public interface HasViewId {
        Integer getViewId(Element element);
        Class<? extends Annotation> getClazz();
    }

    // BindTo
    public final static class BindTo implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindTo";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindTo.class).value();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindTo.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToView
    public final static class BindToView implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToView.class).id();
        }

        @Override
        public Class getClazz() {
            return  se.snylt.witch.annotations.BindToView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToEditText
    public final static class BindToEditText implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToEditText";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToEditText.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindToEditText.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToTextView
    public final static class BindToTextView implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToTextView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToTextView.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindToTextView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToImageView
    public final static class BindToImageView implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToImageView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToImageView.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindToImageView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToCompoundButton
    public final static class BindToCompoundButton implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToCompoundButton";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToCompoundButton.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindToCompoundButton.class;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    // BindToRecyclerView
    public final static class BindToRecyclerView implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToRecyclerView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToRecyclerView.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindToRecyclerView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToViewPager
    public final static class BindToViewPager implements HasViewId {
        public final static String name = "se.snylt.witch.annotations.BindToViewPager";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToViewPager.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.witch.annotations.BindToViewPager.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Mod
    public final static class Mod {
        public final static String name = "se.snylt.witch.annotations.Mod";
    }

    // OnBind
    public final static class OnBind {
        public final static String name = "se.snylt.witch.annotations.OnBind";
    }

    // AlwaysBind
    public final static class AlwaysBind {
        public final static String name = "se.snylt.witch.annotations.AlwaysBind";
    }

    public final static class OnBindEach {
        public final static String name = "se.snylt.witch.annotations.OnBindEach";
    }


    // All annotations that bind to a view id
    public final static HasViewId[] ALL_BIND_VIEW = new HasViewId[] {
            new BindTo(),
            new BindToView(),
            new BindToRecyclerView(),
            new BindToCompoundButton(),
            new BindToEditText(),
            new BindToTextView(),
            new BindToImageView(),
            new BindToViewPager()
    };


}
