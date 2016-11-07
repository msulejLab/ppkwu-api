package pl.ppkwu.lab.impl;

import pl.ppkwu.lab.api.FileOperations;
import pl.ppkwu.lab.api.ReadCallback;
import pl.ppkwu.lab.api.WriteCallback;

import java.io.*;

public class FileOperationsImpl implements FileOperations {

    public static final int DELAY_SECS = 3;

    public void readFile(String fileName, ReadCallback callback) {
        BufferedReader reader = null;
        try {
            delay(DELAY_SECS);

            reader = new BufferedReader(new FileReader(new File(fileName)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            callback.onSuccess(sb.toString());
        } catch (FileNotFoundException e) {
            callback.onFailure("File was not found");
        } catch (IOException e) {
            callback.onFailure("Unexpected error while reading file");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeFile(String fileName, String content, WriteCallback callback) {
        FileWriter writer = null;
        try {
            delay(DELAY_SECS);

            writer = new FileWriter(new File(fileName));
            writer.write(content);

            callback.onSuccess();
        } catch (FileNotFoundException e) {
            callback.onFailure("Error while creating file");
        } catch (IOException e) {
            callback.onFailure("Unexpected error while writing to file");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void delay(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
