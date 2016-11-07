package pl.ppkwu.lab.api;

public interface ReadCallback {

    void onSuccess(String content);

    void onFailure(String errorMessage);
}
