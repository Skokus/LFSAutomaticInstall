import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CheckForStuff {
    File file;

    public CheckForStuff(File file){
        this.file = file;
    }

    public String findNext() {
        String next = "";
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("<li class=\"next\">")){
                    String foundLine = myReader.nextLine();
                    String[] currencies = foundLine.split(" ");
                    for(int i = currencies.length-1; i >= 0; i--){
                        if(currencies[i].contains("href")){
                            next = currencies[i];
                            next = next.replaceAll("href=", "");
                            next = next.replaceAll("\"", "");
                            return next;
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return next;
    }

    public ArrayList<String> findCommands(){
        ArrayList<String> commands = new ArrayList<String>();
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("<kbd class=")){
                    commands.add(data);
                    if(!data.contains("</kbd>")) {
                        data = myReader.nextLine();
                        while (!data.contains("</kbd>")) {
                            commands.add(data);
                            data = myReader.nextLine();
                        }
                        commands.add(data);
                    }
                }
            }
            for(int i = 0; i < commands.size(); i++){
                commands.set(i,commands.get(i).replaceAll("<kbd class=\"command\">", ""));
                commands.set(i,commands.get(i).replaceAll("<kbd class=", ""));
                commands.set(i,commands.get(i).replaceAll("\"command\">", ""));
                commands.set(i,commands.get(i).replaceAll("<code class=", ""));
                commands.set(i,commands.get(i).replaceAll("\"literal\">", ""));
                commands.set(i,commands.get(i).replaceAll("<code class=\"literal\">", ""));
                commands.set(i,commands.get(i).replaceAll("</kbd>", ""));
                commands.set(i,commands.get(i).replaceAll("</code>", ""));
                commands.set(i,commands.get(i).replaceAll("<em class=", ""));
                commands.set(i,commands.get(i).replaceAll("\"parameter\"><code>ABI=32</em> ", ""));
                commands.set(i,commands.get(i).replaceAll("&lt;", "<"));
                commands.set(i,commands.get(i).replaceAll("&gt;", ">"));
                commands.set(i,commands.get(i).replaceAll("&lt;", "<"));

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return commands;
    }

    public String findTitle(){
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("<title>")){
                    data = myReader.nextLine();
                    String title = data.split("&nbsp;")[1];
                    return title;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "";
    }
}
