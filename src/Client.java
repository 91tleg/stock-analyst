import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Client {
    public static void main(String[] args) {
        StockAnalyst sa = new StockAnalyst();
        String url = "https://stockanalysis.com/list/";
        String text = sa.getUrlText(url);
        Scanner scanner = new Scanner(System.in);

        System.out.println("##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("These are the available stock list categories. Please choose one:");
        for (int i = 0; i < sa.getStocksListCategories(text).size(); i++) {
            System.out.println(i + ". " + sa.getStocksListCategories(text).get(i));
        }
        int selectedCategory = scanner.nextInt();
        String selectedCategoryName = sa.getStocksListCategories(text).get(selectedCategory);

        System.out.println("##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("These are the available stock lists within the category. Please choose key:");
        Map<String, String> stockLists = sa.getStocksListsInListCategory(text, selectedCategoryName);

        int i = 0;
        for (Map.Entry<String, String> entry : stockLists.entrySet()) {
            System.out.println(i + ". " + entry.getKey());
            i++;
        }

        int selectedList = scanner.nextInt();

        System.out.println("##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("This is the list of top companies by change percentage:");

        String listUrl = "";
        String listUrlText = "";
        i = 0;
        for (Map.Entry<String, String> entry : stockLists.entrySet()) {
            if (i == selectedList) {
                listUrl = entry.getValue();
                break;
            }
            i++;
        }
        listUrlText = sa.getUrlText(listUrl); // Get text from the new url

        TreeMap<Double, List<String>> treeMap = new TreeMap<>();
        Map<String, String> tableMap = new HashMap<>();
        tableMap = sa.getSubUrl(listUrlText);

        for (Map.Entry<String, String> entry : tableMap.entrySet()) {

            System.out.println("\nTable: " + entry.getKey());
            // System.out.println(entry.getValue());
            treeMap = sa.getTopCompaniesByChangeRate(entry.getValue(), 20);
            for (Map.Entry<Double, List<String>> entry1 : treeMap.entrySet()) {
                Double changeRate = entry1.getKey();
                List<String> companies = entry1.getValue();
                // If companies have duplicate change rate, print them out individually
                if (companies.size() > 1) {
                    for (String company : companies) {
                        System.out.println(company + ", " + changeRate + "%");
                    }
                } else {
                    System.out.println(companies.get(0) + ", " + changeRate + "%");
                }
            }
        }
        System.out.println("##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        scanner.close();
    }
}
