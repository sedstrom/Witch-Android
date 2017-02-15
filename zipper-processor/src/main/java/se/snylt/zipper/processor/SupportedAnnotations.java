package se.snylt.zipper.processor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

public class SupportedAnnotations {

    public interface HasViewId {
        Integer getViewId(Element element);
        Class<? extends Annotation> getClazz();
    }

    // BindTo
    public final static class BindTo implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindTo";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindTo.class).value();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindTo.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToView
    public final static class BindToView implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToView.class).id();
        }

        @Override
        public Class getClazz() {
            return  se.snylt.zipper.annotations.BindToView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToEditText
    public final static class BindToEditText implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToEditText";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToEditText.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindToEditText.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToTextView
    public final static class BindToTextView implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToTextView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToTextView.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindToTextView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToImageView
    public final static class BindToImageView implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToImageView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToImageView.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindToImageView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToCompoundButton
    public final static class BindToCompoundButton implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToCompoundButton";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToCompoundButton.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindToCompoundButton.class;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    // BindToRecyclerView
    public final static class BindToRecyclerView implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToRecyclerView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToRecyclerView.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindToRecyclerView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToViewPager
    public final static class BindToViewPager implements HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToViewPager";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToViewPager.class).id();
        }

        @Override
        public Class getClazz() {
            return se.snylt.zipper.annotations.BindToViewPager.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Mod
    public final static class Mod {
        public final static String name = "se.snylt.zipper.annotations.Mod";
    }

    // OnBind
    public final static class OnBind {
        public final static String name = "se.snylt.zipper.annotations.OnBind";
    }

    public final static class OnBindEach {
        public final static String name = "se.snylt.zipper.annotations.OnBindEach";
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
