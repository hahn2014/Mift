package postProcessing.contrast;

import shaders.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/postProcessing/contrast/contrastVertex.glsl";
	private static final String FRAGMENT_FILE = "/postProcessing/contrast/contrastFragment.glsl";
	
	private int location_contrast;
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_contrast = super.getUniformLocation("contrast");
	}
	
	public void loadContrast(float contrast) {
		super.loadFloat(location_contrast, contrast);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}