package postProcessing.DOF;

import shaders.ShaderProgram;

public class DOFShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/postProcessing/contrast/contrastVertex.glsl";
	private static final String FRAGMENT_FILE = "/postProcessing/contrast/contrastFragment.glsl";
	
	private int location_dofDistance;
	
	public DOFShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_dofDistance = super.getUniformLocation("DOFDistance");
	}
	
	public void loadDepthDistance(float depth) {
		super.loadFloat(location_dofDistance, depth);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}