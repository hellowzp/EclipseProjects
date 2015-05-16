package editor.codeassist;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import editor.parser.ASTStart;
import editor.parser.SimpleNode;
import editor.util.EditorUtil;

/**
 * 
 * @author Ram Kulkarni (rakulkar@adobe.com)
 *
 */
public class VGLCompletionProcessor implements IContentAssistProcessor {

	private static final char[] autoActivationChars = new char[] {'.',' '}; 

	
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		
		ASTStart startNode = EditorUtil.parseDocument(viewer.getDocument());
		
		if (startNode == null)
			return null;
		
		SimpleNode nodeAtOffset = EditorUtil.getNodeAt(offset, startNode);
		
		return null;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return autoActivationChars;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

}
