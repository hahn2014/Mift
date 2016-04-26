#version 400 core

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D colourTexture;
uniform float contrast;

void main(void) {

	out_Colour = texture(colourTexture, textureCoords);
	out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;
}