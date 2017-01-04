#version 120

attribute vec4 vPosition;
uniform vec3 theta;
uniform mat4 store;
uniform mat4 rotatez;
uniform mat4 rotatey;
uniform mat4 scale;
uniform mat4 translate;
uniform mat4 identity;
uniform vec3 Eye;
uniform vec3 Look;
uniform vec3 UpVector;
uniform float count;
void main()
{
    
 	vec3 a = normalize(Eye - Look);
	vec3 b = normalize(cross(UpVector,a));
	vec3 c = cross(a,b);
	
	mat4 view_transform = mat4(b.x,c.x,a.x, 0,
							  b.y,c.y,a.y, 0,
							  b.z,c.z,a.z, 0,
							  dot(-b,Eye), dot(-c,Eye),dot(-a,Eye), 1.0);

if(count ==1.0){
  gl_Position=  vPosition;
  }
  else if(count==2.0 )
  {
  
  gl_Position= view_transform*rotatez*rotatey*translate* vPosition;
  }
  
}
