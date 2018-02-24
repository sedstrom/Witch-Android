package com.example.witch.api;

public class Result<ResultData> {

    public final ResultData value;

    public final String error;

    Result(ResultData value, String error) {
        this.value = value;
        this.error = error;
    }

    public static <Data> Result<Data> value(Data value) {
        return new Result<>(value, null);
    }

    public static <Data> Result<Data> error(String message) {
        return new Result<>(null, message);
    }

}
