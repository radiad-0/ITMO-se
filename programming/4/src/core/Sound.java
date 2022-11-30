package core;

public enum Sound {
    CACKLE("кудахчет"),
    SQUEAL_WITH_PLEASURE("визжит от удовольствия"),
    YAP_FURIOUSLY("бешено тявкает"),
    EXULT("ликует");

    private String sound;
    Sound(String sound){
        this.sound = sound;
    }
    public String getSound() {
        return sound;
    }
}
