package reminator.RemiBot.Services.motdujour;

import java.util.regex.Pattern;

public record Word(int id, String mot, String categorie, String description, String etymologie, String sources) {
    private static Pattern URL_PATTERN = Pattern.compile("\"(http://[^\"]+)\"");

    public Word(int id, String mot, String categorie, String description, String etymologie, String sources) {
        this.id = id;
        this.mot = mot;
        this.categorie = categorie;
        this.description = description.replaceAll("<[^>]+>", "");
        this.etymologie = etymologie.replaceAll("<[^>]+>", "");
        this.sources = URL_PATTERN.matcher(sources).results().map(result -> result.group(1)).findFirst().orElse("--");
    }
}
