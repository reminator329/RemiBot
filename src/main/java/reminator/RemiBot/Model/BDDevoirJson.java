package reminator.RemiBot.Model;

import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class BDDevoirJson extends BDDevoir {

    private static final BDDevoir instance = new BDDevoirJson();

    String nomFile = "devoirs.txt";
    File fileDevoir = new File(nomFile);

    private BufferedWriter bw = null;
    private BufferedReader br = null;

    public static BDDevoir getInstance() {
        return instance;
    }

    private BDDevoirJson() {
        try {
            if (!fileDevoir.exists()) {
                final boolean newFile = fileDevoir.createNewFile();
                if (!newFile) {
                    System.out.println("problème");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Devoir> getDevoirs(User author) {
        ArrayList<Devoir> devoirs = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(fileDevoir.getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return devoirs;
        }

        StringBuilder content = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return devoirs;
            }
            content.append(line);
        }
        if (content.toString().equals("")) {
            content.append("{}");
        }
        JSONObject json = new JSONObject(content.toString());

        Eleve eleve = new Eleve(author);
        String id = eleve.getUser().getId();
        try {
            JSONArray devoirUser = json.getJSONArray(id);

            devoirUser.forEach(o -> {
                if (o instanceof JSONObject) {
                    JSONObject j = (JSONObject) o;
                    devoirs.add(new Devoir((String) j.get("course"), (String) j.get("description"), (boolean) j.get("all")));
                }
            });
        } catch (JSONException e) {
            return devoirs;
        }

        return devoirs;
    }

    @Override
    public void addDevoir(ArrayList<User> users, String course, String description, boolean all) {

        try {
            br = new BufferedReader(new FileReader(nomFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Devoir devoir = new Devoir(course, description, all);
        JSONObject devoirJson = devoir.toJson();

        StringBuilder content = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                    content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (content.toString().equals("")) {
            content.append("{}");
        }
        JSONObject json = new JSONObject(content.toString());


        for (User user : users) {
            Eleve eleve = new Eleve(user);
            String id = eleve.getUser().getId();
            JSONArray devoirUser;

            try {
                devoirUser = json.getJSONArray(id);
            } catch (JSONException e) {
                devoirUser = new JSONArray();
            }
            devoirUser.put(devoirJson);
            json.put(id, devoirUser);
        }

        try {
            bw = new BufferedWriter(new FileWriter(fileDevoir.getAbsoluteFile()));
            bw.write(json.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Devoir finiDevoir(User author, int numeroDevoir) {

        try {
            br = new BufferedReader(new FileReader(fileDevoir.getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder content = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            content.append(line);
        }
        if (content.toString().equals("")) {
            content.append("{}");
        }
        JSONObject json = new JSONObject(content.toString());

        Eleve eleve = new Eleve(author);
        String id = eleve.getUser().getId();
        try {
            JSONArray devoirUser = json.getJSONArray(id);

            if (numeroDevoir <= 0 || numeroDevoir > devoirUser.length()) {
                return null;
            }
            JSONArray devoirsTrie = new JSONArray();

            for (Object o : devoirUser) {
                if (o instanceof JSONObject) {
                    JSONObject d = (JSONObject) o;
                    if (d.getBoolean("all")) {
                        devoirsTrie.put(devoirsTrie.length(), d);
                    }
                }
            }
            for (Object o : devoirUser) {
                if (o instanceof JSONObject) {
                    JSONObject d = (JSONObject) o;
                    if (!d.getBoolean("all")) {
                        devoirsTrie.put(devoirsTrie.length(), d);
                    }
                }
            }
            JSONObject devoir = (JSONObject) devoirsTrie.get(numeroDevoir - 1);
            for (int i = 0; i < devoirUser.length(); i++) {
                if (devoir.equals(devoirUser.get(i))) {
                    devoirUser.remove(i);
                    break;
                }
            }
            json.remove(id);
            json.put(id, devoirUser);

            try {
                bw = new BufferedWriter(new FileWriter(fileDevoir.getAbsoluteFile()));
                bw.write(json.toString());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Devoir((String) devoir.get("course"), (String) devoir.get("description"), (boolean) devoir.get("all"));
        } catch (JSONException e) {
            return null;
        }
    }
}
