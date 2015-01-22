package br.com.danielhabib.core.rules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import br.com.danielhabib.core.Config;

/**
 * Mapper for direction->image.
 */
public class ImageHandler {

	private HashMap<Direction, BufferedImage> images;

	public Image initImage(Direction initDirection) {
		try {
			images = new HashMap<Direction, BufferedImage>();
			String fileBaseName = "psico_";
			for (Direction direction : Direction.values()) {
				String fileName = fileBaseName.concat(
						direction.name().toLowerCase()).concat(".png");
				InputStream inputStream = readResource(fileName);
				BufferedImage originalImage = ImageIO.read(inputStream);
				images.put(direction, resizeImage(originalImage, originalImage.getType(), Config.SIZE));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return get(initDirection);
	}

	private InputStream readResource(String fileName) {
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(fileName);
		if (stream == null) {
			stream = getClass().getClassLoader().getResourceAsStream(fileName);
			if (stream == null) {
				stream = getClass().getResourceAsStream(fileName);
			}
		}
		return stream;

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
