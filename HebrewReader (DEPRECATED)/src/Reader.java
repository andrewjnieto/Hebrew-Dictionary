DEPRECATED; DO NOT USE
package processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.Normalizer;

public class Reader {
	private Word[] dictionary;

	public Reader(String path) throws FileNotFoundException {
		this.dictionary = new Word[8674];
		readFileAndSet(path);
		setNewFormat();
	}

	public void readFileAndSet(String path) throws FileNotFoundException {
		File f = new File(path);
		if (!f.exists()) {
			System.err.println("File not found");
			return;
		}
		Scanner scan = new Scanner(f);
		int i = 0;
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			if (s.contains("div type=\"entry\"")) {
				Word w = new Word();
				int id = Integer.parseInt(s.substring(s.lastIndexOf("=") + 2, s.length() -
					2));
				s = scan.nextLine();
				String hebWord = s.substring(s.indexOf("lemma") + 7, s.indexOf("morph") - 2);
				String morphology = s.substring(s.indexOf("morph") + 7, s.indexOf("POS") -
					2);
				String vocalization = s.substring(s.indexOf("xlit") + 6, s.indexOf("ID") -
					2);
				vocalization = Normalizer.normalize(vocalization, Normalizer.Form.NFKD);
				String noNiqud = s.substring(s.indexOf(">") + 1, s.indexOf("</w>"));
				String[] definitions = getDefinitons(s, scan);
				w.setAll(id, hebWord, morphology, vocalization, noNiqud, definitions);
				dictionary[i++] = w;
			}
		}
		scan.close();
	}

	private String[] getDefinitons(String s, Scanner scan) {
		while (!s.contains("<list>")) {
			s = scan.nextLine();
		}
		s = scan.nextLine();
		List<String> list = new ArrayList<>();
		while (!s.contains("</list>")) {
			list.add(cleanDefiniton(s));
			s = scan.nextLine();
		}
		String[] definitions = new String[list.size()];
		definitions = list.toArray(definitions);
		return definitions;
	}

	private void setNewFormat() throws FileNotFoundException {
		PrintWriter w = new PrintWriter("Hebrew.txt");
		for (Word word : dictionary) {
			w.println(word.toStringFormatted());
		}
		w.close();
	}

	private String cleanDefiniton(String def) {
		String temp = def.substring(def.indexOf("<item>") + 6,
			def.indexOf("</item>"));
		temp = temp.trim();
		if (Character.isDigit(temp.charAt(0))) {
			temp = temp.substring(temp.indexOf(" ") + 1);
		}
		return temp;
	}

	public List<Word> search(String h) {
		List<Integer> indices = SearchIndices(h);
		List<Word> result = new ArrayList<>();
		for (Integer index : indices) {
			result.add(dictionary[index]);
			System.out.println(dictionary[index]);
		}
		return result;
	}

	private List<Integer> SearchIndices(String s) {
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].getHeb().startsWith(s)) {
				l.add(i);
			}
		}
		return l;
	}

}

public String toStringFormatted() {
	String s = (this.id + "," + this.morphology + "," + this.hebWord + "," +
		this.noNiqud + "," + this.vocalization
		+ "\n" + this.definitionsFormatted()).trim();
	return s;
}
private String definitionsFormatted() {
	StringBuilder s = new StringBuilder();
	for (String string : definitions) {
		s.append(string + "\n");
	}
	s.append("ENDDEFS");
	return s.toString();
}
