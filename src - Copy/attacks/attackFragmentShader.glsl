#version 400 core

in vec2 pass_textureCoordinates;
in float visibility;

out vec4 out_Color;

uniform sampler2D modelTexture;

void main(void) {
	vec4 textureColor = texture(modelTexture, pass_textureCoordinates);
	if (textureColor.a < 0.5){
		discard;
	}
	out_Color = textureColor + visibility;
}