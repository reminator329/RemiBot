package reminator.RemiBot.Services.pricescanner.scans;

import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;
import java.io.Serializable;

public interface Scan extends Serializable {
    /**
     * Returns true if need to be saved
     * @param channel
     * @return
     * @throws IOException
     */
    boolean scan(TextChannel channel) throws IOException;

    String getUrl();
}
