package main.java.sw.cip.effects;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;

class ChoroplethEffect {

	public BufferedImage choropleth(BufferedImage src, Integer size) {
		int w = src.getHeight();
		int h = src.getWidth();

		if (src == null || src.getType() != BufferedImage.TYPE_INT_ARGB) {
			throw new IllegalArgumentException(
					"Effect only works with images of type BufferedImage.TYPE_INT_ARGB.");
		}

		int tmpW = w + size + size;
		int tmpH = h + size;		
		byte[] srcAlphaBuf = new byte[tmpW * tmpH];
		Arrays.fill(srcAlphaBuf, (byte) 0xFF);
		byte[] tmpBuf1 = new byte[tmpW * tmpH];
		byte[] tmpBuf2 = new byte[tmpW * tmpH];
		int[] lineBuf = new int[w];
		// extract src image alpha channel and inverse and offset
		Raster srcRaster = src.getRaster();
		for (int y = 0; y < h; y++) {
			int offset = y * tmpW;
			srcRaster.getDataElements(0, y, w, 1, lineBuf);
			for (int x = 0; x < w; x++) {
				srcAlphaBuf[offset + x] = (byte) ((255 - ((lineBuf[x] & 0xFF000000) >>> 24)) & 0xFF);
			}
		}

		float[] kernel = createGaussianKernel(size * 2);
		blur(srcAlphaBuf, tmpBuf2, tmpW, tmpH, kernel, size * 2); // horizontal
		blur(tmpBuf2, tmpBuf1, tmpH, tmpW, kernel, size * 2); // vertical

		// rescale
		for (int i = 0; i < tmpBuf1.length; i++) {
			int val = (int) (((int) tmpBuf1[i] & 0xFF));
			tmpBuf1[i] = (val > 255) ? (byte) 0xFF : (byte) val;
		}

		BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		WritableRaster shadowRaster = dst.getRaster();

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				lineBuf[x] = ((byte) origianlAlphaVal & 0xFF) << 24 | 0xFFFFFF;
			}
			shadowRaster.setDataElements(0, y, w, 1, lineBuf);
		}
		return dst;
	}

	static void blur(byte[] srcPixels, byte[] dstPixels, int width, int height,
			float[] kernel, int radius) {
		float p;
		int cp;
		for (int y = 0; y < height; y++) {
			int index = y;
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				p = 0.0f;
				for (int i = -radius; i <= radius; i++) {
					int subOffset = x + i;
					if (subOffset < 0 || subOffset >= width) {
						subOffset = (x + width) % width;
					}
					int pixel = srcPixels[offset + subOffset] & 0xFF;
					float blurFactor = kernel[radius + i];
					p += blurFactor * pixel;
				}
				cp = (int) (p + 0.5f);
				dstPixels[index] = (byte) (cp > 255 ? 255 : cp);
				index += height;
			}
		}
	}

	static float[] createGaussianKernel(int radius) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}

		float[] data = new float[radius * 2 + 1];

		float sigma = radius / 3.0f;
		float twoSigmaSquare = 2.0f * sigma * sigma;
		float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
		float total = 0.0f;

		for (int i = -radius; i <= radius; i++) {
			float distance = i * i;
			int index = i + radius;
			data[index] = (float) Math.exp(-distance / twoSigmaSquare)
					/ sigmaRoot;
			total += data[index];
		}

		for (int i = 0; i < data.length; i++) {
			data[i] /= total;
		}

		return data;
	}
}
