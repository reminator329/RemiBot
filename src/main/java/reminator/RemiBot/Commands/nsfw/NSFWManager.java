package reminator.RemiBot.Commands.nsfw;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class NSFWManager {
    private static final Random rand = new Random();

    private final static NSFWManager INSTANCE = new NSFWManager();

    private NSFWManager() {
        try {
            System.out.println("[NSFWManager] Initializing...");

            FileInputStream serviceAccount =
                    new FileInputStream("serviceAccountKey.json");

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
            ApiFuture<QuerySnapshot> query = db.collection("categories").get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String title = document.getString("title");
                categories.put(document.getId(), new Category(document.getId(), title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if(categories == null) {
                    continue;
                }
                for (String category : categories) {
                    imagesURLByCategory.computeIfAbsent(category, k -> new ArrayList<>())
                            .add(imageURL);
                    this.categories.computeIfAbsent(category, k -> new Category(category, category))
                            .incrImagesAmount();
                }
            }

            nonEmptyCategories = imagesURLByCategory.keySet().stream()
                    .map(catId -> categories.get(catId))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private String createImageURL(String filename, String ext) {
        if(ext != null) {
            filename += '.'+ext;
        }

        return "https://firebasestorage.googleapis.com/v0/b/remi-nsfw.appspot.com/o/images%2F"+filename+"?alt=media";
    }
}
