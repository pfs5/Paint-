package hr.pfs.paint.drawing.objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.pfs.paint.drawing.Color;
import hr.pfs.paint.main.*;

public class FreeLine implements IDrawableObject {

	// Object representatin
	private ArrayList<Point> points;
	private Color color;
	private float thickness;
	
	public FreeLine(ArrayList<Point> points, Color color, float thickness) {
		this.points = points;
		this.color = color;
		this.thickness = thickness;
	}

	@Override
	public void draw(GL2 gl2) {
		gl2.glLineWidth(thickness);
		gl2.glBegin(GL.GL_LINE_STRIP);
		gl2.glColor3f(color.getR(), color.getG(), color.getB());
		for (Point p : points)
			gl2.glVertex2i(p.getX(), p.getY());
		gl2.glEnd();
	}
}