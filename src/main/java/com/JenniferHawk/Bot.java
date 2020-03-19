package com.JenniferHawk;

import com.JenniferHawk.JenniferGUI.JChatPane;
import com.JenniferHawk.features.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.io.InputStream;
import java.net.URL;

public class Bot {

    /**
     * Holds the Bot Configuration
     */
    public static Configuration configuration;

    /**
     * Twitch4J API
     */
    public static TwitchClient twitchClient;

    /**
     * JDA API
     */
    public static JDA discordClient;


    /**
     * Constructor
     */
    public Bot() throws LoginException, InterruptedException {

        URL localPackage = this.getClass().getResource("");
        URL urlLoader = Bot.class.getProtectionDomain().getCodeSource().getLocation();
        String localDir = localPackage.getPath();
        String loaderDir = urlLoader.getPath();
        System.out.printf("loaderDir = %s\n localDir = %s\n", loaderDir, localDir);


        // Load Configuration
        loadConfiguration();

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        //region Auth
        OAuth2Credential credential = new OAuth2Credential(
                "twitch",
                configuration.getCredentials().get("irc")
        );
        //endregion

        //region TwitchClient
        twitchClient = clientBuilder
                .withClientId(configuration.getApi().get("twitch_client_id"))
                .withClientSecret(configuration.getApi().get("twitch_client_secret"))
                .withEnableHelix(true)
                /*
                 * Chat Module
                 * Joins irc and triggers all chat based events (viewer join/leave/sub/bits/gifted subs/...)
                 */
                .withChatAccount(credential)
                .withEnableChat(true)
                /*
                 * GraphQL has a limited support
                 * Don't expect a bunch of features enabling it
                 */
                .withEnableGraphQL(true)
                /*
                 * Kraken is going to be deprecated
                 * see : https://dev.twitch.tv/docs/v5/#which-api-version-can-you-use
                 * It is only here so you can call methods that are not (yet)
                 * implemented in Helix
                 */
                .withEnableKraken(false)
                .withEnableTMI(false)
                .withEnablePubSub(true)
                /*
                 * Build the TwitchClient Instance
                 */
                .build();
        //endregion

        //region DiscordClient
        discordClient = new JDABuilder(AccountType.BOT)
                .setToken(configuration.getDiscord().get("token"))
                .addEventListeners(new DiscordCommands())
                .addEventListeners(new DiscordPrivateMessages())
                .build();
        discordClient.awaitReady();
        //endregion

    }




    /**
     * Method to register all features
     */

    public void registerFeatures() {
       // Register Event-based features
          new TwitchCommands(twitchClient.getEventManager()); //
          new WriteChannelChatToFile(twitchClient.getEventManager());
          new N64(twitchClient.getEventManager());
          new WriteChannelChatToConsole(twitchClient.getEventManager());
    }

    public JChatPane createChatPane() {
        JChatPane jChatPane = new JChatPane("Highly","Appreciated!");

        jChatPane.initChat(twitchClient.getEventManager());

        return jChatPane;
    }

    /**
     * Load the Configuration
     */
    private void loadConfiguration() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("config.yaml");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            configuration = mapper.readValue(is, Configuration.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to load Configuration ... Exiting.");
            System.exit(1);
        }
    }



    public void start() {
        // Connect to all channels

        for (String channel : configuration.getChannels()) {
            twitchClient.getChat().joinChannel(channel);
        }
        // twitchClient.getChat().sendMessage("sp0ck1","I am here!");





    }



}
