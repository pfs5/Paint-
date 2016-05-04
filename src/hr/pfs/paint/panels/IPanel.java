package hr.pfs.paint.panels;

import java.awt.event.MouseEvent;

import com.jogamp.opengl.GL2;

import hr.pfs.paint.main.State;

public interface IPanel {
	public void update(GL2 gl2, int width, int height, State state);

	public void draw();

	public void mouseClicked(int x, int y);
}
