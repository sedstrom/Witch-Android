package se.snylt.zipper.viewbinder;

import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;

public abstract class ViewBinder {

    public final int viewId;

    public final String key;

    public final List<BindAction> bindActions;

    public ViewBinder(int viewId, String key, List<BindAction> bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = bindActions;
    }

    public void doBind(View view, Object value) {
        for(BindAction action: bindActions) {
            if (action instanceof OnPreBindAction) {
                ((OnPreBindAction) action).onPreBind(view, value);
            }
            if (action instanceof OnBindAction) {
                ((OnBindAction) action).onBind(view, value);
            }
            if (action instanceof OnPostBindAction) {
                ((OnPostBindAction) action).onPostBind(view, value);
            }
        }
    }

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);
}
