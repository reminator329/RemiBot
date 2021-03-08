package reminator.RemiBot.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomImagePicker {
    private final Random random = new Random();

    private final File directory;
    private List<File> imageFiles;

    public RandomImagePicker(String directoryPath) throws URISyntaxException {
        directory = new File(directoryPath);
        if(!directory.isDirectory()) {
            throw new RuntimeException(directoryPath + " is not a directory.");
        }
        this.imageFiles = getImages();
    }

    private List<File> getImages() {
        return new ArrayList<File>(Arrays.asList(directory.listFiles()));
    }

    public File getRandomImage() {
        if(imageFiles.size() == 0) {
            imageFiles = getImages();
        }
        return imageFiles.remove(random.nextInt(imageFiles.size()));
    }

}
