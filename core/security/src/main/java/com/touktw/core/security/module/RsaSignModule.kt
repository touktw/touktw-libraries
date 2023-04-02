package com.touktw.core.security.module

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.touktw.core.security.ANDROID_KEYSTORE
import com.touktw.core.security.isKeyExists
import com.touktw.core.security.keyStore
import timber.log.Timber
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature
import javax.crypto.Cipher

class RsaSignModule(
    @SignaturePadding
    private val padding: String,
    @SignatureDigest
    private val digest: String,
) : SignModule() {
    private val signature: Signature by lazy {
        val algorithm = when (padding) {
            KeyProperties.SIGNATURE_PADDING_RSA_PSS -> "RSA/PSS"
            else -> "RSA"
        }

        Timber.d("get signature:${digest.replace("-", "")}with${algorithm}")
        Signature.getInstance("${digest.replace("-", "")}with${algorithm}")
    }

    private val keyEntry: (alias: String) -> KeyStore.PrivateKeyEntry? =
        { alias ->
            if (isKeyExists(alias).not()) {
                generateKey(alias)
            }
            keyStore.getEntry(alias, null) as? KeyStore.PrivateKeyEntry
        }

    override val cipher: Cipher
        get() = Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_RSA
                    + "/" + padding
        )

    override fun generateKey(alias: String) {
        if (isKeyExists(alias)) {
            return
        }

        val keyPairGenerator =
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).run {
            setDigests(digest)
            setSignaturePaddings(padding)
            build()
        }
        keyPairGenerator.initialize(spec)
        keyPairGenerator.generateKeyPair()
    }

    override fun sign(
        alias: String,
        value: ByteArray,
    ): ByteArray {
        val entry = keyEntry(alias)
        return signature.run {
            Timber.d("provider name ${provider.name}")
            initSign(entry?.privateKey)
            update(value)
            sign()
        }
    }

    override fun verify(
        alias: String,
        origin: ByteArray,
        signature: ByteArray,
    ): Boolean {
        val entry = keyEntry(alias)
        return this.signature.run {
            initVerify(entry?.certificate)
            update(origin)
            verify(signature)
        }
    }
}