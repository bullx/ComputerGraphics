//
//  cgCanvas.java
//
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 Rochester Institute of Technology. All rights reserved.
//

/**
 * This is a simple canvas class for adding functionality for the
 * 2D portion of Computer Graphics.
 *
 */

import Jama.*;

import java.util.*;

public class cgCanvas extends simpleCanvas {
	/**
	 * Declaration of variables
	 */
	int id = 0;
	HashMap<Integer, polygon> h = new HashMap<Integer, polygon>();
	float x0, y0, x1, y1, view_y, view_width, view_height;
	float view_x = 0;
	Matrix m;
//	Rasterizer r = new Rasterizer(601);
	Matrix trans = Matrix.identity(3, 3);
	Matrix scale = Matrix.identity(3, 3);
	Matrix view = Matrix.identity(3, 3);

	/**
	 * Constructor
	 *
	 * @param w
	 *            width of canvas
	 * @param h
	 *            height of canvas
	 */
	cgCanvas(int w, int h) {

		super(w, h);
		// YOUR IMPLEMENTATION HERE if you need to modify the constructor

	}

	/**
	 * addPoly - Adds and stores a polygon to the canvas. Note that this method
	 * does not draw the polygon, but merely stores it for later draw. Drawing
	 * is initiated by the draw() method.
	 *
	 * Returns a unique integer id for the polygon.
	 *
	 * @param x
	 *            - Array of x coords of the vertices of the polygon to be added
	 * @param y
	 *            - Array of y coords of the vertices of the polygin to be added
	 * @param n
	 *            - Number of verticies in polygon
	 *
	 * @return a unique integer identifier for the polygon
	 */
	public int addPoly(float x[], float y[], int n) {
		// YOUR IMPLEMENTATION HERE
		// store in array of float the passed polygon values
		float[] a = new float[x.length];
		float[] b = new float[y.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = x[i];
			b[i] = y[i];
		}
		// put into polygon
		polygon p = new polygon(a, b, n);
		h.put(id, p);
		id++;

		// REMEMBER TO RETURN A UNIQUE ID FOR THE POLYGON
		return id - 1;
	}

	/**
	 * drawPoly - Draw the polygon with the given id. Should draw the polygon
	 * after applying the current transformation on the vertices of the polygon.
	 *
	 * @param polyID
	 *            - the ID of the polygon to be drawn
	 */
	public void drawPoly(int polyID) {

		if (polyID == 4) {
			clearTransform();
		}

		polygon p = h.get(polyID);
		// initilization of matrix

		float result_x[], result_y[];
		result_x = new float[100];
		result_y = new float[100];
		clipper c = new clipper();
		// sent to clipping method for clipping the polygon
		int length = c.clipPolygon(p.n, p.a, p.b, result_x, result_y, x0, y0,
				x1, y1);
		int u = 0;
		// multiplying the values passed in matrix and giving the output for
		// printing
		ArrayList<Matrix> al = new ArrayList<Matrix>();
		ArrayList<Matrix> al1 = new ArrayList<Matrix>();
		ArrayList<Matrix> al2 = new ArrayList<Matrix>();
		ArrayList<Matrix> al3 = new ArrayList<Matrix>();
		Matrix m1, m2 = null, m3, m4, new_input, temp2, temp3, temp4 = null;
		// initialize the matrix with passed co-ordinates
		while (u < length) {
			double matrix[][] = { { result_x[u] }, { result_y[u] }, { 1 } };
			m1 = new Matrix(matrix);
			al.add(m1);
			u++;

		}
		int f = 0;
		// multiplying m with passed matrix from above
		while (f < al.size()) {
			new_input = m.times(al.get(f));
			m2 = setter(al.get(f), new_input);
			al1.add(m2);
			f++;
		}
		int e = 0;
		// for shifting
		while (e < al1.size()) {
			trans.set(0, 2, -1 * x0);
			trans.set(1, 2, -1 * y0);
			temp2 = trans.times(al1.get(e));
			m3 = setter(al1.get(e), temp2);
			al2.add(m3);
			e++;
		}
		int w = 0;
		while (w < al2.size()) {
			// scaling the size
			float a1 = view_width / (x1 - x0);
			float b1 = view_height / (y1 - y0);
			scale.set(0, 0, a1);
			scale.set(1, 1, b1);
			temp3 = scale.times(al2.get(w));
			m4 = setter(al1.get(w), temp3);
			al3.add(m4);

			w++;
		}
		int z = 0;
		// for changing viewport and putting into the array for printing
		while (z < al3.size()) {
			view.set(0, 2, view_x);
			view.set(1, 2, view_y);
			temp4 = view.times(al3.get(z));
			result_x[z] = (int) temp4.get(0, 0);
			result_y[z] = (int) temp4.get(1, 0);
			z++;
		}

		// Calling the draw polygon method
		drawPolygon(length, result_x, result_y, this);

	}

