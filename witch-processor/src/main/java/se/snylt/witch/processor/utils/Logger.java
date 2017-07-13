package se.snylt.witch.processor.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class Logger {

    private final Messager messager;

    public Logger(Messager messager) {
        this.messager = messager;
    }

    public void logNote(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    public void logManWarn(String message) {
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, message);
    }

    public void logWarn(String message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message);
    }

    public String logError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
        return message;
    }
}
