//
//  cgShape.java
//
//  Class that includes routines for tessellating a number of basic shapes.
//
//  Students are to supply their implementations for the functions in
//  this file using the function "addTriangle()" to do the tessellation.
//

import java.awt.*;
import java.nio.*;
import java.util.ArrayList;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import java.io.*;

public class cgShape extends simpleShape {
	/**
	 * constructor
	 */
	public cgShape() {
	}

	/**
	 * makeCube - Create a unit cube, centered at the origin, with a given
	 * number of subdivisions in each direction on each face.
	 *
	 * @param subdivision
	 *            - number of equal subdivisons to be made in each direction
	 *            along each face
	 *
	 *            Can only use calls to addTriangle()
	 */
	public void makeCube(int subdivisions) {
		if (subdivisions < 1) {
			subdivisions = 1;
		}
		float x, y, z, n;
		n = 0;
		n = (float) (1.0 / subdivisions);
		System.out.println(subdivisions + " " + n);
		// z is negative
		x = y = -0.5f;

		z = -0.5f;
		int cd = 0, cd1 = 0;
		while (cd1 < subdivisions) {
			cd = 0;
			while (cd < subdivisions) {
				addTriangle(x, y, z, x + n, y + n, z, x + n, y, z);
				addTriangle(x, y, z, x, y + n, z, x + n, y + n, z);

				x = x + n;
				cd++;
			}
			x = -0.5f;
			y = y + n;
			cd1++;
		}

		// z is positive
		x = y = -0.5f;
		z = 0.5f;
		cd1 = 0;
		while (cd1 < subdivisions) {
			cd = 0;
			while (cd < subdivisions) {
				addTriangle(x, y, z, x + n, y, z, x + n, y + n, z);
				addTriangle(x, y, z, x + n, y + n, z, x, y + n, z);
				x = x + n;
				cd++;
			}
			x = -0.5f;
			y = y + n;

			cd1++;
		}

		// when x is negative
		z = y = -0.5f;
		x = -0.5f;
		cd1 = cd = 0;
		while (cd1 < subdivisions) {
			cd = 0;
			while (cd < subdivisions) {
				addTriangle(x, y, z, x, y, z + n, x, y + n, z + n);
				addTriangle(x, y, z, x, y + n, z + n, x, y + n, z);

				z = z + n;
				cd++;
			}
			z = -0.5f;
			y = y + n;
			cd1++;
		}
		// when x is positive
		z = y = -0.5f;
		x = 0.5f;
		cd1 = cd = 0;
		while (cd1 < subdivisions) {
			cd = 0;
			while (cd < subdivisions) {
				addTriangle(x, y, z, x, y + n, z + n, x, y, z + n);
				addTriangle(x, y, z, x, y + n, z, x, y + n, z + n);

				z = z + n;
				cd++;
			}
			z = -0.5f;
			y = y + n;
			cd1++;
		}

		// when y is negative
		z = x = -0.5f;
		y = -0.5f;
		cd1 = cd = 0;
		while (cd1 < subdivisions) {
			cd = 0;
			while (cd < subdivisions) {
				addTriangle(x, y, z, x + n, y, z + n, x, y, z + n);
				addTriangle(x, y, z, x + n, y, z, x + n, y, z + n);

				z = z + n;
				cd++;
			}
			z = -0.5f;
			x = x + n;
			cd1++;
		}
		// when y is positive
		z = x = -0.5f;
		cd1 = cd = 0;
		y = 0.5f;
		while (cd1 < subdivisions) {
			cd = 0;
			while (cd < subdivisions) {
				addTriangle(x, y, z, x, y, z + n, x + n, y, z + n);
				addTriangle(x, y, z, x + n, y, z + n, x + n, y, z);
				z = z + n;
				cd++;
			}
			z = -0.5f;
			x = x + n;
			cd1++;
		}

	}

