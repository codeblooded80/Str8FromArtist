plugins {
    id("com.android.application")
}

android {
    namespace = "com.mh.str8fromartist_v3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mh.str8fromartist_v3"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.2.0")
    /*implementation("javax.mail:mail:1.4.1")
    implementation("javax.activation:activation:1.1.1")
    implementation(files("libs/additionnal.jar"))*/

    implementation("com.sun.mail:android-mail:1.6.0")
    implementation("com.sun.mail:android-activation:1.6.0")

    implementation("org.postgresql:postgresql:42.2.5")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}