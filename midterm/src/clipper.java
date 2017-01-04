//
//  Clipper.java
//
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * Object for performing clipping
 *
 */

public class clipper {

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
			if (intersect(inx[b], x0, y0, x1, y1, "right")) {
				if (intersect(inx[a], x0, y0, x1, y1, "right")) {
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
			else if (intersect(inx[a], x0, y0, x1, y1, "right")) {

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

			// both points are in

			if (intersect(out_tempy[b], x0, y0, x1, y1, "top")) {
				if (intersect(out_tempy[a], x0, y0, x1, y1, "top")) {
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

			} else if (intersect(out_tempy[a], x0, y0, x1, y1, "top")) {

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

			// both points are in

			if (intersect(out_tempy1[b], x0, y0, x1, y1, "bottom")) {
				if (intersect(out_tempy1[a], x0, y0, x1, y1, "bottom")) {
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
			else if (intersect(out_tempy1[a], x0, y0, x1, y1, "bottom")) {

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
			// both points are in

			if (intersect(out_tempx2[b], x0, y0, x1, y1, "left")) {
				if (intersect(out_tempx2[a], x0, y0, x1, y1, "left")) {
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
			else if (intersect(out_tempx2[a], x0, y0, x1, y1, "left")) {
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
		if (string == "right") {
			if (f > x1) {
				return false;
			}
		} else if (string == "left") {
			if (f < x0) {
				return false;
			}
		} else if (string == "top") {
			if (f > y1) {
				return false;
			}
		} else if (string == "bottom") {
			if (f < y0) {
				return false;
			}
		}
		return true;
	}

}
