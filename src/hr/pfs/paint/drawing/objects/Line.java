package hr.pfs.paint.drawing.objects;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.pfs.paint.drawing.Color;
import hr.pfs.paint.main.Point;

public class Line implements IDrawableObject {

	private Point start, end;
	private Color color;
	private float thickness;

	public Line(Point start, Point end, Color color, float thickness) {
		this.start = start;
		this.end = end;
		this.color = color;
		this.thickness = thickness;
	}

	@Override
	public void draw(GL2 gl2) {
		gl2.glLineWidth(thickness);
		gl2.glColor3f(color.getR(), color.getG(), color.getB());
		gl2.glBegin(GL.GL_LINES);
		gl2.glVertex2i(start.getX(), start.getY());
		gl2.glVertex2i(end.getX(), end.getY());
		gl2.glEnd();
	}

}
