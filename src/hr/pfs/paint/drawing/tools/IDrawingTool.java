package hr.pfs.paint.drawing.tools;

import com.jogamp.opengl.GL2;

import hr.pfs.paint.drawing.objects.IDrawableObject;
import hr.pfs.paint.main.State;

public interface IDrawingTool {

	// Classes implementing IDrawableObject create objects dynamically and store
	// them internally.
	// The getObject method pops a object from the container.
	public IDrawableObject getObject();

	public void draw(GL2 gl2, State state);

	public void mousePressed(int x, int y, State state);

	public void mouseReleased(int x, int y, State state);

	public void mouseDragged(int x, int y, State state);
}
