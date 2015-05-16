package editor;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Shell;

import editor.codeassist.VGLContentAssistant;

/**
 * 
 * @author Ram Kulkarni (rakulkar@adobe.com)
 *
 */
public class VGLSourceViewConfiguration extends SourceViewerConfiguration {

	VGLContentAssistant contentAssistant = new VGLContentAssistant();
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		
		contentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

		return contentAssistant;
	}
	
	public IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, false);
			}
		};
	}
	
}
