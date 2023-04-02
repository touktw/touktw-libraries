package com.touktw.core.security

import timber.log.Timber
import kotlin.random.Random

object TestUtil {
    private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
    internal fun randomString(length: Int): String {
        val random = Random(System.currentTimeMillis())
        val sb = StringBuilder()
        for (i in 0..length) {
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        }
        return sb.toString()
    }

    internal fun timberInit() {
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                println("${tag ?: ""} $message")
            }
        })
    }
}

internal const val SAMPLE_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n" +
        "MIIDVTCCAj2gAwIBAgIEHOHfzDANBgkqhkiG9w0BAQsFADBbMQswCQYDVQQGEwJr\n" +
        "cjELMAkGA1UECBMCbm8xCzAJBgNVBAcTAm5vMQ8wDQYDVQQKEwZ0b3VrdHcxDzAN\n" +
        "BgNVBAsTBnRvdWt0dzEQMA4GA1UEAxMHdGFlIGtpbTAeFw0yMzAxMDcxMzI4MTha\n" +
        "Fw0yMzA0MDcxMzI4MThaMFsxCzAJBgNVBAYTAmtyMQswCQYDVQQIEwJubzELMAkG\n" +
        "A1UEBxMCbm8xDzANBgNVBAoTBnRvdWt0dzEPMA0GA1UECxMGdG91a3R3MRAwDgYD\n" +
        "VQQDEwd0YWUga2ltMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo696\n" +
        "ykpPD5H7cTNjVkYffpknqdfVc+e80gvIpCrxuPd6GB5QTfBTU/aCt3VGBt0v3OWb\n" +
        "e9mdZHf5qGMVZ12U35wYiAUqGhXl54PhMtoY2euw0qxDFzC0aoRoFUCXmuN3cvtO\n" +
        "HtV3TE5bRjd95jJsTkIGoaf80QLuRKimhjUYZ22v+L0yDByS9S4SVcPAoZ4uc2j9\n" +
        "jwtrU3pNEKWr7DOBT5Jyas4h2ox6IOyWZqKeKWYVYZFtFxhyAPoO6tOA5q0svXpr\n" +
        "Ix+imehX7L9yQ+tlRKBoXaegrsVTU+R4ZmVWkMTSfGMVdkfrBqLhpeIUANuYjZXz\n" +
        "kLz8ixU6WB1f+/8NVwIDAQABoyEwHzAdBgNVHQ4EFgQUK0duemOXkDhFqbnyf24C\n" +
        "c1CS9QYwDQYJKoZIhvcNAQELBQADggEBADzQOxIasRX4kea0200qlSd2fy+6vyDx\n" +
        "W5oz6R4QuzaCnMIZIANiy8V+hHEKgVoECv1mN0pBGUBEv4KaxzlLLxOu83KImqbz\n" +
        "gvcFQN7J08M3VueuC1U5IsBsI5lNCV0DXE/tpP3FYnMz0y25uKRLlNw6EXqOiuby\n" +
        "TW2IEM/EqNEHVPXGiej797fkDKmlOruIqBzjOCXEwwrHBwa6gnMjN77iH/UWja2K\n" +
        "ejZHmbETp92yQvEbIC14mnmQHfLVwhinoHmWkPkq6ak2utwXVRMrWipEwFXBcbhM\n" +
        "iqn7pEQz+envP6um7Eg03228hyskXy4YVA+etY8NkhjXvNvZm2illfg=\n" +
        "-----END CERTIFICATE-----"