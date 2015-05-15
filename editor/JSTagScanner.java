package editor;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;


public class JSTagScanner extends RuleBasedScanner {

	public JSTagScanner(ColorManager manager) {
		IToken string = new Token(IJSColorConstants.STRING);
		IToken comment = new Token(IJSColorConstants.COMMENT);

		IRule[] rules = new IRule[5];

		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new JSWhitespaceDetector());
		// Add single-line comment rule
		rules[3] = new EndOfLineRule("//", comment);
		// Add multi-line comment rule
		rules[4] = new MultiLineRule("/*", "*/", comment);

		setRules(rules);
	}
}
