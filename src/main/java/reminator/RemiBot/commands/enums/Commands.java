package reminator.RemiBot.commands.enums;

import reminator.RemiBot.commands.*;
import reminator.RemiBot.commands.Devoir.*;
import reminator.RemiBot.commands.Japonais.HiraganaCommand;
import reminator.RemiBot.commands.Japonais.KatakanaCommand;
import reminator.RemiBot.commands.Japonais.VocabulaireCommand;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.music.*;
import reminator.RemiBot.commands.perso.RemiSpam;
import reminator.RemiBot.commands.perso.RemiWork;
import reminator.RemiBot.commands.pricescanner.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Commands {
    
    PING(new PingCommand()),
    PONG(new PongCommand()),
    //ALBUM(new AlbumCommand()),
    ECOUTE_BILAL(new EcouteBilalCommand()),
    SPAM(new SpamCommand()),
    HELP(new HelpCommand()),
    BILAL(new BilalCommand()),
    INCONNU(new InconnuCommand()),
    DEVINETTE(new DevinetteCommand()),
    AMONG_US(new AmongusCommand()),
    PLUS_OU_MOINS(new PlusMoinsCommand()),
    JEUX_MULTI(new JeuxMultiCommand()),
    POLL(new PollCommand()),
    DEVOIR(new DevoirCommand()),
    DEVOIR_ADD(new DevoirAddCommand()),
    DEVOIR_FINI(new DevoirFiniCommand()),
    DEVOIR_RAPPEL(new DevoirRappelCommand()),
    GHOST_PING(new GhostPingCommand()),
    MATEO(new Mateo()),
    HIRAGANA(new HiraganaCommand()),
    KATAKANA(new KatakanaCommand()),
    VOCABULAIRE(new VocabulaireCommand()),
    REMI_WORK(new RemiWork()),
    REMI_SPAM(new RemiSpam()),
    ADD_SCAN(new AddScanCommand()),
    REM_SCAN(new RemScanCommand()),
    ADD_SCAN_STOCK(new AddScanStockCommand()),
    SCAN_LIST(new ScanListCommand()),
    SCAN(new ScanCommand()),
    JOIN(new JoinCommand()),
    PLAY(new PlayCommand()),
    LEAVE(new LeaveCommand()),
    STOP(new StopCommand()),
    SKIP(new SkipCommand()),
    NOWPLAYING(new NowPlayingCommand()),
    QUEUE(new QueueCommand()),
    SHUFFLE(new ShuffleCommand()),
    ECOUTE_MUSIQUE(new EcouteMusiqueCommand()),
    ;

    private final Command command;

    Commands(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Map<Category, List<Command>> getCommandsGroupedByCategory() {
        return Arrays.stream(Commands.values()).map(Commands::getCommand).collect(Collectors.groupingBy(Command::getCategory));
    }

    public static Command getCommand(String label) {
        for (Commands commands : values()) {
            Command command = commands.getCommand();
            if (command.getLabel().equalsIgnoreCase(label) || command.isAlias(label)) {
                return command;
            }
        }
        return null;
    }
}
