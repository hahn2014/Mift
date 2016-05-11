package guis;

import java.nio.FloatBuffer;

public interface VertexData {
	void bind();
	void draw(int goem, int first, int count);
	void unbinder();

	VertexData clear();
	VertexData flip();
	VertexData put(float[] verts, int offset, int length);
	VertexData put(float v);

	FloatBuffer buffer();

	int getComponentCount();
	int getVertexCount();
}
