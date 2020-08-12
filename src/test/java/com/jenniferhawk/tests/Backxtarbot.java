package com.jenniferhawk.tests;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.events.ChannelChangeGameEvent;
import com.github.twitch4j.events.ChannelChangeTitleEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.github.twitch4j.helix.domain.GameList;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import main.de.backxtar.commands.CommandManager;
import main.de.backxtar.listener.CommandListener;
import main.de.backxtar.listener.manage.JoinMessageListener;
import main.de.backxtar.listener.manage.AutoReactMessageListener;
import main.de.backxtar.listener.manage.ReactRolesListener;
import main.de.backxtar.listener.voice.VoiceListener0;
import main.de.backxtar.listener.voice.VoiceListener1;
import main.de.backxtar.manage.DONOTOPEN;
import main.de.backxtar.manage.LiteSQL;
import main.de.backxtar.manage.SQLManager;
import main.de.backxtar.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Random;

public class Backxtarbot {

    public static Backxtarbot INSTANCE;
    public ShardManager shardMan;
    private CommandManager cmdMan;
    private Thread StatusLoop;
    public AudioPlayerManager audioPlayerManager;
    public PlayerManager playerManager;


    public static void main(String[] args) {
        try {
            new Backxtarbot();
        } catch (LoginException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Backxtarbot() throws LoginException, IllegalArgumentException {
        INSTANCE = this;

        LiteSQL.connect();
        SQLManager.onCreate();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.create(DONOTOPEN.token,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS);


        builder.setActivity(Activity.watching("STARTING..."));
        builder.setStatus(OnlineStatus.IDLE);

        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.playerManager = new PlayerManager();
        this.cmdMan = new CommandManager();

        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new JoinMessageListener());
        builder.addEventListeners(new AutoReactMessageListener());
        builder.addEventListeners(new ReactRolesListener());
        builder.addEventListeners(new VoiceListener0());
        builder.addEventListeners(new VoiceListener1());


        shardMan = builder.build();
        System.out.println("INITIALIZE START...");
        System.out.println("BOT STARTING...");
        System.out.println("BOT HAS SUCCESSFULLY STARTED!");

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        shutdown();
        runLoop();
        loadTwitch();
    }

    public void loadTwitch() {

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withClientId(DONOTOPEN.twitchclientid)
                .withClientSecret(DONOTOPEN.twitchsecret)
                .withEnableHelix(true)
                .build();
        System.out.println("TWITCH API LOADED!");

        twitchClient.getClientHelper().enableStreamEventListener(DONOTOPEN.channelname);
        TextChannel channel = jda.getGuildById("693870129993220207l").getTextChannelById("740287933361422437l");
        twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelGoLiveEvent.class, e -> {
            GameList resultList = twitchClient.getHelix().getGames(null, Collections.singletonList(e.getStream().getGameId()), null).execute();
            String url = "https://twitch.tv/" + e.getChannel().getName();
twitch.tv/backxtar
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Twitch Benachrichtigung:");
            builder.setColor(0x8e44ad);
            builder.setThumbnail(jda.getUserById("139131758267465728l").getEffectiveAvatarUrl());
            builder.setTimestamp(OffsetDateTime.now());
            builder.setFooter("Powered by Backxtar#1524.", "http://i.epvpimg.com/tbIibab.png");
            builder.setDescription("<:twitch:694607220754612244> " + e.getStream().getUserName() + " ist LIVE und streamt: " + "[" + e.getStream().getTitle() + "](" + url + ")");
            builder.addField("Spiel:", resultList.toString(), true);
            builder.addField("Viewers", e.getStream().getViewerCount().toString(), true);
            builder.setImage(e.getStream().getThumbnailUrl());

            channel.sendMessage(builder.build()).queue();
        });

        twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelGoOfflineEvent.class, e -> {

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Twitch Benachrichtigung:");
            builder.setColor(0x8e44ad);
            builder.setThumbnail(jda.getUserById("139131758267465728l").getEffectiveAvatarUrl());
            builder.setTimestamp(OffsetDateTime.now());
            builder.setFooter("Powered by Backxtar#1524.", "http://i.epvpimg.com/tbIibab.png");
            builder.setDescription("<:twitch:694607220754612244> " + e.getChannel().getName() + " ist jetzt offline!");
            builder.addField("Folgst du mir schon?", "<:twitch:694607220754612244> [**Backxtar auf Twitch**](https://www.twitch.tv/backxtar)", false);
            builder.addField("Lass doch ein Abo da!", "<:youtube:735377506378580031> [**Backxtar auf YouTube**](https://www.youtube.com/user/backxtar)", false);
            builder.addField("Zwitscher mir zu!", "<:twitter:697717521305829436> [**Backxtar auf Twitter**](https://twitter.com/Backxtar)", false);
            builder.setImage("http://i.epvpimg.com/yA8xaab.jpg");

            channel.sendMessage(builder.build()).queue();
        });

        twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelChangeTitleEvent.class, e -> {
            GameList resultList = twitchClient.getHelix().getGames(null, Collections.singletonList(e.getStream().getGameId()), null).execute();
            String url = "https://twitch.tv/" + e.getChannel().getName();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Twitch Benachrichtigung:");
            builder.setColor(0x8e44ad);
            builder.setThumbnail(jda.getUserById("139131758267465728l").getEffectiveAvatarUrl());
            builder.setTimestamp(OffsetDateTime.now());
            builder.setFooter("Powered by Backxtar#1524.", "http://i.epvpimg.com/tbIibab.png");
            builder.setDescription("<:twitch:694607220754612244> " + e.getStream().getUserName() + " hat den Titel zu " + "[" + e.getStream().getTitle() + "](" + url + ") geändert!");
            builder.addField("Spiel:", resultList.toString(), true);
            builder.addField("Viewers", e.getStream().getViewerCount().toString(), true);
            builder.setImage(e.getStream().getThumbnailUrl());

            channel.sendMessage(builder.build()).queue();
        });

        twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelChangeGameEvent.class, e -> {
            GameList resultList = twitchClient.getHelix().getGames(null, Collections.singletonList(e.getStream().getGameId()), null).execute();
            String url = "https://twitch.tv/" + e.getChannel().getName();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Twitch Benachrichtigung:");
            builder.setColor(0x8e44ad);
            builder.setThumbnail(jda.getUserById("139131758267465728l").getEffectiveAvatarUrl());
            builder.setTimestamp(OffsetDateTime.now());
            builder.setFooter("Powered by Backxtar#1524.", "http://i.epvpimg.com/tbIibab.png");
            builder.setDescription("<:twitch:694607220754612244> " + e.getStream().getUserName() + " hat das Spiel gewechselt:");
            builder.addField("Aktuelles Spiel:", resultList.toString(), true);
            builder.addField("Viewers", e.getStream().getViewerCount().toString(), true);
            builder.addField("Hier gehts zum Stream:", "[**KLICK MICH!**](" + url + ")", true);
            builder.setImage(e.getStream().getThumbnailUrl());

            channel.sendMessage(builder.build()).queue();
        });
    }

    public void shutdown() {

        new Thread(() -> {

            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while((line = reader.readLine()) != null) {

                    if(line.equalsIgnoreCase("exit")) {
                        shutdown = true;
                        if(shardMan != null) {
                            shardMan.setStatus(OnlineStatus.OFFLINE);
                            shardMan.shutdown();
                            LiteSQL.disconnect();
                            System.out.println("BOT IS SHUTTING DOWN...");
                            try {
                                Thread.sleep(200);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("BOT OFFLINE.");
                            System.exit(0);
                        }
                        if(StatusLoop != null) {
                            StatusLoop.interrupt();
                        }
                        reader.close();
                    }
                    else {
                        System.out.println("USE 'exit' TO SHUTDOWN!");
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public boolean shutdown = false;
    public boolean hasStarted = false;

    public void runLoop() {
        this.StatusLoop = new Thread(() ->  {

            long time = System.currentTimeMillis();

            while(!shutdown) {
                if(System.currentTimeMillis() >= time + 1000) {
                    time = System.currentTimeMillis();
                    onSecond();
                }
            }

        });
        this.StatusLoop.setName("StatusLoop");
        this.StatusLoop.start();
    }

    String[] status = new String[] {"Version: 2.0", "https://twitch.tv/backxtar", "Prefix: ?", "Type: ?help", "User Counter: %members"};
    int next = 60;

    public void onSecond() {

        if(next%10 == 0) {

            if(!hasStarted) {
                hasStarted = true;
            }

            Random rand = new Random();
            int i = rand.nextInt(status.length);

            shardMan.getShards().forEach(jda -> {
                String text = status[i].replaceAll("%members", "" + jda.getUsers().size());

                jda.getPresence().setActivity(Activity.watching(text));
            });

            if(next == 0) {
                next = 59;
                onCheckTimeRanks();
            }
            else {
                next--;
            }
        }
        else {
            next--;
        }
    }

    public void onCheckTimeRanks() {
        ResultSet set = LiteSQL.onQuery("SELECT userid, guildid FROM timeranks WHERE ((julianday(CURRENT_TIMESTAMP) - julianday(time)) * 1000) >= 15");

        try {
            while(set.next()) {
                long userid = set.getLong("userid");
                long guildid = set.getLong("guildid");

                Guild guild = this.shardMan.getGuildById(guildid);
                guild.removeRoleFromMember(guild.getMemberById(userid), guild.getRoleById(648047607486087168l)).queue();

                LiteSQL.onUpdate("DELETE FROM timeranks WHERE userid = " + userid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public static JDA jda;
}