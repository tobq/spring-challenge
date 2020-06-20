Setup
---

###  1. Configure environment variables for API Keys
* Marvel API keys
    * `MARVAL_API_PRIVATE_KEY`
    * `MARVAL_API_PUBLIC_KEY`
* Google API keys (for translation)
    * `GOOGLE_APPLICATION_CREDENTIALS `
    
### 2. Startup server
* run `gradlew run`

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