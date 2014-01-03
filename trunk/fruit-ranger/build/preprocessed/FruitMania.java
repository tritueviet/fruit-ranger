
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class FruitMania extends MIDlet {

    Command ok = new Command("OK", Command.OK, 1);
    Command ex = new Command("NO", Command.OK, 1);

    public FruitMania() {
        midletPaused = false;
        canvas = null;
        midletPaused = false;
    }

    public void startApp() {
        if (!midletPaused) {
            display = Display.getDisplay(this);
            canvas = new FruitCanvas(this);
            (new Thread(canvas)).start();
            display.setCurrent(canvas);
            midletPaused = true;
            canvas.bhide = false;
        }
    }

    public void pauseApp() {
        midletPaused = true;
    }

    public void destroyApp(boolean unconditional) {
        canvas.bPlay = false;
        //canvas.File_Save();
        canvas.FreeMem();
        canvas.FreeSound();
        canvas = null;
        try {
            System.gc();
        } catch (Exception e) {
        }
        Alert a = new Alert("messenger", "rate app for me?", null, AlertType.CONFIRMATION);
        a.addCommand(ex);
        a.addCommand(ok);


        Display.getDisplay(this).setCurrent(a);
        a.setCommandListener(new CommandListener() {

            public void commandAction(Command c, Displayable d) {
                if (c == ok) {
                    try {
                        platformRequest("http://store.ovi.com/publisher/PerfectAppFree");
                    } catch (ConnectionNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    notifyDestroyed();
                }
            }
        });


    }
    private boolean midletPaused;
    public FruitCanvas canvas;
    public static Display display = null;
}
