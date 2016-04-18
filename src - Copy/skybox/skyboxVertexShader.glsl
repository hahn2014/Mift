#version 400 core

in vec3 position;
out vec3 textureCoords;
out float visibility;

const float density = 0.0008;
const float gradient = 1.9;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void){
	
	vec4 positionRelativeToCam = viewMatrix * vec4(position, 1.0); 
	gl_Position = projectionMatrix * positionRelativeToCam; 
	textureCoords = position;
	
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density),gradient));
	visibility = clamp(visibility,0.0,1.0);
}