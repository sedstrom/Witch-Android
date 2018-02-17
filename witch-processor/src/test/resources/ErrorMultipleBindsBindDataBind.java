
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;

import android.view.View;

class ErrorMultipleBindsBindDataBind {

    @Bind
    @BindData(id = 0, view = View.class, set="text")
    void bind() {

    }
}
