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
			element = element.replaceAll("‘", "");
			element = element.replaceAll("’", "");
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
		String[] array = { "birþey", "birkaç", "biri", "belki", "bazý", "az", "aslýnda", "ama", "acaba", "eðer", "en",
				"diye", "defa", "de", "daha", "da", "bu", "biz", "kez", "için", "ise", "ile", "hiç", "her", "hepsi",
				"hep", "gibi", "nerde", "neden", "ne", "nasýl", "mý", "mü", "mu", "kim", "hem", "siz", "sanki", "o",
				"niçin", "niye", "nereye", "nerede", "ki", "þu", "þey", "çünkü", "çok", "yani", "ya", "veya", "ve",
				"tüm", "a", "altý", "altmýþ", "ancak", "arada", "artýk", "asla", "ayrýca", "bana", "bazen", "bazýlarý",
				"ben", "benden", "beni", "benim", "beri", "beþ", "bile", "bilhassa", "bin", "bir", "biraz", "birçoðu",
				"birçok", "birisi", "bizden", "bize", "bizi", "bizim", "böyle", "böylece", "buna", "bunda", "bundan",
				"bunlar", "bunlarý", "bunlarýn", "bunu", "bunun", "burada", "bütün", "çoðu", "çoðunu", "dahi", "dan",
				"deðil", "diðer", "diðeri", "diðerleri", "doksan", "dokuz", "dolayý", "dolayýsýyla", "dört", "e",
				"edecek", "eden", "ederek", "edilecek", "ediliyor", "edilmesi", "ediyor", "elbette", "elli", "etmesi",
				"etti", "ettiði", "ettiðini", "fakat", "falan", "filan", "gene", "gereði", "gerek", "göre", "hala",
				"halde", "halen", "hangi", "hangisi", "hani", "hatta", "henüz", "herhangi", "herkes", "herkese",
				"herkesi", "herkesin", "hiçbir", "hiçbiri", "i", "ý", "içinde", "iki", "ilgili", "iþte", "itibaren",
				"itibariyle", "kaç", "kadar", "karþýn", "kendi", "kendilerine", "kendine", "kendini", "kendisi",
				"kendisine", "kendisini", "kime", "kimi", "kimin", "kimisi", "kimse", "kýrk", "madem", "mi", "milyar",
				"milyon", "nedenle", "neyse", "nin", "nýn", "nun", "nün", "öbür", "olan", "olarak", "oldu", "olduðu",
				"olduðunu", "olduklarýný", "olmadý", "olmadýðý", "olmak", "olmasý", "olmayan", "olmaz", "olsa", "olsun",
				"olup", "olur", "olursa", "oluyor", "on", "ön", "ona", "önce", "ondan", "onlar", "onlara", "onlardan",
				"onlarý", "onlarýn", "onu", "onun", "orada", "öte", "ötürü", "otuz", "öyle", "oysa", "pek", "raðmen",
				"sana", "þayet", "þekilde", "sekiz", "seksen", "sen", "senden", "seni", "senin", "þeyden", "þeye",
				"þeyi", "þeyler", "þimdi", "sizden", "size", "sizi", "sizin", "sonra", "þöyle", "þuna", "þunlarý",
				"þunu", "ta", "tabii", "tam", "tamam", "tamamen", "tarafýndan", "trilyon", "tümü", "u", "ü", "üç", "un",
				"ün", "üzere", "var", "vardý", "yapacak", "yapýlan", "yapýlmasý", "yapýyor", "yapmak", "yaptý",
				"yaptýðý", "yaptýðýný", "yaptýklarý", "ye", "yedi", "yerine", "yetmiþ", "yi", "yý", "yine", "yirmi",
				"yoksa", "yu", "yüz", "zaten", "zira" };

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
				"both", "shouldn", "very", "further", "than", "it", "myself", "ý" };

		Collections.addAll(english_stopwords, array);
	}
}