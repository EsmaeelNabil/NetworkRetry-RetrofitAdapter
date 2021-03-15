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
## Usage - Features

``` kotlin

Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .....
            .addCallAdapterFactory(NetworkRetryCallAdapterFactory.create())
            .build()

```
