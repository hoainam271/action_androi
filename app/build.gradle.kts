plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.newspaper"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newspaper"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.multi.sliding.up.panel)
    implementation(libs.reable.bottom.bar)

    implementation(project(path = ":lib:icons_pack"))

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)

}