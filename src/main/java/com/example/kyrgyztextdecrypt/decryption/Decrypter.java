package com.example.kyrgyztextdecrypt.decryption;

import java.util.*;

public class Decrypter {
    private final Reader reader;
    private final FrequencyAnalysis frequencyAnalysis;
    private Map<Character, Character> correspondsMap;
    private Map<Character, Character> correspondsMap2;

    public Map<Character, Character> getCorrespondsMap() {
        return correspondsMap;
    }

    public void setCorrespondsMap(Map<Character, Character> correspondsMap) {
        this.correspondsMap = correspondsMap;
    }

    public Decrypter() {
        this.reader = new Reader();
        this.frequencyAnalysis = new FrequencyAnalysis();
    }

    public String decrypt(String encryptedText){
        StringBuilder result = new StringBuilder();

        for (char c : encryptedText.toCharArray()){
            if (Character.isLetter(c)){
                char c1 = correspondsMap.getOrDefault(Character.toLowerCase(c),c);

                if (Character.isUpperCase(c))
                    c1 = Character.toUpperCase(c1);
                result.append(c1);

            }else
                result.append(c);
        }
        return result.toString();
    }

    public Map<Character, Character> getCorrespondsMap2() {
        return correspondsMap2;
    }

    public void setCorrespondsMap2(Map<Character, Character> correspondsMap2) {
        this.correspondsMap2 = correspondsMap2;
    }

    public void analyze(String encryptedText, String textAnalyzeFile){
        String dataFileStr = reader.readFile(textAnalyzeFile);
        char[]output = frequencyAnalysis.getFrequencyStats(dataFileStr);
        char[]input = frequencyAnalysis.getFrequencyStats(encryptedText);

        this.setCorrespondsMap(createCharacterMatchingMap(input,output));
        this.setCorrespondsMap2(createCharacterMatchingMap(output,input));

        System.out.println(correspondsMap);
    }

    public void analyze2(String encryptedText, String textAnalyzeFile){
        String dataFileStr = reader.readFile(textAnalyzeFile);
        char[]output = frequencyAnalysis.getFrequencyStats(dataFileStr);
        char[]input = frequencyAnalysis.getFrequencyStats(encryptedText);

        this.setCorrespondsMap(createCharacterMatchingMap(input,output));
        this.setCorrespondsMap2(createCharacterMatchingMap(output,input));
    }

    private Map<Character, Character> createCharacterMatchingMap(char[] freqList, char[] freqList2){
        Map<Character, Character> characterMatchingMap = new TreeMap<>();

        for (int i = 0; i<freqList.length; i++){
            if(Character.isLetter(freqList[i]))
                characterMatchingMap.put(freqList[i], freqList2[i]);
        }

        return characterMatchingMap;
    }


    public static String decryptText(String encryptedText, Map<Character, Integer> letterFrequencies, int numIterations) {
        String decryptedText = encryptedText; // Сначала расшифрованный текст будет равен зашифрованному.

        for (int i = 0; i < numIterations; i++) { // Повторяем процесс замены букв numIterations раз.
            Map<Character, Integer> letterCounts = new HashMap<>(); // Создаем таблицу для подсчета количества каждой буквы в расшифрованном тексте.
            for (int j = 0; j < decryptedText.length(); j++) { // Проходимся по каждой букве в расшифрованном тексте.
                char letter = decryptedText.charAt(j); // Получаем очередную букву.
                if (letterCounts.containsKey(letter)) { // Если таблица уже содержит такую букву, увеличиваем ее количество на 1.
                    int count = letterCounts.get(letter);
                    letterCounts.put(letter, count + 1);
                } else { // Иначе добавляем букву в таблицу со значением 1.
                    letterCounts.put(letter, 1);
                }
            }

            // Сортируем таблицу по убыванию количества букв.
            ArrayList<Map.Entry<Character, Integer>> sortedLetterCounts = new ArrayList<>(letterCounts.entrySet());
            sortedLetterCounts.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

            // Заменяем самые частые буквы в расшифрованном тексте на наиболее часто встречающиеся буквы в языке.
            for (int j = 0; j < sortedLetterCounts.size(); j++) {
                char encryptedLetter = sortedLetterCounts.get(j).getKey(); // Получаем букву, которую нужно заменить.
                if (letterFrequencies.containsKey(encryptedLetter)) { // Если такая буква есть в таблице частотности языка...
                    char decryptedLetter = getMostFrequentLetter(letterFrequencies, letterCounts.keySet(), encryptedLetter); // Получаем наиболее часто встречающуюся букву в языке.
                    decryptedText = decryptedText.replace(encryptedLetter, decryptedLetter); // Заменяем букву в расшифрованном тексте.
                }
            }
        }

        return decryptedText; // Возвращаем расшифрованный текст.
    }

    public static char getMostFrequentLetter(Map<Character, Integer> letterFrequencies, Set<Character> usedLetters, char encryptedLetter) {
        char mostFrequentLetter = encryptedLetter;
        int highestFrequencyDifference = Integer.MAX_VALUE;
        for (char letter : letterFrequencies.keySet()) {
            if (!usedLetters.contains(letter)) {
                int frequencyDifference = Math.abs(letterFrequencies.get(letter) - letterFrequencies.get(encryptedLetter));
                if (frequencyDifference < highestFrequencyDifference) {
                    mostFrequentLetter = letter;
                    highestFrequencyDifference = frequencyDifference;
                }
            }
        }
        return mostFrequentLetter;
    }
}
