package editor;

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * 
 * @author Ram Kulkarni (rakulkar@adobe.com)
 *
 */
public class VGLEditor extends TextEditor {

	SourceViewerConfiguration sourceViewerConfiguration  = new VGLSourceViewConfiguration();
	
	public VGLEditor() {
		super();
	}

	public void createPartControl(Composite parent)
	{
		this.setSourceViewerConfiguration(sourceViewerConfiguration);
		super.createPartControl(parent);
	}
	
}
