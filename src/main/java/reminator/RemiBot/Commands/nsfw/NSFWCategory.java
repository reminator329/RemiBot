package reminator.RemiBot.Commands.nsfw;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public enum NSFWCategory {
    ANAL("anal"),
    BOOBS("boobs"),
    CREAMPIE("creampie"),
    FEET("feet"),
    HENTAI("hentai"),
    LESBIAN("lesbian"),
    MASTURBATION("masturbation"),
    MILF("milf"),
    ORAL("oral"),
    PUBLIC("public"),
    SQUIRT("squirt"),
    TEEN("teen"),
    JAPANESE("japanese"),
    PUSSY("pussy");


    private final static Random random = new Random();

    private final String label;
    private final NSFWImageManager imageManager;

    NSFWCategory(String label) {
        this.label = label;
        this.imageManager = new NSFWImageManager(this);
    }

    public static Optional<NSFWCategory> fromString(String label) {
        for (NSFWCategory value : NSFWCategory.values()) {
            if(value.label.equalsIgnoreCase(label)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public static NSFWCategory random() {
        List<NSFWCategory> categories = Arrays.stream(NSFWCategory.values())
                .filter(NSFWCategory::hasImage)
                .collect(Collectors.toList());
        if(!categories.isEmpty()) {
            return categories.get(random.nextInt(categories.size()));
        }else{
            return NSFWCategory.values()[random.nextInt(NSFWCategory.values().length)];
        }
    }

    public String label() {
        return label;
    }

    public boolean hasImage() {
        return imageManager.hasImage();
    }

    public NSFWImageManager getImageManager() {
        return imageManager;
    }
}
