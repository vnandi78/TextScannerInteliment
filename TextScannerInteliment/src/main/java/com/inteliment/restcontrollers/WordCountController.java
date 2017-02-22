/**
 * 
 */
package com.inteliment.restcontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.inteliment.domain.WordsWithCount;
import com.inteliment.request.SearchInput;
import com.inteliment.wordcounter.service.WordCounterService;

/**
 * @author VDRKumar
 *
 */
@RestController
public class WordCountController {
	
	//-------------------Search for givens words and return their number of occurances/counts--------------------------------------------------------
	
	@RequestMapping(value="/counter-api/search", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List <WordsWithCount>> search(@RequestBody SearchInput searchInput){
		WordCounterService.initialize();
		ArrayList<WordsWithCount> list = new ArrayList<WordsWithCount>();
		
		for(String input: searchInput.getSearchText()){
			String inputWord = input;
			long count = WordCounterService.getWordCount(inputWord);
			WordsWithCount wordWithCount = new WordsWithCount();
			wordWithCount.setWord(inputWord);
			wordWithCount.setCount(new Long(count).toString());
			list.add(wordWithCount);
		}
		
		return new ResponseEntity<List<WordsWithCount>>(list, HttpStatus.OK);
	}
	
	//-------------------List top {requestedNumber} of Words with their counts in descending order--------------------------------------------------------
	

	@RequestMapping(value="/counter-api/top/{requestedNumber}",  method=RequestMethod.GET)	
	public void top(@PathVariable Integer requestedNumber, HttpServletResponse response) throws IOException{
	
		WordCounterService.initialize();
		LinkedHashSet<WordsWithCount> list = new LinkedHashSet<WordsWithCount>();
		Map<String, String> topWordScorings = WordCounterService.topWordScorers(requestedNumber);
		
		for(Map.Entry<String, String> topScorer: topWordScorings.entrySet()){
			WordsWithCount wordWithCount = new WordsWithCount();
			wordWithCount.setWord(topScorer.getKey());
			wordWithCount.setCount(topScorer.getValue());
			list.add(wordWithCount);
		}
		

		String csvFileName = "topWordScorings.csv";
		 
        response.setContentType("text/csv");
 
        // creates mock data
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);		
		
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        
        String[] header = { "word", "count" };
        
        csvWriter.writeHeader(header);
        
        for(WordsWithCount topScorer: list){
        	csvWriter.write(topScorer, header);
        }
		
        csvWriter.close();
		
	}
	

}
