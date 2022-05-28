package com.jenniferhawk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration {

    private Boolean debug;

    private Map<String, String> bot;

    private Map<String, String> api;

    private Map<String, String> credentials;

    private Map<String,String> database;

    private List<String> channels;

    private Map<String, String> discord;

    private Map<String, String> irc;

    private Map<String, String> racetime;

    public Map<String, String> getRacetime() { return racetime; }

    public void setRacetime(Map<String, String> racetime) { this.racetime = racetime; }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Map<String, String> getBot() {
        return bot;
    }

    public void setBot(Map<String, String> bot) {
        this.bot = bot;
    }

    public Map<String, String> getApi() {
        return api;
    }

    public void setApi(Map<String, String> api) {
        this.api = api;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public Map<String, String> getDiscord () {
        return discord;
    }

    public void setDiscord(Map<String, String> discord) { this.discord = discord; }

    public Map<String, String> getDatabase() { return database; }

    public void setDatabase(Map<String, String> database) { this.database = database; }

    public Map<String, String> getIRC() { return irc; }

    public void setIrc(Map<String, String> irc) { this.irc = irc; }

    @Override
    public String toString() {
        return "Configuration{" +
                "bot=" + bot +
                ", api=" + api +
                ", credentials=" + credentials +
                ", channels=" + channels +
                ", discord =" + discord +
                ", database ="+ database +
                ", irc =" + irc +
                ", racetime = " + racetime +
                '}';
    }
}
