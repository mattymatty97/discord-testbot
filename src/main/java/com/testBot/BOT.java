package com.testBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import java.sql.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.testBot.BotGuild;
import com.testBot.MyListener;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BOT
{
    static String url;
    public static void main(String[] arguments) throws Exception
    {
        Connection conn=null;

        List<BotGuild> savedGuilds=new ArrayList<BotGuild>();
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            //System.out.println("Missing MySQL JDBC Driver!");
            System.out.println("Missing postgresql JDBC Driver!");
            e.printStackTrace();
            return;
        }
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("SQL INITIALIZZATED");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.exit(-1);
        }

        JDA api = new JDABuilder(AccountType.BOT).setToken("NDIwNTY0MTU1NjQ5NjIyMDE4.DYA5dA._MmdVLt7jHqwlJpbEI4YE07ULxs").buildAsync();
        api.addEventListener(new MyListener(conn,savedGuilds));
        api.getPresence().setGame(Game.listening("suggestions :/"));
    }
}
