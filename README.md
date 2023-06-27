# Teaching Aura Backend service

**How to clone repo ?**

git clone --recurse-submodules git@github.com:TeachingAura/TeachingAuraBackend.git

**How to build project ?**

./gradlew build

Pre requisite Steps :

1. Install google cloud sli : https://cloud.google.com/sdk/docs/install
2. Get yourself added to the google cloud project : teachingaura
3. Run :  
          gcloud init <br>
          gcloud auth configure-docker
4. Set 2 environment variables in Intellij Run config <br>
          GOOGLE_APPLICATION_CREDENTIALS=PATH_TO_KEY<br>
          GOOGLE_CLOUD_PROJECT=teachingaura
