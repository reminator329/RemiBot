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

        if(connection.getResponseCode() >= 300) {
            System.out.println("Error: "+InputStreamUtils.readAsString(connection.getErrorStream()));
        }

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
        connection.setRequestProperty("accept-language", "en");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54");
        return connection;
    }
}
