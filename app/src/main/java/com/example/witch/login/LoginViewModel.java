package com.example.witch.login;

import com.example.witch.R;
import com.example.witch.utils.ToastShort;

import android.view.View;

import java.util.PriorityQueue;

import se.snylt.witch.annotations.AlwaysBind;
import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToEditText;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.annotations.OnBind;

class LoginViewModel {

    private String password;

    private String username;

    private PriorityQueue<String> message = new PriorityQueue<>(1);

    private boolean isError = false;

    private boolean isLoggingIn = false;

    LoginViewModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @BindToEditText(id = R.id.login_fragment_password)
    String password() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    @BindToEditText(id = R.id.login_fragment_username)
    String username() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setIsError(boolean isError) {
        this.isError = isError;
    }

    @BindTo(R.id.login_fragment_password)
    @OnBind(EditTextValidOnBind.class)
    boolean passwordValid() {
        return !isError;
    }

    @BindToView(id = R.id.login_fragment_button, view = View.class, set = "enabled")
    Boolean loginButtonEnabled() {
        return isLoggingIn ? false : true;
    }

    @BindTo(R.id.login_fragment_username)
    @OnBind(EditTextValidOnBind.class)
    boolean usernameValid() {
        return !isError;
    }

    @BindToView(id = R.id.login_fragment_username, view = View.class, set = "enabled")
    Boolean usernameEnabled() {
        return !isLoggingIn;
    }

    @BindToView(id = R.id.login_fragment_password, view = View.class, set = "enabled")
    Boolean passwordEnabled() {
        return !isLoggingIn;
    }

    @BindToView(id = R.id.login_fragment_progress_view, view = View.class, set = "visibility")
    Integer progressVisible() {
        return isLoggingIn ? View.VISIBLE : View.INVISIBLE;
    }

    void setLoggingIn(boolean isLoggingIn) {
        this.isLoggingIn = isLoggingIn;
    }

    @BindTo(R.id.login_fragment_container)
    @OnBind(ToastShort.class)
    @AlwaysBind
    String message() {
        return message.isEmpty() ? null : message.poll();
    }

    void setMessage(String alert) {
        this.message.add(alert);
    }
}
