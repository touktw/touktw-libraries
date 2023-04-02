package com.touktw.core.security.module

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.IntDef
import androidx.annotation.StringDef
import com.touktw.core.security.ANDROID_KEYSTORE
import com.touktw.core.security.isKeyExists
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec

@StringDef(value = [KeyProperties.KEY_ALGORITHM_AES])
annotation class CipherAlgorithm

@StringDef(value = [
    KeyProperties.BLOCK_MODE_GCM,
    KeyProperties.BLOCK_MODE_CBC, // padding
])
annotation class CipherBlockMode

@StringDef(value = [
    KeyProperties.ENCRYPTION_PADDING_NONE,
    KeyProperties.ENCRYPTION_PADDING_PKCS7,
])
annotation class CipherPadding

@IntDef(value = [128, 192, 256])
annotation class AESKeySize

internal class AESCipherModule(
    @CipherBlockMode
    private val blockMode: String,
    @CipherPadding
    private val padding: String,
    @AESKeySize
    private val keySize: Int,
) : CipherModule() {
    companion object {
        private const val TAG_LENGTH = 16
    }

    init {
        if (blockMode == KeyProperties.BLOCK_MODE_CBC
            && padding == KeyProperties.ENCRYPTION_PADDING_NONE
        ) throw RuntimeException("CBC needs padding")

        if (blockMode == KeyProperties.BLOCK_MODE_GCM
            && padding != KeyProperties.ENCRYPTION_PADDING_NONE
        ) throw RuntimeException("GCM needs no padding")
    }

    override val cipher: Cipher by lazy {
        Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES
                    + "/" + blockMode
                    + "/" + padding
        )
    }

    override fun encrypt(
        alias: String,
        value: ByteArray,
        iv: ByteArray?,
    ): EncryptData {
        if (iv != null) {
            cipher.init(
                Cipher.ENCRYPT_MODE,
                key(alias),
                when (blockMode) {
                    KeyProperties.BLOCK_MODE_GCM -> GCMParameterSpec(TAG_LENGTH * 8, iv)
                    else -> IvParameterSpec(iv)
                }
            )
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key(alias))
        }

        val encryptedValue = cipher.doFinal(value)
        return EncryptData(
            iv = cipher.iv,
            cipherText = encryptedValue,
        )
    }

    override fun decrypt(
        alias: String,
        encryptData: EncryptData,
    ): ByteArray {
        cipher.init(Cipher.DECRYPT_MODE,
            key(alias),
            when (blockMode) {
                KeyProperties.BLOCK_MODE_GCM -> GCMParameterSpec(TAG_LENGTH * 8, encryptData.iv)
                else -> IvParameterSpec(encryptData.iv)
            }
        )
        return cipher.doFinal(encryptData.cipherText)
    }

    override fun generateKey(alias: String) {
        if (isKeyExists(alias)) return
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setBlockModes(blockMode)
            setEncryptionPaddings(padding)
            setKeySize(keySize)
            setRandomizedEncryptionRequired(false)
            build()
        }

        keyGenerator.init(spec)
        keyGenerator.generateKey()
    }
}