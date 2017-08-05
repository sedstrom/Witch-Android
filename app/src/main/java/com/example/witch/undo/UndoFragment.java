package com.example.witch.undo;

import com.example.witch.R;
import com.example.witch.utils.TextWatcherAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Stack;

import se.snylt.witch.viewbinder.Witch;

public class UndoFragment extends Fragment {

    private final static int HISTORY_SIZE = 10;

    private Stack<UndoViewModel> models = new Stack<UndoViewModel>(){
        private static final long serialVersionUID = 1L;
        public UndoViewModel push(UndoViewModel item) {
            if (this.size() == HISTORY_SIZE) {
                this.removeElementAt(0);
            }
            return super.push(item);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.undo_fragment, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.undo_fragment_add_edittext);
        editText.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                String text = editable.toString();
                if(!text.isEmpty() && text.charAt(text.length()-1) == 32) {
                    addTodo(text);
                }
            }
        });

        // Undo
        view.findViewById(R.id.undo_fragment_undo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo();
            }
        });

        return view;
    }

    private void addTodo(String text) {
        if(!text.equals(models.peek().text)) {
            models.push(new UndoViewModel(text));
            Log.d("TAG", "addTodo");
        }
        bind();
    }

    private void undo(){
        if(models.size() > 1) {
            models.pop();
        }
        bind();
    }

    private void bind(){
        Witch.bind(models.peek(), getView());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        models.add(new UndoViewModel("", false));
        bind();
    }
}
