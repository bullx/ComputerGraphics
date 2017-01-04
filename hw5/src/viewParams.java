/**
 * viewParams.java
 *
 * Simple class for setting up the viewing and projection transforms
 * for the Transformation Assignment.
 *
 * Students are to complete this class.
 *
 */

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*;

import Jama.Matrix;

public class viewParams {
	float Left = -1.0f;
	float Right = 1.0f;
	float Top = 1.0f;
	float Bottom = -1.0f;
	float Near = 0.9f;
	float Far = 4.5f;

	/**
	 * constructor
	 */
	public viewParams() {

	}

	/**
	 * This function sets up the view and projection parameter for a frustum
	 * projection of the scene. See the assignment description for the values
	 * for the projection parameters.
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
	public void setUpFrustum(int program, GL2 gl2) {
		// array to store frustum matrix
		float frustrum[] = { (2 * Near) / (Right - Left), 0,
				(Right + Left) / (Right - Left), 0, 0,
				(2 * Near) / (Top - Bottom), (Top + Bottom) / (Top - Bottom),
				0, 0, 0, -(Far + Near) / (Far - Near),
				-2 * Far * Near / (Far - Near), 0, 0, -1, 0 };
		// send matrix to repsected variable
		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "store"), 1,
				true, frustrum, 0);

	}

	/**
	 * This function sets up the view and projection parameter for an
	 * orthographic projection of the scene. See the assignment description for
	 * the values for the projection parameters.
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
	public void setUpOrtho(int program, GL2 gl2) {
		// array to store ortho matrix values
		float ortho[] = { 2 / (Right - Left), 0, 0,
				-(Right + Left) / (Right - Left), 0, 2 / (Top - Bottom), 0,
				-(Top + Bottom) / (Top - Bottom), 0, 0, -2 / (Far - Near),
				-(Far + Near) / (Far - Near), 0, 0, 0, 1 };
		// send matrix to repsected variable
		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "store"), 1,
				true, ortho, 0);

	}

	/**
	 * This function clears any transformations, setting the values to the
	 * defaults: no scaling (scale factor of 1), no rotation (degree of rotation
	 * = 0), and no translation (0 translation in each direction).
	 *
	 * You will need to write this function, and maintain all of the values
	 * which must be sent to the vertex shader.
	 *
	 * @param program
	 *            - The ID of an OpenGL (GLSL) shader program to which parameter
	 *            values are to be sent
	 * @param gl2
	 *            - GL2 object on which all OpenGL calls are to be made
	 */
	public void clearTransforms(int program, GL2 gl2) {
		// array for storing matrix for z-axis rotate
		float matrix_z[] = { (float) Math.cos(Math.toRadians(0)),
				(float) ((-1) * Math.sin(Math.toRadians(0))), 0, 0,
				(float) Math.sin(Math.toRadians(0)),
				(float) Math.cos(Math.toRadians(0)), 0, 0, 0, 0, 1, 0, 0, 0, 0,
				1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "rotatez"), 1,
				true, matrix_z, 0);
		// array matrix for storing matrix for y-axis rotate
		float matrix_y[] = { (float) Math.cos(Math.toRadians(0)), 0,
				(float) Math.sin(Math.toRadians(0)), 0, 0, 1, 0, 0,
				(float) ((-1) * Math.sin(Math.toRadians(0))), 0,
				(float) Math.cos(Math.toRadians(0)), 0, 0, 0, 0, 1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "rotatey"), 1,
				true, matrix_y, 0);
		// array matrix for storing matrix for scaling
		float matrix_scale[] = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "scale"), 1,
				true, matrix_scale, 0);
		// array matrix for storing matrix for translation
		float matrix_translate[] = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0,
				0, 1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "translate"),
				1, true, matrix_translate, 0);

	}

	/**
	 * This function sets up the transformation parameters for the vertices of
	 * the teapot. The order of application is specified in the driver program.
	 *
	 * You will need to write this function, and maintain all of the values
	 * which must be sent to the vertex shader.
	 *
	 * @param program
	 *            - The ID of an OpenGL (GLSL) shader program to which parameter
	 *            values are to be sent
	 * @param gl2
	 *            - GL2 object on which all OpenGL calls are to be made
	 * @param xScale
	 *            - amount of scaling along the x-axis
	 * @param yScale
	 *            - amount of scaling along the y-axis
	 * @param zScale
	 *            - amount of scaling along the z-axis
	 * @param xRotate
	 *            - angle of rotation around the x-axis, in degrees
	 * @param yRotate
	 *            - angle of rotation around the y-axis, in degrees
	 * @param zRotate
	 *            - angle of rotation around the z-axis, in degrees
	 * @param xTranslate
	 *            - amount of translation along the x axis
	 * @param yTranslate
	 *            - amount of translation along the y axis
	 * @param zTranslate
	 *            - amount of translation along the z axis
	 */
	public void setUpTransforms(int program, GL2 gl2, float xScale,
			float yScale, float zScale, float xRotate, float yRotate,
			float zRotate, float xTranslate, float yTranslate, float zTranslate) {
		// array matrix for clearing matrix for z-axis rotate
		float matrix_z[] = { (float) Math.cos(Math.toRadians(zRotate)),
				(float) ((-1) * Math.sin(Math.toRadians(zRotate))), 0, 0,
				(float) Math.sin(Math.toRadians(zRotate)),
				(float) Math.cos(Math.toRadians(zRotate)), 0, 0, 0, 0, 1, 0, 0,
				0, 0, 1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "rotatez"), 1,
				true, matrix_z, 0);
		// array matrix for clearing matrix for y-axis rotate
		float matrix_y[] = { (float) Math.cos(Math.toRadians(yRotate)), 0,
				(float) Math.sin(Math.toRadians(yRotate)), 0, 0, 1, 0, 0,
				(float) ((-1) * Math.sin(Math.toRadians(yRotate))), 0,
				(float) Math.cos(Math.toRadians(yRotate)), 0, 0, 0, 0, 1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "rotatey"), 1,
				true, matrix_y, 0);
		// array matrix for clearing matrix for z-axis rotate
		float matrix_scale[] = { xScale, 0, 0, 0, 0, yScale, 0, 0, 0, 0,
				zScale, 0, 0, 0, 0, 1 };

		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "scale"), 1,
				true, matrix_scale, 0);
		// array matrix for clearing matrix for scaling
		float matrix_translate[] = { 1, 0, 0, xTranslate, 0, 1, 0, yTranslate,
				0, 0, 1, zTranslate, 0, 0, 0, 1 };
		// array matrix for clearing matrix for translate
		gl2.glUniformMatrix4fv(gl2.glGetUniformLocation(program, "translate"),
				1, true, matrix_translate, 0);

		// Add your code here.
	}

	/**
	 * This function clears any changes made to camera parameters, setting the
	 * values to the defaults: eyepoint (0.0,0.0,0.0), lookat (0,0,0.0,-1.0),
	 * and up vector (0.0,1.0,0.0).
	 *
	 * You will need to write this function, and maintain all of the values
	 * which must be sent to the vertex shader.
	 *
	 * @param program
	 *            - The ID of an OpenGL (GLSL) shader program to which parameter
	 *            values are to be sent
	 * @param gl2
	 *            - GL2 object on which all OpenGL calls are to be made
	 */
	void clearCamera(int program, GL2 gl2) {
		// array matrix for clearing matrix for Eye
		float eye[] = { 0, 0, 0 };
		gl2.glUniform3fv(gl2.glGetUniformLocation(program, "Eye"), 1, eye, 0);
		// array matrix for clearing matrix for look
		float look[] = { 0, 0, -1 };
		gl2.glUniform3fv(gl2.glGetUniformLocation(program, "Look"), 1, look, 0);
		// array matrix for clearing matrix for upvector
		float up[] = { 0, 1, 0 };
		gl2.glUniform3fv(gl2.glGetUniformLocation(program, "UpVector"), 1, up,
				0);
		// Add your code here.
	}

	/**
	 * This function sets up the camera parameters controlling the viewing
	 * transformation.
	 *
	 * You will need to write this function, and maintain all of the values
	 * which must be sent to the vertex shader.
	 *
	 * @param program
	 *            - The ID of an OpenGL (GLSL) shader program to which parameter
	 *            values are to be sent
	 * @param gl2
	 *            - GL2 object on which all OpenGL calls are to be made
	 * @param eyepointX
	 *            - x coordinate of the camera location
	 * @param eyepointY
	 *            - y coordinate of the camera location
	 * @param eyepointZ
	 *            - z coordinate of the camera location
	 * @param lookatX
	 *            - x coordinate of the lookat point
	 * @param lookatY
	 *            - y coordinate of the lookat point
	 * @param lookatZ
	 *            - z coordinate of the lookat point
	 * @param upX
	 *            - x coordinate of the up vector
	 * @param upY
	 *            - y coordinate of the up vector
	 * @param upZ
	 *            - z coordinate of the up vector
	 */
	void setUpCamera(int program, GL2 gl2, float eyepointX, float eyepointY,
			float eyepointZ, float lookatX, float lookatY, float lookatZ,
			float upX, float upY, float upZ) {
		// array matrix for setup of eye
		float eye[] = { eyepointX, eyepointY, eyepointZ };
		gl2.glUniform3fv(gl2.glGetUniformLocation(program, "Eye"), 1, eye, 0);
		// array matrix for setup of look
		float look[] = { lookatX, lookatY, lookatZ };
		gl2.glUniform3fv(gl2.glGetUniformLocation(program, "Look"), 1, look, 0);
		// array matrix for setup of upvector
		float up[] = { upX, upY, upZ };
		gl2.glUniform3fv(gl2.glGetUniformLocation(program, "UpVector"), 1, up,
				0);

		// Add your code here.
	}

}
