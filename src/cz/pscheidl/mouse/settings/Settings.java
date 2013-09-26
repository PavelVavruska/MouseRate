package cz.pscheidl.mouse.settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Settings {
	
	private static final Color bgColor = new Color(30,101,134);
	private static Font mouseFont = null;

	private static final ImageIcon appIcon = new ImageIcon(Settings.class.getClass().getResource("/cz/pscheidl/mouse/files/mouseIcon.png"));

	public static ImageIcon getAppicon() {
		return appIcon;
	}

	public static Color getBgcolor() {
		return bgColor;
	}
	
	public static Font getMouseFont(){
	
		if(mouseFont == null){
			try {

				mouseFont = Font.createFont(Font.TRUETYPE_FONT, Settings.class.getClass().getResourceAsStream("/cz/pscheidl/mouse/files/mouseFont.ttf"));
				mouseFont = mouseFont.deriveFont(14.0f);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		
		return mouseFont;
	}

}
