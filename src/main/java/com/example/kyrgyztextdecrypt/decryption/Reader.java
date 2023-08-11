package com.example.kyrgyztextdecrypt.decryption;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public String readFile(String fileName){
        StringBuilder str = new StringBuilder();


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line).append("\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }
}
