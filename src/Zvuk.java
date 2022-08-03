import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Zvuk {
    Clip clip;
    URL zvukURL[] = new URL[3];
    FloatControl fc;
    int hlasitostZvuku = 3;
    float hlasitost;


    /**
     * Zastresuje zvuky do URL aby to bolo vsetko na jednom mieste
     */
    public Zvuk() {

        zvukURL[0] = getClass().getResource("zvuky/bbb.wav");
        zvukURL[1] = getClass().getResource("zvuky/tada.wav");
        zvukURL[2] = getClass().getResource("zvuky/prehralSi.wav");

    }

    public void nastavSubor(int i){
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(zvukURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        }catch (Exception e){
        }
    }

    public void hraj(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void zastav(){
        clip.stop();
    }

    public void skontrolujZvuk(){

        switch (hlasitostZvuku){
            case 0: hlasitost = -80f; break;
            case 1: hlasitost = -20f; break;
            case 2: hlasitost = -12f; break;
            case 3: hlasitost = -5f; break;
            case 4: hlasitost = 1f; break;
            case 5: hlasitost = 6f; break;
        }


    }
}
