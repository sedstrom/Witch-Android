package com.example.witch.value;

import com.example.witch.R;
import com.example.witch.utils.TextWatcherAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

public class ValueFragment extends Fragment {

    @BindTo(R.id.value_view_fragment_input)
    ValueBinder<EditText, TextWatcher> textWatcher = ValueBinder.create(new TextWatcherAdapter() {

        @Override
        public void afterTextChanged(Editable s) {
            // Use update to flag value as dirty
            text.update().setText(s.toString());
            bind();
        }
    }, Binder.create(new SyncOnBind<EditText, TextWatcher>() {
        @Override
        public void onBind(EditText editText, TextWatcher textWatcher) {
            editText.addTextChangedListener(textWatcher);
        }
    }));

    @BindTo(R.id.value_view_fragment_text)
    final ValueBinder<TextView, Model> text = ValueBinder.create(new Model(),
            Binder.create(new SyncOnBind<TextView, Model>() {
                @Override
                public void onBind(TextView textView, Model m) {
                    textView.setText(m.getText());
                }
            }));

    private void bind() {
        Witch.bind(this, getView());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.value_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    static class Model {

        private String text = "";

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

