#version 120

// Gouraud shading fragment shader
varying vec4 outcolor;
void main()
{	//output color
    gl_FragColor = outcolor;
}
