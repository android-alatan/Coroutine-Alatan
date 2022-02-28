plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {
    api(libs.common.coroutine)
    api(libs.test.coroutineTest)
    api(projects.coroutineDispatcherApi)

    testImplementation(projects.coroutineTestUtil)
}