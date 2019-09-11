package com.shubhloans.shubhloans;

import com.shubhloans.shubhloans.utils.StringHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        String html = "<html><head><title>Sample Title</title></head>"
//                + "<body>"
//                + "<p>Sample Content</p>"
//                + "<div id='sampleDiv'><a href='www.google.com'>Google</a>"
//                + "<h3><a>Sample</a><h3>"
//                +"</div>"
//                +"</body></html>";
//        Document document = Jsoup.parse(html);
//
//        //a with href
//        Element link = document.select("a").first();

        String url = "https://www.shubhloans.com/index.html";
       /* String htmlString = Jsoup.connect(url).get().html();
        Document dirtyDoc = Jsoup.parse(htmlString);
        Document cleanDoc = new Cleaner(Whitelist.none()).clean(dirtyDoc);
       // Element link = document.select("a").first();
       // System.out.println("Text: " + link.nextSibling());}
      Elements e=  cleanDoc.select("a");
            //while(e.hasText()){
        System.out.println("Text: " + e.text());}*/
//        Document document = Jsoup.connect(url).get();
//        document.outputSettings(new Document.OutputSettings().prettyPrint(true));
//        Elements links = document.select("a");
//
//        for (Element link : links) {
//
//           // System.out.println("link : " + link.attr("href"));
//            System.out.println("text : " + link.text());
//        }
        Document document = Jsoup.connect(url).get();
        // Using Elements to get the Meta data
        Elements description = document.select("div");
        // Locate the content attribute
        String desc1 = description.text().replaceAll("\\{\\{displayFeature.title\\}\\}|\\{\\{displayFeature.details\\}", "");
        System.out.println("s compl : " + desc1);
        System.out.println("s 10 : " + StringHelper.getEveryNthCharacter(desc1, 10, 10));
        System.out.println("m 10 : " + StringHelper.getEveryNthCharacter(desc1, 10, desc1.length()));
        System.out.println("char 10 : " + Arrays.toString(StringHelper.getEveryNthCharacter(desc1, 10, desc1.length()).toCharArray()));

        String desc = desc1.replaceAll("[!|'.“/,‘’”}]", "").replaceAll("com", ".com");
        String words[] = desc.split("\\s+");
        for (Map.Entry<String, Integer> entry : StringHelper.getUniqueWordCountFromStringArray(words).entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("text : " + Arrays.toString(words));

    }
    // assertEquals(4, 2 + 2);
    //}
}