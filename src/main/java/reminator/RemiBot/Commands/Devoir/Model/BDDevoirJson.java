package reminator.RemiBot.Commands.Devoir.Model;

import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class BDDevoirJson extends BDDevoir {

    private static final BDDevoir instance = new BDDevoirJson();

    String nomFile = "devoirs.txt";
    File fileDevoir = new File(nomFile);

    private BufferedWriter bw = null;
    private BufferedReader br = null;

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

    public static BDDevoir getInstance() {
        return instance;
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
            JSONArray devoirUser = json.getJSONObject(id).getJSONArray("devoirs");

            devoirUser.forEach(o -> {
                if (o instanceof JSONObject) {
                    JSONObject j = (JSONObject) o;
                    Date date = null;
                    try {
                        date = new Date((long) j.get("date"));
                    } catch (JSONException ignored) {
                    }
                    devoirs.add(new Devoir((String) j.get("course"), (String) j.get("description"), (boolean) j.get("all"), date));
                }
            });
        } catch (JSONException e) {
            return devoirs;
        }

        devoirs.sort(Devoir::compareTo);
        return devoirs;
    }

    @Override
    public void addDevoir(ArrayList<User> users, String course, String description, boolean all, Date date) {

        try {
            br = new BufferedReader(new FileReader(nomFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Devoir devoir = new Devoir(course, description, all, date);
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
            JSONObject userJson;

            try {
                userJson = json.getJSONObject(id);
                devoirUser = userJson.getJSONArray("devoirs");
            } catch (JSONException e) {
                userJson = new JSONObject();
                devoirUser = new JSONArray();
            }
            devoirUser.put(devoirJson);
            userJson.put("devoirs", devoirUser);
            json.put(id, userJson);
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
        JSONObject userJson;

        Eleve eleve = new Eleve(author);
        String id = eleve.getUser().getId();

        ArrayList<Devoir> devoirs1 = getDevoirs(author);

        ArrayList<Devoir> devoirsAll = new ArrayList<>();
        ArrayList<Devoir> devoirsPerso = new ArrayList<>();

        for (Devoir d : devoirs1) {
            if (d.isAll()) {
                devoirsAll.add(d);
            } else {
                devoirsPerso.add(d);
            }
        }

        ArrayList<Devoir> devoirs = new ArrayList<>(devoirsAll);
        devoirs.addAll(devoirsPerso);

        if (numeroDevoir <= 0 || numeroDevoir > devoirs.size()) {
            return null;
        }

        Devoir retour = devoirs.remove(numeroDevoir - 1);

        try {
            userJson = json.getJSONObject(id);
            userJson.remove("devoirs");
            JSONArray devoirUser = new JSONArray();

            for (Devoir d : devoirs) {
                JSONObject devoirJson = d.toJson();
                devoirUser.put(devoirJson);
            }
            userJson.put("devoirs", devoirUser);
            json.put(id, userJson);

            try {
                bw = new BufferedWriter(new FileWriter(fileDevoir.getAbsoluteFile()));
                bw.write(json.toString());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return retour;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public void setRappel(User user, boolean b, int heure) {

        try {
            br = new BufferedReader(new FileReader(fileDevoir.getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        StringBuilder content = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            content.append(line);
        }
        if (content.toString().equals("")) {
            content.append("{}");
        }
        JSONObject json = new JSONObject(content.toString());

        Eleve eleve = new Eleve(user);
        String id = eleve.getUser().getId();
        JSONObject jsonUser = json.getJSONObject(id);
        JSONObject rappelUser;

        try {
            rappelUser = jsonUser.getJSONObject("rappel");
        } catch (JSONException e) {
            rappelUser = new JSONObject();
        }

        rappelUser.put("statut", b);
        if (heure >= 0) {
            rappelUser.put("heure", heure);
        } else if (getHeure(user) < 0) {
            rappelUser.put("heure", HEURE_DEFAULT);
        }
        jsonUser.put("rappel", rappelUser);
        json.put(id, jsonUser);

        try {
            bw = new BufferedWriter(new FileWriter(fileDevoir.getAbsoluteFile()));
            bw.write(json.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHeure(User user) {

        try {
            br = new BufferedReader(new FileReader(fileDevoir.getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        StringBuilder content = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
            content.append(line);
        }
        if (content.toString().equals("")) {
            content.append("{}");
        }
        JSONObject json = new JSONObject(content.toString());

        Eleve eleve = new Eleve(user);
        String id = eleve.getUser().getId();
        JSONObject jsonUser = json.getJSONObject(id);
        JSONObject rappelUser = jsonUser.getJSONObject("rappel");

        try {
            return (int) rappelUser.get("heure");
        } catch (JSONException e) {
            return -1;
        }
    }

    @Override
    public boolean getStatut(User user) {

        try {
            br = new BufferedReader(new FileReader(fileDevoir.getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        StringBuilder content = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            content.append(line);
        }
        if (content.toString().equals("")) {
            content.append("{}");
        }
        JSONObject json = new JSONObject(content.toString());

        Eleve eleve = new Eleve(user);
        String id = eleve.getUser().getId();
        JSONObject jsonUser = json.getJSONObject(id);
        JSONObject rappelUser = jsonUser.getJSONObject("rappel");

        return (boolean) rappelUser.get("statut");
    }
}
