import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CreateChapterFive {

    private int chapternumber;

    public CreateChapterFive(int chapternumber) {
        this.chapternumber = chapternumber;
    }

    public ArrayList<String> createchapterfivescript() throws MalformedURLException, InterruptedException {
        ArrayList<String> commands = new ArrayList<String>();
        String url = "https://www.linuxfromscratch.org/lfs/view/stable/chapter05/";
        String chapter = "introduction.html";
        String packagename;
        for(int i = 0; i < chapternumber; i++) {
            URL sideurl = new URL(url + chapter);
            DownloadHTML html = new DownloadHTML(sideurl, "currentpage.txt");
            html.createHTMLFile();
            CheckForStuff check = new CheckForStuff(new File("currentpage.txt"));
            packagename = check.findTitle();
            if (packagename.contains("Introduction")) {
                commands.add("#CHAPTER 5 INSTALLATION BEGINS");
            } else if (packagename.contains("GCC")){
                packagename = "gcc";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Glibc")) {
                packagename = "glibc";
                commands.addAll(createpackageinstall(packagename, html));
            } else {
                commands.addAll(createpackageinstall(packagename, html));
            }
            chapter = check.findNext();
        }
        return commands;
    }

    private ArrayList<String> createpackageinstall(String packagename, DownloadHTML html) {
        ArrayList<String> packageinstaller = new ArrayList<String>();
        String realpackagename = "";
        String dirname = "";
        try {
            File myObj = new File("src/packagenames.txt");
            Scanner myReader = new Scanner(myObj);
            if(packagename.length() > 6) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.contains(packagename.substring(1, 6))) {
                        String dataarray[] = data.split(" ", 2);
                        realpackagename = dataarray[1];
                        dirname = realpackagename.replaceAll("\\.gz", "");
                        dirname = dirname.replaceAll("\\.xz", "");
                        dirname = dirname.replaceAll("\\.tar", "");
                        dirname = dirname.replaceAll("\\.bz2", "");
                    }
                }
            } else {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.contains(packagename)) {
                        String dataarray[] = data.split(" ", 2);
                        realpackagename = dataarray[1];
                        dirname = realpackagename.replaceAll("\\.gz", "");
                        dirname = dirname.replaceAll("\\.xz", "");
                        dirname = dirname.replaceAll("\\.tar", "");
                        dirname = dirname.replaceAll("\\.bz2", "");
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        packageinstaller.add("tar -xvf " + realpackagename);
        packageinstaller.add("cd " + dirname);
        html.createHTMLFile();
        ArrayList<String> commands;
        commands = new ArrayList<String>(new CheckForStuff(html.getFile()).findCommands());
        packageinstaller.addAll(commands);
        packageinstaller.add("cd $LFS/sources");
        packageinstaller.add("rm -Rf " + dirname);
        packageinstaller.add("\n");
        return packageinstaller;
    }
}
