#/bin/bash
javac -d classes src/com/ephox/keytyper/MagicKeyTyper.java && \
jar cvf MagicKeyTyper.jar -C classes com && \
jarsigner -keystore $STORE -storetype pkcs12 -storepass password -keypass password MagicKeyTyper.jar d9d674e4-3c59-42ca-b5ce-5e1fdf5158d6
