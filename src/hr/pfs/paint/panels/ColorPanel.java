package hr.pfs.paint.panels;

import java.util.ArrayList;
import java.util.HashMap;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.pfs.paint.drawing.*;
import hr.pfs.paint.main.State;

public class ColorPanel {

	private static int MIN_WIDTH = 80;
	private static double WIDTH_FACTOR = 1. / 8.;
	private static double COLOR_BOX_FACTOR = 1. / 3.;
	private static double COLOR_BOX_OFFSET_FACTOR = 1. / 8.;

	private GL2 gl2;

	private int panelWidth;
	private int panelHeight;

	private int windowWidth;
	private int windowHeight;

	private int colorBoxSize;
	private int colorBoxOffset;

	private Color currentColor;

	private HashMap<Coordinates, Color> colorMap;
	private ArrayList<Color> colorArray;

	public ColorPanel(GL2 gl2, int width, int height, Color currentColor) {
		this.gl2 = gl2;
		this.panelHeight = height;
		this.panelWidth = (int) Math.max(width * WIDTH_FACTOR, MIN_WIDTH);

		this.windowWidth = width;
		this.windowHeight = height;

		this.colorBoxSize = (int) (panelWidth * COLOR_BOX_FACTOR);
		this.colorBoxOffset = (int) (panelWidth * COLOR_BOX_OFFSET_FACTOR);

		this.currentColor = currentColor;

		initColors();
	}

	private void initColors() {
		// Set panel colors
		colorArray = new ArrayList<Color>();

		// Color list
		colorArray.add(new Color(255, 0, 0));
		colorArray.add(new Color(0, 255, 0));
		colorArray.add(new Color(0, 0, 255));
		colorArray.add(new Color(255, 0, 255));
		colorArray.add(new Color("37A7D7"));
		colorArray.add(new Color("F7FF00"));
		colorArray.add(new Color("FF9100"));
		colorArray.add(new Color("00FFE6"));

		initColorMap();
	}

	private void initColorMap() {
		colorMap = new HashMap<Coordinates, Color>();

		int rootX = windowWidth - colorBoxOffset;
		int rootY = windowHeight - colorBoxOffset;

		for (int i = 0; i < colorArray.size(); i++) {
			int x = rootX - colorBoxSize - (i % 2) * (colorBoxSize + colorBoxOffset);
			int y = rootY - (i / 2) * (colorBoxSize + colorBoxOffset);

			for (int a = 0; a < colorBoxSize; a++)
				for (int b = 0; b < colorBoxSize; b++)
					colorMap.put(new Coordinates(x + a, y - b), colorArray.get(i));
		}
	}

	public void draw() {
		gl2.glLoadIdentity();

		// Draw background panel
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glColor3f((float) (0xA0) / 255, (float) (0xA0) / 255, (float) (0xA0) / 255); // 0xA0B7C1
		gl2.glVertex2i(windowWidth, 0);
		gl2.glVertex2i(windowWidth, windowHeight);
		gl2.glVertex2i(windowWidth - panelWidth, windowHeight);
		gl2.glVertex2i(windowWidth - panelWidth, 0);
		gl2.glEnd();

		// Draw colors
		int rootX = windowWidth - colorBoxOffset;
		int rootY = windowHeight - colorBoxOffset;

		int nextY = colorBoxOffset;

		for (int i = 0; i < colorArray.size(); i++) {
			int x = rootX - colorBoxSize - (i % 2) * (colorBoxSize + colorBoxOffset);
			int y = rootY - (i / 2) * (colorBoxSize + colorBoxOffset);
			Color color = colorArray.get(i);

			gl2.glBegin(GL2.GL_POLYGON);
			gl2.glColor3f(color.getR(), color.getG(), color.getB());
			gl2.glVertex2i(x, y);
			gl2.glVertex2i(x, y - colorBoxSize);
			gl2.glVertex2i(x + colorBoxSize, y - colorBoxSize);
			gl2.glVertex2i(x + colorBoxSize, y);
			gl2.glEnd();

			nextY = rootY - ((i + 1) / 2) * (colorBoxSize + colorBoxOffset);
		}

		// Draw current color
		int currentColorSize = colorBoxSize * 2 + colorBoxOffset;
		int x = windowWidth - currentColorSize - colorBoxOffset;
		int y = nextY;
		//
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glColor3f(currentColor.getR(), currentColor.getG(), currentColor.getB());
		gl2.glVertex2i(x, y);
		gl2.glVertex2i(x, y - currentColorSize);
		gl2.glVertex2i(x + currentColorSize, y - currentColorSize);
		gl2.glVertex2i(x + currentColorSize, y);
		gl2.glEnd();
		
	}

	public Color getColor(int x, int y) {
		return colorMap.get(new Coordinates(x, y));
	}

}

class Coordinates {
	private int x;
	private int y;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
