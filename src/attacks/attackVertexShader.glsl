#version 400 core

in vec3 position;
in mat4 modelViewMatrix;
in vec4 textureOffsets;

uniform mat4 projectionMatrix;
uniform float numberOfRows;

void main(void) {
	vec2 textureCoords = position.xy + vec2(0.5, 0.5);
	textureCoords.y = 1.0 - textureCoords.y;
	textureCoords /= numberOfRows;
	
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
}