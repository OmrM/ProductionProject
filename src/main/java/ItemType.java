public enum ItemType {

    AUDIO("AU"), VISUAL("VI"), AUDIO_MOBILE("AM"), VISUAL_MOBILE("VM");

    private final String label; //private constructor
    ItemType(String c){
        label = c;
    }
    // method that should get the String value data inside of an enum??
    public String getItemType(){
        return label;
    }


}