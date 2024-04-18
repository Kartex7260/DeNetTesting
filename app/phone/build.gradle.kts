import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    signingConfigs {
        val keyStoreProperties = "signingLocation"
        if (!project.hasProperty(keyStoreProperties)) {
            System.err.println("Project no has property=$keyStoreProperties")
            return@signingConfigs
        }
        val keyStorePropFile = File(project.property(keyStoreProperties) as String)
        if (!keyStorePropFile.exists()) {
            System.err.println("Not find file from prop=$keyStoreProperties, " +
                    "file=${project.property(keyStoreProperties)}")
            return@signingConfigs
        }

        create("release") {
            val props = Properties()
            props.load(FileInputStream(keyStorePropFile))

            storeFile = file(props["RELEASE_STORE_FILE"] as String)
            storePassword = props["RELEASE_STORE_PASSWORD"] as String
            keyAlias = props["RELEASE_KEY_ALIAS"] as String
            keyPassword = props["RELEASE_KEY_PASSWORD"] as String
        }
    }

    namespace = "kanti.denet"
    compileSdk = 34

    defaultConfig {
        applicationId = "kanti.denet"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
            matchingFallbacks += listOf("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val platform = platform(libs.compose.bom)
    implementation(platform)

    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.activity)

    implementation(project(":feat:node"))
    implementation(project(":data:node:impl:room"))

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
}