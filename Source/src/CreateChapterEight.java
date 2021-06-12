import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CreateChapterEight {

    private int chapternumber;

    public CreateChapterEight(int chapternumber) {
        this.chapternumber = chapternumber;
    }

    public ArrayList<String> createchaptereightscript() throws MalformedURLException, InterruptedException {
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("exit"); //wychodzi do roota
        String url = "https://www.linuxfromscratch.org/lfs/view/stable/chapter08/";
        String chapter = "introduction.html";
        String packagename;
        for(int i = 0; i < chapternumber; i++) {
            URL sideurl = new URL(url + chapter);
            DownloadHTML html = new DownloadHTML(sideurl, "currentpage.txt");
            html.createHTMLFile();
            CheckForStuff check = new CheckForStuff(new File("currentpage.txt"));
            packagename = check.findTitle();
            if (packagename.contains("Introduction")) {
                commands.add("#CHAPTER 8 INSTALLATION BEGINS");
            } else if (packagename.contains("Automake")) {
                commands.addAll(createpackageinstall(packagename, html));
                packagename = "libtool";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Coreutils")) {
                commands.addAll(createpackageinstall(packagename, html));
                packagename = "acl";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Iana-Etc")){
                packagename = "iana";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("GMP")){
                packagename = "gmp";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("MPC")){
                packagename = "mpc";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Tcl")) {
                packagename = "tcl8.6.11";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("DejaGNU")){
                packagename = "dejagnu";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("MPFR")){
                packagename = "mpfr";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("GDBM")){
                    packagename = "gdbm";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("XML::Parser")){
                packagename = "XML-Parser";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Libelf")){
                packagename = "elfutils";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("OpenSSL")){
                packagename = "openssl";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Python")) {
                packagename = "Python";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("GRUB")) {
                packagename = "grub";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("IPRoute")) {
                packagename = "iproute2";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Man-DB")) {
                packagename = "man-db";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("GCC")) {
                packagename = "gcc";
                commands.addAll(createpackageinstall(packagename, html));
            } else if (packagename.contains("Cleaning") || packagename.contains("Stripping Again") || packagename.contains("About Debugging Symbols") || packagename.contains("Package Management")) {
                commands.addAll(createpagescript(html));
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
        packageinstaller.add("cd ~/sources/");
        packageinstaller.add("rm -Rf " + dirname);
        packageinstaller.add("\n");
        return packageinstaller;
    }

    private ArrayList<String> createpagescript(DownloadHTML html){
        ArrayList<String> packageinstaller = new ArrayList<String>();
        html.createHTMLFile();
        ArrayList<String> commands;
        commands = new ArrayList<String>(new CheckForStuff(html.getFile()).findCommands());
        packageinstaller.addAll(commands);
        packageinstaller.add("\n");
        return packageinstaller;
    }
}
