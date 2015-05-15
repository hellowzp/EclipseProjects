package editor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {

	private Map<RGB, Color> colorTable = new HashMap<>(10);
	private Map<RGB, Token> tokenTable = new HashMap<>(10);

	public void dispose() {
		Iterator<Color> e = colorTable.values().iterator();
		while (e.hasNext())  ((Color) e.next()).dispose();
	}
	
	public Color getColor(RGB rgb) {
		Color color = (Color) colorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}
		return color;
	}
	
	public Token getToken(RGB rgb) {
		Token token = tokenTable.get(rgb);
		if(token == null) {
			token = new Token( new TextAttribute( getColor(rgb) ));
			tokenTable.put(rgb, token);
		}
		return token;
	}

}
