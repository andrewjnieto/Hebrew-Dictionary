package processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader2 {
	private Word[] dictionary;

	public Reader2(String path) throws FileNotFoundException {
		this.dictionary = new Word[8674];
		readFileAndSet(path);

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
			String[] s = scan.nextLine().split(",");
			String t = scan.nextLine();
			List<String> defList = new ArrayList<String>();
			defList.add(t);
			t = scan.nextLine();
			while (!t.equals("ENDDEFS")) {
				defList.add(t);
				t = scan.nextLine();
			}
			String[] definitions = new String[defList.size()];
			definitions = defList.toArray(definitions);
			dictionary[i++] = new Word(Integer.parseInt(s[0]), s[1], s[2], s[3], s[4], definitions);
		}
		scan.close();
	}

	public List<Word> search(String h) {
		List<Integer> indices = SearchIndices(h.trim());
		List<Word> result = new ArrayList<>();
		for (Integer index : indices) {
			result.add(dictionary[index]);
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
