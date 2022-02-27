import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.*;

public class homework {
	static List<String> turkish_stopwords = new ArrayList<String>();
	static List<String> english_stopwords = new ArrayList<String>();
	static List<String> wordFiltered = new ArrayList<String>();
	static String filePath = "D:/CME4408/assignment1/Novel-Samples/grimms-fairy-tales_P1.txt";

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		// create list of turkish and english stop words
		createTurkishStopWords();
		createEnglishStopWords();

		// read corpus and tokenize
		StringBuilder sb = new StringBuilder();
		sb = readFile(sb);
		List<String> elements = new ArrayList<String>();
		StringTokenizer defaultTokenizer = new StringTokenizer(sb.toString());
		while (defaultTokenizer.hasMoreTokens()) {
			elements.add(defaultTokenizer.nextToken());
		}

		// apply preprocessing steps
		preProcessing(elements);

		// remove stopwords according to language of file
		//removeTurkishStopWords();
		removeEnglishStopWords();

		// find top 50 items in n-grams
		findTop50(wordFiltered);// for 1-gram
		List<String> two_grams = ngrams(2, wordFiltered);
		findTop50(two_grams);// for 2-gram
		List<String> three_grams = ngrams(3, wordFiltered);
		findTop50(three_grams);// for 3-gram

		long endTime = System.currentTimeMillis();

