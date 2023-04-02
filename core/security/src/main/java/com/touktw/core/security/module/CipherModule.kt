package com.touktw.core.security.module

import com.touktw.core.security.SecurityModule
import com.touktw.core.security.base64EncodeString
import com.touktw.core.security.decodeBas64String
import com.touktw.core.security.isKeyExists
import com.touktw.core.security.keyStore
import javax.crypto.SecretKey

class EncryptData(
    val iv: ByteArray,
    val cipherText: ByteArray,
) {
    companion object {
        private const val DELIMITER = ","
        fun parse(string: String): EncryptData {
            val split = string.split(DELIMITER)
            return EncryptData(
                iv = decodeBas64String(split[0]),
                cipherText = decodeBas64String(split[1]),
            )
        }
    }

    override fun toString(): String {
        return base64EncodeString(iv) +
                DELIMITER + base64EncodeString(cipherText)
    }
}

sealed class CipherModule : SecurityModule {
    companion object {
        const val IV_LENGTH = 12
    }

    abstract fun encrypt(
        alias: String,
        value: ByteArray,
        iv: ByteArray? = null,
    ): EncryptData

    abstract fun decrypt(
        alias: String,
        encryptData: EncryptData,
    ): ByteArray

    protected fun key(alias: String): SecretKey {
        if (isKeyExists(alias).not()) {
            generateKey(alias)
        }
        return keyStore.getKey(alias, null) as SecretKey
    }
}