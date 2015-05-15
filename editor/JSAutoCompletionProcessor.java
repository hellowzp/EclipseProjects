package editor;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class JSAutoCompletionProcessor implements IContentAssistProcessor {

	private final static String[] fgProposals= { "abstract", "boolean", "break", "byte", "case", "catch", "char", "class", "continue", "default", "do", "double", "else", "extends", "false", "final", "finally", "float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while" }; //$NON-NLS-48$ //$NON-NLS-47$ //$NON-NLS-46$ //$NON-NLS-45$ //$NON-NLS-44$ //$NON-NLS-43$ //$NON-NLS-42$ //$NON-NLS-41$ //$NON-NLS-40$ //$NON-NLS-39$ //$NON-NLS-38$ //$NON-NLS-37$ //$NON-NLS-36$ //$NON-NLS-35$ //$NON-NLS-34$ //$NON-NLS-33$ //$NON-NLS-32$ //$NON-NLS-31$ //$NON-NLS-30$ //$NON-NLS-29$ //$NON-NLS-28$ //$NON-NLS-27$ //$NON-NLS-26$ //$NON-NLS-25$ //$NON-NLS-24$ //$NON-NLS-23$ //$NON-NLS-22$ //$NON-NLS-21$ //$NON-NLS-20$ //$NON-NLS-19$ //$NON-NLS-18$ //$NON-NLS-17$ //$NON-NLS-16$ //$NON-NLS-15$ //$NON-NLS-14$ //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$

	private IContextInformationValidator fValidator= new Validator();

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
		System.out.println("Auto completion " + documentOffset);
//		IDocument document = viewer.getDocument();
		ICompletionProposal[] result= new ICompletionProposal[fgProposals.length];
		for (int i= 0; i < fgProposals.length; i++) {
			result[i]= new CompletionProposal( fgProposals[i], documentOffset, 0, fgProposals[i].length() ); 
		}
		return result;
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int documentOffset) {
		return null;
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '.', '(' };
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '#' };
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return fValidator;
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	@Override
	public String getErrorMessage() {
		return null;
	}
	
	/**
	 * Simple content assist tip closer. The tip is valid in a range
	 * of 5 characters around its popup location.
	 */
	protected static class Validator implements IContextInformationValidator, IContextInformationPresenter {

		protected int fInstallOffset;

		/*
		 * @see IContextInformationValidator#isContextInformationValid(int)
		 */
		@Override
		public boolean isContextInformationValid(int offset) {
			return Math.abs(fInstallOffset - offset) < 5;
		}

		/*
		 * @see IContextInformationValidator#install(IContextInformation, ITextViewer, int)
		 */
		@Override
		public void install(IContextInformation info, ITextViewer viewer, int offset) {
			fInstallOffset= offset;
		}

		/*
		 * @see org.eclipse.jface.text.contentassist.IContextInformationPresenter#updatePresentation(int, TextPresentation)
		 */
		@Override
		public boolean updatePresentation(int documentPosition, TextPresentation presentation) {
			return false;
		}
	}

}
