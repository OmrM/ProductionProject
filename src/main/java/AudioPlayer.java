/**
 * Create a class called AudioPlayer that is a subclass of Product and implements the
 * MultimediaControl interface.
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private static String supportedAudioFormats;
  private static String supportedPlaylistFormats;


  //constructor
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    AudioPlayer.supportedAudioFormats = supportedAudioFormats;
    AudioPlayer.supportedPlaylistFormats = supportedPlaylistFormats;

  }

  /**
   * Create a toString method that will display the superclass's toString method, but also add rows
   * for supportedAudioFormats and supportedPlaylistFormats.
   */
  public String toString() {
    return super.toString() + "\n" + "Supported Audio Formats: " + getSupportedAudioFormats() + "\n"
        + "Supported Playlist Formats: " + getSupportedPlaylistFormats();
  }


  @Override
  public void play() {
    //implementation of method play() from MultimediaControl interface
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");

  }

  public static String getSupportedAudioFormats() {
    return supportedAudioFormats;
  }

  public static String getSupportedPlaylistFormats() {
    return supportedPlaylistFormats;
  }


}
