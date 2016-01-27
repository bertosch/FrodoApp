package de.spdmc.frodo.textparser;

import de.spdmc.frodo.textparser.InputContent;

public abstract class Parser {

    protected String[] pattern; // in pattern liegen (Teil-)Woerter nach denen
    // im jeweiligen Bot gesucht werden soll

    protected Parser() {
        super();
    }

    protected Parser(String[] pattern) {
        super();
        this.pattern = pattern;
    }

	/*
     * moeglicherweise unnoetig (Duplikat zu parse(String) )?
	 * 
	public InputContent parseForKeyword(String in, String key){
		String normalizedIn = normalize(in);
		key = normalize(key);
		String[] tokenizedIn = normalizedIn.split(" ");
		//nur sinnvolles Token zurueckgeben
		return new InputContent(null, tokenizedIn);
	}*/

    public abstract InputContent parse(String in);

    public InputContent parse(String in, boolean answerQuestion) {
        return null;
    }

    protected String normalize(String s) {
        String normalizedStr = s.replaceAll("[^a-zA-Z 0-9]", "");
        normalizedStr = normalizedStr.toLowerCase();
        return normalizedStr;
    }

    public String[] getPattern() {
        return pattern;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }

}