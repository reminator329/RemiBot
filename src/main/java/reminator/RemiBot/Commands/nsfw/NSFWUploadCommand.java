package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.Downloader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static reminator.RemiBot.Commands.nsfw.NSFWImageManager.NSFW_IMAGES_PATH;

public class NSFWUploadCommand extends Command {
    private static final Random rand = new Random();

    public NSFWUploadCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("nsfw-upload");
        this.setHelp(setHelp());

        NSFWImageManager.setup();
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande d'upload NSFW");
        builder.appendDescription("Commande de d'upload base NSFW.");
        builder.addField("Signature", "`r!nsfw-upload <categories...> [url]`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        String[] args = event.getMessage().getContentRaw().split(" ");
        URL url;
        try {
            if(args.length < 3) {
                throw new Exception();
            }
            url = new URL(args[args.length-1]);
        } catch (Exception e) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.RED);
            builder.setTitle("RémiNSFW");
            builder.appendDescription("Commande invalide");
            builder.addField("Signature", "`r!nsfw-upload <categories...> <url>`", false);

            String description = Arrays.stream(NSFWCategory.values())
                    .map(cat -> "`" + cat.label() + "`")
                    .collect(Collectors.joining(" "));
            builder.addField("Catégories disponibles", description, false);

            channel.sendMessage(builder.build()).queue();
            return;
        }

        String[] categoriesStr = new String[args.length - 2];
        System.arraycopy(args, 1, categoriesStr, 0, args.length - 2);

        System.out.println(Arrays.toString(categoriesStr));

        EmbedBuilder builder = new EmbedBuilder();
        try {
            Downloader downloader = new Downloader(url);
            String filename = downloader.getFileName();
            BufferedImage image = downloader.downloadImage().orElseThrow(() -> new RuntimeException("l'URL indiquée ne correspond pas à une image valide."));

            filename = rand.nextInt(100000) + "_" + filename;
            File file = new File(NSFW_IMAGES_PATH + filename);
            ImageIO.write(image, "png", file);

            List<String> unknownCategories = new ArrayList<>();
            List<NSFWCategory> correctCategories = new ArrayList<>();

            for (String catStr : categoriesStr) {
                Optional<NSFWCategory> category = NSFWCategory.fromString(catStr);
                if (category.isPresent()) {
                    correctCategories.add(category.get());
                    category.get().getImageManager().addImage(filename, file);
                } else {
                    unknownCategories.add(catStr);
                }
            }

            if (correctCategories.isEmpty()) {
                throw new RuntimeException(String.format("Les catégories %s n'existent pas.",
                        unknownCategories.stream().map(cat -> "`" + cat + "`").collect(Collectors.joining(" "))));
            }

            builder.setColor(Color.GREEN);
            builder.setTitle("RémiNSFW");
            if (correctCategories.size() == 1) {
                builder.appendDescription("L'image a bien été engistrée dans la catégorie `" + correctCategories.get(0).label() + "` ! :ok_hand:");
            } else {
                builder.appendDescription(String.format("L'image a bien été engistrée dans les catégories %s ! :ok_hand:",
                        correctCategories.stream().map(cat -> "`" + cat.label() + "`").collect(Collectors.joining(" "))));
            }
            if (!unknownCategories.isEmpty()) {
                builder.appendDescription(String.format("Mais elle n'a pas été enregistrée dans les catégories %s car elles n'existent pas.",
                        unknownCategories.stream().map(cat -> "`" + cat + "`").collect(Collectors.joining(" "))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setColor(Color.RED);
            builder.setTitle("RémiNSFW");
            String message = e instanceof MalformedURLException ? "L'URL indiquée est incorrecte." : e.getMessage();
            builder.appendDescription("**Erreur :** " + message);
            if (message.contains("catégorie")) {
                String description = Arrays.stream(NSFWCategory.values())
                        .map(cat -> "`" + cat.label() + "`")
                        .collect(Collectors.joining(" "));
                builder.addField("Catégories disponibles", description, false);
            }
        }

        builder.setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());
        channel.sendMessage(builder.build()).queue();
    }
}
