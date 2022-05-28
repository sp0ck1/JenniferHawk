package com.jenniferhawk.speedrunslive;
import com.jenniferhawk.MockSSLSocketFactory;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@Getter
@Setter
public class RaceResultJSONGrabber {

    private String jsonResult;

    public RaceResultJSONGrabber() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        Unirest.setHttpClient(httpclient);
    }

    private void setJsonResult(String jsonResult) {this.jsonResult = jsonResult;}

    public RaceResultJSONGrabber(String raceID) {

        String SRL_RACE_URL = "https://www.speedrunslive.com/api/pastresults/" + raceID;

        HttpResponse<String> response;

//        // To set SSL verification to none for past
//        SchemeRegistry schemeRegistry = new SchemeRegistry();
//        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
//        try {
//            schemeRegistry.register(new Scheme("https", 443, new MockSSLSocketFactory()));
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);
//        DefaultHttpClient httpclient = new DefaultHttpClient(cm);
//        Unirest.setHttpClient(httpclient);

        try {
            response = Unirest.get(SRL_RACE_URL)
            .asString();
            this.jsonResult = response.getBody().substring(response.getBody().indexOf(
                    "\"entrants\": [\n"+
                    "{\n")+14, // Get text from this point...
                    response.getBody().indexOf(
                            "],")+2); // ...to this point.
            //System.out.println("Grabbed JSON result of race " + raceID);
            System.out.println(jsonResult);
        } catch (
                UnirestException e) {
            setJsonResult(null);
            System.out.println("SRL Error: " + e.getMessage());
            // throw new ContextedRuntimeException("Sp0ck1 SRL Placeholder error", e).addContextValue("errorId", "SRL BEING WEIRD. " + e.getMessage());
        }
    }

    public String getJsonResult() {
        return this.jsonResult;
    }
}
