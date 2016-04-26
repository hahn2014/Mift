#version 400 core

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D colourTexture;
uniform sampler2D depthTexture;
uniform float DOFDistance;
 
const float blurclamp = 3.0;
const float bias = 0.6;
 

void main() {
 
        float aspectratio 	= 800.0 / 600.0;
        vec2 aspectcorrect 	= vec2(1.0, aspectratio);
        vec4 depth1 		= texture2D(depthTexture, gl_TexCoord[0].xy);
        float factor 		= (depth1.x - DOFDistance);
        vec2 dofblur 		= vec2 (clamp(factor * bias, -blurclamp, blurclamp));
        
        vec4 col = vec4(0.0);
        
        col += texture2D(colourTexture, gl_TexCoord[0].xy);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.0,0.4 )	* aspectcorrect) * dofblur);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.15,0.37 )	* aspectcorrect) * dofblur);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.29,0.29 )	* aspectcorrect) * dofblur);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.37,0.15 )	* aspectcorrect) * dofblur);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.4,0.0 )	* aspectcorrect) * dofblur);   
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.37,-0.15 )	* aspectcorrect) * dofblur);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.29,-0.29 )	* aspectcorrect) * dofblur);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.15,-0.37)	* aspectcorrect) * dofblur);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.0,-0.4 )	* aspectcorrect) * dofblur); 
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.15,0.37 )	* aspectcorrect) * dofblur);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.29,0.29 )	* aspectcorrect) * dofblur);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.37,0.15 )	* aspectcorrect) * dofblur); 
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.4,0.0 )	* aspectcorrect) * dofblur); 
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.37,-0.15)	* aspectcorrect) * dofblur);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.29,-0.29)	* aspectcorrect) * dofblur);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.15,-0.37 )	* aspectcorrect) * dofblur);
       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.15,0.37 )	* aspectcorrect) * dofblur * 0.9);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.37,0.15 )	* aspectcorrect) * dofblur * 0.9);           
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.37,-0.15 )	* aspectcorrect) * dofblur * 0.9);           
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.15,-0.37)	* aspectcorrect) * dofblur * 0.9);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.15,0.37 )	* aspectcorrect) * dofblur * 0.9);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.37,0.15 )	* aspectcorrect) * dofblur * 0.9);            
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.37,-0.15)	* aspectcorrect) * dofblur * 0.9);   
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.15,-0.37 )	* aspectcorrect) * dofblur * 0.9);   
       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.29,0.29 )	* aspectcorrect) * dofblur * 0.7);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.4,0.0 )	* aspectcorrect) * dofblur * 0.7);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.29,-0.29 )	* aspectcorrect) * dofblur * 0.7);   
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.0,-0.4 )	* aspectcorrect) * dofblur * 0.7);     
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.29,0.29 )	* aspectcorrect) * dofblur * 0.7);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.4,0.0 )	* aspectcorrect) * dofblur * 0.7);     
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.29,-0.29)	* aspectcorrect) * dofblur * 0.7);   
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.0,0.4 )	* aspectcorrect) * dofblur * 0.7);
                         
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.29,0.29 )	* aspectcorrect) * dofblur * 0.4);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.4,0.0 )	* aspectcorrect) * dofblur * 0.4);       
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.29,-0.29 )	* aspectcorrect) * dofblur * 0.4);   
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.0,-0.4 )	* aspectcorrect) * dofblur * 0.4);     
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.29,0.29 )	* aspectcorrect) * dofblur * 0.4);
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.4,0.0 )	* aspectcorrect) * dofblur * 0.4);     
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( -0.29,-0.29)	* aspectcorrect) * dofblur * 0.4);   
        col += texture2D(colourTexture, gl_TexCoord[0].xy + (vec2( 0.0,0.4 )	* aspectcorrect) * dofblur * 0.4);       
        
        out_Colour = col / 41.0;
        out_Colour.a = 1.0;
}