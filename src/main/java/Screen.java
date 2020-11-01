public class Screen implements ScreenSpec{
    String resolution;
    int refreshRate;
    int responseTime;

    //constructor
    Screen(String resolution, int refreshRate, int responseTime){
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.responseTime = responseTime;
    }
    //toString method for Screen info
    public String toString(){
        return "Screen: \nResolution: " + resolution + "\nRefresh rate: " + refreshRate + "\nResponse time: " + responseTime;
    }

    @Override //need to learn why this is a thing.
    public String getResolution(){

        return resolution;
    }

    @Override
    public int getRefreshRate() {
        return refreshRate;
    }

    @Override
    public int getResponseTime() {
        return responseTime;
    }


}
