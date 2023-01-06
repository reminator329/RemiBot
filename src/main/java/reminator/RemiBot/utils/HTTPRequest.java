package reminator.RemiBot.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPRequest {
    private final String url;

    private final Map<String, String> parametersGET = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();

    static {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    public HTTPRequest(String url) {
        this.url = url;
    }

    public HTTPRequest withHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public HTTPRequest withGETParameter(String key, String value) {
        parametersGET.put(key, value);
        return this;
    }

    public String GET() throws IOException {
        String args = "";
        if(parametersGET.size() > 0) {
            args = "?" + parametersGET.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
            this.parametersGET.clear();
        }
        HttpsURLConnection connection = newConnection(this.url+args);
        connection.setRequestMethod("GET");
        System.out.println(connection.getResponseCode());
        System.out.println(connection.getResponseMessage());

        return InputStreamUtils.readAsString(connection.getInputStream());
    }

    public String POST(String data) throws IOException {
        HttpsURLConnection connection = newConnection(this.url);
        connection.setRequestMethod("POST");

        OutputStream out = connection.getOutputStream();
        out.write(data.getBytes());
        out.flush();

        InputStream inputStream = connection.getInputStream();

        return InputStreamUtils.readAsString(inputStream);
    }

    private HttpsURLConnection newConnection(String url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connection.setRequestProperty("accept-language", "fr");
        connection.setRequestProperty("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Microsoft Edge\";v=\"108\"");
        connection.setRequestProperty("sec-ch-ua-mobile", "?0");
        connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        connection.setRequestProperty("sec-fetch-dest", "document");
        connection.setRequestProperty("sec-fetch-mode", "navigate");
        connection.setRequestProperty("sec-fetch-site", "none");
        connection.setRequestProperty("sec-fetch-user", "?1");
        connection.setRequestProperty("upgrade-insecure-requests", "1");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54");
        return connection;
    }
}
