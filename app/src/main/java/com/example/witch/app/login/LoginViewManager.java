package com.example.witch.app.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class LoginViewManager extends Observable {

    private String password;

    private String email;

    class LoginState {
        private List<LoginManager.LoginError> errors = new ArrayList<>();
        private boolean isLoggingIn = false;
        private boolean isLoggedIn = false;
        private boolean loginFailed = false;

        List<LoginManager.LoginError> getErrors() {
            return errors;
        }

        boolean isLoggingIn() {
            return isLoggingIn;
        }

        boolean isLoggedIn() {
            return isLoggedIn;
        }

        boolean loginFailed() {
            return loginFailed;
        }

        boolean hasEmailErrors() {
            return this.errors.contains(LoginManager.LoginError.EMPTY_EMAIL) ||
                    this.errors.contains(LoginManager.LoginError.INVALID_EMAIL);
        }

        boolean hasPasswordErrors() {
            return this.errors.contains(LoginManager.LoginError.EMPTY_PASSWORD);
        }
    }

    private LoginState state = new LoginState();

    LoginViewManager() {
        onPasswordChanged(null);
        onEmailChanged(null);
    }

    LoginState getState() {
        return state;
    }

    private void setState(LoginState state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }

    void onPasswordChanged(String password) {
        this.password = password;
        validate();
    }

    void onEmailChanged(String email) {
        this.email = email;
        validate();
    }

    void login() {
        validate();
        if (!state.errors.isEmpty()) {
            state.loginFailed = true;
            setState(state);
        } else {
            state.loginFailed = false;
            this.state.isLoggingIn = true;
            setState(state);
            LoginManager.login(new LoginManager.LoginCallback() {
                @Override
                public void success() {
                    LoginViewManager.this.state.isLoggingIn = false;
                    LoginViewManager.this.state.isLoggedIn = true;
                    setState(state);
                }
            });
        }
    }

    private void validate() {
        this.state.errors = LoginManager.validate(email, password);
        this.state.loginFailed = state.loginFailed && !this.state.errors.isEmpty();
        setState(state);
    }

}
