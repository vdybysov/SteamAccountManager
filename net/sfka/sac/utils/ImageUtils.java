package net.sfka.sac.utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageUtils {

	private static HashMap<String, BufferedImage> opened = new HashMap<>();

	public static BufferedImage getLocalImage(String name)
	{
		if(opened.containsKey(name)) {
			BufferedImage img;
			if((img = opened.get(name)) != null) {
				return img;
			}
		}
		try
		{
			BufferedImage img = ImageIO.read(ImageUtils.class.getResource("/res/" + name + ".png"));
			//System.out.println("Opened local image: " + name + ".png");
			opened.put(name, img);
			return img;
		}
		catch (Exception e)
		{
			System.out.println("Fail to open local image: " + name + ".png");
			return getEmptyImage();
		}
	}

	public static BufferedImage getEmptyImage() {
		return new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
	}

}
