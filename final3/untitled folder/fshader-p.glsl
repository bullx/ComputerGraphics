#version 120

// Phong shading fragment shader
uniform vec4 color;
uniform vec4 diffuse;
uniform float kd;
uniform float ks;
uniform vec4 specular;
uniform vec4 ambient;
uniform float ka;
uniform float sreflection;
varying vec4 lightposition;
varying vec4 vertexposition;
varying vec4 vertexnormal;
uniform sampler2D texture;
varying vec2 texCoord;

void main()
{
//normalizing the parameters
 vec4 L = normalize (lightposition - vertexposition);
    vec4 N = normalize (vertexnormal);
      vec4 V = normalize (vertexposition);
    vec4 R = normalize( reflect(L,N) );
    //	calculation of ambient,diffuse,specular light
     vec4 specular1;
    vec4 diffuse1 =color*kd* diffuse * (dot(N, L));
    vec4 ambient1= ambient *ka;
	if((dot(R,V)) < 0)
    {
    specular1 =  color*specular * 0;
	}
	else
	{
	specular1 = color* specular * ks *pow((dot(R,V)),sreflection);
	}
//output color
    gl_FragColor = diffuse1 +specular1+ambient1*texture2D(texture, texCoord);
}
