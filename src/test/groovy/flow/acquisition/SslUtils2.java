/**
 * Copyright (C) 2017 HttpBuilder-NG Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package flow.acquisition;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import groovyx.net.http.HttpObjectConfig;
import groovyx.net.http.util.SslUtils;


/**
 * Full copy of {@link SslUtils} except of particular implementation of {@link TrustManager} used for relaxing checks.
 * Thus, {@link SslUtils} uses {@link X509TrustManager} whereas current implementation use {@link X509ExtendedTrustManager}
 * <p>
 * The <code>"java.security.cert.CertificateException: Certificates does not conform to algorithm constraints"</code> exception
 * was experienced otherwise.
 * <p>
 * The fix was inspired by <a href="https://stackoverflow.com/questions/14149545/java-security-cert-certificateexception-certificates-does-not-conform-to-algori">java.security.cert.CertificateException: Certificates does not conform to algorithm constraints</a>
 *
 * <p>
 * SSL helper utilities.
 */
public class SslUtils2 {

    /**
     * A `HostnameVerifier` that accepts any host name.
     */
    @SuppressWarnings("WeakerAccess")
    public static final HostnameVerifier ANY_HOSTNAME = (s, sslSession) -> true;

    // trust manager that trusts everything
    private static final TrustManager[] TRUST_MANAGERS = {new X509ExtendedTrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine)
              throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine)
              throws CertificateException {

        }
    }};

    /**
     * Configuration helper used to ignore any SSL certificate-related issues by configuring an `SSLContext` that allows everything.
     *
     * [source,groovy]
     * ----
     * def http = JavaHttpBuilder.configure {
     *     ignoreSslIssues(execution)
     * }
     * ----
     *
     * This will inject the correct configuration to set the `sslContext` and `hostnameVerifier` - these configuration properties should not
     * be directly specified when this method is applied.
     *
     * @param execution the {@link HttpObjectConfig.Execution} instance
     */
    public static void ignoreSslIssues(final HttpObjectConfig.Execution execution){
        execution.setSslContext(acceptingSslContext());
        execution.setHostnameVerifier(ANY_HOSTNAME);
    }

    /**
     * Creates an {@link SSLContext} that allows all requests, regardless of certificate issues.
     *
     * @return an all-accepting {@link SSLContext}
     */
    public static SSLContext acceptingSslContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, TRUST_MANAGERS, new SecureRandom());
            return sslContext;

        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            throw new RuntimeException("Unable to create issue-ignoring SSLContext: " + ex.getMessage(), ex);
        }
    }
}
