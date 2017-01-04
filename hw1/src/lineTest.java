//
//  Test.java
//
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
//  Modified by Vasudev Prasad Bethamcherla on 08/19/2014
//

import java.awt.*;
import java.awt.event.*;

public class lineTest {

	public lineTest() {
	}

	static public void main(String[] args) {

		simpleCanvas T = new simpleCanvas(600, 600);
		Rasterizer R = new Rasterizer(600);

		T.setColor(0.0f, 0.0f, 0.0f);
		T.clear();
		T.setColor(1.0f, 1.0f, 1.0f);

		// Idea for lettering style from:
		// http://patorjk.com/software/taag/#p=display&f=Star%20Wars&t=Type%20Something
		// _______ ______
		// / _____| / __ \
		// | | __ | | | |
		// | | |_ | | | | |
		// | |__| | | `--' |
		// \______| \______/

		// ######## The letter 'G' in green ########
		T.setColor(0.0f, 1.0f, 0.0f);
		R.drawLine(80, 340, 220, 340, T); // Horizontal left to right
		R.drawLine(40, 380, 80, 340, T); // 315 degree slope
		R.drawLine(220, 340, 260, 380, T); // 45 degree slope
		R.drawLine(260, 380, 260, 440, T); // Vertical bottom to top
		R.drawLine(260, 440, 180, 440, T); // Horizontal right to left
		R.drawLine(180, 440, 180, 400, T);
		R.drawLine(180, 400, 220, 400, T);
		R.drawLine(220, 400, 200, 380, T);
		R.drawLine(200, 380, 100, 380, T);
		R.drawLine(100, 380, 80, 400, T);
		R.drawLine(80, 400, 80, 500, T);
		R.drawLine(80, 500, 100, 520, T);
		R.drawLine(100, 520, 200, 520, T);
		R.drawLine(200, 520, 220, 500, T);
		R.drawLine(220, 500, 220, 480, T);
		R.drawLine(220, 480, 260, 480, T);
		R.drawLine(260, 480, 260, 520, T);
		R.drawLine(260, 520, 220, 560, T); // 135 degree slope
		R.drawLine(220, 560, 80, 560, T);
		R.drawLine(80, 560, 40, 520, T); // 225 degree slope
		R.drawLine(40, 520, 40, 380, T); // Vertical top to bottom

		// ######## The letter 'O' in red ########
		T.setColor(1.0f, 0.0f, 0.0f);
		R.drawLine(450, 320, 520, 340, T); // 16.6 degree slope
		R.drawLine(520, 340, 540, 360, T); // 45 degree slope
		R.drawLine(540, 360, 560, 450, T); // 77.47 degree slope
		R.drawLine(560, 450, 540, 540, T); // 102.83 degree slope
		R.drawLine(540, 540, 520, 560, T); // 135 degree slope
		R.drawLine(520, 560, 450, 580, T); // 163.3 degree slope
		R.drawLine(450, 580, 380, 560, T); // 196.71 degree slope
		R.drawLine(380, 560, 360, 540, T); // 225 degree slope
		R.drawLine(360, 540, 340, 450, T);
		R.drawLine(340, 450, 360, 360, T);
		R.drawLine(360, 360, 380, 340, T);
		R.drawLine(380, 340, 450, 320, T);
		R.drawLine(420, 380, 480, 380, T);
		R.drawLine(480, 380, 520, 420, T);
		R.drawLine(520, 420, 520, 480, T);
		R.drawLine(520, 480, 480, 520, T);
		R.drawLine(480, 520, 420, 520, T);
		R.drawLine(420, 520, 380, 480, T);
		R.drawLine(380, 480, 380, 420, T);
		R.drawLine(380, 420, 420, 380, T);

		// ############# Use blue color (0,0.5,1) to write your initials
		// #############

		T.setColor(0.0f, 0.5f, 1.0f);
		// letter K

		R.drawLine(40, 40, 40, 260, T);
		R.drawLine(40, 40, 80, 40, T);
		R.drawLine(80, 170, 80, 260, T);
		R.drawLine(40, 260, 80, 260, T);
		R.drawLine(80, 170, 180, 260, T);
		R.drawLine(180, 260, 220, 260, T);
		R.drawLine(100, 150, 220, 260, T);
		R.drawLine(80, 40, 80, 130, T);
		R.drawLine(180, 40, 80, 130, T);
		R.drawLine(180, 40, 220, 40, T);
		R.drawLine(220, 40, 100, 150, T);

		// letter C
		R.drawLine(340, 40, 480, 40, T); // Horizontal left to right
		R.drawLine(300, 80, 340, 40, T); // 315 degree slope
		R.drawLine(480, 40, 520, 80, T); // 45 degree slope
		R.drawLine(520, 80, 520, 100, T); // Vertical bottom to top
		R.drawLine(480, 100, 520, 100, T); // Horizontal right to left
		R.drawLine(460, 80, 480, 100, T);
		R.drawLine(360, 80, 460, 80, T);
		R.drawLine(340, 100, 360, 80, T);
		R.drawLine(340, 200, 340, 100, T);
		R.drawLine(340, 200, 360, 220, T);
		R.drawLine(360, 220, 460, 220, T);
		R.drawLine(460, 220, 480, 200, T);
		R.drawLine(480, 200, 520, 200, T);
		R.drawLine(520, 200, 520, 220, T);
		R.drawLine(520, 220, 480, 260, T); // 135 degree slope
		R.drawLine(340, 260, 480, 260, T);
		R.drawLine(340, 260, 300, 220, T); // 225 degree slope
		R.drawLine(300, 80, 300, 220, T); // Vertical top to bottom
		Frame f = new Frame("Line Test");
		f.add("Center", T);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

}