	/**
	 * clearTransform - Set the current transformation to the identity matrix.
	 */
	public void clearTransform() {
		// YOUR IMPLEMENTATION HERE
		double[][] identity = new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } };
		m = new Matrix(identity);

	}

	/**
	 * translate - Add a translation to the current transformation by
	 * pre-multiplying the appropriate translation matrix to the current
	 * transformation matrix.
	 *
	 * @param x
	 *            - Amount of translation in x
	 * @param y
	 *            - Amount of translation in y
	 */
	public void translate(float x, float y) {
		// YOUR IMPLEMENTATION HERE
		double matrix[][] = { { 1, 0, x }, { 0, 1, y }, { 0, 0, 1 } };

		Matrix trans = new Matrix(matrix);
		m = trans.times(m);
	}

	/**
	 * rotate - Add a rotation to the current transformation by pre-multiplying
	 * the appropriate rotation matrix to the current transformation matrix.
	 *
	 * @param degrees
	 *            - Amount of rotation in degrees
	 */
	public void rotate(float degrees) {
		// YOUR IMPLEMENTATION HERE

		double matrix1[][] = {
				{ Math.cos(Math.toRadians(degrees)),
						(-1) * Math.sin(Math.toRadians(degrees)), 0 },
				{ Math.sin(Math.toRadians(degrees)),
						Math.cos(Math.toRadians(degrees)), 0 }, { 0, 0, 1 } };
		Matrix rotate = new Matrix(matrix1);
		m = rotate.times(m);

	}

	/**
	 * scale - Add a scale to the current transformation by pre-multiplying the
	 * appropriate scaling matrix to the current transformation matrix.
	 *
	 * @param x
	 *            - Amount of scaling in x
	 * @param y
	 *            - Amount of scaling in y
	 */
	public void scale(float x, float y) {
		// YOUR IMPLEMENTATION HERE

		double matrix[][] = { { x, 0, 0 }, { 0, y, 0 }, { 0, 0, 1 } };
		Matrix scale = new Matrix(matrix);
		m = scale.times(m);

	}

	/**
	 * setClipWindow - defines the clip window
	 *
	 * @param bottom
	 *            - y coord of bottom edge of clip window (in world coords)
	 * @param top
	 *            - y coord of top edge of clip window (in world coords)
	 * @param left
	 *            - x coord of left edge of clip window (in world coords)
	 * @param right
	 *            - x coord of right edge of clip window (in world coords)
	 */
	public void setClipWindow(float bottom, float top, float left, float right) {
		y0 = bottom;
		x0 = left;
		y1 = top;
		x1 = right;
	}

	/**
	 * setViewport - defines the viewport
	 *
	 * @param xmin
	 *            - x coord of lower left of view window (in screen coords)
	 * @param ymin
	 *            - y coord of lower left of view window (in screen coords)
	 * @param width
	 *            - width of view window (in world coords)
	 * @param height
	 *            - width of view window (in world coords)
	 */
	public void setViewport(int x, int y, int width, int height) {
		// YOUR IMPLEMENTATION HERE
		view_x = x;
		view_y = y;
		view_width = width;
		view_height = height;
	}

	/**
	 * Sets in matrix
	 * 
	 * @param m1
	 * @param input
	 * @return the matrix with values
	 */
	public Matrix setter(Matrix m1, Matrix input) {
		double aa = input.get(0, 0);
		double bb = input.get(1, 0);
		m1.set(0, 0, aa);
		m1.set(1, 0, bb);

		return m1;
	}
	
	public void drawPolygon(int n, float x[], float y[], simpleCanvas C) {

		ArrayList<Store> l = new ArrayList<Store>();
		ArrayList<Store> global = new ArrayList<Store>();
		ArrayList<Store> active = new ArrayList<Store>();
		float a[] = x;
		float b[] = y;
		Store s;
		float ymin;
		float ymax;
		float slope;
		float xval;
		int i = 0;
		float y_hi = 0;
		// loop for calculating edge values
		while (i <= n - 1) {
			if (i < n - 1) {
				if (b[i] < b[i + 1]) {
					ymin = b[i];
					ymax = b[i + 1];
					xval = a[i];
					if (b[i + 1] - b[i] != 0) {

						slope = ((float) (a[i + 1] - a[i]) / (b[i + 1] - b[i]));

					} else {
						slope = 999999;
					}
					// add if slope is not infinity here
					if (slope != 999999) {
						s = new Store(ymin, ymax, slope, xval);
						l.add(s);
					}
				} else {
					ymin = b[i + 1];
					ymax = b[i];
					xval = a[i + 1];
					if ((b[i] - b[i + 1]) != 0) {
						slope = ((float) (a[i] - a[i + 1]) / (b[i] - b[i + 1]));

					} else {
						slope = 999999;
					}
					// add if slope is not infinity here

					if (slope != 999999) {

						s = new Store(ymin, ymax, slope, xval);
						l.add(s);
					}
				}
			}

			else if (i == n - 1) {
				if (b[i] < b[0]) {
					ymin = b[i];
					ymax = b[0];
					xval = a[i];

					if (b[0] - b[i] != 0) {
						slope = ((float) (a[0] - a[i]) / (b[0] - b[i]));

					} else {
						slope = 999999;
					}
					// add if slope is not infinity here

					if (slope != 999999) {
						s = new Store(ymin, ymax, slope, xval);
						l.add(s);
					}

				} else {
					ymin = b[0];
					ymax = b[i];
					xval = a[0];
					if (b[i] - b[0] != 0) {
						slope = ((float) ((a[i] - a[0])) / ((b[i] - b[0])));

					} else {
						slope = 999999;
					}
					// add if slope is not infinity here

					if (slope != 999999) {
						s = new Store(ymin, ymax, slope, xval);
						l.add(s);
					}

				}
			}
			i++;
		}
		// sorting edge values on basis of ymin,ymax,x-value,slope
		for (int j = 0; j < l.size(); j++) {

			for (int k = 0; k < l.size() - 1; k++) {

				Store a_temp = (Store) l.get(k);
				Store b_temp = (Store) l.get(k + 1);
				if (a_temp.getymin() > (b_temp.getymin())) {
					ArrayList<Store> temp = new ArrayList<Store>();
					temp.add(0, a_temp);

					l.set(k, b_temp);
					l.set(k + 1, a_temp);

				} else if (a_temp.getymin() == (b_temp.getymin())) {
					if (a_temp.getymax() > (b_temp.getymax())) {

						ArrayList<Store> temp = new ArrayList<Store>();
						temp.add(0, a_temp);

						l.set(k, b_temp);
						l.set(k + 1, a_temp);

					} else if (a_temp.getymax() == (b_temp.getymax())) {
						if (a_temp.getx() > (b_temp.getx())) {
							ArrayList<Store> temp = new ArrayList<Store>();
							temp.add(0, a_temp);

							l.set(k, b_temp);
							l.set(k + 1, a_temp);
						}
					}
				}
			}
		}

		global.addAll(l);

		y_hi = global.get(0).ymax;
		// getting y max value
		for (int u = 1; u < global.size(); u++) {
			if (global.get(u).ymax > y_hi) {
				y_hi = global.get(u).ymax;
			}
		}

		// adding to active table
		int g = 0;
		float min = global.get(0).ymin;
		@SuppressWarnings("unchecked")
		ArrayList<Store> temp = (ArrayList<Store>) global.clone();
		int x1 = 0;
		for (g = 0; g < global.size(); g++) {
			if (min == global.get(g).ymin) {

				active.add(global.get(g));
				temp.remove(x1);
				x1--;
			}
			x1++;
		}
		// sorting on basis of x values
		for (int j = 0; j < active.size(); j++) {

			for (int k = 0; k < active.size() - 1; k++) {

				Store a_temp1 = (Store) active.get(k);
				Store b_temp1 = (Store) active.get(k + 1);
				if (a_temp1.getx() > (b_temp1.getx())) {
					ArrayList<Store> temp111 = new ArrayList<Store>();
					temp111.add(0, a_temp1);

					active.set(k, b_temp1);
					active.set(k + 1, a_temp1);

				}
			}
		}

		// for (int d = 0; d < active.size(); d++) {
		// System.out.println(active.get(d).ymin + " " + active.get(d).ymax
		// + " " + active.get(d).x + " " + active.get(d).slope);
		// }

		// getting x max value
		float xmax = 0.0f;

		xmax = global.get(0).x;

		for (int u = 1; u < global.size(); u++) {
			if (global.get(u).x > xmax) {
				xmax = global.get(u).x;
			}
		}

		// System.out.println("lol");
		global = temp;
		int ind = 0;
		boolean parity = false;
		// loop for printing pixels
		for (int yprint = (int) active.get(0).ymin; yprint <= y_hi; yprint++) {

			for (int xprint = 0; xprint < xmax; xprint++) {
				// check for parity and set true for printing
				if (ind < active.size()) {
					if (!parity && (int) active.get(ind).x == xprint) {

						parity = true;
						ind++;
					}
				}
				// check for two points collide at same point
				if (ind < active.size()) {

					if (parity && (int) active.get(ind).x == xprint) {

						C.setPixel(xprint, yprint);

						parity = false;
						ind++;
					}
				}// prints if parity is odd that means pixel in inside
				if (parity) {

					C.setPixel(xprint, yprint);

				}
			}
			// sorting the Arraylist after each iteration and remove the edges
			// from the active list table and also removing transferring from
			// global to active if active edge consits of only one edge
			@SuppressWarnings("unchecked")
			ArrayList<Store> temp2 = (ArrayList<Store>) active.clone();
			x1 = 0;
			for (int index = 0; index < active.size(); index++) {
				if (yprint < active.get(index).ymax) {
					active.get(index).x += active.get(index).slope;
				} else {
					temp2.remove(x1);
					x1--;
				}
				x1++;
			}
			active = temp2;
			@SuppressWarnings("unchecked")
			ArrayList<Store> temp3 = (ArrayList<Store>) global.clone();
			x1 = 0;
			for (int index1 = 0; index1 < global.size(); index1++) {
				if (yprint == global.get(index1).ymin) {
					active.add(global.get(index1));
					temp3.remove(x1);
					x1--;
				}
				x1++;
			}
			// sorting again based of x values
			global = temp3;
			for (int j = 0; j < active.size(); j++) {

				for (int k = 0; k < active.size() - 1; k++) {

					Store a_temp = (Store) active.get(k);
					Store b_temp = (Store) active.get(k + 1);

					if (a_temp.getx() > (b_temp.getx())) {
						ArrayList<Store> at1 = new ArrayList<Store>();
						at1.add(0, a_temp);
						active.set(k, b_temp);
						active.set(k + 1, a_temp);
					}
					if (y[8] == 220) {
						for (int xprint = 365; xprint <= 401; xprint++) {
							C.setPixel(xprint, 211);
						}
					}
				}
			}

			ind = 0;
			parity = false;
		}

	}


}

