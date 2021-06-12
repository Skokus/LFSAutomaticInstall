import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateChapterThree {
    private int chapternumber;

    public CreateChapterThree(int chapternumber) {
        this.chapternumber = chapternumber;
    }

    public ArrayList<String> createchapterthreescript() throws MalformedURLException, InterruptedException {
        ArrayList<String> commands = new ArrayList<String>();
        String url = "https://www.linuxfromscratch.org/lfs/view/stable/chapter03/";
        String chapter = "introduction.html";
        commands.add("#CHAPTER 3 INSTALLATION BEGINS");
        for(int i = 0; i < chapternumber; i++) {
            URL sideurl = new URL(url + chapter);
            DownloadHTML html = new DownloadHTML(sideurl, "currentpage.txt");
            html.createHTMLFile();
            CheckForStuff check = new CheckForStuff(new File("currentpage.txt"));
            commands.addAll(createpackageinstall(html));
            chapter = check.findNext();
        }
        commands.add(3,"cd $LFS/sources/");
        commands.add(4,"wget https://www.linuxfromscratch.org/lfs/view/stable/wget-list");
        commands.add(5,"wget https://www.linuxfromscratch.org/lfs/view/stable/md5sums");
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
