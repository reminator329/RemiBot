package reminator.RemiBot.Commands.nsfw;

import org.jetbrains.annotations.NotNull;

public class Category implements Comparable<Category>{
    private final String id;
    private final String title;

    private int imagesAmount = 0;

    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void incrImagesAmount() {
        imagesAmount++;
    }

    public int getImagesAmount() {
        return imagesAmount;
    }

    @Override
    public int compareTo(@NotNull Category o) {
        return title.compareTo(o.title);
    }
}
