plugins {
    kotlin("jvm")
}

dependencies {
    api(libs.common.coroutine)
    api(libs.test.turbine)
    api(libs.test.coroutineTest)
}