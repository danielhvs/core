package br.com.danielhabib.core.rules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import br.com.danielhabib.core.Config;

/**
 * Mapper for direction->image.
 */
public class ImageHandler {

	private File imageFile;

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	private Map<Integer, BufferedImage> images;

	// private List<Integer> directions;

	public void setDirections(List<Integer> directions) throws IOException {
		images = new HashMap<Integer, BufferedImage>();
		BufferedImage originalImage = ImageIO.read(imageFile);
		for (Integer angle : directions) {
			images.put(angle, rotate(originalImage, -angle));
		}
	}

	private BufferedImage rotate(BufferedImage originalImage, int degrees) {
		BufferedImage resizedImage = resizeImage(originalImage, originalImage.getType(), Config.SIZE);
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degrees), resizedImage.getWidth() / 2, resizedImage.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(resizedImage, null);
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type,
			int size) {
		BufferedImage resizedImage = new BufferedImage(size, size, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, size, size, null);
		g.dispose();
		return resizedImage;
	}

	public Image get(Integer direction) {
		return images.get(direction);
	}

}
