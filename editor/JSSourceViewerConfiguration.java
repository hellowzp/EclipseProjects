package editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

//compare with JavaSourceViewerConfiguration
public class JSSourceViewerConfiguration extends TextSourceViewerConfiguration {
	private JSDoubleClickStrategy doubleClickStrategy;
	private JSTagScanner tagScanner;
//	private JSRuleScanner ruleScanner;
	private ColorManager colorManager;

	public JSSourceViewerConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			JSPartitionScanner.JS_COMMENT,
			JSPartitionScanner.JS_TAG };
	}
	
	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
						ISourceViewer sourceViewer,
						String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new JSDoubleClickStrategy();
		return doubleClickStrategy;
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getJSTagScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

//		dr = new DefaultDamagerRepairer(getJSRuleScanner());
//		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
//		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

//		NonRuleBasedDamagerRepairer ndr =
//			new NonRuleBasedDamagerRepairer(
//				new TextAttribute(
//					colorManager.getColor(IJSColorConstants.JS_COMMENT)));
//		reconciler.setDamager(ndr, JSPartitionScanner.JS_COMMENT);
//		reconciler.setRepairer(ndr, JSPartitionScanner.JS_COMMENT);

		return reconciler;
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant ca = new ContentAssistant();
		ca.enableAutoActivation(true);
		ca.enableAutoInsert(true);
		ca.setContentAssistProcessor(new JSAutoCompletionProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
		
		ca.setAutoActivationDelay(200);
        ca.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
        ca.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
		//display extra info with a pop-up window when a proposal is highlight but not inserted yet
        ca.setInformationControlCreator(getInformationControlCreator(sourceViewer));
        
        return ca;
	}

//	private JSRuleScanner getJSRuleScanner() {
//		if (ruleScanner == null) {
//			ruleScanner = new JSRuleScanner(colorManager);
//			ruleScanner.setDefaultReturnToken(
//				new Token(
//					new TextAttribute(
//						colorManager.getColor(IJSColorConstants.DEFAULT))));
//		}
//		return ruleScanner;
//	}
	
	//set rules for highlighting tags like a comment
	private JSTagScanner getJSTagScanner() {
		if (tagScanner == null) {
			tagScanner = new JSTagScanner(colorManager);
			
			//Configures the scanner's default return token. 
			//This is the token which is returned 
			//when none of the rules fired and EOF has not been reached.
			tagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IJSColorConstants.DEFAULT))));
		}
		return tagScanner;
	}

}
