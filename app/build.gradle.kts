plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    id ("kotlin-kapt")

}

android {
    namespace = "cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14"
    compileSdk = 34

    defaultConfig {
        applicationId = "cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Lifecycle
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.activity.ktx)
    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //RecyclerView
    implementation (libs.androidx.recyclerview)
    //Serialization
    implementation (libs.kotlinx.serialization.json)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    implementation (libs.kotlinx.coroutines.android)
    kapt (libs.androidx.room.compiler)
    //liveData
    implementation(libs.lifecycleLiveData)
    implementation(libs.lifecycleRuntime)


}