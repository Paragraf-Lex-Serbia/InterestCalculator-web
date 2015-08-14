package rs.paragraf.se.calc.interest.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import rs.paragraf.se.calc.interest.ui.MainFrame;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

public abstract class ResourceManager {

	private static final String IMAGE_PATH = MainFrame.RUNTIME_PATH + "conf/";

	public static final Font getFont(String styleName) {
		Font font = null;
		try {
			font = new Font(MainFrame.properties.getProperty(styleName
					+ ".font.name"), Integer.valueOf(MainFrame.properties
					.getProperty(styleName + ".font.style")),
					Integer.valueOf(MainFrame.properties.getProperty(styleName
							+ ".font.size")));
		} catch (NumberFormatException e) {
		}

		return font;
	}

	public static final BaseFont getBaseFont() {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(
					MainFrame.printProperties.getProperty("baseFont.name"),
					MainFrame.printProperties.getProperty("baseFont.encoding"),
					BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bf;
	}

	public static final BaseColor getBaseColor(String color) {
		return new BaseColor(Integer.parseInt(MainFrame.printProperties
				.getProperty(color)));
	}

	public static final Color getBackgroundColor(String color) {
		try {
			Color backgroundColor = new Color(
					Integer.parseInt(MainFrame.properties.getProperty(color)));
			return backgroundColor;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return Color.WHITE;
		}
	}

	public static final Color getFontColor(String styleName) {
		try {
			Color fontColor = new Color(Integer.parseInt(MainFrame.properties
					.getProperty(styleName + ".font.color")));
			return fontColor;
		} catch (Exception e) {
			return Color.black;
		}
	}

	public static final Icon getImageIcon(String imageName) {
		try {
			Icon icon = new ImageIcon(IMAGE_PATH
					+ MainFrame.properties.getProperty(imageName
							+ ".image.name"));
			return icon;
		} catch (Exception e) {
			return new ImageIcon();
		}
	}

	public static final JComponent decorateComponent(JComponent component,
			String style) {
		try {
			Font font = ResourceManager.getFont(style);
			if (font == null)
				font = component.getFont();
			component.setFont(font);
			component.setForeground(ResourceManager.getFontColor(style));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return component;
	}
}