[![Main Commit](https://github.com/android-alatan/Coroutine-Alatan/actions/workflows/lib-main-branch.yml/badge.svg?branch=main)](https://github.com/android-alatan/Coroutine-Alatan/actions/workflows/lib-main-branch.yml)
[![Release](https://jitpack.io/v/android-alatan/coroutine-alatan.svg)](https://jitpack.io/#android-alatan/coroutine-alatan)
# Coroutine Alatan

Coroutine Flow can be controlled running thread. If code access to Dispatchers.XX directly, it will make test hard.

DispatcherProvider in this repo provides CoroutineDispatcher. And MockDispatcherProvider provides testable CoroutineDispatcher

### Installation
```kotlin
implementation("com.github.android-alatan.coroutine-alatan:coroutine-dispatcher-api:$version")
testImplementation("com.github.android-alatan.coroutine-alatan:coroutine-dispatcher-assertion:$version")
```

### Example
```kotlin
// in production
class FooViewModel(private val dispatcherProvider: DispatcherProvider) {
  fun bar() {
    flowOf(1,2,3)
      .flowOn(dispatcherProvider.default())
      .collect { /* do something */ }
  }
}

// in test
class FooTest {
  private val dispatcherProvider = MockDispatcherProvider()
  private val viewModel = FooViewModel(dispatcherProvider)

  @Test
  fun test() {
    dispatcherProvider.immediate() // works on caller thread
    viewModel.bar()
  }
    
  @Test
  fun test2() {
    dispatcherProvider.async() // works on other thread
    viewModel.bar()
    dispatcherProvider.advanceTimeBy(3000L)
    dispatcherProvider.triggerActions() // run flow operators.
  }

}
```
`MockDispatcherProvider` in `coroutine-dispatcher-assertion` helps to control Coroutine Flow thread during test  