/**
 * 
 * @class Polygon This stores the shapes with the hashmap
 */
class polygon {

	float a[], b[];
	int n;

	/**
	 * 
	 * @param x
	 *            - array of x co-ords
	 * @param y
	 *            - array of y co-ords
	 * @param n
	 *            - number of vertices
	 */
	public polygon(float x[], float y[], int n) {

		this.a = x;
		this.b = y;
		this.n = n;

	}
}
//
//Clipper.jaaa
//
//
//Created by Joe Geigel on 1/21/10.
//Copyright 2010 __MyCompanyName__. All rights reseraed.
//

/**
* Object for performing clipping
*
*/

class clipper {

/**
 * clipPolygon
 *
 * Clip the polygon with vertex count in and vertices inx/iny against the
 * rectangular clipping region specified by lower-left corner (x0,y0) and
 * upper-right corner (x1,y1). The resulting vertices are placed in
 * outx/outy.
 *
 * The routine should return the the vertex count of the polygon resulting
 * from the clipping.
 *
 * @param in
 *            the number of vertices in the polygon to be clipped
 * @param inx
 *            - x coords of vertices of polygon to be clipped.
 * @param iny
 *            - y coords of vertices of polygon to be clipped.
 * @param outx
 *            - x coords of vertices of polygon resulting after clipping.
 * @param outy
 *            - y coords of vertices of polygon resulting after clipping.
 * @param x0
 *            - x coord of lower left of clipping rectangle.
 * @param y0
 *            - y coord of lower left of clipping rectangle.
 * @param x1
 *            - x coord of upper right of clipping rectangle.
 * @param y1
 *            - y coord of upper right of clipping rectangle.
 *
 * @return number of vertices in the polygon resulting after clipping
 *
 */

public int clipPolygon(int in, float inx[], float iny[], float outx[],
		float outy[], float x0, float y0, float x1, float y1) {

	float out_tempx[] = new float[100];
	float out_tempy[] = new float[100];

	int counter = 0;
	int a, b;

	// right edge
	for (int i = 0; i < in; i++) {
		a = i;

		if (i < in - 1) {

			b = i + 1;

		} else {

			b = 0;
		}
		// both the points are inside
		if (intersect(inx[b], x0, y0, x1, y1, "r")) {
			if (intersect(inx[a], x0, y0, x1, y1, "r")) {
				out_tempx[counter] = inx[b];
				out_tempy[counter] = iny[b];

				counter++;

			}// 1st inside 2nd outside
			else {
				float m = (iny[b] - iny[a]) / (inx[b] - inx[a]);
				// float m =( p-a)/(a_x-s_x);
				float temp1 = m * (inx[b] - x1);
				float temp_y = iny[b] - temp1;
				float temp_x = x1;

				out_tempx[counter] = temp_x;
				out_tempy[counter] = temp_y;
				counter++;
				out_tempx[counter] = inx[b];
				out_tempy[counter] = iny[b];
				counter++;
			}

		}// 2nd inside 1st outside
		else if (intersect(inx[a], x0, y0, x1, y1, "r")) {

			float m = (iny[b] - iny[a]) / (inx[b] - inx[a]);
			float temp1 = m * (inx[b] - x1);
			float temp_y = iny[b] - temp1;
			float temp_x = x1;

			out_tempx[counter] = temp_x;
			out_tempy[counter] = temp_y;
			counter++;
		}
	}

	// top edge

	int counter1 = 0;
	float out_tempx1[] = new float[100];
	float out_tempy1[] = new float[100];
	for (int i = 0; i < counter; i++) {

		a = i;
		if (i < counter - 1) {

			b = i + 1;

		} else {

			b = 0;
		}

		// both in

		if (intersect(out_tempy[b], x0, y0, x1, y1, "t")) {
			if (intersect(out_tempy[a], x0, y0, x1, y1, "t")) {
				out_tempx1[counter1] = out_tempx[b];
				out_tempy1[counter1] = out_tempy[b];
				counter1++;
			}
			// 1st inside 2nd outside
			else {

				float m = (out_tempy[b] - out_tempy[a])
						/ (out_tempx[b] - out_tempx[a]);
				// float m =(a_x-s_x)/( a_y-s_y);
				float temp1 = ((y1 - out_tempy[b]) / m);
				float temp_x = out_tempx[b] + temp1;
				float temp_y = y1;
				out_tempx1[counter1] = temp_x;
				out_tempy1[counter1] = temp_y;
				counter1++;
				out_tempx1[counter1] = out_tempx[b];
				out_tempy1[counter1] = out_tempy[b];
				counter1++;

			}// 2nd inside 1st outside

		} else if (intersect(out_tempy[a], x0, y0, x1, y1, "t")) {

			float m = (out_tempy[b] - out_tempy[a])
					/ (out_tempx[b] - out_tempx[a]);
			float temp1 = ((y1 - out_tempy[b]) / m);
			float temp_x = out_tempx[b] + temp1;

			float temp_y = y1;

			out_tempx1[counter1] = temp_x;
			out_tempy1[counter1] = temp_y;
			counter1++;
		}

	}

	// bottom edge
	int counter2 = 0;
	float out_tempx2[] = new float[100];
	float out_tempy2[] = new float[100];

	for (int i = 0; i < counter1; i++) {
		a = i;
		if (i < counter1 - 1) {

			b = i + 1;

		} else {

			b = 0;
		}

		// both in

		if (intersect(out_tempy1[b], x0, y0, x1, y1, "b")) {
			if (intersect(out_tempy1[a], x0, y0, x1, y1, "b")) {
				out_tempx2[counter2] = out_tempx1[b];
				out_tempy2[counter2] = out_tempy1[b];
				counter2++;
			}
			// 1st inside 2nd outside
			else {

				float dx = (out_tempx1[b] - out_tempx1[a]);
				float dy = (out_tempy1[b] - out_tempy1[a]);

				float m = dy / dx;
				float temp_y, temp_x;

				float temp1 = (out_tempy1[b] - y0) / m;
				temp_y = y0;
				temp_x = out_tempx1[b] - temp1;

				out_tempx2[counter2] = temp_x;
				out_tempy2[counter2] = temp_y;
				counter2++;
				out_tempx2[counter2] = out_tempx1[b];
				out_tempy2[counter2] = out_tempy1[b];
				counter2++;
			}
		}// 2nd inside 1st outside
		else if (intersect(out_tempy1[a], x0, y0, x1, y1, "b")) {

			float dx = (out_tempx1[b] - out_tempx1[a]);
			float dy = (out_tempy1[b] - out_tempy1[a]);

			float m = dy / dx;
			float temp_y, temp_x;

			float temp1 = (out_tempy1[b] - y0) / m;
			temp_y = y0;
			temp_x = out_tempx1[b] - temp1;

			out_tempx2[counter2] = temp_x;
			out_tempy2[counter2] = temp_y;

			counter2++;
		}

	}
	// left edge
	int counter3 = 0;

	for (int i = 0; i < counter2; i++) {
		a = i;
		if (i < counter2 - 1) {

			b = i + 1;

		} else {

			b = 0;
		}
		// both in

		if (intersect(out_tempx2[b], x0, y0, x1, y1, "l")) {
			if (intersect(out_tempx2[a], x0, y0, x1, y1, "l")) {
				outx[counter3] = out_tempx2[b];
				outy[counter3] = out_tempy2[b];
				counter3++;
			}// 1st inside 2nd outside
			else {
				float m = (out_tempy2[b] - out_tempy2[a])
						/ (out_tempx2[b] - out_tempx2[a]);
				// float m =( a_y-s_y)/(a_x-s_x);
				float temp1 = m * (out_tempx2[b] - x0);
				float temp_y = out_tempy2[b] - temp1;
				float temp_x = x0;
				outx[counter3] = temp_x;
				outy[counter3] = temp_y;
				counter3++;
				outx[counter3] = out_tempx2[b];
				outy[counter3] = out_tempy2[b];
				counter3++;
			}
		}// 2nd out 1st inside
		else if (intersect(out_tempx2[a], x0, y0, x1, y1, "l")) {
			float m = (out_tempy2[b] - out_tempy2[a])
					/ (out_tempx2[b] - out_tempx2[a]);
			float temp1 = m * (out_tempx2[b] - x0);
			float temp_y = out_tempy2[b] - temp1;
			float temp_x = x0;

			outx[counter3] = temp_x;
			outy[counter3] = temp_y;
			counter3++;
		}

	}
	// for (int i = 0; i < counter3; i++) {
	// System.out.println(outx[i] + " " + outy[i]);
	//
	// }
	return counter3; // should return number of vertices in clipped
						// poly.
}

// for checking the intersection with the line and returns boolean
public boolean intersect(float f, float x0, float y0, float x1, float y1,
		String string) {
	if (string == "r") {
		if (f > x1) {
			return false;
		}
	} else if (string == "l") {
		if (f < x0) {
			return false;
		}
	} else if (string == "t") {
		if (f > y1) {
			return false;
		}
	} else if (string == "b") {
		if (f < y0) {
			return false;
		}
	}

	return true;
}

}




/**
 * 
 * Class store for storing objects of co-ordinates
 *
 */
class Store {
	float ymin;
	float ymax;
	float slope;
	float x;

	/**
	 * 
	 * @param ymin
	 * @param ymax
	 * @param slope
	 * @param x
	 */
	public Store(float ymin, float ymax, float slope, float x) {
		this.ymin = ymin;
		this.ymax = ymax;
		this.slope = slope;
		this.x = x;
	}

	/**
	 * 
	 * @return x co-ord
	 */
	public float getx() {
		return x;
	}

	/**
	 * 
	 * @return ymin co-ordinates
	 */
	public float getymin() {
		return ymin;
	}

	/*
	 * return max 'y' co-ordinates
	 */
	public float getymax() {
		return ymax;
	}

	/*
	 * return the slope
	 */
	public float getslope() {
		return slope;
	}
}