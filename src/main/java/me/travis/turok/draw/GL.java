package me.travis.turok.draw;

import org.lwjgl.opengl.GL11;

/**
* @author me
*
* Created by me.
* 08/04/20.
*
*/
public class GL {
	public static void color(float r, float g, float b, float a) {
		GL11.glColor4f(r / 255, g / 255, b / 255, a / 255);
	}

	public static void start() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public static void finish() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void draw_rect(int x, int y, int width, int height) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glBegin(GL11.GL_QUADS); {
			GL11.glVertex2d(x + width, y);
			GL11.glVertex2d(x, y);
			GL11.glVertex2d(x, y + height);
			GL11.glVertex2d(x + width, y + height);
		}

		GL11.glEnd();
	}

	public static void resize(int x, int y, float size) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(size, size, 1);
		GL11.glColor4f(1, 1, 1, 1);
	}

	public static void resize(int x, int y, float size, String tag) {
		GL11.glScalef(1f / size, 1f / size, 1);
		GL11.glTranslatef(- x, - y, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
}