//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * This is a class that performas rasterization algorithms
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
	 *            - number of scanlines
	 *
	 */
	Rasterizer(int n) {
		n_scanlines = n;
	}

	/**
	 * Draw a filled polygon in the simpleCanvas C.
	 *
	 * The polygon has n distinct vertices. The coordinates of the vertices
	 * making up the polygon are stored in the x and y arrays. The ith vertex
	 * will have coordinate (x[i], y[i])
	 *
	 * You are to add the implementation here using only calls to C.setPixel()
	 */
	public void drawPolygon(int n, int x[], int y[], simpleCanvas C) {

		ArrayList<Store> l = new ArrayList<Store>();
		ArrayList<Store> global = new ArrayList<Store>();
		ArrayList<Store> active = new ArrayList<Store>();
		int a[] = x;
		int b[] = y;
		Store s;
		int ymin;
		int ymax;
		float slope;
		float xval;
		int i = 0;
		int y_hi = 0;
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

		for (int u = 1; u < global.size(); u++) {
			if (global.get(u).ymax > y_hi) {
				y_hi = global.get(u).ymax;
			}
		}
		// adding to active table
		int g = 0;
		int min = global.get(0).ymin;
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
		global = temp;
		int ind = 0;
		boolean parity = false;
		// loop for printing pixels
		for (int yprint = active.get(0).ymin; yprint <= y_hi; yprint++) {

			for (int xprint = 0; xprint < C.getWidth(); xprint++) {

				if (ind < active.size()) {
					if (!parity && (int) active.get(ind).x == xprint) {

						parity = true;
						ind++;
					}
				}
				if (ind < active.size()) {

					if (parity && (int) active.get(ind).x == xprint) {

						if ((int) active.get(ind - 1).x == xprint) {
							C.setPixel(xprint, yprint);
						}
						parity = false;
						ind++;
					}
				}
				if (parity) {
					C.setPixel(xprint, yprint);
				}
			}

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
				}
			}
			ind = 0;
			parity = false;
		}

	}

}

// class to store each edge as an object
class Store {
	int ymin;
	int ymax;
	float slope;
	float x;

	public Store(int ymin, int ymax, float slope, float x) {
		this.ymin = ymin;
		this.ymax = ymax;
		this.slope = slope;
		this.x = x;
	}

	public float getx() {
		return x;
	}

	public int getymin() {
		return ymin;
	}

	public int getymax() {
		return ymax;
	}

	public float getslope() {
		return slope;
	}
}