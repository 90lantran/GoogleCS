/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private Map<String, ArrayList<String>> lettersToWord = new HashMap<>();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (!lettersToWord.containsKey(sortedWord)) {
                lettersToWord.put(sortedWord, new ArrayList<String>());
            }
            ArrayList<String> currentList = lettersToWord.get(sortedWord);
            currentList.add(word);
            lettersToWord.put(sortedWord, currentList);
        }
    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word)) {
            if(word.toLowerCase().contains(base.toLowerCase())){
                return false;
            }
            else
                return true;
        }
        else{
            return false;
        }
    }

    /**
     * Sort all chars in targetWord
     *
     * @param targetWord
     * @return
     */

    private static String sortLetters(String targetWord) {
        char[] targetChar = targetWord.toCharArray();
        Arrays.sort(targetChar);
        return new String(targetChar);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        List<String> result = getAnagrams(sortLetters(word));
        for (char c = 'a'; c <= 'z'; c++){
            String newWord = word.concat(String.valueOf(c));
            String newWordSorted = sortLetters(newWord);
            if (lettersToWord.containsKey(newWordSorted)){
                result.addAll(lettersToWord.get(newWordSorted));
            }
        }
        // Uncomment to cheat
        //Log.i("List of anagrams " , result.toString());
        return result;
    }

    public List<String> getAnagrams(String word){
        return lettersToWord.get(sortLetters(word));

    }

    public String pickGoodStarterWord() {
        String randomWord;
        List<String> anagramList;

        do {
            randomWord = wordList.get(random.nextInt(wordList.size()));
            anagramList = lettersToWord.get(sortLetters(randomWord));
        } while (anagramList.size() != MIN_NUM_ANAGRAMS);


        return randomWord;
    }
}
