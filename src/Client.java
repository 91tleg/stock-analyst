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

        System.out.println("##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("This is the list of top companies by change percentage:");
        int selectedList = scanner.nextInt();
        StockAnalyst s = new StockAnalyst();
        String listUrl = "";
        i = 0;
        for (Map.Entry<String, String> entry : stockLists.entrySet()) { 
            if (i == selectedList)
            {
                listUrl = entry.getValue();
                break;
            }
            i++;
        }
        listUrl = sa.getUrlText(listUrl);
        TreeMap<Double, String> treeMap = new TreeMap<>();
        treeMap = sa.getTopCompaniesByChangeRate(listUrl, 20);
        int j = 0;
        for (Double key : treeMap.keySet()) {
            System.out.println(treeMap.get(key) + ", " + key + "%");
        }
        System.out.println("##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
