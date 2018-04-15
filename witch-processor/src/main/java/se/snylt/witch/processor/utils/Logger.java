package se.snylt.witch.processor.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

import se.snylt.witch.processor.WitchException;

public class Logger {

    private final Messager messager;

    public Logger(Messager messager) {
        this.messager = messager;
    }

    public void logError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    public void log(WitchException e) {
        logError(e.getMessage());
    }

    public void logWarn(String message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message);
    }
}