		// find total running time
		float sec = (endTime - startTime) / 1000F;
		System.out.println("Total Running Time " + sec + " seconds.");
	}

	static void findTop50(List<String> n_grams) {
		Map<String, Long> map = n_grams.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));
		List<Map.Entry<String, Long>> result = map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(50).collect(Collectors.toList());

		System.out.println("--------------------------");
		for (Map.Entry<String, Long> element : result) {
			System.out.println(element);
		}
	}

	public static List<String> ngrams(int n, List<String> list) {
		int size = list.size();
		List<String> ngrams = new ArrayList<String>();
		String s = "";
		if (n == 2) {
			for (int i = 0; i < size - n + 1; i++) {
				s = list.get(i) + "," + list.get(i + 1);
				ngrams.add(s);
			}
		}
		if (n == 3) {
			for (int i = 0; i < size - n + 1; i++) {
				s = list.get(i) + "," + list.get(i + 1) + "," + list.get(i + 2);
				ngrams.add(s);
			}
		}
		return ngrams;
	}

	public static void removeAll(List<String> list, String element) {
		while (list.contains(element)) {
			list.remove(element);
		}
	}

	public static void removeTurkishStopWords() {
		for (String sw : turkish_stopwords) {
			removeAll(wordFiltered, sw);
		}
	}

	public static void removeEnglishStopWords() {
		for (String sw : english_stopwords) {
			removeAll(wordFiltered, sw);
		}
	}

	public static void preProcessing(List<String> elements) {
		for (String element : elements) {
			element = element.toLowerCase();
			element = element.replaceAll("[0123456789]", "");
			element = element.replaceAll("\\p{Punct}", "");
			element = element.replaceAll("�", "");
			element = element.replaceAll("�", "");
			if (element != null && element != "")
				wordFiltered.add(element);
		}
	}

	public static StringBuilder readFile(StringBuilder sb) throws IOException {
		File File = new File(filePath);
		if (File.exists()) {
			FileReader file = new FileReader(filePath);
			BufferedReader br = new BufferedReader(file);
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} else {
			System.out.println("File does not exist!");
			System.exit(0);
		}
		return sb;
	}

	public static void createTurkishStopWords() {
		String[] array = { "bir�ey", "birka�", "biri", "belki", "baz�", "az", "asl�nda", "ama", "acaba", "e�er", "en",
				"diye", "defa", "de", "daha", "da", "bu", "biz", "kez", "i�in", "ise", "ile", "hi�", "her", "hepsi",
				"hep", "gibi", "nerde", "neden", "ne", "nas�l", "m�", "m�", "mu", "kim", "hem", "siz", "sanki", "o",
				"ni�in", "niye", "nereye", "nerede", "ki", "�u", "�ey", "��nk�", "�ok", "yani", "ya", "veya", "ve",
				"t�m", "a", "alt�", "altm��", "ancak", "arada", "art�k", "asla", "ayr�ca", "bana", "bazen", "baz�lar�",
				"ben", "benden", "beni", "benim", "beri", "be�", "bile", "bilhassa", "bin", "bir", "biraz", "bir�o�u",
				"bir�ok", "birisi", "bizden", "bize", "bizi", "bizim", "b�yle", "b�ylece", "buna", "bunda", "bundan",
				"bunlar", "bunlar�", "bunlar�n", "bunu", "bunun", "burada", "b�t�n", "�o�u", "�o�unu", "dahi", "dan",
				"de�il", "di�er", "di�eri", "di�erleri", "doksan", "dokuz", "dolay�", "dolay�s�yla", "d�rt", "e",
				"edecek", "eden", "ederek", "edilecek", "ediliyor", "edilmesi", "ediyor", "elbette", "elli", "etmesi",
				"etti", "etti�i", "etti�ini", "fakat", "falan", "filan", "gene", "gere�i", "gerek", "g�re", "hala",
				"halde", "halen", "hangi", "hangisi", "hani", "hatta", "hen�z", "herhangi", "herkes", "herkese",
				"herkesi", "herkesin", "hi�bir", "hi�biri", "i", "�", "i�inde", "iki", "ilgili", "i�te", "itibaren",
				"itibariyle", "ka�", "kadar", "kar��n", "kendi", "kendilerine", "kendine", "kendini", "kendisi",
				"kendisine", "kendisini", "kime", "kimi", "kimin", "kimisi", "kimse", "k�rk", "madem", "mi", "milyar",
				"milyon", "nedenle", "neyse", "nin", "n�n", "nun", "n�n", "�b�r", "olan", "olarak", "oldu", "oldu�u",
				"oldu�unu", "olduklar�n�", "olmad�", "olmad���", "olmak", "olmas�", "olmayan", "olmaz", "olsa", "olsun",
				"olup", "olur", "olursa", "oluyor", "on", "�n", "ona", "�nce", "ondan", "onlar", "onlara", "onlardan",
				"onlar�", "onlar�n", "onu", "onun", "orada", "�te", "�t�r�", "otuz", "�yle", "oysa", "pek", "ra�men",
				"sana", "�ayet", "�ekilde", "sekiz", "seksen", "sen", "senden", "seni", "senin", "�eyden", "�eye",
				"�eyi", "�eyler", "�imdi", "sizden", "size", "sizi", "sizin", "sonra", "��yle", "�una", "�unlar�",
				"�unu", "ta", "tabii", "tam", "tamam", "tamamen", "taraf�ndan", "trilyon", "t�m�", "u", "�", "��", "un",
				"�n", "�zere", "var", "vard�", "yapacak", "yap�lan", "yap�lmas�", "yap�yor", "yapmak", "yapt�",
				"yapt���", "yapt���n�", "yapt�klar�", "ye", "yedi", "yerine", "yetmi�", "yi", "y�", "yine", "yirmi",
				"yoksa", "yu", "y�z", "zaten", "zira" };

		Collections.addAll(turkish_stopwords, array);
	}

	public static void createEnglishStopWords() {
		String[] array = { "of", "mightn't", "should", "while", "our", "s", "same", "re", "they", "at", "here", "out",
				"be", "in", "wasn", "shouldn't", "any", "above", "into", "after", "those", "haven't", "having", "did",
				"nor", "their", "hasn't", "was", "all", "own", "other", "him", "there", "didn't", "wouldn't", "she's",
				"ll", "needn't", "more", "herself", "too", "but", "you're", "that'll", "yourselves", "shan", "himself",
				"when", "or", "over", "have", "what", "won't", "you", "m", "ma", "has", "to", "now", "ours", "under",
				"d", "your", "which", "doesn", "whom", "am", "hadn", "aren", "it's", "few", "my", "if", "you'd", "his",
				"ourselves", "shan't", "don", "most", "such", "are", "don't", "these", "no", "below", "mustn", "her",
				"a", "hadn't", "between", "how", "because", "off", "should've", "been", "haven", "being", "didn",
				"then", "do", "itself", "is", "so", "again", "were", "once", "you'll", "had", "weren't", "why", "y",
				"won", "does", "me", "you've", "before", "them", "this", "she", "only", "hers", "isn", "i", "will",
				"with", "some", "needn", "doesn't", "yourself", "ain", "he", "we", "couldn't", "yours", "and", "from",
				"about", "isn't", "themselves", "by", "through", "wasn't", "o", "until", "theirs", "for", "up",
				"wouldn", "against", "as", "mustn't", "not", "where", "aren't", "t", "its", "each", "ve", "doing",
				"who", "during", "an", "couldn", "hasn", "the", "on", "down", "mightn", "can", "just", "that", "weren",
				"both", "shouldn", "very", "further", "than", "it", "myself", "�" };

		Collections.addAll(english_stopwords, array);
	}
}