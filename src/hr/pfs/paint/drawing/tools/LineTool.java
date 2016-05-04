package hr.pfs.paint.drawing.tools;

import java.util.ArrayList;
import java.util.LinkedList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.pfs.paint.drawing.Color;
import hr.pfs.paint.drawing.objects.IDrawableObject;
import hr.pfs.paint.drawing.objects.Line;
import hr.pfs.paint.main.Point;
import hr.pfs.paint.main.State;
/*
 * 
 * Line tools creates Line objects
 * 
 * 
 */
public class LineTool implements IDrawingTool {

	// Object container
	private LinkedList<IDrawableObject> objects;
	
	// Current object
	private Point start,end;
	
	public LineTool () {
		objects = new LinkedList<IDrawableObject>();
	}
	
	@Override
	public IDrawableObject getObject() {
		if (objects.isEmpty())
			return null;
		return objects.pop();
	}

	@Override
	public void draw(GL2 gl2, State state) {
		if (start != null && end != null) {
			Color color = state.currentColor;
			gl2.glColor3f(color.getR(), color.getG(), color.getB());
			gl2.glLineWidth(state.currentThickness);
			gl2.glBegin(GL.GL_LINES);
			gl2.glVertex2i(start.getX(), start.getY());
			gl2.glVertex2i(end.getX(), end.getY());
			gl2.glEnd();
		}
	}

	@Override
	public void mousePressed(int x, int y, State state) {
		start = new Point(x, y);
		end = new Point(x, y);
	}

	@Override
	public void mouseReleased(int x, int y, State state) {
		end.setX(x);
		end.setY(y);
		objects.push(new Line(start, end, state.currentColor, state.currentThickness));
		
		start = null;
		end = null;
	}

	@Override
	public void mouseDragged(int x, int y, State state) {
		end.setX(x);
		end.setY(y);
	}

}
