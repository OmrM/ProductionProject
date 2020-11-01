//Create a class called AudioPlayer that is a subclass of Product and implements the MultimediaControl interface.
public class AudioPlayer extends Product implements MultimediaControl{

    private static String supportedAudioFormats;
    private static String supportedPlaylistFormats;



    //constructor
    AudioPlayer(String name, String manufacturer,  String supportedAudioFormats, String SupportedPlaylistFormats){
        //the constructor should call its parent's constructor (using super()?) and also setup the media type to AUDIO.
        super(name, manufacturer, ItemType.AUDIO);

        // this.supportedAudioFormats = supportedAudioFormats;  like this?? or:
        //this.supportedPlaylistFormats = SupportedPlaylistFormats;
        //access via class reference instead of this.dot notation?
        AudioPlayer.supportedAudioFormats = supportedAudioFormats;
        AudioPlayer.supportedPlaylistFormats = SupportedPlaylistFormats;

    }
    //toString method for audioPlayer info
    public String toString() {
        //Create a toString method that will display the superclass's toString method, but also add rows for supportedAudioFormats and supportedPlaylistFormats.
        return super.toString() + "\n" +"Supported Audio Formats: " + getSupportedAudioFormats() + "\n" + "Supported Playlist Formats: " + getSupportedPlaylistFormats();
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

    public static String getSupportedPlaylistFormats(){
        return supportedPlaylistFormats;
    }





}
