package reminator.RemiBot.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

public class Downloader {
    private final URL url;

    public Downloader(URL url) {
        this.url = url;
    }

    public String getFileName() {
        return Paths.get(this.url.getPath()).getFileName().toString();
    }

    public Optional<BufferedImage> downloadImage() {
        HttpURLConnection httpcon = null;
        try {
            httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            try (InputStream is = httpcon.getInputStream()) {
                return Optional.of(ImageIO.read(is));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
