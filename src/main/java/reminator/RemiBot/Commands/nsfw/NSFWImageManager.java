package reminator.RemiBot.Commands.nsfw;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class NSFWImageManager {
    private static final Random rand = new Random();

    public static final String NSFW_PATH = "nsfw";
    public static final String NSFW_INDEX_PATH = "nsfw/index.txt";
    public static final String NSFW_IMAGES_PATH = "nsfw/images/";

    private static final Map<String, List<File>> filesByCategory = new HashMap<>();

    public static void setup() {
        File f = new File(NSFW_INDEX_PATH);

        try {
            if (!f.exists()) {
                new File(NSFW_IMAGES_PATH).mkdirs();
                f.createNewFile();
                System.out.println("[NSFWImageManager] Index file did not exist and was created.");
            } else {
                System.out.println("[NSFWImageManager] Index file exists, reading it...");
                setupImagesByCategory(NSFW_INDEX_PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setupImagesByCategory(String indexFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(indexFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String fileName = parts[0];
                String catStr = parts[1];
                filesByCategory.computeIfAbsent(catStr, key -> new ArrayList<>()).add(new File(NSFW_IMAGES_PATH+fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final NSFWCategory category;

    private List<File> images;

    public NSFWImageManager(NSFWCategory category) {
        this.category = category;
        this.initImages();
    }

    private void initImages() {
        this.images = new ArrayList<>(filesByCategory.computeIfAbsent(category.label(), key -> new ArrayList<>()));
    }

    public void addImage(String fileName, File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(NSFW_INDEX_PATH, true))) {
            pw.println(fileName + " " + category.label());
        }
        filesByCategory.get(category.label()).add(file);
        this.images.add(file);
    }

    public File getRandomImage() {
        File img = images.remove(rand.nextInt(images.size()));
        if(images.size() == 0) {
            this.initImages();
        }
        return img;
    }

    public boolean hasImage() {
        return images.size() > 0;
    }

}
