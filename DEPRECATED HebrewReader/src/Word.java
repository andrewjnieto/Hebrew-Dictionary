package processing;

import java.util.Arrays;

public class Word implements Comparable<Word> {
	private int id;
	private String hebWord;
	private String morphology;
	private String vocalization;
	private String noNiqud;
	private String[] definitions;

	public Word() {
		this.id = -1;
		this.hebWord = null;
		this.morphology = null;
		this.noNiqud = null;
		this.definitions = null;
		this.vocalization = null;
	}

	public Word(int id, String morphology, String noNiqud, String hebWord, String vocalization, String[] definitions) {
		this.id = id;
		this.morphology = morphology;
		this.noNiqud = noNiqud;
		this.hebWord = hebWord;
		this.vocalization = vocalization;
		this.definitions = definitions;
	}

	public void setAll(int id, String hebWord, String morphology, String vocalization, String noNiqud,
			String[] definitions) {
		this.id = id;
		this.hebWord = hebWord;
		this.morphology = morphology;
		this.noNiqud = noNiqud;
		this.definitions = definitions;
		this.vocalization = vocalization;
	}

	public String getHeb() {
		return this.hebWord;
	}

	@Override
	public String toString() {
		String s = (this.id + ", " + this.morphology + ", " + this.hebWord + ", " + this.noNiqud + " "
				+ this.vocalization + "\n" + this.defintionsToString()).trim();
		return s;
	}

	private String defintionsToString() {
		StringBuilder s = new StringBuilder("Definitons: \n");
		int i = 1;
		for (String string : definitions) {
			s.append("\t" + i + ") " + string + "\n");
			i++;
		}
		return s.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(definitions);
		result = prime * result + ((hebWord == null) ? 0 : hebWord.hashCode());
		result = prime * result + id;
		result = prime * result + ((morphology == null) ? 0 : morphology.hashCode());
		result = prime * result + ((noNiqud == null) ? 0 : noNiqud.hashCode());
		result = prime * result + ((vocalization == null) ? 0 : vocalization.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (!Arrays.equals(definitions, other.definitions))
			return false;
		if (hebWord == null) {
			if (other.hebWord != null)
				return false;
		} else if (!hebWord.equals(other.hebWord))
			return false;
		if (id != other.id)
			return false;
		if (morphology == null) {
			if (other.morphology != null)
				return false;
		} else if (!morphology.equals(other.morphology))
			return false;
		if (noNiqud == null) {
			if (other.noNiqud != null)
				return false;
		} else if (!noNiqud.equals(other.noNiqud))
			return false;
		if (vocalization == null) {
			if (other.vocalization != null)
				return false;
		} else if (!vocalization.equals(other.vocalization))
			return false;
		return true;
	}

	@Override
	public int compareTo(Word o) {
		return this.noNiqud.compareTo(o.noNiqud);
	}

}