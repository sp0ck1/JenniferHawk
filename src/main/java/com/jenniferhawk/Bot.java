package com.jenniferhawk;

import com.jenniferhawk.discord.DiscordPrivateMessages;
import com.jenniferhawk.discord.N64RoleAssigner;
import com.jenniferhawk.discord.VulcanRoleAssigner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.jenniferhawk.discord.DiscordCommands;
import com.jenniferhawk.irc.IRCBot;
import com.jenniferhawk.messages.IncomingMessageBuilder;
import com.jenniferhawk.twitch.ChannelGoLiveCheck;
import com.jenniferhawk.twitch.ChannelStateEventsHandler;
import com.jenniferhawk.twitch.SubscriptionActions;
import com.jenniferhawk.twitch.WriteChannelChatToConsole;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.pircbotx.exception.IrcException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
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
                .addEventListeners(new N64RoleAssigner())
                .addEventListeners(new VulcanRoleAssigner())
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
          new ChannelStateEventsHandler(twitchClient.getEventManager());
          new IncomingMessageBuilder(twitchClient.getEventManager());
          new SubscriptionActions(twitchClient.getEventManager());

          // Start IRC Bot
          try {
                  new IRCBot();
              } catch (IOException | IrcException e) {
                  e.printStackTrace();
              }
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
        ChannelGoLiveCheck goLiveCheck = new ChannelGoLiveCheck();
        for (String channel : configuration.getChannels()) {
            twitchClient.getChat().joinChannel(channel);
        }

        goLiveCheck.addChannel("sp0ck1");
     //   twitchClient.getClientHelper().enableStreamEventListener("sp0ck1");
        twitchClient.getChat().sendMessage("sp0ck1","I am live!");
    }



}
