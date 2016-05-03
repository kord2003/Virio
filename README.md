# Virio
This is android demo related to video playing.

It utilizes android MediaPlayer as default player, but you can choose between MediaPlayer and ExoPlayer. Just uncomment appropriate block in layout/activity_main.xml and compile the application.
Unfortunately, ExoPlayer r1.5.7 cannot play "Elephant's Dream" movie (looks like it was encoded incorrectly). The only way to fix this issue is to modify ExoPlayer's source code (WebmExtractor.java), but I think it's not cool to modify source code for one particular media file, because this change can affect playback of other media files.
Application utilizes MVP pattern for navigation, so it's easy to add support for another video player and even build applications with different video players by using "flavours".
Application support 3 "flavours": "buck_bunny", "sintel" and "elephants" so you can generate 3 APKs with different names and different initial movie loading on startup. 
Talking about DRM: Android MediaPlayer provide DRM support starting from API 18 (Android 4.3) and minSdkVersion is 16 (Android 4.1), so it's better to switch to ExoPlayer if you want to add DRM support to your application. But as you can see, ExoPlayer is not perfect, so you have to check if media files encoded by your media provider can be played by ExoPlayer correctly.