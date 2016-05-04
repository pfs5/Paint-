package hr.pfs.paint.mainFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.jogamp.opengl.awt.GLCanvas;

public class MainFrame extends JFrame{
	
	JFrame frame = this;

	public MainFrame(String title, int width, int height, GLCanvas glcanvas) {
		super(title);
		super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowevent) {
				frame.dispose();
				System.exit(0);
			}
		});
		super.getContentPane().add(glcanvas, BorderLayout.CENTER);
		super.setSize(width, height);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
	
}
