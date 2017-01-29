package se.snylt.zipper.processor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

public class SupportedAnnotations {


    public abstract static class ViewId {
        abstract Integer getViewId(Element element);
        abstract Class<? extends Annotation> getClazz();
    }

    public final static class BindTo extends ViewId {
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

    public final static class BindToView extends ViewId{
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

    public final static class BindToEditText extends ViewId{
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

    public final static class BindToTextView extends ViewId{
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

    public final static class BindToImageView extends ViewId {
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

    public final static class BindToCompoundButton extends ViewId {
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

    public final static class BindToRecyclerView extends ViewId {
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


    // All supported annotations as string
    public final static String[] ALL_BINDINGS_STRINGS = new String[]{
            BindTo.name,
            BindToView.name,
            BindToRecyclerView.name,
            BindToCompoundButton.name,
            BindToTextView.name,
            BindToEditText.name,
            BindToImageView.name,
            OnBind.name
    };

    // All annotations that bind to a view id
    public final static ViewId[] ALL_BIND_VIEW = new ViewId[] {
            new BindTo(),
            new BindToView(),
            new BindToRecyclerView(),
            new BindToCompoundButton(),
            new BindToEditText(),
            new BindToTextView(),
            new BindToImageView()
    };

}
