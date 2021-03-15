# NetworkRetry-RetrofitAdapter

## Including in your project
[![](https://jitpack.io/v/EsmaeelNabil/NetworkRetry-RetrofitAdapter.svg)](https://jitpack.io/#EsmaeelNabil/NetworkRetry-RetrofitAdapter)
### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
    	....
	maven { url 'https://jitpack.io' }
    }
}
```
And add a dependency code to your **APP**'s `build.gradle` file. [![](https://jitpack.io/v/EsmaeelNabil/NetworkRetry-RetrofitAdapter.svg)](https://jitpack.io/#EsmaeelNabil/NetworkRetry-RetrofitAdapter)
```gradle
dependencies {
	  implementation 'com.github.EsmaeelNabil:NetworkRetry-RetrofitAdapter:0.3'
}
```
## Usage :

#### add this line in your retrofit implementation.
``` kotlin

Retrofit.Builder()
            ...
            .addCallAdapterFactory(NetworkRetryCallAdapterFactory.create())
	    ...
            .build()

```


### ♨️ if you have extented an `Application` class

1 - make those changes : 
  - in the `AndroidManifest.xml` add this line to replace the `library` application class.
``` kotlin 
<application
        ....
	tools:replace="android:name"
	....
	/>
```
2- in your `application class`'s `onCreate()` add those:
```kotlin 
class ApplicationInstance : Application() {

override fun onCreate() {
        super.onCreate()
        initNetworkStateHandler()
        registerActivityTracker()
    }
}
```

3- then add this to retrofit like above `addCallAdapterFactory(NetworkRetryCallAdapterFactory.create())`
