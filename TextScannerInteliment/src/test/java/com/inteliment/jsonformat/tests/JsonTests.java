package com.inteliment.jsonformat.tests;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inteliment.request.SearchInput;

public class JsonTests {

	public static void main(String[] args) throws JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		SearchInput obj = new SearchInput();
		ArrayList<String> inputTexts = new ArrayList<String>();
		inputTexts.add("Duis");
		inputTexts.add("Sed");
		inputTexts.add("Donec");
		inputTexts.add("Augue");
		inputTexts.add("Pellentesque");
		inputTexts.add("123");
		obj.setSearchText(inputTexts);

		//Object to JSON in file
		//mapper.writeValue(new File("c:\\file.json"), obj);

		//Object to JSON in String
		String jsonInString = mapper.writeValueAsString(obj);

		System.out.println(jsonInString);
	
	}
}
