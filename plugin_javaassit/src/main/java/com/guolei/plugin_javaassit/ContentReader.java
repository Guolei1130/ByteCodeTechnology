package com.guolei.plugin_javaassit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ContentReader {

    public String read() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/replace.java")));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine()).append("\n");
        }
        return stringBuilder.toString();
    }

}
