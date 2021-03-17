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
