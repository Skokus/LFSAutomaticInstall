import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateChapterFour {
    private int chapternumber;

    public CreateChapterFour(int chapternumber) {
        this.chapternumber = chapternumber;
    }

    public ArrayList<String> createchapterfourscript() throws MalformedURLException, InterruptedException {
        ArrayList<String> commands = new ArrayList<String>();
        String url = "https://www.linuxfromscratch.org/lfs/view/stable/chapter04/";
        String chapter = "introduction.html";
        commands.add("#CHAPTER 4 INSTALLATION BEGINS");
        for(int i = 0; i < chapternumber; i++) {
            URL sideurl = new URL(url + chapter);
            DownloadHTML html = new DownloadHTML(sideurl, "currentpage.txt");
            html.createHTMLFile();
            CheckForStuff check = new CheckForStuff(new File("currentpage.txt"));
            commands.addAll(createpackageinstall(html));
            chapter = check.findNext();
        }
        commands.remove(commands.size()-3);
        for(int i = 0; i < commands.size(); i++){
            if(commands.get(i).equals("passwd lfs")){
                commands.set(i, "echo lfs | passwd lfs --stdin");
                break;
            }
        }
        return commands;
    }

    private ArrayList<String> createpackageinstall(DownloadHTML html) {
        ArrayList<String> packageinstaller = new ArrayList<String>();
        ArrayList<String> commands;
        commands = new ArrayList<String>(new CheckForStuff(html.getFile()).findCommands());
        packageinstaller.addAll(commands);
        packageinstaller.add("\n");
        return packageinstaller;
    }
}
