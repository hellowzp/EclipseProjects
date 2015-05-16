package editor;

import org.eclipse.ui.editors.text.TextEditor;

public class JavaScriptEditor extends TextEditor {
	private ColorManager colorManager;

	public JavaScriptEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new JSConfiguration(colorManager));
		setDocumentProvider(new JSDocumentProvider());
	}
	
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
