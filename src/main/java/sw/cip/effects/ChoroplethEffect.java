package main.java.sw.cip.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

class ChoroplethEffect {

	public static final int[] gradient = new int[] { 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4,
			0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d4, 0xfff9f7d0, 0xfff9f6ca, 0xfff9f7c6, 0xfff9f6c0, 0xfff9f6bb, 0xfff9f6b8, 0xfff9f6b2, 0xfff9f6ae, 0xfff9f5a9, 0xfff8f5a3, 0xfff8f49f, 0xfff8f49b, 0xfff8f495, 0xfff9f491,
			0xfff9f48c, 0xfff8f487, 0xfff9f482, 0xfff8f37e, 0xfff9f378, 0xfff8f374, 0xfff8f36f, 0xfff8f26a, 0xfff8f264, 0xfff8f25f, 0xfff8f25a, 0xfff8f254, 0xfff8f24f, 0xfff9f14a, 0xfff8f146, 0xfff8f140, 0xfff9f03b, 0xfff8f136, 0xfff8f030, 0xfff8f02c, 0xfff8ef27, 0xfff8f021,
			0xfff8f01c, 0xfff8f017, 0xfff8ee13, 0xfff8eb14, 0xfff8e815, 0xfff9e616, 0xfff9e416, 0xfff9e118, 0xfffade18, 0xfff9db19, 0xfffad81a, 0xfff9d61b, 0xfffad31c, 0xfffad01d, 0xfffacd1d, 0xfffaca1e, 0xfffac81f, 0xfffbc520, 0xfffbc221, 0xfffbbf22, 0xfffcbd23, 0xfffbb924,
			0xfffcb724, 0xfffdb325, 0xfffcb026, 0xfffcae27, 0xfffdab28, 0xfffda829, 0xfffea52a, 0xfffea22b, 0xfffe9f2c, 0xfffe9c2d, 0xfffe992d, 0xffff962e, 0xfffe932f, 0xffff8f30, 0xffff8d32, 0xffff8a32, 0xfffe8932, 0xfffd8831, 0xfffd8731, 0xfffc8530, 0xfffc8430, 0xfffb822f,
			0xfffa812f, 0xfff9802e, 0xfff97e2e, 0xfff87d2d, 0xfff77b2c, 0xfff77a2c, 0xfff6792c, 0xfff5782b, 0xfff4762a, 0xfff4742a, 0xfff3742a, 0xfff37229, 0xfff27129, 0xfff16f28, 0xfff06e28, 0xfff06c27, 0xffef6b27, 0xffef6926, 0xffee6826, 0xffed6726, 0xffed6625, 0xffec6424,
			0xffeb6324, 0xffeb6123, 0xffea6022, 0xffea5f22, 0xffe95d22, 0xffe85c21, 0xffe75b20, 0xffe65921, 0xffe65820, 0xffe55720, 0xffe5551f, 0xffe4551e, 0xffe3531e, 0xffe3511d, 0xffe2501d, 0xffe14f1c, 0xffe04d1c, 0xffe04c1c, 0xffdf4b1b, 0xffde4a1a, 0xffde471a, 0xffdd4719,
			0xffdc4419, 0xffdb4318, 0xffda4117, 0xffd93e16, 0xffd83c16, 0xffd73a15, 0xffd53814, 0xffd53613, 0xffd43412, 0xffd33212, 0xffd12f11, 0xffd02d10, 0xffd02b10, 0xffce280e, 0xffce270e, 0xffcc250d, 0xffcb230c, 0xffca200b, 0xffc91e0b, 0xffc81c0a, 0xffc71a09, 0xffc61709,
			0xffc41608, 0xffc31307, 0xffc21106, 0xffc10f05, 0xffc00d04, 0xffc00b04, 0xffbe0903, 0xffbd0602, 0xffbc0401, 0xffbb0201, 0xffba0000, 0xffb80000, 0xffb70000, 0xffb50000, 0xffb30000, 0xffb10000, 0xffaf0000, 0xffae0000, 0xffac0000, 0xffaa0000, 0xffa80000, 0xffa70000,
			0xffa50000, 0xffa30000, 0xffa10000, 0xffa00000, 0xff9e0000, 0xff9c0000, 0xff9a0000, 0xff980000, 0xff960000, 0xff950000, 0xff930000, 0xff910000, 0xff900000, 0xff8d0000, 0xff8c0000, 0xff8a0000, 0xff890000, 0xff870000, 0xff850000, 0xff830000, 0xff810000, 0xff7f0000,
			0xff7d0000, 0xff7c0000, 0xff7a0000, 0xff780000, 0xff770000, 0xff750000, 0xff730000, 0xff720000, 0xff6f0000, 0xff6d0000, 0xff6b0000, 0xff6a0000, 0xff680000, 0xff660000, 0xff650000, 0xff630000, 0xff610000, 0xff600000, 0xff5d0000, 0xff5c0000, 0xff5c0000, 0xff5b0000,
			0xff5b0000, 0xff5a0000, 0xff5a0000, 0xff5a0000, 0xff590000, 0xff580000, 0xff580000, 0xff570000, 0xff570000, 0xff560000, 0xff560000, 0xff550000, 0xff550000, 0xff540000, 0xff540000, 0xff530000, 0xff520000, 0xff520000, 0xff520000, 0xff510000, 0xff510000, 0xff500000,
			0xff4f0000, 0xff500000, 0xff4e0000, 0xff4e0000, 0xff4e0000, 0xff4d0000, 0xff4c0000, 0xff4c0000, 0xff4b0000, 0xff4b0000, 0xff4b0000, 0xff4a0000, 0xff490000, 0xff490000, 0xff490000, 0xff480000, 0xff470000, 0xff460000, 0xff470000, 0xff460000, 0xff460000, 0xff440000,
			0xff440000, 0xff440000, 0xff430000, 0xff430000, 0xff420000, 0xff410000, 0xff410000, 0xff410000, 0xff400000, 0xff3f0000, 0xff3e0000, 0xff3f0000, 0xff3d0000, 0xff3d0000, 0xff3c0000, 0xff3d0000, 0xff3c0000, 0xff3b0000, 0xff3b0000, 0xff3a0000, 0xff3a0000, 0xff390000,
			0xff390000, 0xff380000, 0xff380000, 0xff370000, 0xff360000, 0xff360000, 0xff350000, 0xff350000, 0xff350000, 0xff340000, 0xff340000, 0xff330000, 0xff320000, 0xff320000, 0xff310000, 0xff310000, 0xff300000, 0xff300000, 0xff2f0000, 0xff2e0000, 0xff2e0000, 0xff2e0000,
			0xff2d0000, 0xff2c0000, 0xff2c0000, 0xff2b0000, 0xff2b0000, 0xff2a0000, 0xff2a0000, 0xff2a0000, 0xff290000, 0xff280000, 0xff280000, 0xff270000, 0xff270000, 0xff260000, 0xff260000, 0xff250000, 0xff250000, 0xff240000, 0xff230000, 0xff230000, 0xff230000, 0xff220000,
			0xff220000, 0xff210000, 0xff200000, 0xff200000, 0xff1f0000, 0xff1f0000, 0xff1f0000, 0xff1e0000, 0xff1d0000, 0xff1c0000, 0xff1c0000, 0xff1c0000, 0xff1b0000, 0xff1a0000, 0xff1a0000, 0xff1a0000, 0xff1a0000, 0xff180000, 0xff180000, 0xff180000, 0xff170000, 0xff160000,
			0xff160000, 0xff160000, 0xff150000, 0xff150000, 0xff130000, 0xff140000, 0xff120000, 0xff120000, 0xff110000, 0xff110000, 0xff100000 };

