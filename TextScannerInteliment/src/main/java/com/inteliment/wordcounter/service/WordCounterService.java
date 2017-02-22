/**
 * 
 */
package com.inteliment.wordcounter.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author VDRKumar
 *
 */
public class WordCounterService {
	
	private static TreeMap<String, Long> wordToCountMapping;
	
	public static void initialize() {
		try{
		readSampleFileToInit();
		}
		catch(Exception ex){
			System.out.println("Sorry Something went Wrong......Please try again later");
		}
	}
	
	private static TreeMap<String, Long> readSampleFileToInit() throws FileNotFoundException{
		if(wordToCountMapping==null){
		    
			String fileName = "resources/sample.txt";
			try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
				wordToCountMapping = new TreeMap<String, Long>(stream.map(w -> w.replaceAll("\\.", "").replaceAll("\\,", "").toLowerCase().split("\\s"))
                        .flatMap(Arrays::stream)
                        .collect(groupingBy(identity(), counting())));
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return wordToCountMapping;
		
	}
	
	public static Map<String, Long> getWordToCountMapping(){
		
		return wordToCountMapping;
				
	}
		
	public static Integer getWordCount(String word){
		if(wordToCountMapping.containsKey(word.toLowerCase())){
			return wordToCountMapping.get(word.toLowerCase()).intValue();
		}
		else{
			return 0;
		}
	}
	
	public static Map<String, String> topWordScorers(int number){
		Map<Object, Object> sortedMap =
				wordToCountMapping.entrySet().stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .limit(number>wordToCountMapping.size()?wordToCountMapping.size():number)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
		
		Map<String, String> map = new LinkedHashMap<String, String>();
        for(Map.Entry<Object, Object> entry : sortedMap.entrySet()) {
            String key = (String) entry.getKey(); //not really unsafe, since you just loaded the properties
            map.put(key, sortedMap.get(key).toString());
        }
        
		return map;
	}
}