	/**
	 * makeCylinder - Create polygons for a cylinder with unit height, centered
	 * at the origin, with separate number of radial subdivisions and height
	 * subdivisions.
	 *
	 * @param radius
	 *            - Radius of the base of the cylinder
	 * @param radialDivision
	 *            - number of subdivisions on the radial base
	 * @param heightDivisions
	 *            - number of subdivisions along the height
	 *
	 *            Can only use calls to addTriangle()
	 */
	public void makeCylinder(float radius, int radialDivisions,
			int heightDivisions) {
		System.out.println(radius + " " + radialDivisions + " "
				+ heightDivisions);
		if (radialDivisions < 3)
			radialDivisions = 3;

		if (heightDivisions < 1)
			heightDivisions = 1;
		float x = 0, y, z = 0, n;
		int i;
		n = i = 0;
		n = (float) (1.0 / heightDivisions);
		float angle = 0;
		;
		float m = (float) 360 / radialDivisions;
		m = (float) (m * Math.PI / 180);
		y = -0.5f;
		// cylinder bottom
		while (i < radialDivisions) {
			addTriangle(x, y, z, (float) (radius * Math.cos(angle)), y,
					(float) (radius * Math.sin(angle)),
					(float) (radius * Math.cos(angle + m)), y,
					(float) (radius * Math.sin(angle + m)));
			angle = angle + m;
			i++;
		}
		// top circle of cylinder
		y = 0.5f;
		x = z = angle = 0.0f;
		int j = 0;
		while (j < radialDivisions) {
			addTriangle((float) (radius * Math.cos(angle + m)), y,
					(float) (radius * Math.sin(angle + m)),
					(float) (radius * Math.cos(angle)), y,
					(float) (radius * Math.sin(angle)), x, y, z);
			angle = angle + m;
			j++;
		}
		// loop for making triangles of quadilateral
		int uu = 0;
		while (uu < radialDivisions) {
			y = 0.5f;
			int ww = 0;
			while (ww < heightDivisions) {
				addTriangle((float) (radius * Math.cos(angle)), y - n,
						(float) (radius * Math.sin(angle)),
						(float) (radius * Math.cos(angle)), y,
						(float) (radius * Math.sin(angle)),
						(float) (radius * Math.cos(angle + m)), y,
						(float) (radius * Math.sin(angle + m)));

				addTriangle((float) (radius * Math.cos(angle)), y - n,
						(float) (radius * Math.sin(angle)),
						(float) (radius * Math.cos(angle + m)), y,
						(float) (radius * Math.sin(angle + m)),
						(float) (radius * Math.cos(angle + m)), y - n,
						(float) (radius * Math.sin(angle + m)));
				y = y - n;
				ww++;
			}
			// angle incrementing
			angle = angle + m;
			uu++;
		}

	}

