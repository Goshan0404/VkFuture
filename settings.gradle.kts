pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            setUrl("https://artifactory-external.vkpartner.ru/artifactory/superappkit-maven-public/")
        }

    }

}

rootProject.name = "VkFuture"
include(":app")
 