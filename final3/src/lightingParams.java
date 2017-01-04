//
// lightingParams.java
//
// Simple class for setting up the viewing and projection transforms
// for the Shading Assignment.
//
// Students are to complete this class.
//

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*;

public class lightingParams {
	// Add any global class variables you need here.
	private float ambientcolor[] = { 0.5f, 0.5f, 0.4f, 1.0f };
	private float diffusecolor[] = { 0.9f, 0.0f, 0.0f, 1.0f };
	private float specularcolor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float ka = 0.2f;
	private float kd = 0.2f;
	private float sreflection = 80.0f;
	private float ks = 1.0f;
	private float lightcolor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float lightposition[] = { 	0.0f, 5.0f, 10.0f, 1.0f };

	/**
	 * constructor
	 */
	public lightingParams() {
	}

	/**
	 * This functions sets up the lighting, material, and shading parameters for
	 * the Phong shader.
	 *
	 * You will need to write this function, and maintain all of the values
	 * needed to be sent to the vertex shader.
	 *
	 * @param program
	 *            - The ID of an OpenGL (GLSL) shader program to which parameter
	 *            values are to be sent
	 *
	 * @param gl2
	 *            - GL2 object on which all OpenGL calls are to be made
	 *
	 */
	public void setUpPhong(int program, GL2 gl2) {
		// getting locations of the variables
		int ambient = gl2.glGetUniformLocation(program, "ambient");
		int diffuse = gl2.glGetUniformLocation(program, "diffuse");
		int spec = gl2.glGetUniformLocation(program, "specular");
		int color1 = gl2.glGetUniformLocation(program, "color");
		int position1 = gl2.glGetUniformLocation(program,
				"incominglightposition");
		// passing the global declared variables
		gl2.glUniform4fv(ambient, 1, ambientcolor, 0);
		gl2.glUniform4fv(diffuse, 1, diffusecolor, 0);
		gl2.glUniform4fv(spec, 1, specularcolor, 0);
		gl2.glUniform4fv(color1, 1, lightcolor, 0);
		gl2.glUniform4fv(position1, 1, lightposition, 0);
		gl2.glUniform1f(gl2.glGetUniformLocation(program, "ka"), ka);
		gl2.glUniform1f(gl2.glGetUniformLocation(program, "kd"), kd);
		gl2.glUniform1f(gl2.glGetUniformLocation(program, "ks"), ks);
		gl2.glUniform1f(gl2.glGetUniformLocation(program, "sreflection"),
				sreflection);
	}
}
