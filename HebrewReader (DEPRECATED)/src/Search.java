package processing;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Search {
	public static void main(String[] args) throws FileNotFoundException {
		Driver();
	}

	public static void Driver() throws FileNotFoundException {
		Reader2 r = new Reader2("Hebrew.txt");
		Scanner input = new Scanner(System.in, "UTF-8");
		System.out.println("    HEBREW DICTIONARY");
		System.out.println("אאאאאאאאאאאאאאאאאאאאאאאאאא");
		while (true) {
			System.out.print("Search: ");
			String s = input.nextLine().trim();
			if (s.equals("exit")) {
				break;
			}
			queryResults(r.search(s), input);
		}
		input.close();
	}

	public static void queryResults(List<Word> defList, Scanner input) {
		System.out.println(defList.size() + " definitons found");
		System.out.print("How many possible definitions would you like to view:");
		int num = input.nextInt();
		input.nextLine();
		for (int i = 0; i < num; i++) {
			System.out.println(defList.get(i));
		}

	}
}
