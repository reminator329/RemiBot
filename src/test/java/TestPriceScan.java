import org.junit.Test;
import reminator.RemiBot.Services.pricescanner.scans.PriceScan;

import java.io.IOException;

public class TestPriceScan {
    @Test
    public void testPriceScan() throws IOException {
        PriceScan priceScan = new PriceScan("test", "name", "https://www.darty.com/nav/achat/informatique/ordinateur_portable/portable_hybride/microsoft_pack_ms_pro_9.html", "itemprop=\"price\" content=\"");
        float price = priceScan.retrievePrice(priceScan.getUrl());
        System.out.println(price);
    }
}
