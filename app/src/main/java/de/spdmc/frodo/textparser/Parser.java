package de.spdmc.frodo.textparser;

import de.spdmc.frodo.Bot;

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
        return Bot.normalize(s);
    }

    protected String removeArticles(String s){
        String regex = "\\b(the|die|der|das)\\b\\s*";
        return s.replaceAll(regex, "");
    }

    public String[] getPattern() {
        return pattern;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }

}
