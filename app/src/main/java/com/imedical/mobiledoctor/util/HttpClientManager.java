/**
 * This is a demo of client-expert mobile system.
 * Author: Akalius Kung
 * Date: 2009-4-17
 */

package com.imedical.mobiledoctor.util;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 统一管理httpClient
 *
 * @author sszvip@qq.com
 * @since 2014-7-2
 */
public class HttpClientManager {
    private static HttpClient httpClientGlobal = null;

    public static HttpClient createHttpClient() {
        // Create and initialize HTTP parameters
        DefaultHttpClient tempClient = null;
        try {


            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setMaxTotalConnections(params, 100);
            // HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            // Create and initialize scheme registry
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", sf, 443));

            // schemeRegistry.register(new Scheme("https",
            // SSLSocketFactory.getSocketFactory(), 443));
            ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
            tempClient = new DefaultHttpClient(cm, params);
            tempClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 30000); //
            tempClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 60000);//

            String proxyHost = android.net.Proxy.getDefaultHost();
            if (proxyHost != null && !proxyHost.equals("")) {//
                HttpHost proxy = new HttpHost(android.net.Proxy.getDefaultHost(), android.net.Proxy.getDefaultPort());
                tempClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
            }
        } catch (Exception e) {
            return tempClient;
        }

        return tempClient;
    }


    public static synchronized HttpClient getHttpClient(boolean isSingleton) {
        if (isSingleton) {
            httpClientGlobal = getHttpClient();
        } else {
            httpClientGlobal = createHttpClient();
        }
        return httpClientGlobal;
    }// end method

    private static synchronized HttpClient getHttpClient() {
        if (httpClientGlobal == null) {
            httpClientGlobal = createHttpClient();
        }
        return httpClientGlobal;
    }// end method


    public static void reset() {
        try {
            httpClientGlobal.getConnectionManager().shutdown();
            httpClientGlobal = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
                throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

}
