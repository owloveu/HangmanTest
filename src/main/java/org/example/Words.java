package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Words {
    public static String getRandomWordFromFile() throws IOException {
        File word = new File("src/main/resources/words.txt");
        List<String> words = readWordsFromWord(String.valueOf(word));
        if (words.isEmpty()) {
            System.out.println("Файл пуст или слова не найдены.");
            System.out.println("Проверьте файл");
            return null;
        } else {return getRandomWord(words);}
    }

    static List<String> readWordsFromWord(String word){
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(word))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] wordArray = line.split("\\s+ ");
                Collections.addAll(words, wordArray);
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения файла"+e.getMessage());
        }
        return words;
    }

    static String getRandomWord (List<String> words) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
}
