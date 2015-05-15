package editor;

import java.util.ResourceBundle;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextEditorAction;

//compare with JavaEditor which extends AbstractDecoratedTextEditor
//but TextEditor extends AbstractDecoratedTextEditor again
public class JavaScriptEditor extends TextEditor  {

	private ColorManager colorManager;
	
	public static final String GROUP_JAVASCRIPT = "_javascript";
	public static final String ACTION_COMMENT = "_comment";
	public static final String ACTION_DEBUGGER = "_launch_debugger";
	public static final String ACTION_FORMAT = "_format";
	public static final String ACTION_OUTLINE = "_outline";
	public static final String ACTION_TOGGLE_BREAKPOINT = "_toggle_breakpoint";
	
	private static final String CONTENT_ASSIST_PROPOSAL_ACTION = "editor.ContentAssistProposal"; 

	public JavaScriptEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new JSSourceViewerConfiguration(colorManager));
		setDocumentProvider(new JSDocumentProvider());		
	}
	
	@Override
	protected void createActions() {
		super.createActions();
		
		IAction commentAction = new CommentAction(JavaScriptEditorPlugin.getActionResourceBundle(), "Comment.", this);
		setAction(ACTION_COMMENT,commentAction);
		
		IAction contentAssistAction = new ContentAssistAction(JavaScriptEditorPlugin.getActionResourceBundle(), "ContentAssistProposal.", this); 
		String id = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
		contentAssistAction.setActionDefinitionId(id);
		setAction(CONTENT_ASSIST_PROPOSAL_ACTION, contentAssistAction); 
		markAsStateDependentAction(CONTENT_ASSIST_PROPOSAL_ACTION, true);
		
		

//	   IAction contentAssistAction = new TextOperationAction(JavaScriptEditorPlugin.getDefault().getResourceBundle(),
//	      "ContentAssistProposal", this, SourceViewer.CONTENTASSIST_PROPOSALS);
//	   contentAssistAction.setActionDefinitionId(CONTENT_ASSIST_PROPOSAL_ACTION);
//
//	   // Tell the editor to execute this action 
//	   // when Ctrl+Space is pressed
//	   setActionActivationCode(CONTENT_ASSIST_PROPOSAL_ACTION,' ', -1, SWT.CTRL);
//	   setAction(CONTENT_ASSIST_PROPOSAL_ACTION, contentAssistAction);
	}
	
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
	
	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		addAction(menu, CONTENT_ASSIST_PROPOSAL_ACTION);
		addAction(menu, ACTION_COMMENT);
	}

	/**
	 * Toggle comment out selection range.
	 */
	private class CommentAction extends TextEditorAction {

		public CommentAction(ResourceBundle bundle, String prefix, ITextEditor editor) {
			super(bundle, prefix, editor);
			setEnabled(true);
			setAccelerator(SWT.CTRL | '/');
		}

		@Override
		public void run() {
			System.out.println("comment action");
			ITextSelection sel = (ITextSelection)getSelectionProvider().getSelection();
			IDocument doc = getDocumentProvider().getDocument(getEditorInput());
			String text = sel.getText();
			text = text.replaceAll("[\r\n \t]+$", ""); // rtrim
			int length = text.length();
			try {
				if(text.startsWith("//")){
					text = text.replaceAll("(^|\r\n|\r|\n)//","$1");
				} else {
					text = text.replaceAll("(^|\r\n|\r|\n)","$1//");
				}
				doc.replace(sel.getOffset(), length, text);
			} catch (BadLocationException e) {
				
			}
		}
	}

	

}
