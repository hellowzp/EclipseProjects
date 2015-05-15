package editor;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;


public class JSPartitionScanner extends RuleBasedPartitionScanner {
	public final static String JS_COMMENT = "__js_comment";
	public final static String JS_TAG = "__js_tag";

	public JSPartitionScanner() {

		IToken jsComment = new Token(JS_COMMENT);
		//IToken tag = new Token(JS_TAG);

		IPredicateRule[] rules = new IPredicateRule[1];

		rules[0] = new MultiLineRule("/*", "*/", jsComment);
		//not for partition, but for tagging
		//rules[1] = new SingleLineRule("//", null, jsComment); 
		//rules[1] = new TagRule(tag);

		setPredicateRules(rules);
	}
}
