package com.example.witch.login;

import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import se.snylt.witch.viewbinder.Witch;

public class LoginFragment extends Fragment {

    private final LoginViewModel model = new LoginViewModel("snylt", "password");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.login_fragment, container, false);

        ((EditText)view.findViewById(R.id.login_fragment_password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setPassword(editable.toString());
            }
        });

        ((EditText)view.findViewById(R.id.login_fragment_username)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setUsername(editable.toString());
            }
        });

        view.findViewById(R.id.login_fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setLoggingIn(true);
                bind();

                Login.login(model.getUsername(), model.getPassword(), new Login.LoginListener(){

                    @Override
                    public void loginSuccessful() {
                        // Yey
                        model.setIsError(false);
                        model.setLoggingIn(false);
                        model.setSuccessMessage("Yey! Logged in!");
                        bind();
                        model.setSuccessMessage(null); // Make @AlwaysBind not show message on each bind.
                    }

                    @Override
                    public void loginFailed() {
                        model.setIsError(true);
                        model.setLoggingIn(false);
                        bind();
                    }
                });
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    private void bind(Object ...mods){
        if(isAdded()){
            Witch.bind(model, getView(), mods);
        }
    }
}
