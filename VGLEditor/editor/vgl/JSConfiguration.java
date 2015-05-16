package editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

//compare with JavaSourceViewerConfiguration
public class JSConfiguration extends TextSourceViewerConfiguration {

	private ColorManager colorManager;
	private JSDoubleClickStrategy doubleClickStrategy;
	private JSRuleScanner ruleScanner;

	public JSConfiguration(ColorManager colorManager) {
		super();
		this.colorManager = colorManager;
	}
	
	public ITextDoubleClickStrategy getDoubleClickStrategy(
						ISourceViewer sourceViewer,
						String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new JSDoubleClickStrategy();
		return doubleClickStrategy;
	}

		protected JSRuleScanner getXMLScanner() {
			if (ruleScanner == null) {
				ruleScanner = new JSRuleScanner(colorManager);
				ruleScanner.setDefaultReturnToken(
					new Token(
						new TextAttribute(
							colorManager.getColor(IXMLColorConstants.DEFAULT))));
			}
			return ruleScanner;
		}

		public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
			PresentationReconciler reconciler = new PresentationReconciler();

			DefaultDamagerRepairer dr =
				new DefaultDamagerRepairer(getXMLTagScanner());
			reconciler.setDamager(dr, XMLPartitionScanner.XML_TAG);
			reconciler.setRepairer(dr, XMLPartitionScanner.XML_TAG);

			dr = new DefaultDamagerRepairer(getXMLScanner());
			reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

			NonRuleBasedDamagerRepairer ndr =
				new NonRuleBasedDamagerRepairer(
					new TextAttribute(
						colorManager.getColor(IXMLColorConstants.XML_COMMENT)));
			reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
			reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

			return reconciler;
		}
	
	
}
