package hr.pfs.paint.drawing.tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.pfs.paint.drawing.Color;
import hr.pfs.paint.drawing.objects.FreeLine;
import hr.pfs.paint.drawing.objects.IDrawableObject;
import hr.pfs.paint.main.State;
import hr.pfs.paint.main.*;

public class Pen implements IDrawingTool {

	// Objects container
	private LinkedList<IDrawableObject> objects;

	// Current object ( Free line)
	private ArrayList<Point> line;

	// Flags
	private boolean pressed;

	public Pen() {
		objects = new LinkedList<IDrawableObject>();
		pressed = false;
	}

	@Override
	public IDrawableObject getObject() {
		if (objects.isEmpty())
			return null;

		return objects.pop();
	}

	@Override
	public void mousePressed(int x, int y, State state) {
		pressed = true;
		line = new ArrayList<Point>();
	}

	@Override
	public void mouseReleased(int x, int y, State state) {
		pressed = false;
		objects.push(new FreeLine(line, state.currentColor, state.currentThickness));
		line = null;
	}

	@Override
	public void mouseDragged(int x, int y, State state) {
		line.add(new Point(x, y));
	}

	@Override
	public void draw(GL2 gl2, State state) {
		if (line != null) {
			Color color = state.currentColor;
			gl2.glLineWidth(state.currentThickness);
			gl2.glBegin(GL.GL_LINE_STRIP);
			gl2.glColor3f(color.getR(), color.getG(), color.getB());
			for (Point p : line)
				gl2.glVertex2i(p.getX(), p.getY());
			gl2.glEnd();
		}
	}


}
