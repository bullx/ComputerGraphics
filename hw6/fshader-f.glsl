#version 120

// Flat shading fragment shader
uniform vec4 ambient;
uniform vec4 diffuse;
uniform vec4 specular;
uniform vec4 color;
uniform vec4 incominglightposition;
uniform float ka;
uniform float kd;
uniform float sreflection;
uniform float ks;
varying vec4 lightposition;
varying vec4 vertexposition;
varying vec4 vertexnormal;

void main()
{
	//normalizing the parameters
 	vec4 L_normalized = normalize (lightposition - vertexposition);
    vec4 N_normalized = normalize (vertexnormal);
    vec4 VP = normalize (vertexposition);
    vec4 R_normalized = L_normalized - 2.0 * N_normalized * dot(N_normalized,L_normalized);
    vec4 ambient1;
    vec4 diffuse1;
    //ambient light
    ambient1 =  ambient * ka; 
    //diffuse light
    diffuse1 =  color*kd* diffuse *(dot(N_normalized, L_normalized));
    //final color
    gl_FragColor = ambient1 + diffuse1 ;
 
}
