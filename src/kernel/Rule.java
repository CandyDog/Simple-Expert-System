package kernel;

import java.util.ArrayList;
import java.util.List;

public class Rule
{
	public List<String> conditions = new ArrayList<>();
	public String conclusion;
	
	public Rule() {conditions.add(""); conclusion = "";}
	
	public Rule(String sentence) {
		// Extract conclusion from the whole sentence
		conclusion = sentence.substring(sentence.indexOf("THEN")+5);
		
		// Multiple conditions
		if (sentence.contains("AND")) {
			conditions.add(sentence.substring(3, sentence.indexOf("AND")-1));
			sentence = sentence.substring(sentence.indexOf("AND")+3);
			while(sentence.contains("AND")) {
				conditions.add(sentence.substring(1, sentence.indexOf("AND")-1));
				sentence = sentence.substring(sentence.indexOf("AND")+3);
			}
			conditions.add(sentence.substring(1, sentence.indexOf("THEN")-1));
		}
		else {
			conditions.add(sentence.substring(3, sentence.indexOf("THEN")-1));
		}
	}
	
}
