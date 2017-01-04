#version 120

// Gouraud shading fragment shader
varying vec4 outcolor;
uniform sampler2D texture;
varying vec2 texCoord;

void main()
{	//output color
    gl_FragColor = outcolor *texture2D(texture, texCoord);
}
