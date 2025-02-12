import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockAnalyst implements IStockAnalyst {

    // done
    public List<String> getStocksListCategories(final String urlText) {
        List<String> categories = new ArrayList<>();
        Pattern pattern = Pattern.compile("<h2.*?>(.*?)</h2>");
        Matcher matcher = pattern.matcher(urlText);
        while (matcher.find()) {
            categories.add(matcher.group(1));
        }
        return categories;
    }

    public Map<String, String> getStocksListsInListCategory(final String urlText, final String stockCategoryName) {
        Map<String, String> lists = new HashMap<>();

        Pattern h2Pattern = Pattern.compile("<h2.*?>(.*?)</h2>.*?<ul.*?>(.*?)</ul>");
        Matcher h2Matcher = h2Pattern.matcher(urlText);

        while (h2Matcher.find()) {
            String catName = h2Matcher.group(1).trim();
            String content = h2Matcher.group(2); // The part after the category

            // Check if this is the correct category
            if (catName.equalsIgnoreCase(stockCategoryName)) {
                Pattern listsPattern = Pattern.compile("<a.*?list/(.*?)\">(.*?)</a>");
                Matcher listsMatcher = listsPattern.matcher(content);
                while (listsMatcher.find()) {
                    String listUrl = "https://stockanalysis.com/list/" + listsMatcher.group(1); // url for the sub lists
                    String listName = listsMatcher.group(2);
                    lists.put(listName, listUrl);
                }
            }
        }
        return lists;
    }

    public TreeMap<Double, String> getTopCompaniesByChangeRate(final String urlText, int topCount) {
        TreeMap<Double, String> topCompanies = new TreeMap<>();
        Pattern pattern = Pattern.compile("<td.*?\"slw svelte-utsffj\">(.*?)</td>.*?\"rg svelte-utsffj\">(.*?)%</td>");
        Matcher matcher = pattern.matcher(urlText);
        while (matcher.find()) {
            if ("-".equals(matcher.group(2))) {
                continue;  // Skip if the change rate is -
            }
            else {
            double changeRate = Double.parseDouble(matcher.group(2));
            topCompanies.put(changeRate, matcher.group(1));
            }
            if (topCompanies.size() == topCount) {
                break;
            }
        }
        return topCompanies;
    }

    // done
    public String getUrlText(final String url) {
        String text = "";
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")))) {
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    text += inputLine + "\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public String getStockRankedByMarketCap() {
        String s = "";
        return s;
    }

}
