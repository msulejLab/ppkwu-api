package pl.ppkwu.lab.api;

public interface WriteCallback {

    void onSuccess();

    void onFailure(String errorMessage);
}
