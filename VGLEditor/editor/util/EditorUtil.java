package editor.util;

import java.io.StringReader;

import org.eclipse.jface.text.IDocument;

import editor.parser.ASTStart;
import editor.parser.SimpleNode;
import editor.parser.VGLParser;

/**
 * 
 * @author Ram Kulkarni (rakulkar@adobe.com)
 *
 */
public class EditorUtil {

	public static ASTStart parseDocument (IDocument doc)
	{
		try
		{
			String docText = doc.get();
			
			VGLParser vglParser = new VGLParser(new StringReader(docText));
			
			return (ASTStart) vglParser.Start();
		} catch (Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}	
	
	public static SimpleNode getNodeAt (int offset, ASTStart startNode)
	{
		return null;
	}
}
