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
	  implementation 'com.github.EsmaeelNabil:NetworkRetry-RetrofitAdapter:0.5'
}
```
## Usage :

#### add this line in your retrofit implementation.
``` kotlin

fun getNetworkRetryCallAdapter(): NetworkRetryCallAdapterFactory {
        return NetworkRetryCallAdapterFactory.create { call, exception, retryCall ->

            dialogManager.showNetworkScreen(
                message = exception.message ?: "",
                onRetry = { retryCall() }
            )

        }
    }

Retrofit.Builder()
            ...
            .addCallAdapterFactory(getNetworkRetryCallAdapter())
            .build()

```

#### `dialogManager.showNetworkScreen` screen design.
![dialogManager.showNetworkScreen](art/screen.png)