	/**
	 * makeCone - Create polygons for a cone with unit height, centered at the
	 * origin, with separate number of radial subdivisions and height
	 * subdivisions.
	 *
	 * @param radius
	 *            - Radius of the base of the cone
	 * @param radialDivision
	 *            - number of subdivisions on the radial base
	 * @param heightDivisions
	 *            - number of subdivisions along the height
	 *
	 *            Can only use calls to addTriangle()
	 */
	public void makeCone(float radius, int radialDivisions, int heightDivisions) {
		if (radialDivisions < 3)
			radialDivisions = 3;

		if (heightDivisions < 1)
			heightDivisions = 1;

		float h = (float) 1.0 / heightDivisions;
		float aa, bb, cc, dd;
		float angle = 0;
		float r = (float) ((float) 360 / radialDivisions * Math.PI / 180);
		int i = 0;
		float y = -0.5f;

		// base of cone
		while (i < radialDivisions) {
			cc = (float) (radius * Math.cos(angle));
			dd = (float) (radius * Math.sin(angle));
			aa = (float) (radius * Math.cos(angle + r));
			bb = (float) (radius * Math.sin(angle + r));
			addTriangle(aa, y, bb, cc, y, dd, 0, y, 0);
			angle = angle + r;
			i++;
		}

		int tt = 0;
		while (tt < radialDivisions) {
			y = -0.5f;
			cc = (float) (radius * Math.cos(angle));
			dd = (float) (radius * Math.sin(angle));
			aa = (float) (radius * Math.cos(angle + r));
			bb = (float) (radius * Math.sin(angle + r));
			int ct = 1;
			float k = 0;
			while (y < 0.5) {
				// for making triangles of cone
				if (y + h < 0.5) {
					k = h * ct;
					addTriangle(cc, y, dd, aa, y, bb,
							(float) ((1 - (k)) * Math.cos(angle)) * radius, y
									+ h, (float) ((1 - (k)) * Math.sin(angle))
									* radius);
					addTriangle(aa, y, bb,
							(float) ((1 - (k)) * Math.cos(angle + r)) * radius,
							y + h, (float) ((1 - (k)) * Math.sin(angle + r))
									* radius,
							(float) ((1 - (k)) * Math.cos(angle)) * radius, y
									+ h, (float) ((1 - (k)) * Math.sin(angle))
									* radius);

				} else {
					// after iterating and setting x and z to zero
					// if y i more than 0.5
					addTriangle(cc, y, dd, aa, y, bb, 0, y + h, 0);
					addTriangle(aa, y, bb, 0, y + h, 0, 0, y + h, 0);
				}
				// updating values
				cc = (float) ((1 - k) * Math.cos(angle)) * radius;
				dd = (float) ((1 - k) * Math.sin(angle)) * radius;
				aa = (float) ((1 - k) * Math.cos(angle + r)) * radius;
				bb = (float) ((1 - k) * Math.sin(angle + r)) * radius;
				y = y + h;
				ct++;
			}
			// angle incrementing
			angle += r;
			tt++;
		}

	}

	/**
	 * makeSphere - Create sphere of a given radius, centered at the origin,
	 * using spherical coordinates with separate number of thetha and phi
	 * subdivisions.
	 *
	 * @param radius
	 *            - Radius of the sphere
	 * @param slides
	 *            - number of subdivisions in the theta direction
	 * @param stacks
	 *            - Number of subdivisions in the phi direction.
	 *
	 *            Can only use calls to addTriangle
	 */
	public void makeSphere(float radius, int slices, int stacks) {
		if (slices < 3)
			slices = 3;

		if (stacks < 3)
			stacks = 3;
		float q = 0.5f / stacks;
		float aa, bb, cc, dd;
		float angle = 0.0f;
		float m = 360.0f / slices;
		m = (float) (m * Math.PI / 180);
		int i = 0;
		int p = slices;
		while (i < p) {
			aa = bb = cc = dd = 0;
			float yn = 0.5f;
			while (yn > -0.5) {
				if (yn - q > -0.5) {
					float multiplier = (float) Math.sqrt(radius * radius - yn
							* yn);
					// first triangle
					addTriangle(aa, yn, cc, bb, yn, dd,
							(float) ((float) multiplier * Math.cos(angle)), yn
									- q,
							(float) ((float) multiplier * Math.sin(angle)));
					// second triangle
					addTriangle(bb, yn, dd,
							(float) (multiplier * Math.cos(angle + m)), yn - q,
							(float) (multiplier * Math.sin(angle + m)),
							(float) ((float) multiplier * Math.cos(angle)), yn
									- q,
							(float) ((float) multiplier * Math.sin(angle)));
				} else {
					// adding triangles
					addTriangle(aa, yn, cc, bb, yn, dd, 0, yn - q, 0);
					addTriangle(bb, yn, dd, 0, yn - q, 0, 0, yn - q, 0);
				}
				float multiplier = (float) Math.sqrt(radius * radius - yn * yn);
				aa = (float) ((float) (multiplier) * Math.cos(angle));
				bb = (float) ((float) (multiplier) * Math.cos(angle + m));
				cc = (float) ((float) multiplier * Math.sin(angle));
				dd = (float) ((float) multiplier * Math.sin(angle + m));
				yn = yn - q;
			}
			angle = angle + m;
			i++;
		}

	}

}
