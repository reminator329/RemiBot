reboot : 
	./gradlew shadowjar
	scp build/libs/RémiBot-1.0-all.jar reminator@20.43.33.90:~/RemiBot/build/libs/

jar :
	scp build/libs/RémiBot-1.0-all.jar reminator@20.43.33.90:~/RemiBot/build/libs/