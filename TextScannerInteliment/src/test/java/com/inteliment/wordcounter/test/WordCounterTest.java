package com.inteliment.wordcounter.test;

import com.inteliment.wordcounter.service.WordCounterService;

public class WordCounterTest {

	public static void main(String[] args) {


		    int numberOfTopScorers = 8;
			WordCounterService.initialize();
			
			System.out.println(WordCounterService.getWordToCountMapping());
			/*
			 * ”:[“Duis”, “Sed”, “Donec”, “Augue”, “Pellentesque”, “123”
			 */
			System.out.println("Duis = " + WordCounterService.getWordCount("Duis"));
			System.out.println("Sed = " + WordCounterService.getWordCount("Sed"));
			System.out.println("Donec = " + WordCounterService.getWordCount("Donec"));
			System.out.println("Augue = " + WordCounterService.getWordCount("Augue"));
			System.out.println("Pellentesque = " + WordCounterService.getWordCount("Pellentesque"));
			System.out.println("123 = " + WordCounterService.getWordCount("123"));
			
			System.out.println("Top " + numberOfTopScorers + " words with highest counts is :" + WordCounterService.topWordScorers(numberOfTopScorers));

	}

}
