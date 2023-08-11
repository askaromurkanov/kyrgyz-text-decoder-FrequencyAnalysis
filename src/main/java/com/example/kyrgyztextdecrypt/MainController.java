package com.example.kyrgyztextdecrypt;

import com.example.kyrgyztextdecrypt.decryption.Alphabet;
import com.example.kyrgyztextdecrypt.decryption.Decrypter;
import com.example.kyrgyztextdecrypt.decryption.FrequencyAnalysis;
import com.example.kyrgyztextdecrypt.decryption.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainController {

    private final Decrypter decrypter;
    private FrequencyAnalysis frequencyAnalysis;

    public MainController(){
        decrypter = new Decrypter();
        frequencyAnalysis = new FrequencyAnalysis();
    }

    @FXML
    private Button decryptButton;

    @FXML
    private TextArea inputText;

    @FXML
    private HBox lettersLayout;

    @FXML
    private Pane ji;

    @FXML
    private TextArea outputText;

    private boolean isAlreadyDecrypted = false;

    private String previousInput = "";

    @FXML
    void initialize() {

    }

    @FXML
    void d(){
        Reader reader = new Reader();

        String input = inputText.getText();

        frequencyAnalysis.getFrequency(input);

        outputText.setText(Decrypter.decryptText(input, frequencyAnalysis.getFrequency(reader.readFile("data/manas.txt")),10));
    }

    @FXML
    void decrypt() {
        String input = inputText.getText();

        if (!isAlreadyDecrypted){
            decrypter.analyze(input,"data/data.txt");

            String res = decrypter.decrypt(input);
            outputText.setText(res);

            setData();
            isAlreadyDecrypted = true;
        }

//        else if(isAlreadyDecrypted && input.equals(previousInput)){
//            lettersLayout.getChildren().clear();
//            outputText.clear();
//
//            decrypter.analyze(outputText.getText(),"data/manas.txt");
//
//            String res = decrypter.decrypt(outputText.getText());
//
//            System.out.println(res);
//            outputText.setText(res);
//
//            setData();
//            isAlreadyDecrypted = true;
//            previousInput = input;
//        }

        else if(isAlreadyDecrypted && !input.equals(previousInput)){
            lettersLayout.getChildren().clear();
            outputText.clear();

            decrypter.analyze(input,"data/data.txt");

            String res = decrypter.decrypt(input);
            outputText.setText(res);

            setData();
            isAlreadyDecrypted = true;
            previousInput = input;
        }
        else {
            lettersLayout.getChildren().clear();
            outputText.clear();

            String res = decrypter.decrypt(input);
            outputText.setText(res);

            setData();
        }
    }

    public void setData() {
        Map<Character, Character> correspondsMap = decrypter.getCorrespondsMap();

        Map<Character, Character> updatedMap = new TreeMap<>(correspondsMap);

        for (Map.Entry<Character, Character> entry : correspondsMap.entrySet()) {
            Label label = new Label(entry.getKey().toString() + ":");
            TextField textField = new TextField(entry.getValue().toString());

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                updatedMap.put(entry.getKey(), newValue.charAt(0));
//                updatedMap.put(updatedMap.get(entry.getKey()), updatedMap.get(newValue.charAt(0)));

                Map.Entry<Character, Character> entry1 = getKeyByValue(correspondsMap, newValue.charAt(0));
                updatedMap.put(entry1.getKey(), oldValue.charAt(0));

                decrypter.setCorrespondsMap(updatedMap);
                decrypt();
            });

            lettersLayout.getChildren().add(new VBox(label, textField));
        }
    }

    public Map.Entry<Character, Character> getKeyByValue(Map<Character, Character> map, Character value) {
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
        if (value.equals(entry.getValue())) {
            return entry;
        }
    }
    return null;
}


//    public void updateData(){
//        Map<Character, Character> updatedMap = new TreeMap<>(correspondenceMap);
//
//        for (Map.Entry<Character, Character> entry : correspondenceMap.entrySet()) {
//            Label label = new Label(entry.getKey().toString() + ":");
//            TextField textField = new TextField(entry.getValue().toString());
//            textField.textProperty().addListener((observable, oldValue, newValue) -> {
//                // Обработчик изменения текста TextField
//                updatedMap.put(entry.getKey(), newValue.charAt(0));
//
//                System.out.println(entry.getKey());
//                System.out.println(newValue);
//            });
//            lettersLayout.getChildren().add(new VBox(label, textField));
//        }
//    }

}
