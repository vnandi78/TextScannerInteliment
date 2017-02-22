package com.inteliment.request;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="searchText")
public class SearchInput {
	
	ArrayList<String> searchText;

	/**
	 * @return the searchText
	 */
	public ArrayList<String> getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(ArrayList<String> searchText) {
		this.searchText = searchText;
	}

	
		
}
