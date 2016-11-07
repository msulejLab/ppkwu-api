package pl.ppkwu.lab.impl;

import pl.ppkwu.lab.api.FileOperations;
import pl.ppkwu.lab.api.ReadCallback;
import pl.ppkwu.lab.api.WriteCallback;

import java.io.*;

public class FileOperationsImpl implements FileOperations {

    public static final int DELAY_SECS = 3;

    public void readFile(String fileName, ReadCallback callback) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            delay(DELAY_SECS);

            callback.onSuccess(sb.toString());
        } catch (FileNotFoundException e) {
            callback.onFailure("File was not found");
        } catch (IOException e) {
            callback.onFailure("Unexpected error while reading file");
        }
    }

    public void writeFile(String fileName, String content, WriteCallback callback) {

    }

    private void delay(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
