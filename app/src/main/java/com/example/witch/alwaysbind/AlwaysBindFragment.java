package com.example.witch.alwaysbind;

import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import se.snylt.witch.viewbinder.Witch;

public class AlwaysBindFragment extends Fragment {

    private EditText editText;

    private SwitchCompat switchCompat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.always_bind_fragment, container, false);

        switchCompat = (SwitchCompat) view.findViewById(R.id.always_bind_fragment_switch);

        editText = (EditText) view.findViewById(R.id.always_bind_fragment_edit_text);
        view.findViewById(R.id.always_bind_fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editText.getText().toString();
                try {
                    Integer number = Integer.parseInt(value);
                    bind(number);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Please type a number between 0 and 100", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void bind(Integer number) {
        if(switchCompat.isChecked()) {
            Witch.bind(new AlwaysBindViewModel(number), getView());
        } else {
            Witch.bind(new NoAlwaysBindViewModel(number), getView());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
