package reminator.RemiBot.Commands.nsfw;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class NSFWManager {
    private static final Random rand = new Random();

    private final static NSFWManager INSTANCE = new NSFWManager();

    private NSFWManager() {
        try {
            System.out.println("[NSFWManager] Initializing...");

            InputStream serviceAccount =
                    getClass().getResourceAsStream("/serviceAccountKey2.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            this.db = FirestoreClient.getFirestore();

            updateCategories();
            updateImages();

            System.out.println("[NSFWManager] Initialized.");
            System.out.println("[NSFWManager] Found categories : "+String.join(", ", categories.keySet()));
            System.out.println("[NSFWManager] Found " + allImagesURL.size() + " images.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NSFWManager get() {
        return INSTANCE;
    }

    // ===================== //

    private Firestore db;
    private Map<String, Category> categories = new HashMap<>();

    private List<String> allImagesURL = new ArrayList<>();
    private Map<String, List<String>> imagesURLByCategory = new HashMap<>();

    private List<Category> nonEmptyCategories = new ArrayList<>();

    public void updateCategories() {
        categories.clear();

        try {
            categories.put("girl", new Category("girl", "Girl", false));
            categories.put("boy", new Category("boy", "Boy", true));

            ApiFuture<QuerySnapshot> query = db.collection("categories").get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String title = document.getString("title");
                categories.put(document.getId(), new Category(document.getId(), title, false));
            }

            query = db.collection("categories_boy").get();
            querySnapshot = query.get();
            documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String title = document.getString("title");
                String id = categories.containsKey(document.getId()) ? "boy_"+document.getId() : document.getId();
                categories.put(id, new Category(id, title, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[NSFWManager] Updated categories.");
    }

    public void updateImages() {
        imagesURLByCategory.clear();
        allImagesURL.clear();
        nonEmptyCategories.clear();

        try {
            ApiFuture<QuerySnapshot> query = db.collection("images").get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String imageURL = createImageURL(document.getId(), document.getString("extension"));
                allImagesURL.add(imageURL);

                List<String> categories = (List<String>) document.get("categories");
                if(categories != null && categories.size() > 0) {
                    categories.add("girl");
                    for (String category : categories) {
                        imagesURLByCategory.computeIfAbsent(category, k -> new ArrayList<>())
                                .add(imageURL);

                        this.categories.computeIfAbsent(category, k -> new Category(category, category, false))
                                .incrImagesAmount();
                    }
                }
                categories = (List<String>) document.get("categories_boy");
                if(categories != null && categories.size() > 0) {
                    categories.add("boy");
                    for (String category : categories) {
                        if(this.categories.containsKey("boy_"+category)) {
                            category = "boy_"+category;
                        }
                        imagesURLByCategory.computeIfAbsent(category, k -> new ArrayList<>())
                                .add(imageURL);
                        String finalCategory = category;
                        this.categories.computeIfAbsent(category, k -> new Category(finalCategory, finalCategory, true))
                                .incrImagesAmount();
                    }
                }
            }

            nonEmptyCategories = imagesURLByCategory.keySet().stream()
                    .map(catId -> categories.get(catId))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[NSFWManager] Updated images.");
    }

    public Category getCategoryById(String id) {
        return categories.get(id);
    }

    public String getRandomImageURL() {
        return allImagesURL.get(rand.nextInt(allImagesURL.size()));
    }

    public String getRandomImageURL(Category category) {
        List<String> list = imagesURLByCategory.get(category.getId());
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list.get(rand.nextInt(list.size()));
    }

    public Map<String, Category> getCategories() {
        return categories;
    }

    public int getImagesAmount() {
        return allImagesURL.size();
    }

    public int getBoyImagesAmount() {
        return imagesURLByCategory.get("boy").size();
    }

    public int getGirlImagesAmount() {
        return imagesURLByCategory.get("girl").size();
    }

    private String createImageURL(String filename, String ext) {
        if(ext != null) {
            filename += '.'+ext;
        }

        return "https://firebasestorage.googleapis.com/v0/b/remi-nsfw.appspot.com/o/images%2F"+filename+"?alt=media";
    }
}
