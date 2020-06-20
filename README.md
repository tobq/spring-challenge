Setup
---

###  1. Configure environment variables for API Keys
* Marvel Characters API (free)
    * `MARVAL_API_PUBLIC_KEY`: Marvel API public key
    * `MARVAL_API_PRIVATE_KEY`: Marvel API private key
    - You can find/create keys [here](https://developer.marvel.com/account)
* Google Translate API ([initially free](https://cloud.google.com/translate/pricing))
    * `GOOGLE_APPLICATION_CREDENTIALS`: Path to JSON service account key
    - You can generate a JSON service key [here](https://cloud.google.com/storage/docs/authentication?hl=en#service_accounts)   
    
### 2. Startup server
Running the server is simple, using the installed gradle wrapper
 * Windows:`gradlew run`
 * Mac/Linux: `./gradlew run`

---
Now, you should be able to interact with the REST API at the following endpoints:
 * `http://localhost:8080/characters`
 * `http://localhost:8080/characters/{character_id}`

TODO: 404 page

DEPS:
- spring web
- spring webflux
- bytes-java (tiny)
 -- mainly for fast byes-to-hex
 -- also has an md5 hash function
 ---- users the standard MessageDigest crypto class under the hood 