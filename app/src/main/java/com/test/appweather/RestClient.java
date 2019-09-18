package com.test.appweather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;



public class RestClient {
    private OkHttpClient client;

    //https://samples.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22


    public List<ItemData> getTemperatures() throws IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://samples.openweathermap.org/data/2.5/forecast").newBuilder();
        urlBuilder.addQueryParameter("lat", "35");
        urlBuilder.addQueryParameter("lon", "139");
        urlBuilder.addQueryParameter("appid", "b6907d289e10d714a6e88b30761fae22");
        Request request = new Request.Builder().url(urlBuilder.build()).build();
        Response response = getClient().newCall(request).execute();

        Log.d("TAG", response.message());

        if (response.isSuccessful()) {
            List<ItemData> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response.body().string());

           // Log.d("jsonObject", jsonObject.toString());
            JSONArray listArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject object = listArray.getJSONObject(i);
                JSONObject value = object.getJSONObject("main");

                JSONObject value2 =  (JSONObject)object.getJSONArray("weather").get(0);
                //JSONObject value2 = (JSONObject)weatherArr.getString(1);
               // Log.d("value", value.toString());
                list.add(new ItemData(value2.getString("main"),value.getString("temp_min"), value.getString("temp_max"),value2.getString("description")));
            }

            return list;
        } else {
            throw new IOException();
        }
    }

    private OkHttpClient getClient() {
        if (client == null) {
            try {
                client = createClient();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    private OkHttpClient createClient() throws NoSuchAlgorithmException, KeyManagementException {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        /*okHttpClientBuilder.addInterceptor(
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        );*/
        return okHttpClientBuilder.build();
    }
}