package kernel;

import java.util.ArrayList;
import java.util.List;

public class RuleSystem
{
	// Built-in rules (Would be better if read from a file)
	static Rule rule0 = new Rule();
	static Rule rule1 = new Rule("IF covering hair THEN subclass mammal");
	static Rule rule2 = new Rule("IF milk t THEN subclass mammal");
	static Rule rule3 = new Rule("IF covering feathers THEN subclass bird");
	static Rule rule4 = new Rule("IF flies t AND eggs t THEN subclass bird");
	static Rule rule5 = new Rule("IF eats meat THEN order carnivore");
	static Rule rule6 = new Rule("IF teeth pointed AND claws t AND eyes forward THEN order carnivore");
	static Rule rule7 = new Rule("IF subclass mammal AND hoofs t THEN order ungulate");
	static Rule rule8 = new Rule("IF subclass bird AND flies f THEN order ratite");
	static Rule rule9 = new Rule("IF subclass mammal AND eats cud AND antlers none THEN order ungulate");
	static Rule rule10 = new Rule("IF subclass mammal AND eats cud AND antlers hornlike THEN order ruminant");
	static Rule rule11 = new Rule("IF subclass mammal AND eats cud AND antlers branching THEN order ruminant");
	static Rule rule12 = new Rule("IF order ruminant AND antlers hornlike THEN suborder pecora");
	static Rule rule13 = new Rule("IF suborder pecora THEN stomachs several");
	static Rule rule14 = new Rule("IF order ratite AND feet webbed AND wings swimming THEN type penguin");
	static Rule rule15 = new Rule("IF subclass mammal AND order carnivore AND color tawny AND spots dark THEN type cheetah");
	static Rule rule16 = new Rule("IF subclass mammal AND order carnivore AND color tawny AND stripes black THEN type tiger");
	static Rule rule17 = new Rule("IF order ungulate AND neck long AND legs long AND spots dark THEN type giraffe");
	static Rule rule18 = new Rule("IF order ungulate AND stripes black THEN type zebra");
	static Rule rule19 = new Rule("IF order ratite AND neck long AND legs long AND color black-white THEN type ostrich");
	static Rule rule20 = new Rule("IF subclass bird AND flies t THEN type albatross");
	
	public static Rule[] rules = {rule0, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12, rule13, rule14, rule15, rule16, rule17, rule18, rule19, rule20};
	
	// Dealing with new input rule, similar to Rule's Constructor
	public static List<String> Dealing(String input) {
		if (input == null)
			return null;
		List<String> conditions = new ArrayList<>();
		if (input.contains("AND")) {
			conditions.add(input.substring(0, input.indexOf("AND")-1));
			input = input.substring(input.indexOf("AND")+3);
			while(input.contains("AND")) {
				conditions.add(input.substring(1, input.indexOf("AND")-1));
				input = input.substring(input.indexOf("AND")+3);
			}
			conditions.add(input.substring(1));
		}
		else {
			conditions.add(input);
		}
		return conditions;
	}
	
	// Remove a specific rule of rule system by index
	public static void remove(int num) {
        Rule[] tmp = new Rule[rules.length - 1];
 
        for (int i = 0; i <= num; i++)
        	tmp[i] = rules[i];
        for (int i = num + 1; i < rules.length - 1; i++)
        	tmp[i] = rules[i+1];
        
        rules = tmp;
    }
	
	// Add a new rule in rule system
	public static void add(Rule r) {
		Rule[] tmp = new Rule[rules.length + 1];
		
		for (int i = 0; i < rules.length; i++)
			tmp[i] = rules[i];
		tmp[rules.length] = r;
		rules = tmp;
	}
}
