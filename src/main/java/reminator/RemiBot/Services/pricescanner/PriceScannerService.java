package reminator.RemiBot.Services.pricescanner;

import net.dv8tion.jda.api.entities.TextChannel;
import reminator.RemiBot.Services.pricescanner.scans.ComparerMalinScan;
import reminator.RemiBot.Services.pricescanner.scans.Scan;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Objectif : contenir une liste de scan de prix à observer. Sauvegarder (serializable).
 * Un check toutes les heures.
 * Dès qu'un prix diminue ou augmente : notification Discord.
 * Dans l'idéal : indiquer également si le produit est en rupture de stock.
 */
public class PriceScannerService {
    private static PriceScannerService INSTANCE;

    public static synchronized void init(TextChannel channel) {
        if(INSTANCE != null) {
            return;
        }
        INSTANCE = new PriceScannerService(channel);
        System.out.println("Created PriceScannerService");
    }

    public static PriceScannerService get() {
        return INSTANCE;
    }

    public static PriceScannerService priceScanner() {
        return INSTANCE;
    }

    /////////////////////////////////////

    private final TextChannel channel;
    private List<Scan> scans = new ArrayList<>();

    private PriceScannerService(TextChannel channel) {
        this.channel = channel;
        File file = new File("prices.bin");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("prices.bin"))) {
                this.scans = (List<Scan>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(scans.isEmpty() || !(scans.get(0) instanceof ComparerMalinScan)) {
            scans.add(0, new ComparerMalinScan());
        }

        System.out.println("[PriceScannerService] intialized");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Lancement du scan ("+priceScanner().getScans().size()+")...").build()).queue();
                scan();/*.thenRun(() -> {
                    channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Scan terminé !").setColor(Color.GREEN).build()).queue();
                });*/
            }
        }, 5000, 60*60*1000);
    }

    public CompletableFuture<Void> scan() {
        return CompletableFuture.runAsync(() -> {
            boolean needSave = false;
            for (Scan scan : scans) {
                try {
                    needSave = scan.scan(channel);
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                    channel.sendMessage("Une erreur est survenue : "+ e.getMessage()+"\n\n"+
                            Arrays.stream(e.getStackTrace())
                            .map(StackTraceElement::toString)
                            .collect(Collectors.joining("\n"))
                    ).queue();
                }
            }
            if(needSave) {
                try {
                    channel.sendMessage("Sauvegardé !").queue();
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                    channel.sendMessage("Erreur de sauvegarde !").queue();
                }
            }
        });

    }

    public List<Scan> getScans() {
        return scans;
    }

    public void addScan(Scan scan) {
        scans.add(scan);
    }

    public void save() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("prices.bin"));
        oos.writeObject(scans);
        oos.close();
    }
}
