package com.lenovo.androidrepositories.util;

import android.content.Context;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
/**
 * HTTPS 单向验证
 */
public class HttpsUtils {
	public static String RecvDatas = null;
	private static final AllowAllHostnameVerifier HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    private static boolean isSever=false;//是否为生产环境
	private static URLConnection conn;

	public static int Lan_Dial_Urovo(String ServerIp, int iPort, int maxTime,
			Context context) {

		String urlStr = ServerIp;
		int iRet = 0;
		conn = null;
		try {
			URL url = new URL(urlStr);
			conn = url.openConnection();
			if (conn instanceof HttpsURLConnection) {
              if(!isSever){
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				// uwca.crt 打包在 asset
				// 中，该证书可以从https://itconnect.uw.edu/security/securing-computer/install/safari-os-x/下载
				InputStream caInput = new BufferedInputStream(context
						.getAssets().open("ca.crt"));
				final Certificate ca;
				try {
					ca = cf.generateCertificate(caInput);
				} finally {
					caInput.close();
				}
				// Create an SSLContext that uses our TrustManager
				SSLContext sslContext = SSLContext.getInstance("TLSv1",
						"AndroidOpenSSL");
				sslContext.init(null,
						new TrustManager[] { new X509TrustManager() {
							@Override
							public void checkClientTrusted(
									X509Certificate[] chain, String authType)
									throws CertificateException {

							}

							@Override
							public void checkServerTrusted(
									X509Certificate[] chain, String authType)
									throws CertificateException {
								for (X509Certificate cert : chain) {

									// Make sure that it hasn't expired.
									cert.checkValidity();

									// Verify the certificate's public key
									// chain.
									try {
										cert.verify(((X509Certificate) ca).getPublicKey());
									} catch (NoSuchAlgorithmException e) {
										e.printStackTrace();
									} catch (InvalidKeyException e) {
										e.printStackTrace();
									} catch (NoSuchProviderException e) {
										e.printStackTrace();
									} catch (SignatureException e) {
										e.printStackTrace();
										throw new CertificateException("签名校验失败");
									}
								}
							}

							@Override
							public X509Certificate[] getAcceptedIssuers() {
								return new X509Certificate[0];
							}
						} }, null);

				((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
				((HttpsURLConnection) conn).setHostnameVerifier(HOSTNAME_VERIFIER);
              }else{
//				 SSLSocketFactory socketFactory = HttpsUrlConnectionDan.getSSLSocketFactory(context);
//				((HttpsURLConnection) conn).setSSLSocketFactory(socketFactory);
				((HttpsURLConnection) conn).setHostnameVerifier(HOSTNAME_VERIFIER);
			}
			}
			((HttpURLConnection) conn).setRequestMethod("POST");
			conn.setConnectTimeout(maxTime * 1000);
			conn.setReadTimeout(maxTime * 1000);
			conn.setDoInput(true); // 读取数据
			conn.setDoOutput(true); // 向服务器写数据
			conn.connect();
		} catch (SSLHandshakeException ex) {
			ex.printStackTrace();
			iRet = -2;
		} catch (Exception e) {
			e.printStackTrace();
			iRet = -1;
		}
		return iRet;
	}








}
