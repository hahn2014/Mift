package guis;

public class VertexAttrib {
	public final String name;
	public final int componentCount;
	public final int location; // Index for OpenGL

	public VertexAttrib(int loc, String name, int compNum) {
		this.location = loc;
		this.name = name;
		this.componentCount = compNum;
	}

	@Override
	public String toString() {
		return "VertexAttrib{name=" + name + ", componentCount=" + componentCount + ", location=" + location + "}";
	}
}
