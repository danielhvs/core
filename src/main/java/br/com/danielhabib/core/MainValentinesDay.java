package br.com.danielhabib.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


/**
 *	Generates levelx.txt
 */
public class MainValentinesDay {
	public static void main(String[] args) throws IOException {
		String map =
				"                    \n"
						+ "                    \n"
						+ "    xxxxx   xxxxx   \n"
						+ "   xxxxxxx xxxxxxx  \n"
						+ "  xxxxxxxxxxxxxxxxx \n"
						+ "  xxxxxxxxxxxxxxxxx \n"
						+ "  xxxxxxxxxxxxxxxxx \n"
						+ "  xxxxxxxxxxxxxxxxx \n"
						+ "   xxxxxxxxxxxxxxx  \n"
						+ "    xxxxxxxxxxxxx   \n"
						+ "     xxxxxxxxxxx    \n"
						+ "      xxxxxxxxx     \n"
						+ "       xxxxxxx      \n"
						+ "        xxxxx       \n"
						+ "         xxx        \n"
						+ "          x         \n"
						+ "                    ";
		String[] lines = map.split("\n");
		int y = 0;
		List<String> output = new ArrayList<String>();
		for (String line : lines) {
			y++;
			int x = 0;
			for (int i = 0; i < line.length(); i++) {
				x++;
				if (line.charAt(i) == 'x') {
					String e = "r:2,17-" + x + "," + y;
					System.out.println(e);
					output.add(e);
				}
			}
		}
		File file = new File("levels/levelx.txt");
		FileUtils.deleteQuietly(file);
		for (String string : output) {
			FileUtils.writeStringToFile(file, string + "\n", true);
		}
		FileUtils.writeStringToFile(file, "p:1,17\n", true);

	}
}
