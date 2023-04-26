import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Plugin
import org.gradle.api.PolymorphicDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class MavenPublishConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val localProperties = gradleLocalProperties(target.rootDir.parentFile)

        with(target) {
            with(pluginManager) {
                apply("signing")
            }

            publishing {
                publications {
                    register<MavenPublication>("release") {
                        groupId = rootProject.group.toString()
                        version = rootProject.version.toString()

                        pom {
                            url.set("https://github.com/touktw/touktw-libraries")
                            licenses {
                                license {
                                    name.set("Apache License")
                                    url.set("https://github.com/touktw/touktw-libraries/blob/main/LICENSE")
                                }
                            }
                            scm {
                                connection.set("scm:git:github.com/touktw/touktw-libraries.git")
                                url.set("https://github.com/touktw/touktw-libraries.git")
                            }
                            developers {
                                developer {
                                    id.set("touktw")
                                    name.set("tae.kim")
                                    email.set("touktw@gmail.com")
                                }
                            }
                        }

                        afterEvaluate {
                            from(components.getByName("release"))
                        }

                        repositories {
                            maven {
                                name = "sonatype"
                                val snapshot =
                                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                                val release =
                                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                                val url = if (version.endsWith("SNAPSHOT")) snapshot else release

                                setUrl(url)
                                credentials {
                                    username = localProperties["sonatypeUserName"].toString()
                                    password = localProperties["sonatypeToken"].toString()
                                }
                            }
                        }
                    }
                }
            }

            signing {
                useInMemoryPgpKeys(
                    localProperties["signing.keyId"].toString(),
                    localProperties["signing.key"].toString(),
                    localProperties["signing.password"].toString(),
                )
                sign(publishing.publications)
            }
        }
    }
}

private fun Project.`publishing`(configure: Action<PublishingExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("publishing", configure)

private inline fun <reified T : Any> PolymorphicDomainObjectContainer<in T>.register(
    name: String,
    noinline configuration: T.() -> Unit,
): NamedDomainObjectProvider<T> =
    register(name, T::class.java, configuration)

private fun Project.`signing`(configure: Action<org.gradle.plugins.signing.SigningExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("signing", configure)

private val Project.`publishing`: PublishingExtension
    get() =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("publishing") as PublishingExtension

private val Project.`ext`: org.gradle.api.plugins.ExtraPropertiesExtension get() =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("ext") as org.gradle.api.plugins.ExtraPropertiesExtension