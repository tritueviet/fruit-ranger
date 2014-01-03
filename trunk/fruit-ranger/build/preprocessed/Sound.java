
import java.io.PrintStream;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public class Sound
        implements Runnable {

    public Sound(int wantSleep, int id) {
        mPlayer = null;
        sleep = wantSleep;
        ID = id;
        try {
            if (id != 2) {
                mPlayer = Manager.createPlayer(getClass().getResourceAsStream("/sound/" + id + ".wav"), "audio/x-wav");
                mPlayer.prefetch();
            }
        } catch (Exception e) {
        }
    }

    public void run() {
        if (bPlay) {
            try {
                if (mPlayer != null) {
                    switch (mode) {
                        case 0: // '\0'
                            mPlayer.setLoopCount(1);
                            break;

                        case 1: // '\001'
                            mPlayer.setLoopCount(-1);
                            break;
                    }
                    mPlayer.start();
                }
            } catch (Exception e) {
            }
        }
        try {
            Thread _tmp = t;
            Thread.sleep(2000L);
            Thread _tmp1 = t;
            Thread.yield();
        } catch (Exception e) {
        }
    }

    public void stopSound() {
        try {
            bPlay = false;
            mode = 0;
            if (mPlayer != null) {
                mPlayer.stop();
                if (ID == 2) {
                    mPlayer.deallocate();
                    mPlayer.close();
                    mPlayer = null;
                }
            }
        } catch (Exception e) {
        }
    }

    public synchronized void startSound(int ID, int LOOP) {
        stopSound();
        mode = LOOP;
        bPlay = true;
        try {
            if (ID == 2) {
                mPlayer = Manager.createPlayer(getClass().getResourceAsStream("/sound/" + ID + ".mid"), "audio/midi");
                mPlayer.prefetch();
                w = (VolumeControl) mPlayer.getControl("VolumeControl");
                int vl = w.getLevel();
                if (w != null) {
                    w.setLevel(vl / 2);
                }
            }
            t = new Thread(this);
            t.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    Player mPlayer;
    int mode;
    int sleep;
    boolean bPlay;
    Thread t;
    int ID;
    VolumeControl w;
}
