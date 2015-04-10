package br.com.danielhabib.core.rules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import br.com.danielhabib.core.Config;

/**
 * Mapper for direction->image.
 */
public class ImageHandler {

	private HashMap<Direction, BufferedImage> images;

	public void setImagesMap(HashMap<Direction, File> imagesMap) throws IOException {
		images = new HashMap<Direction, BufferedImage>();
		for (Entry<Direction, File> entry : imagesMap.entrySet()) {
			BufferedImage originalImage = ImageIO.read(entry.getValue());
			images.put(entry.getKey(), resizeImage(originalImage, originalImage.getType(), Config.SIZE));
		}
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type,
			int size) {
		BufferedImage resizedImage = new BufferedImage(size, size, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, size, size, null);
		g.dispose();
		return resizedImage;
	}

	public Image get(Direction direction) {
		return images.get(direction);
	}

}
