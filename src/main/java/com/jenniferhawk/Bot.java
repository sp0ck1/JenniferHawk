package com.jenniferhawk;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.helix.domain.UserList;
import com.jenniferhawk.features.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.jenniferhawk.messages.DiscordCommands;
import com.jenniferhawk.messages.IncomingMessageBuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
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

    public static String OAUTH;

    public static String CLIENT_ID;

    public static String CLIENT_SECRET;

    public static String BROADCASTER_ID;

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
                OAUTH
        );
        //endregion

        //region TwitchClient
        twitchClient = clientBuilder
            //    .withClientId(CLIENT_ID)
              //  .withClientSecret(CLIENT_SECRET)
                .withEnableHelix(true)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnablePubSub(true)
                .withEnableTMI(true)
                .withEnableKraken(true)
                .withDefaultAuthToken(credential)
                .build();
        //endregion

        //region DiscordClient
        discordClient = new JDABuilder(AccountType.BOT)
                .setToken(configuration.getDiscord().get("token"))
                .addEventListeners(new DiscordCommands())
                .addEventListeners(new DiscordPrivateMessages())
                .addEventListeners(new IncomingMessageBuilder())
                .build();
        discordClient.awaitReady();
        //endregion

    }




    /**
     * Method to register all features
     */

    public void registerFeatures() {
            // Register Event-based features
          new WriteChannelChatToConsole(twitchClient.getEventManager());
          new N64(twitchClient.getEventManager());
          new JenniferGoLive(twitchClient.getEventManager());
          new IncomingMessageBuilder(twitchClient.getEventManager());
          new SubscriptionActions(twitchClient.getEventManager());
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

            CLIENT_ID = configuration.getApi().get("twitch_client_id");
            CLIENT_SECRET = configuration.getApi().get("twitch_client_secret");
            BROADCASTER_ID = configuration.getApi().get("user_id");
            OAUTH = configuration.getCredentials().get("irc");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to load Configuration ... Exiting.");
            System.exit(1);
        }


    }



    public void start() {
        // Connect to all channels
        TextChannel streamIsHappening = discordClient.getTextChannelById("627611883335319602");
        streamIsHappening.sendMessage("Hola!").queue();

        for (String channel : configuration.getChannels()) {
            twitchClient.getChat().joinChannel(channel);
            twitchClient.getClientHelper().enableStreamEventListener("sp0ck1");

        }
         twitchClient.getChat().sendMessage("sp0ck1","I am live!");
        // Utils.updateSubscriberInfo();
    }



}
