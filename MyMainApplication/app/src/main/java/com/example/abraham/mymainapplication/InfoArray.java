package com.example.abraham.mymainapplication;

/**
 * Created by Abraham on 1/6/2015.
 */
public class InfoArray {

    public Info[] mInfoI;

    public InfoArray(){

        mInfoI = new Info[3];

        mInfoI[0] = new Info(R.drawable.abarahm,
                "(Mini) Biography\n" +
                        "Born in Gran Canaria, Spain. My dad comes from Lebanon, and my mother from the US. I have one sister (21 years old, studying in Sheffield, England). Grew up in Gran Canaria, and came to Grinnell in 2013 to pursue a BA. Major unknown yet (possibly chemistry or computer science, or both). That’s about it I guess.");

        mInfoI[1] = new Info(R.drawable.abarahm,
                "Interests\n" +
                        "Soccer (specifically, Real Madrid)\n" +
                        "System Of A Down\n" +
                        "Videogames\n" +
                        "Computer Science\n" +
                        "Ping Pong\n" +
                        "Activism\n" +
                        "Debate\n" +
                        "Aikido\n");

        mInfoI[2] = new Info(R.drawable.abarahm,
                "Reasons to like Abraham\n" +
                        "I am:\n" +
                        "Smart, intelligent, bright, erudite, cultured, intellectual, learned, genius\n" +
                        "Handsome, beautiful, attractive, good-looking, \n" +
                        "Funny, witty, humorous, hilarious, comical, amusing\n" +
                        "Understanding, caring, compassionate, altruistic, generous, kind, \n" +
                        "Modest\n");

    }

    public Info getInfo (int number){
        return mInfoI[number];
    }
}