	public BufferedImage choropleth(BufferedImage img, int size) {
		int h = img.getHeight();
		int w = img.getWidth();

		// convert the image type
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		img = newImage;

		byte[] alphaBuf = new byte[w * h];
		Arrays.fill(alphaBuf, (byte) 0xFF);
		byte[] tmpBuf1 = new byte[w * h];
		byte[] tmpBuf2 = new byte[w * h];
		int[] lineBuf = new int[w];
		Raster srcRaster = img.getRaster();

		for (int y = 0; y < h; y++) {
			int offset = y * w;
			srcRaster.getDataElements(0, y, w, 1, lineBuf);
			for (int x = 0; x < w; x++) {
				alphaBuf[offset + x] = (byte) ((255 - ((lineBuf[x] & 0xFF000000) >>> 24)) & 0xFF);
			}
		}

		float[] kernel = createSimpleGaussianKernel(size * 2);
		blur(alphaBuf, tmpBuf2, w, h, kernel, size * 2);
		blur(tmpBuf2, tmpBuf1, h, w, kernel, size * 2);

		// rescale
		for (int i = 0; i < tmpBuf1.length; i++) {
			int val = (int) (((int) tmpBuf1[i] & 0xFF));
			tmpBuf1[i] = (val > 255) ? (byte) 0xFF : (byte) val;
		}

		BufferedImage outputBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		WritableRaster shadowRaster = outputBuffer.getRaster();

		Color color = Color.WHITE;

		int red = color.getRed(), green = color.getGreen(), blue = color.getBlue();
		for (int y = 0; y < h; y++) {
			int offset = y * w;
			for (int x = 0; x < w; x++) {
				int shadowVal = (255 - (int) tmpBuf1[offset + x] & 0xFF);
				lineBuf[x] = ((byte) shadowVal & 0xFF) << 24 | red << 16 | green << 8 | blue;
			}
			shadowRaster.setDataElements(0, y, w, 1, lineBuf);
		}

		for (int y = 0; y < outputBuffer.getHeight(); y++) {
			for (int x = 0; x < outputBuffer.getWidth(); x++) {
				int grey = (outputBuffer.getRGB(x, y) & 0xff000000) >>> 24;

				int c = gradient[(int) ((((double) grey) / 255) * gradient.length)];
				outputBuffer.setRGB(x, y, c);
			}
		}

		return outputBuffer;
	}

	static void blur(byte[] srcPixels, byte[] dstPixels, int width, int height, float[] kernel, int radius) {
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

	static float[] createSimpleGaussianKernel(int r) {
		if (r < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}

		float[] data = new float[r * 2 + 1];

		float sigma = r / 3.0f;
		float twoSigmaSquare = 2.0f * sigma * sigma;
		float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
		float total = 0.0f;

		for (int i = -r; i <= r; i++) {
			float distance = i * i;
			int index = i + r;
			data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
			total += data[index];
		}

		for (int i = 0; i < data.length; i++) {
			data[i] /= total;
		}

		return data;
	}

	public static void main(String[] args) {
		ChoroplethEffect choroplethEffect = new ChoroplethEffect();

		File input = new File("./resources/input.png");
		File output = new File("./resources/heatmap.png");

		BufferedImage inputBuffer = null;
		try {
			inputBuffer = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage outputBuffer = choroplethEffect.choropleth(inputBuffer, 15);

		try {
			ImageIO.write(outputBuffer, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
