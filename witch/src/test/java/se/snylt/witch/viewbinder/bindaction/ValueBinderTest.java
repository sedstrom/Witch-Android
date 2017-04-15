package se.snylt.witch.viewbinder.bindaction;

import org.junit.Test;

import android.widget.TextView;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValueBinderTest {

    @Test
    public void test() {

        Binder<TextView, String> binder = Binder.create(new SyncOnBind<TextView, String>() {
            @Override
            public void onBind(TextView textView, String s) {
                textView.setText(s);
            }
        }).next(new AsyncOnBind<TextView, String>() {
            @Override
            public void onBind(TextView textView, String s, OnBindListener onBindDone) {
                textView.append("B");
                onBindDone.onBindDone();
            }
        }).next(new AsyncOnBind<TextView, String>() {
            @Override
            public void onBind(TextView textView, String s, OnBindListener onBindDone) {
                textView.append("C");
                onBindDone.onBindDone();
            }
        });

        ValueBinder<TextView, String> valueBinder = ValueBinder.create("A", binder);

        // Bind
        TextView view = mock(TextView.class);
        String value = valueBinder.getValue();
        valueBinder.getBinder().bind(view, value);

        verify(view).setText(eq("A"));
        verify(view).append(eq("B"));
        verify(view).append(eq("C"));
    }
}