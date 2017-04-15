package se.snylt.witch.processor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

class SupportedAnnotations {

    interface HasViewId {
        Integer getViewId(Element element);
        Class<? extends Annotation> getClazz();
    }

    // BindTo
    public final static class BindTo implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindTo";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindTo.class).value();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindTo.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToView
    public final static class BindToView implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToView.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return  se.snylt.witch.annotations.BindToView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToEditText
    final static class BindToEditText implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToEditText";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToEditText.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindToEditText.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToTextView
    public final static class BindToTextView implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToTextView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToTextView.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindToTextView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToImageView
    final static class BindToImageView implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToImageView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToImageView.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindToImageView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToCompoundButton
    final static class BindToCompoundButton implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToCompoundButton";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToCompoundButton.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindToCompoundButton.class;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    // BindToRecyclerView
    final static class BindToRecyclerView implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToRecyclerView";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToRecyclerView.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindToRecyclerView.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // BindToViewPager
    final static class BindToViewPager implements HasViewId {
        final static String name = "se.snylt.witch.annotations.BindToViewPager";

        @Override
        public Integer getViewId(Element element) {
            return element.getAnnotation(se.snylt.witch.annotations.BindToViewPager.class).id();
        }

        @Override
        public Class<? extends Annotation> getClazz() {
            return se.snylt.witch.annotations.BindToViewPager.class;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Mod
    @Deprecated
    final static class Mod {
        final static String name = "se.snylt.witch.annotations.Mod";
    }

    // OnBind
    @Deprecated
    public final static class OnBind {
        final static String name = "se.snylt.witch.annotations.OnBind";
    }

    // AlwaysBind
    final static class AlwaysBind {
        final static String name = "se.snylt.witch.annotations.AlwaysBind";
    }

    @Deprecated
    final static class OnBindEach {
        final static String name = "se.snylt.witch.annotations.OnBindEach";
    }


    // All annotations that bind to a view id
    final static HasViewId[] ALL_BIND_VIEW = new HasViewId[] {
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
