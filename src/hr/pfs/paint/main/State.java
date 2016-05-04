package hr.pfs.paint.main;

import java.util.ArrayList;

import hr.pfs.paint.drawing.Color;
import hr.pfs.paint.drawing.objects.IDrawableObject;
import hr.pfs.paint.drawing.tools.IDrawingTool;
import hr.pfs.paint.panels.ColorPanel;

public class State {

	// Panels
	public ColorPanel colorPanel;

	// Current color and thickness
	public Color currentColor;
	public float currentThickness;

	// Drawable objects
	public ArrayList<IDrawableObject> drawables;
	
	// Currently active drawing tool
	public IDrawingTool activeTool;
}
