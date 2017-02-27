package se.snylt.zipper.viewbinder;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

public abstract class ViewBinder {

    public final int viewId;

    public final String key;

    public final BindActions bindActions;

    private Object historyValue = BindActionsRunner.NO_HISTORY;

    public ViewBinder(int viewId, String key, BindActions bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = bindActions;
    }

    public void doBind(View view, Object value, Object ...mods) {
        BindActions actions = this.bindActions.applyMods(this, mods);
        BindActionsRunner.runner().bind(actions, view, value);
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target, Object ...mods) {
        Object value = getValue(target);
        if(isAlwaysBind() || BindActionsRunner.runner().isNewValue(value, historyValue)) {
            historyValue = value;
            View view = findView(viewHolder, viewFinder);
            doBind(view, value, mods);
        }
    }

    private View findView(Object viewHolder, ViewFinder viewFinder) {
        if (getView(viewHolder) == null) {
            View view = viewFinder.findViewById(viewId);
            setView(viewHolder, view);
            return view;
        }
        return (View) getView(viewHolder);
    }

    public abstract BindAction[] getModActions(Object mod);

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);

    public abstract boolean isAlwaysBind();
}
