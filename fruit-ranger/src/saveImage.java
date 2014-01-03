
import java.io.DataOutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TRITUEVIET
 */
public class saveImage {

    static void saveImage2File(final byte[] photo, final String text) {
        new Thread() {

            public void run() {
                try {
                    // Receive a photo as byte array 
                    // Save Image to file
                    FileConnection fileConn = null;
                    DataOutputStream dos = null;
                    //fileConn = (FileConnection) Connector.open("file:///E:/"+text+".gif");

                    fileConn = (FileConnection) Connector.open(System.getProperty("fileconn.dir.photos") + text + ".PNG");


                    if (!fileConn.exists()) {
                        fileConn.create();
                    }

                    dos = new DataOutputStream(fileConn.openOutputStream());
                    dos.write(photo);

                    dos.flush();
                    dos.close();
                    fileConn.close();

                } catch (Exception e) {
                    System.out.println("Error!" + e.getMessage());
                }
            }
        }.start();
        Runtime.getRuntime().gc();
    }
}
