package com.example.kyrgyztextdecrypt.decryption;

import java.util.*;

public class FrequencyAnalysis {

    public char[] getFrequencyStats(String text){
        Map<Character, Integer> freqList = new HashMap<>();

        for (char c : text.toLowerCase().toCharArray()){
            if (!Alphabet.KYRGYZ_ALPHABET.contains(c)){
                continue;
            }
            if (Character.isLetter(c))
                freqList.put(c,freqList.getOrDefault(c, 0)+1);
        }
        return convertToFrequencyList(freqList);
    }

    public Map<Character, Integer> getFrequency(String text){
        Map<Character, Integer> freqList = new HashMap<>();

        for (char c : text.toLowerCase().toCharArray()){
            if (!Alphabet.KYRGYZ_ALPHABET.contains(c)){
                continue;
            }
            if (Character.isLetter(c))
                freqList.put(c,freqList.getOrDefault(c, 0)+1);
        }

        System.out.println(freqList);
        return freqList;
    }

    private char[] convertToFrequencyList(Map<Character, Integer> map){
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        char[] frequencyList = new char[200];

        int i = 0;
        for (Map.Entry<Character, Integer> entry : list) {
            frequencyList[i] = entry.getKey();
            i++;
        }

        System.out.println(list);

        return frequencyList;
    }

    public Map<Character, Character> characterMatchingList(char[] freqList, char[] freqList2){
        Map<Character, Character> characterMatchingList = new HashMap<>();

        for (int i = 0; i<freqList.length; i++){
            characterMatchingList.put(freqList2[i], freqList[i]);
        }

        return characterMatchingList;
    }
}
