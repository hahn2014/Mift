package bloom;

import org.lwjgl.opengl.Display;

import gaussianBlur.HorizontalBlur;
import gaussianBlur.VerticalBlur;

public class Bloom {
	private BrightFilter brightFilter;
	private static CombineFilter combineFilter;
	
	private HorizontalBlur hBlur;
	private VerticalBlur vBlur;
	
	private int colorTexture;
	
	private boolean render = false;
	
	public Bloom() {
		brightFilter = new BrightFilter(Display.getWidth() / 2, Display.getHeight() / 2);
		hBlur = new HorizontalBlur(Display.getWidth() / 5, Display.getHeight() / 5);
		vBlur = new VerticalBlur(Display.getWidth() / 5, Display.getHeight() / 5);
		combineFilter = new CombineFilter();
	}
	
	public void render(int texture, int colorTexture, boolean render) {
		this.colorTexture = colorTexture;
		this.render = render;
		if (render) {
			brightFilter.render(texture);
			hBlur.render(brightFilter.getColorTexture(), Display.getWidth());
			vBlur.render(hBlur.getOutputTexture(), Display.getHeight());
			combineFilter.render(colorTexture, vBlur.getOutputTexture());
		}
	}
	
	public void cleanUp() {
		brightFilter.cleanUp();
		hBlur.cleanUp();
		vBlur.cleanUp();
		combineFilter.cleanUp();
	}
	
	public int getColorTexture() {
		if (render) {
			return combineFilter.getColorTexture();
		}
		return colorTexture;
	}
	
	public boolean getRender() {
		return render;
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
}