This is a RESTful API, implemented using Spring boot, for querying and translating Marvel Superhero data.

##Setup
####  1. Configure environment variables for API Keys
* Marvel Characters API (free)
    * `MARVAL_API_PUBLIC_KEY`: Marvel API public key
    * `MARVAL_API_PRIVATE_KEY`: Marvel API private key
    - You can find/create keys [here](https://developer.marvel.com/account)
* Google Translate API ([initially free](https://cloud.google.com/translate/pricing))
    * `GOOGLE_APPLICATION_CREDENTIALS`: Path to JSON service account key
    - You can generate a JSON service key [here](https://cloud.google.com/storage/docs/authentication?hl=en#service_accounts)   
    
#### 2. Startup server
Running the server is simple, using the installed gradle wrapper
 * Windows: `gradlew run`
 * Mac/Linux: `./gradlew run`

---
Now, you should be able to interact with the REST API at the following endpoints:
 * `http://localhost:8080/characters`
 * `http://localhost:8080/characters/{character_id}?languageCode={translationCode}`
    * The `translationCode` parameter is optional
    * [List of supported language codes](https://cloud.google.com/translate/docs/languages)
 * Additionally, you can visit the [swagger-ui](http://localhost:8080/swagger-ui.html) page to interactively "Try it out"
 
  
> Note on Translation:
>After a quick search, Marvel Super Hero name translations seem to be unorganised. Most countries use the English super hero names, and some of those who don't have multiple translations - Google's translation API does not have special entries for Marvel Super Heroes.
>
>I've opted to turn off translation of Hero names, however, some uncommenting in the `TranslationService` will give translation (without marvel context)