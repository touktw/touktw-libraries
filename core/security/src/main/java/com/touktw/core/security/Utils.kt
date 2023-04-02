package com.touktw.core.security

import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.File
import java.security.KeyFactory
import java.security.KeyStore
import java.security.PublicKey
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.spec.X509EncodedKeySpec

fun getCertificate(
    certificateName: String,
): Certificate {
    return keyStore.getCertificate(certificateName)
}

fun saveCertificate(
    certificateName: String,
    certificateString: String,
) {
    val certificate = CertificateFactory
        .getInstance("X509")
        .generateCertificate(
            ByteArrayInputStream(
                certificateString.toByteArray(defaultCharset)
            )
        )

    val keyProtection = KeyProtection
        .Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_VERIFY)
        .setDigests(KeyProperties.DIGEST_SHA1, KeyProperties.DIGEST_SHA256)
        .setRandomizedEncryptionRequired(true)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS)
        .setUserAuthenticationRequired(false)
        .build()

    val trustedCertificateEntry = KeyStore.TrustedCertificateEntry(certificate)
    keyStore.setEntry(
        certificateName,
        trustedCertificateEntry,
        keyProtection
    )
}

fun createPublicKey(keyData: ByteArray): PublicKey {
    val keyFactory = KeyFactory.getInstance("RSA")
    val keySpec = X509EncodedKeySpec(keyData)
    return keyFactory.generatePublic(keySpec)
}

fun readPublicKey(file: File): PublicKey {
    val key = ByteArray(file.length().toInt())
    file.inputStream().use {
        it.read(key, 0, file.length().toInt())
    }

    val keyString = key.toString(defaultCharset)

    val publicPem = keyString
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")

    val encoded = Base64.decode(publicPem, Base64.DEFAULT)
    return createPublicKey(encoded)
}
