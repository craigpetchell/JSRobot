#/bin/bash
javac -classpath lib/jsobject.jar -d classes src/main/java/com/ephox/jsrobot/JSRobot.java && \
jar cvf JSRobot.jar -C classes com && \
jarsigner -keystore $STORE -storetype pkcs12 -storepass password -keypass password JSRobot.jar d9d674e4-3c59-42ca-b5ce-5e1fdf5158d6
