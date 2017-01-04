/**
 *
 * textureParams.java
 *
 * Simple class for setting up the textures for the textures
 * Assignment.
 *
 * Students are to complete this class.
 *
 */
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.*;

public class textureParams {

	/**
	 * constructor
	 */
	public textureParams() {

	}

	/**
	 * This functions loads texture data to the GPU.
	 *
	 * You will need to write this function, and maintain all of the values
	 * needed to be sent to the various shaders.
	 *
	 * @param filename
	 *            - The name of the texture file.
	 *
	 */
	BufferedImage bufferimage;
	IntBuffer buffer;

	public void loadTexture(String s) {

		// inserts the image into a buffer
		File file = new File(s);
		try {
			bufferimage = ImageIO.read(file);
			int pixels[] = bufferimage.getRGB(0, 0, bufferimage.getWidth(),
					bufferimage.getHeight(), null, 0, bufferimage.getWidth());
			buffer = IntBuffer.wrap(pixels);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * This functions sets up the parameters for texture use.
	 *
	 * You will need to write this function, and maintain all of the values
	 * needed to be sent to the various shaders.
	 *
	 * @param program
	 *            - The ID of an OpenGL (GLSL) program to which parameter values
	 *            are to be sent
	 *
	 * @param gl2
	 *            - GL2 object on which all OpenGL calls are to be made
	 *
	 */
	public void setUpTextures(int program, GL2 gl2) {
		// sets the parameters for texture
		gl2.glGenTextures(1, buffer);
		gl2.glActiveTexture(gl2.GL_TEXTURE0);
		gl2.glBindTexture(gl2.GL_TEXTURE_2D,
				gl2.glGetUniformLocation(program, "abc"));
		gl2.glTexParameteri(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_MIN_FILTER,
				gl2.GL_LINEAR);
		gl2.glTexParameteri(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_MAG_FILTER,
				gl2.GL_LINEAR);
		gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGBA,
				bufferimage.getWidth(), bufferimage.getHeight(), 0,
				gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, buffer);
	}
}