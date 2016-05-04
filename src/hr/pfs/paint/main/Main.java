package hr.pfs.paint.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import hr.pfs.paint.drawing.Color;
import hr.pfs.paint.drawing.objects.IDrawableObject;
import hr.pfs.paint.mainFrame.*;
import hr.pfs.paint.panels.ColorPanel;
import hr.pfs.paint.drawing.tools.Pen;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	public static int MAIN_HEIGTH = 600;
	public static int MAIN_WIDTH = 600;

	// Status
	private boolean resized = false;

	static {
		GLProfile.initSingleton();
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				GLProfile glprofile = GLProfile.getDefault();
				GLCapabilities glcapabilities = new GLCapabilities(glprofile);
				final GLCanvas glcanvas = new GLCanvas(glcapabilities);

				// ## Init state ##
				State state = new State();
				state.currentColor = new Color();

				// Init drawable objects
				state.drawables = new ArrayList<IDrawableObject>();

				// Set default drawing tool
				state.activeTool = new Pen();

				// #################

				// Mouse click react
				glcanvas.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int x = e.getX();
						int y = glcanvas.getHeight() - e.getY();

						// Check if color clicked
						Color color = state.colorPanel.getColor(x, y);
						if (color != null) {
							state.currentColor = color;
							glcanvas.display();
						}

					}

					public void mousePressed(MouseEvent e) {
						// Notify tool
						int x = e.getX();
						int y = glcanvas.getHeight() - e.getY();
						state.activeTool.mousePressed(x, y, state);
						glcanvas.display();
					}

					public void mouseReleased(MouseEvent e) {
						// Notify tool
						int x = e.getX();
						int y = glcanvas.getHeight() - e.getY();
						state.activeTool.mouseReleased(x, y, state);
						glcanvas.display();
					}

				});

				// Mouse move react
				glcanvas.addMouseMotionListener(new MouseMotionAdapter() {
					public void mouseMoved(MouseEvent e) {
						int x = e.getX();
						int y = glcanvas.getHeight() - e.getY();

						// Notify tool
						state.activeTool.mouseMoved(x, y, state);
						glcanvas.display();
					}

					public void mouseDragged(MouseEvent e) {
						int x = e.getX();
						int y = glcanvas.getHeight() - e.getY();

						// Notify tool
						state.activeTool.mouseDragged(x, y, state);
						glcanvas.display();
					}
				});

				// Keyboard keypress react
				glcanvas.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						// Reset
						if (e.getKeyCode() == KeyEvent.VK_R) {
							e.consume();
							state.drawables.clear();
							glcanvas.display();
						}

						// Increase thickness
						if (e.getKeyCode() == KeyEvent.VK_E) {
							state.currentThickness += 0.5;
							glcanvas.display();
						}

						// Decrease thickness
						if (e.getKeyCode() == KeyEvent.VK_Q) {
							state.currentThickness -= 0.5;
							glcanvas.display();
						}
					}
				});

				// React to canvas size change, redraw requests, ...
				glcanvas.addGLEventListener(new GLEventListener() {

					@Override
					public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
						GL2 gl2 = glautodrawable.getGL().getGL2();
						gl2.glMatrixMode(GL2.GL_PROJECTION);
						gl2.glLoadIdentity();

						GLU glu = new GLU();
						glu.gluOrtho2D(0.0f, width, 0.0f, height);

						gl2.glMatrixMode(GL2.GL_MODELVIEW);
						gl2.glLoadIdentity();

						gl2.glViewport(0, 0, width, height);
					}

					@Override
					public void init(GLAutoDrawable glautodrawable) {
					}

					@Override
					public void dispose(GLAutoDrawable glautodrawable) {
					}

					@Override
					public void display(GLAutoDrawable glautodrawable) {
						GL2 gl2 = glautodrawable.getGL().getGL2();
						int width = glautodrawable.getSurfaceWidth();
						int height = glautodrawable.getSurfaceHeight();
						gl2.glClearColor(1, 1, 1, 1);
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

						// Draw panels
						state.colorPanel = new ColorPanel(gl2, width, height, state.currentColor);
						state.colorPanel.draw();

						// Get objects
						IDrawableObject o;
						while ((o = state.activeTool.getObject()) != null)
							state.drawables.add(o);

						// Draw objects
						for (IDrawableObject object : state.drawables)
							object.draw(gl2);

						// Draw currently created object
						state.activeTool.draw(gl2, state);

					}
				});

				// Create main frame
				final JFrame mainFrame = new MainFrame("Paint++", MAIN_WIDTH, MAIN_HEIGTH, glcanvas);

				glcanvas.requestFocusInWindow();
			}
		});
	}
}
