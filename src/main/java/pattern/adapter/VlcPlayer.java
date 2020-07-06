package pattern.adapter;

public class VlcPlayer implements AdvancedMediaPlayer{
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file, name=" + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        // do nothing
    }
}
