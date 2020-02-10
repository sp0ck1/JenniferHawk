package com.JenniferHawk.VimmParser;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class VimmN64 {

    private String link;
    private String download;
    private String otherDownload;
    private String fileName;
    private String otherFileName;

    private String getVimmResultsPage(String gameName) {
        String VIMM_SEARCH_URL = "https://vimm.net/vault/?p=list&system=N64&q=" + gameName.replace(' ', '+');

        HttpResponse<String> response;
        try {
            response = Unirest.post(VIMM_SEARCH_URL).header("accept", "*/*")
                    .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE).queryString("page", "1")
                    .field("queryString", gameName).field("t", "games").field("sorthead", "popular")
                    .field("sortd", "Normal Order").field("plat", "").field("length_type", "main").field("length_min", "")
                    .field("length_max", "").field("detail", "0").asString();
            return response.getBody();
        } catch (
                UnirestException e) {
            throw new ContextedRuntimeException("Vimm not available", e).addContextValue("errorId", "VIMM GONE")
                    .addContextValue("gameName", gameName).addContextValue("url", "https://vimm.net");
        }
    }

    private String parseVimmResultsPage(String htmlToParse) {

        Document html = Jsoup.parseBodyFragment(htmlToParse);
        Elements odd = html.getElementsByClass("odd");
        Elements link = odd.first().getElementsByAttribute("href");

        return link.attr("href");
    }

    public VimmN64(String gameName) {
        String VIMM = "https://vimm.net";
        String link = VIMM + parseVimmResultsPage(getVimmResultsPage(gameName));
        String vimmId = link.substring(23);  // TODO: Make this number dynamic to safeguard against future URL changes

        setLink(link);
        setDownloads(vimmId);
        setFilename(parseFilename(getDownload()));
        setOtherFileName(parseFilename(getOtherDownload()));
    }

    private void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return this.link;
    }

    public String getDownload() {
        return this.download;
    }

    public String getOtherDownload() {
        return this.otherDownload;
    }

    private void setDownloads(String vimmId) {
        String downloadPrefix = "https://download2.vimm.net/download.php?id=";
        int adjustedId = Integer.parseInt(vimmId) - 53;
        int readjustedId = adjustedId - 1;

        this.download = downloadPrefix + adjustedId;
        this.otherDownload = downloadPrefix + readjustedId;
    }

    private String parseFilename(String link) {

        URL url;
        HttpURLConnection con;
        String fileName = "";

        try {

            url = new URL(link);
            con = (HttpURLConnection) url.openConnection();

            con.setInstanceFollowRedirects(true);
            con.setRequestProperty("User-Agent", "Mozilla/4.0");

            String contentDisposition = con.getHeaderField("Content-Disposition");
            fileName = contentDisposition.substring(contentDisposition.indexOf("filename=") + 9, contentDisposition.indexOf(";filename*"));

        } catch (IOException e) {
            e.printStackTrace();
        }   System.out.println(fileName);
        return fileName;
    }

    private void setFilename(String fileName) {
        this.fileName = fileName;

    }

    private void setOtherFileName(String fileName) {
        this.otherFileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getOtherFileName() {
        return this.otherFileName;
    }
}
