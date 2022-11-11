apply<LocalProjectPublishPlugin>()


class LocalProjectPublishPlugin : Plugin<Gradle> {

    override fun apply(gradle: Gradle) {
        gradle.allprojects {
            val mavenProjectRepoDir = providers.environmentVariable("LOCAL_PROJECT_PUBLISH_DIR").map { file(it) }

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

        gradle.beforeSettings { println("::group::rootProject ${rootProject.name}") }
        gradle.settingsEvaluated { println("::endgroup::") }

        gradle.beforeProject { println("::group::${path}") }
        gradle.afterProject { println("::endgroup::") }

        gradle.taskGraph.whenReady {
            beforeTask { println("::group::${path}") }
            afterTask { println("::endgroup::") }
        }
    }
}
