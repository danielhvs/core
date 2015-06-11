package br.com.danielhabib.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class MainDN {
	public static void main(String[] args) throws IOException {
		String mapa =
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
		String[] linhas = mapa.split("\n");
		int y = 0;
		List<String> saida = new ArrayList<String>();
		for (String linha : linhas) {
			y++;
			int x = 0;
			for (int i = 0; i < linha.length(); i++) {
				x++;
				if (linha.charAt(i) == 'x') {
					String e = "r:2,17-" + x + "," + y;
					System.out.println(e);
					saida.add(e);
				}
			}
		}
		File file = new File("levels/levelx.txt");
		FileUtils.deleteQuietly(file);
		for (String string : saida) {
			FileUtils.writeStringToFile(file, string + "\n", true);
		}
		FileUtils.writeStringToFile(file, "p:1,17\n", true);

	}
}
