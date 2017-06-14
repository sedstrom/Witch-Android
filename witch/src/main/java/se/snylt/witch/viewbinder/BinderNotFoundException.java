package se.snylt.witch.viewbinder;

class BinderNotFoundException extends RuntimeException {
    BinderNotFoundException(String message) {
        super(message);
    }
}
