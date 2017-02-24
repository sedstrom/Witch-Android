package se.snylt.zipper.viewbinder;

import se.snylt.zipper.ClassUtils;

public class ViewHolderFactory {

    /**
     * Creates a view holder from annotated target view model
     * @param target the view model
     * @return view holder for views defined in target
     */
    public Object createViewHolder(Object target) {
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ViewHolderNotFoundException("Could not find view holder for " + target.getClass().getName());
        }
    }
}
