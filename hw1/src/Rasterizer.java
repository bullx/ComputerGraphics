//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * A simple class for performing rasterization algorithms.
 *
 */

import java.util.*;

public class Rasterizer {

	/**
	 * number of scanlines
	 */
	int n_scanlines;

	/**
	 * Constructor
	 *
	 * @param n
	 *            number of scanlines
	 *
	 */
	Rasterizer(int n) {
		n_scanlines = n;
	}

	/**
	 * Draw a line from (x0,y0) to (x1, y1) on the simpleCanvas C.
	 *
	 * Implementation should be using the Midpoint Method
	 *
	 * You are to add the implementation here using only calls to C.setPixel()
	 *
	 * @param x0
	 *            x coord of first endpoint
	 * @param y0
	 *            y coord of first endpoint
	 * @param x1
	 *            x coord of second endpoint
	 * @param y1
	 *            y coord of second endpoint
	 * @param C
	 *            The canvas on which to apply the draw command.
	 */
	public void drawLine(int x0, int y0, int x1, int y1, simpleCanvas C) {

		// if x0= x1 vertical line
		if (x0 == x1) {
			if (y1 < y0) {
				for (int y = y0; y1 <= y; y1++) {
					C.setPixel(x0, y1);
				}
			} else {
				for (int y = y0; y <= y1; y++) {
					C.setPixel(x0, y);
				}
			}
		}
		// horizontal line
		if (y0 == y1) {
			if (x0 < x1) {
				for (int x = x0; x <= x1; x++) {
					C.setPixel(x, y1);
				}
			} else {
				for (int x = x1; x <= x0; x++) {
					C.setPixel(x, y1);
				}
			}
		}

		int dy = y1 - y0;
		int dx = x1 - x0;
		float m = (float) dy / dx;

		// diagonal
		if (m==1) {
			if (x1 < x0) {
				for (int x = x1, y = y1; x <= x0; x++, y++) {
					C.setPixel(x, y);
				}
			} else {
				for (int x = x0, y = y0; x <= x1; x++, y++) {
					C.setPixel(x, y);
				}
			}
		} else if (m==-1)

		{
			if (x1 > x0) {
				for (int x = x0, y = y0; x <= x1; x++, y--) {
					C.setPixel(x, y);
				}
			} else {
				for (int x = x0, y = y0; x >= x1; x--, y++) {
					C.setPixel(x, y);
				}
			}
		}


		// left to right shallow
		if (x0 < x1 && y0 < y1) {

			if (m > 0 && m < 1) {
				int de, dne, x, y, d;
				de = 2 * dy;
				dne = 2 * (dy - dx);
				d = de - dx;
				for (x = x0, y = y0; x <= x1; ++x) {

					C.setPixel(x, y);

					if (d <= 0) {
						d += de;
					} else {
						y++;
						d += dne;
					}
				}
			}

			// left to right deep
			else if (m >= 1) {
				int dn, dne, x, y, d;
				dn = -2 * dx;
				dne = 2 * (-dx + dy);
				d = dn - dx;
				for (x = x0, y = y0; y < y1; ++y) {

					C.setPixel(x, y);

					if (d >= 0) {
						d += dn;
					} else {
						x++;
						d += dne;
					}
				}
			}

		} else if (x0 < x1 && y0 > y1) {
			// shallow right to left
			if (m > -1 && m < 0) {
				int de, dse, x, y, d;
				de = -2 * dy;
				dse = (dy - dx);
				d = de - dx;
				for (x = x0, y = y0; x < x1; ++x) {

					C.setPixel(x, y);

					if (d <= 0) {

						d += de;

					} else {
						y--;
						d += dse;
					}
				}
			} else if (m < -1) {
				// right to left deep
				int ds, dse, x, y, d;

				ds = 2 * dx;
				dse = 2 * (dx + dy);
				d = ds + dy;

				for (x = x0, y = y0; y >= y1; --y) {
					C.setPixel(x, y);

					if (d <= 0) {
						d += ds;
					} else {

						x++;
						d += dse;
					}
				}
			}

		} else if (x0 > x1) {
			// shallow left to right down to up
			if (y0 < y1 && (m > -1 && m < 0)) {
				int de, dne, x, y, d;
				de = 2 * dy;
				dne = 2 * (dy + dx);
				d = de + dx;
				for (x = x0, y = y0; x >= x1; --x) {

					C.setPixel(x, y);

					if (d <= 0) {

						d += de;
					} else {
						y++;
						d += dne;
					}
				}
			}

			else if (y0 > y1) {
				// shallow right to left and down to up
				if (m > 0 && m < 1) {
					int de, dse, x, y, d;
					de = -2 * dy;
					dse = 2 * (-dy + dx);
					d = de - dx;
					for (x = x1, y = y1; x <= x0; x++) {

						C.setPixel(x, y);

						if (d <= 0) {
							d += de;

						} else {
							++y;
							d += dse;

						}
					}
				}
			}

			// swapping done to reverse x values if x1 is lesser than x0
			if (x1 < x0) {

				int a = x0;
				int b = y0;
				x0 = x1;
				y0 = y1;
				x1 = a;
				y1 = b;
			}
			dx = x1 - x0;
			dy = y1 - y0;
			if (x1 - x0 != 0) {
				m = dy / dx;
			}
			// deep right to left and down to up
			if (dy > 0 && m >= 1) {

				int dn, dne, x, y, d;
				dn = -2 * dx;
				dne = 2 * (-dx + dy);
				d = dn - dx;
				for (x = x0, y = y0; y <= y1; ++y) {

					C.setPixel(x, y);

					if (d >= 0) {
						d += dn;
					} else {
						x++;
						d += dne;
					}
				}
			} else {
				// deep left to right and down to up
				if (m < -1) {
					int ds, dse, x, y, d;

					ds = 2 * dx;
					dse = 2 * (dx + dy);
					d = ds + dy;

					for (x = x1, y = y1; y <= y0; ++y) {
						C.setPixel(x, y);

						if (d <= 0) {
							d += ds;
						} else {
							x--;
							d += dse;

						}
					}
				}
			}

		}

	}
}