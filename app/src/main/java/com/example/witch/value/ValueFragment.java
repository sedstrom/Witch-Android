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
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

public class ValueFragment extends Fragment {

    private String text = "";

    @BindTo(R.id.value_view_fragment_input)
    TextWatcher textWatcher = new TextWatcherAdapter() {

        @Override
        public void afterTextChanged(Editable s) {
            // Use update to flag value as dirty
            text = s.toString();
            bind();
        }
    };

    @Binds
    Binder bindTextWatcher = Binder.create(new SyncOnBind<EditText, TextWatcher>() {
        @Override
        public void onBind(EditText editText, TextWatcher textWatcher) {
            editText.addTextChangedListener(textWatcher);
        }
    });

    @BindTo(R.id.value_view_fragment_text)
    final String text() {
        return text;
    }

    @Binds
    final Binder<TextView, String> bindText() {
        return Binder.create(new SyncOnBind<TextView, String>() {
            @Override
            public void onBind(TextView textView, String text) {
                textView.setText(text);
            }
        });
    }

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
}

