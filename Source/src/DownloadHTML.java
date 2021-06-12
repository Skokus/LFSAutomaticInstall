import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadHTML {
    URL url;
    File out;

    public DownloadHTML(String url, String out){
        try{
            this.url = new URL(url);
        } catch (MalformedURLException mue){
            mue.printStackTrace();
        }
        this.out = new File(out);
    }

    public DownloadHTML(URL url, String out){
        this.url = url;
        this.out = new File(out);
    }

    public File getFile(){
        return this.out;
    }
    public void createHTMLFile() {
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            FileWriter myWriter = new FileWriter(out);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                myWriter.write(line + '\n');
            }
            myWriter.close();
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
    }

}
