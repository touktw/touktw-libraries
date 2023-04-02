package com.touktw.core.security

import android.security.keystore.KeyProperties
import com.touktw.core.security.module.CipherModule
import com.touktw.core.security.module.SignModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Aes128CbcCipherModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Aes192CbcCipherModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Aes256CbcCipherModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Aes128GcmCipherModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Aes192GcmCipherModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Aes256GcmCipherModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha1PKCS1SignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha256PKCS1SignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha384PKCS1SignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha512PKCS1SignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha1PssSignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha256PssSignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha384PssSignatureModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RsaSha512PssSignatureModule

@Module
@InstallIn(SingletonComponent::class)
object SecurityModuleProvider {

    @Provides
    @Aes128CbcCipherModule
    fun provideAes128CbcCipherModule(): CipherModule = cipherModule(
        algorithm = KeyProperties.KEY_ALGORITHM_AES,
        blockMode = KeyProperties.BLOCK_MODE_CBC,
        padding = KeyProperties.ENCRYPTION_PADDING_PKCS7,
        aesKeySize = 128
    )

    @Provides
    @Aes192CbcCipherModule
    fun provideAes192CbcCipherModule(): CipherModule = cipherModule(
        algorithm = KeyProperties.KEY_ALGORITHM_AES,
        blockMode = KeyProperties.BLOCK_MODE_CBC,
        padding = KeyProperties.ENCRYPTION_PADDING_PKCS7,
        aesKeySize = 192
    )

    @Provides
    @Aes256CbcCipherModule
    fun provideAes256CbcCipherModule(): CipherModule = cipherModule(
        algorithm = KeyProperties.KEY_ALGORITHM_AES,
        blockMode = KeyProperties.BLOCK_MODE_CBC,
        padding = KeyProperties.ENCRYPTION_PADDING_PKCS7,
        aesKeySize = 256
    )

    @Provides
    @Aes128GcmCipherModule
    fun provideAes128GcmCipherModule(): CipherModule = cipherModule(
        algorithm = KeyProperties.KEY_ALGORITHM_AES,
        blockMode = KeyProperties.BLOCK_MODE_GCM,
        padding = KeyProperties.ENCRYPTION_PADDING_NONE,
        aesKeySize = 128
    )

    @Provides
    @Aes192GcmCipherModule
    fun provideAes192GcmCipherModule(): CipherModule = cipherModule(
        algorithm = KeyProperties.KEY_ALGORITHM_AES,
        blockMode = KeyProperties.BLOCK_MODE_GCM,
        padding = KeyProperties.ENCRYPTION_PADDING_NONE,
        aesKeySize = 192
    )

    @Provides
    @Aes256GcmCipherModule
    fun provideAes256GcmCipherModule(): CipherModule = cipherModule(
        algorithm = KeyProperties.KEY_ALGORITHM_AES,
        blockMode = KeyProperties.BLOCK_MODE_GCM,
        padding = KeyProperties.ENCRYPTION_PADDING_NONE,
        aesKeySize = 256
    )

    @Provides
    @RsaSha1PKCS1SignatureModule
    fun provideRsaSha1PKCS1SignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PKCS1,
        digest = KeyProperties.DIGEST_SHA1
    )

    @Provides
    @RsaSha256PKCS1SignatureModule
    fun provideRsaSha256PKCS1SignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PKCS1,
        digest = KeyProperties.DIGEST_SHA256
    )

    @Provides
    @RsaSha384PKCS1SignatureModule
    fun provideRsaSha384PKCS1SignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PKCS1,
        digest = KeyProperties.DIGEST_SHA384
    )

    @Provides
    @RsaSha512PKCS1SignatureModule
    fun provideRsaSha512PKCS1SignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PKCS1,
        digest = KeyProperties.DIGEST_SHA512
    )

    @Provides
    @RsaSha1PssSignatureModule
    fun provideRsaSha1PssSignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PSS,
        digest = KeyProperties.DIGEST_SHA1
    )

    @Provides
    @RsaSha256PssSignatureModule
    fun provideRsaSha256PssSignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PSS,
        digest = KeyProperties.DIGEST_SHA256
    )

    @Provides
    @RsaSha384PssSignatureModule
    fun provideRsaSha384PssSignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PSS,
        digest = KeyProperties.DIGEST_SHA384
    )

    @Provides
    @RsaSha512PssSignatureModule
    fun provideRsaSha512PssSignatureModule(): SignModule = signModule(
        padding = KeyProperties.SIGNATURE_PADDING_RSA_PSS,
        digest = KeyProperties.DIGEST_SHA512
    )
}
