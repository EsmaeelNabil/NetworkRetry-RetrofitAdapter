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
	  implementation 'com.github.EsmaeelNabil:NetworkRetry-RetrofitAdapter:0.4'
}
```
## Usage :

#### add this line in your retrofit implementation.
``` kotlin

Retrofit.Builder()
            ...
            .addCallAdapterFactory(NetworkRetryCallAdapterFactory.create())
            .build()

```


## customization :
##### to provide a new design for the screen that shows by default 
- create a [no_internet_view](https://github.com/EsmaeelNabil/NetworkRetry-RetrofitAdapter/blob/master/NetworkRetryCallAdapter/src/main/res/layout/no_internet_view.xml) xml layout file with the added ui components 
- make sure the file has the same `name` and view's `types` and `id` ar the same as well 😁.

