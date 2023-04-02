package com.touktw.core.security

import android.security.keystore.KeyProperties
import android.util.Base64
import com.touktw.core.security.module.AESCipherModule
import com.touktw.core.security.module.AESKeySize
import com.touktw.core.security.module.CipherAlgorithm
import com.touktw.core.security.module.CipherBlockMode
import com.touktw.core.security.module.CipherModule
import com.touktw.core.security.module.CipherPadding
import com.touktw.core.security.module.RsaSignModule
import com.touktw.core.security.module.SignModule
import com.touktw.core.security.module.SignatureDigest
import com.touktw.core.security.module.SignaturePadding
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.KeyStore
import javax.crypto.Cipher
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier

internal const val ANDROID_KEYSTORE = "AndroidKeyStore"
internal val defaultCharset = charset("UTF-8")
internal val keyStore: KeyStore
    get() = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

internal fun isKeyExists(alias: String): Boolean {
    return keyStore.containsAlias(alias)
}

internal fun base64EncodeString(data: ByteArray): String {
    return Base64.encodeToString(data, Base64.DEFAULT)
}

internal fun decodeBas64String(string: String): ByteArray {
    return Base64.decode(string, Base64.DEFAULT)
}

interface SecurityModule {
    val cipher: Cipher

    fun generateKey(alias: String)
}

private val cipherModuleMap = mutableMapOf<String, CipherModule>()
private val signModuleMap = mutableMapOf<String, SignModule>()

fun signModule(
    @SignaturePadding
    padding: String,
    @SignatureDigest
    digest: String,
): SignModule {
    val key = "$padding/$digest"
    return signModuleMap.getOrPut(key) {
        RsaSignModule(
            padding = padding,
            digest = digest
        )
    }
}

fun cipherModule(
    @CipherAlgorithm
    algorithm: String,
    @CipherBlockMode
    blockMode: String,
    @CipherPadding
    padding: String,
    @AESKeySize
    aesKeySize: Int = 128,
): CipherModule {
    val key = "$algorithm/$blockMode/$padding/$aesKeySize"
    return when (algorithm) {
        KeyProperties.KEY_ALGORITHM_AES -> cipherModuleMap.getOrPut(key) {
            AESCipherModule(
                blockMode = blockMode,
                padding = padding,
                keySize = aesKeySize
            )
        }

        else -> throw IllegalStateException("Only AES supported")
    }
}
