package com.example.witch.app.login;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.witch.R;
import com.example.witch.app.TextWatcherAdapter;
import com.example.witch.app.witch.WitchBinder;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.Data;
import se.snylt.witchcore.viewfinder.ViewFinder;

class LoginInputBinder extends WitchBinder {

    private LoginState loginState;

    LoginInputBinder(ViewFinder viewFinder) {
        super(viewFinder);
    }

    void setLoginState(LoginState loginState) {
        this.loginState = loginState; bind();
    }

    @BindData(id= R.id.loginbutton, view = View.class, set = "onClickListener")
    View.OnClickListener loginButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginState.doLogin();
        }
    };

    @Data
    final TextWatcherAdapter passwordChanged = new TextWatcherAdapter() {

        @Override
        public void afterTextChanged(Editable s) {
            loginState.setPassword(s.toString());
        }
    };

    @Bind(id = R.id.password)
    void passwordChanged(EditText et, TextWatcher watcher) {
        et.addTextChangedListener(watcher);
    }

    @Data
    final TextWatcherAdapter emailChanged = new TextWatcherAdapter() {

        @Override
        public void afterTextChanged(Editable s) {
            loginState.setEmailAddress(s.toString());
        }
    };

    @Bind(id = R.id.emailAddress)
    void emailChanged(EditText et, TextWatcher watcher) {
        et.addTextChangedListener(watcher);
    }
}
