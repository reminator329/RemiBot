package reminator.RemiBot.Commands.nsfw;

import org.jetbrains.annotations.NotNull;

public class Category implements Comparable<Category>{
    private final String id;
    private final String title;
    private final boolean isBoy;

    private int imagesAmount = 0;

    public Category(String id, String title, boolean isBoy) {
        this.id = id;
        this.title = title;
        this.isBoy = isBoy;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBoy() {
        return isBoy;
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
