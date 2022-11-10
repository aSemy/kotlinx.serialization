apply<LocalProjectPublishPlugin>()


class LocalProjectPublishPlugin : Plugin<Gradle> {

    override fun apply(gradle: Gradle) {
        gradle.allprojects {

            val mavenProjectRepoDir =
                providers.environmentVariable("LOCAL_PROJECT_PUBLISH_DIR").map { file(it) }

            plugins.withType<MavenPublishPlugin>().configureEach {
                extensions.configure<PublishingExtension> {
                    repositories {
                        maven(mavenProjectRepoDir) {
                            name = "MavenProjectRepo"
                        }
                    }
                }
            }
        }
    }
}
