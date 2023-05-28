package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.*;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.operator.OperatorCreationException;

import java.util.Calendar;
import java.util.Date;

@Log4j2
public class CertificateGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException, CertificateException, IOException, OperatorCreationException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
// Step 3: Initialize the KeyGenerator with a certain keysize
        keyPairGenerator.initialize(512);
// Step 4: Generate the key pairs
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
// Step 5: Extract the keys
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        X509Certificate certificate = selfSign(keyPair, "CN=Chloe");

        log.info("Generated certificate is: \n\n" + certificate.toString());
    }

    public static X509Certificate selfSign(KeyPair keyPair, String subjectDN) throws OperatorCreationException, CertificateException, IOException
    {
        Provider bcProvider = new BouncyCastleProvider();
        Security.addProvider(bcProvider);

        long now = System.currentTimeMillis();
        Date startDate = new Date(now);

        X500Name dnName = new X500Name(subjectDN);
        BigInteger certSerialNumber = new BigInteger(Long.toString(now)); // <-- Using the current timestamp as the certificate serial number

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 1); // <-- 1 Yr validity

        Date endDate = calendar.getTime();

        String signatureAlgorithm = "SHA256WithRSA"; // <-- Use appropriate signature algorithm based on your keyPair algorithm.

        ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).build(keyPair.getPrivate());

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber, startDate, endDate, dnName, keyPair.getPublic());

        // Extensions --------------------------

        // Basic Constraints
        BasicConstraints basicConstraints = new BasicConstraints(true); // <-- true for CA, false for EndEntity

        certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints); // Basic Constraints is usually marked as critical.

        // -------------------------------------

        return new JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(certBuilder.build(contentSigner));
    }

//    private static X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws OperatorCreationException, CertificateException {
//        PKCS10CertificationRequestBuilder requestBuilder = new PKCS10CertificationRequestBuilder(
//                new X500Name("CN=Self-Signed"),
//                (SubjectPublicKeyInfo) keyPair.getPublic()
//        );
//
//        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA256withRSA");
//        ContentSigner contentSigner = signerBuilder.build(keyPair.getPrivate());
//
//        PKCS10CertificationRequest csr = requestBuilder.build(contentSigner);
//
//        X509Certificate certificate = generateCertificate(csr);
//        certificate.verify(keyPair.getPublic()); // Verify the certificate using the public key
//
//        return certificate;
//    }
//
//    private static X509Certificate generateCertificate(PKCS10CertificationRequest csr) throws OperatorCreationException, CertificateException {
//        // Set validity period for the certificate (e.g., 1 year)
//        long validityPeriodMillis = 365 * 24 * 60 * 60 * 1000L;
//        Date startDate = new Date();
//        Date endDate = new Date(startDate.getTime() + validityPeriodMillis);
//
//        X500Name issuer = csr.getSubject();
//
//        JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
//                issuer,
//                BigInteger.valueOf(startDate.getTime()),
//                startDate,
//                endDate,
//                csr.getSubject(),
//                (PublicKey) csr.getSubjectPublicKeyInfo()
//        );
//
//        // Sign the certificate using the private key
//        X509CertificateHolder certificateHolder = certificateBuilder.build(new JcaContentSignerBuilder("SHA256withRSA").build(PrivateKey));
//
//        return new JcaX509CertificateConverter().getCertificate(certificateHolder);
//    }
//
//    private static void saveCertificateToFile(X509Certificate certificate, String filename) throws CertificateEncodingException, IOException {
//        try (FileWriter fileWriter = new FileWriter(filename)) {
//            PemWriter pemWriter = new PemWriter(fileWriter);
//            PemObject pemObject = new PemObject("CERTIFICATE", certificate.getEncoded());
//            pemWriter.writeObject(pemObject);
//            pemWriter.close();
//        }
//    }
}
