package org.example.parser;

import org.example.entity.ArticlePreview;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class LJParser {
    public LJParser() {}

    // it's better to call it String nickname and than create url
    public List<ArticlePreview> getAllLaterArticlePreviewsSinceId(String url) {
        List<ArticlePreview> listOfArticlePreviews = new ArrayList<>();
        String stringHTML = getHTMLbyLink(url);
        // TODO having previous pages

        Document doc = Jsoup.parse(stringHTML);

        Elements elements = doc.select("div.post-asset");

        for (Element element : elements) {
            if (isNotStickyArticle(element)) {
                ArticlePreview articlePreview = getArticlePreviewFromElement(element);
                listOfArticlePreviews.add(articlePreview);
            }
        }
        return listOfArticlePreviews;
    }

    private String getHTMLbyLink(String url) {
        // String url = "https://vykhochetepesen.livejournal.com/";
        // TODO Exception managing
        BufferedReader in = null;
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            // int responseCode = connection.getResponseCode();
            // System.out.println("Response code: " + responseCode);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response.toString();
    }

    private ArticlePreview getArticlePreviewFromElement(Element element) {
        int id = Integer.parseInt(element.id().split("-")[2]);
        Elements h2 = element.select("h2");
        String mainHeader = h2.first().text();
        String subHeader = h2.size() == 1 ? "" : h2.get(1).text();
        String link = element.select("a.subj-link").attr("href");
        Date date = parseDate(element.select("abbr.datetime").text());
        //Date date = new Date();
        String text = element.select("div.asset-body p").text();

        return new ArticlePreview(id, mainHeader, subHeader, text, link, date);
    }
    private boolean isNotStickyArticle(Element element) {
        return element.select("h2 span img[title=[sticky post]]").isEmpty();
    }

    private Date parseDate(String strDate) {
        // Data format looks like this: "Dec. 23rd, 2023 at 12:44 PM"
        System.out.println(strDate);
        String[] rawDate = strDate.split(" ");
        int year = Integer.parseInt(rawDate[2]);
        int month = months.get(rawDate[0]);
        int lenOfDays = rawDate[1].charAt(1) >= '0' && rawDate[1].charAt(1) <= '9' ? 2 : 1;
        int day = Integer.parseInt(rawDate[1].substring(0, lenOfDays));
        String[] rawTime = rawDate[4].split(":");
        int hours = Integer.parseInt(rawTime[0]);
        if ("PM".equals(rawDate[5])) hours += 12;
        int minutes = Integer.parseInt(rawTime[1]);

        return new Date(year, month, day, hours, minutes);
    }

    // I can make it as ImmutableList
    private static final Map<String, Integer> months = Map.ofEntries(
            entry("Jan.", 0),
            entry("Feb.", 1),
            entry("Mar.", 2),
            entry("Apr.", 3),
            entry("May", 4),
            entry("Jun.", 5),
            entry("Jul.", 6),
            entry("Aug.", 7),
            entry("Sep.", 8),
            entry("Oct.", 9),
            entry("Nov.", 10),
            entry("Dec.", 11)
    );
}
