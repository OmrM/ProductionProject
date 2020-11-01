import java.lang.reflect.Type;

public class MoviePlayer extends Product implements MultimediaControl{

    private Screen screen;
    private MonitorType monitorType;

    //constructor
    MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
        super(name, manufacturer, ItemType.VISUAL);
        this.screen = screen;
        this.monitorType = monitorType;

    }

    //Create a toString method that calls the Product toString and displays the monitor and the screen details.
    public String toString(){
        return super.toString() + "\n" + screen.toString() + "\nMonitor Type: " + monitorType;

    }


    @Override
    public void play() {
        //implementation of method play() from MultimediaControl interface
        System.out.println("Playing movie");
    }

    @Override
    public void stop() {
        System.out.println("Stopping movie");
    }

    @Override
    public void previous() {
        System.out.println("Previous movie");
    }

    @Override
    public void next() {
        System.out.println("Next movie");

    }

}
