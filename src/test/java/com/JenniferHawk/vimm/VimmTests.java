package com.JenniferHawk.vimm;

import com.JenniferHawk.VimmParser.VimmN64;
import com.JenniferHawk.howlongtobeat.HowLongToBeatSearchResultEntry;
import com.JenniferHawk.howlongtobeat.HowLongToBeatSearchResultPage;
import com.JenniferHawk.howlongtobeat.HowLongToBeatService;
import com.JenniferHawk.howlongtobeat.HowLongToBeatServiceDefaultImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static com.JenniferHawk.howlongtobeat.HowLongToBeatUtil.*;
import static junit.framework.TestCase.assertEquals;

public class VimmTests {



    public HowLongToBeatSearchResultPage searchVimm64(String gameName) {
        String VIMM_SEARCH_URL = "https://vimm.net/vault/?p=list&system=N64&q="+gameName.replace(' ','+');

    HttpResponse<String> response;
    try {
        response = Unirest.post(VIMM_SEARCH_URL).header("accept", "*/*")
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE).queryString("page", "1")
                .field("queryString", gameName).field("t", "games").field("sorthead", "popular")
                .field("sortd", "Normal Order").field("plat", "").field("length_type", "main").field("length_min", "")
                .field("length_max", "").field("detail", "0").asString();
        return new HowLongToBeatSearchResultPage(gameName, response.getBody());
    } catch (
    UnirestException e) {
        throw new ContextedRuntimeException("Vimm not available", e).addContextValue("errorId", "VIMM GONE")
                .addContextValue("gameName", gameName).addContextValue("url", "https://vimm.net");
    }
}

    @Test
    public void parseVimm() {

        Document html = Jsoup.parseBodyFragment(searchVimm64("Bug").getHtmlFragment());
        Elements odd = html.getElementsByClass("odd");
        Elements link = odd.first().getElementsByAttribute("href");
        String vimmAppend = link.attr("href");
        System.out.println("https://vimm.net"+vimmAppend);

        }

        @Test
                public void VimmDownload() throws IOException {
        VimmN64 result = new VimmN64("bug");
            URL url = new URL(result.getDownload());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setInstanceFollowRedirects(true);
            con.setRequestProperty("User-Agent","Mozilla/4.0");
            String contentDisposition = con.getHeaderField("Content-Disposition");
            String fileName = contentDisposition.substring(contentDisposition.indexOf("filename=")+9,contentDisposition.indexOf(";filename*"));
            System.out.println(fileName);
        }



    }



