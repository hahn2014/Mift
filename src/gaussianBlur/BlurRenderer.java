package gaussianBlur;

public class BlurRenderer {
	//guassian Blur
	private static HorizontalBlur hBlur;
	private static VerticalBlur vBlur;
	private static HorizontalBlur hBlur2;
	private static VerticalBlur vBlur2;
	
	public int DOUBLEPASS = 2;
	public int SINGLEPASS = 1;
	private int width;
	private int height;
	private boolean render;
	private int origTexture;
	
	public BlurRenderer(int width, int height) {
		setSize(width, height);
		init();
	}
	
	private void init() {
		hBlur = new HorizontalBlur(width, height);
		vBlur = new VerticalBlur(width, height);
		hBlur2 = new HorizontalBlur(width, height);
		vBlur2 = new VerticalBlur(width, height);
	}
	
	public void render(int colorTexture, int passes, boolean render) {
		this.render = render;
		origTexture = colorTexture;
		if (render) {
			if (passes == DOUBLEPASS) {
				hBlur2.render(origTexture, width);
				vBlur2.render(hBlur2.getOutputTexture(), height);
				
				hBlur.render(vBlur2.getOutputTexture(), width);
				vBlur.render(hBlur.getOutputTexture(), height);
			} else {
				hBlur.render(origTexture, width);
				vBlur.render(hBlur.getOutputTexture(), height);
			}
		}
	}
	
	public int getColorTexture() {
		if (render) {
			return vBlur.getOutputTexture();
		} else {
			return origTexture;
		}
	}
	
	public void setSize(int w, int h) {
		width = w;
		height = h;
		init();
	}
	
	public boolean getRender() {
		return render;
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
	
	public void cleanUp() {
		hBlur.cleanUp();
		vBlur.cleanUp();
		hBlur2.cleanUp();
		vBlur2.cleanUp();
	}
}