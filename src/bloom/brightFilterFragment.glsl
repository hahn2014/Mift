#version 400 core

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D colourTexture;

void main(void) {
	vec4 color = texture(colourTexture, textureCoords);
	float brightness = (color.r * 0.2126) + (color.g * .7152) + (color.b * 0.0722);
	out_Colour = color * brightness;
}