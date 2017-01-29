package se.snylt.zipper.processor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

public class SupportedAnnotations {

    public abstract static class HasViewId {
        abstract Integer getViewId(Element element);
        abstract Class<? extends Annotation> getClazz();
    }

    // BindTo
    public final static class BindTo extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindTo";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindTo.class).value();
        }

        @Override
        Class getClazz() {
            return se.snylt.zipper.annotations.BindTo.class;
        }
    }

    // BindToView
    public final static class BindToView extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToView";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToView.class).id();
        }

        @Override
        Class getClazz() {
            return  se.snylt.zipper.annotations.BindToView.class;
        }
    }

    // BindToEditText
    public final static class BindToEditText extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToEditText";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToEditText.class).id();
        }

        @Override
        Class getClazz() {
            return se.snylt.zipper.annotations.BindToEditText.class;
        }
    }

    // BindToTextView
    public final static class BindToTextView extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToTextView";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToTextView.class).id();
        }

        @Override
        Class getClazz() {
            return se.snylt.zipper.annotations.BindToTextView.class;
        }
    }

    // BindToImageView
    public final static class BindToImageView extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToImageView";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToImageView.class).id();
        }

        @Override
        Class getClazz() {
            return se.snylt.zipper.annotations.BindToImageView.class;
        }
    }

    // BindToCompoundButton
    public final static class BindToCompoundButton extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToCompoundButton";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToCompoundButton.class).id();
        }

        @Override
        Class getClazz() {
            return se.snylt.zipper.annotations.BindToCompoundButton.class;
        }

    }

    // BindToRecyclerView
    public final static class BindToRecyclerView extends HasViewId {
        public final static String name = "se.snylt.zipper.annotations.BindToRecyclerView";

        @Override
        Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.zipper.annotations.BindToRecyclerView.class).id();
        }

        @Override
        Class getClazz() {
            return se.snylt.zipper.annotations.BindToRecyclerView.class;
        }
    }

    public final static class OnBind {
        public final static String name = "se.snylt.zipper.annotations.OnBind";
    }

    // All annotations that bind to a view id
    public final static HasViewId[] ALL_BIND_VIEW = new HasViewId[] {
            new BindTo(),
            new BindToView(),
            new BindToRecyclerView(),
            new BindToCompoundButton(),
            new BindToEditText(),
            new BindToTextView(),
            new BindToImageView()
    };

}
