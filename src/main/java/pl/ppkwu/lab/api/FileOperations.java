package pl.ppkwu.lab.api;

public interface FileOperations {

    void readFile(String fileName, ReadCallback callback);

    void writeFile(String fileName, String content, WriteCallback callback);
}
