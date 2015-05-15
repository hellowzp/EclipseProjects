package editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.*;

public class JSRuleScanner extends RuleBasedScanner {
	private ColorManager colorManager;
	
	public JSRuleScanner(ColorManager manager) {
		colorManager = manager;
		
		List<IRule> rules = createRules();
		setRules(rules.toArray(new IRule[rules.size()]));
	}
	
	public static final String KEYWORDS[] = {
		"abstract",
		"boolean", "break", "byte",
		"case", "catch", "char", "class", "const", "continue",
		"debugger", "default", "delete", "do", "double",
		"else", "enum", "export", "extends",
		"false", "final", "finally", "float", "for", "function",
		"goto", "if", "implements", "import", "in", "instanceof", "int", "interface",
		"let", "long",
		"native", "new", "null",
		"package", "private", "protected", "prototype", "public",
		"return", "short", "static", "super", "switch", "synchronized",
		"this", "throw", "throws", "transient", "true", "try", "typeof",
		"var", "void", "while", "with",
		"typeof", "yield", "undefined", "Infinity", "NaN"
	};
	
	private List<IRule> createRules() {
		IToken procInstr = colorManager.getToken(IJSColorConstants.PROC_INSTR);
		IToken string = colorManager.getToken(IJSColorConstants.STRING);
		IToken comment = colorManager.getToken(IJSColorConstants.COMMENT);
		 
		List<IRule> rules = new ArrayList<IRule>();
		//Add rule for processing instructions
		rules.add(new SingleLineRule("<?", "?>", procInstr));
		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new JSWhitespaceDetector()));
		
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));	
		rules.add(new EndOfLineRule("//",comment));
		
		return rules;
	}

}
