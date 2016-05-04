package hr.pfs.paint.drawing;

public class Color {

	private int R;
	private int G;
	private int B;

	public Color(int r, int g, int b) {
		R = r;
		G = g;
		B = b;
	}

	public Color(String hex) {
		R = Integer.parseInt(hex.substring(0, 2), 16);
		G = Integer.parseInt(hex.substring(2, 4), 16);
		B = Integer.parseInt(hex.substring(4, 6), 16);
	}

	// Default = black
	public Color() {
		R = 0;
		G = 0;
		B = 0;
	}

	public float getR() {
		return (float) R / 255;
	}

	public float getG() {
		return (float) G / 255;
	}

	public float getB() {
		return (float) B / 255;
	}

	@Override
	public String toString() {
		return "Color [R=" + R + ", G=" + G + ", B=" + B + "]";
	}

}
