plugins {
    id("lib-jvm")
    id("code-quality")
    id("publish-jvm")
}

dependencies {
    api(libs.common.coroutine)
}