#version 400 core

out vec4 out_color;
in vec2 textureCoords;

uniform sampler2D modelTexture;

void main(void){
	vec4 textureColor = texture(modelTexture, textureCoords);
	out_color = textureColor;
}