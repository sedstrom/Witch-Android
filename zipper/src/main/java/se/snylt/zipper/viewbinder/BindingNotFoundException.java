package se.snylt.zipper.viewbinder;

public class BindingNotFoundException extends RuntimeException {

    public BindingNotFoundException(Object target) {
        super(getErrorMessage(target));
    }

    private static String getErrorMessage(Object target) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Could not find binding for target: " + target.getClass().getName() + ".");
        return errorMessage.toString();
    }
}
