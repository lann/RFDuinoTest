# Application to control RFduino devices from an Android phone

Compiles fine on Ubuntu 13.10. 

Requirements, gradle:

    sudo add-apt-repository ppa:cwchien/gradle
    sudo apt-cache search gradle
    sudo aptitude install gradle-1.10

Do not download the 1.11 one, it fails on my system. Also do not use the default one from Canonical. I don't even know how old that one is.

Update Android if necessary. I adjusted the gradle.build file here to use Android 4.4 (version 19) as is the target in the Manifest.

    cd /opt/android-sdk-linux/tools
    ./monitor

And update the thing via the menu options.

## Bugs

If you see bugs, please contact the original developer. The one I pletted myself was the absence of drawables.

## Debugging

Everything time you use another application to connect to the RFduino, for example the nRF Console Panel from Nordic itself to read the services and characteristics, it is wise to restart Bluetooth. 

If you run the debugger:

    adb logcat

You see often commands like:

    W/bt-l2cap(17164): L2CAP - LE - cannot start new connection at conn st: 2

This means that there is still a connection request somewhere. It can be also a request done by the same app, but you switched temporarily to look at an email or something. Just be lazy and turn off and on Bluetooth as a habit. This is very likely due to some bugs in the bluetooth stack of Samsung and not much you can do about it. Perhaps except for one thing. Change the code to a disconnect before a connect. I've to check that.

# License

This project is licensed under the [MIT License](http://opensource.org/licenses/MIT).

# Copyright 

Copyright 2013 Lann
