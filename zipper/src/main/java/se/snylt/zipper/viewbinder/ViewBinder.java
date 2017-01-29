package se.snylt.zipper.viewbinder;

import android.view.View;

import java.util.List;

import se.snylt.zipper.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBind;
import se.snylt.zipper.viewbinder.bindaction.OnPostBind;
import se.snylt.zipper.viewbinder.bindaction.OnPreBind;

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
            if (action instanceof OnPreBind) {
                ((OnPreBind) action).onPreBind(view, value);
            }
            if (action instanceof OnBind) {
                ((OnBind) action).onBind(view, value);
            }
            if (action instanceof OnPostBind) {
                ((OnPostBind) action).onPostBind(view, value);
            }
        }
    }

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);
}
