package client;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DataExchangeWithServer {
    private static SocketChannel outcomingchannel;

    public DataExchangeWithServer(SocketChannel outcomingchannel){
        this.outcomingchannel = outcomingchannel;
    }

    public void sendToServer(Object object) throws IOException {

       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(baos);
       oos.writeObject(object);

       byte[] outcoming = baos.toByteArray();
       ByteBuffer byteBuf = ByteBuffer.wrap(outcoming);
       outcomingchannel.write(byteBuf);
       byteBuf.clear();
       baos.flush();
    }

    public Object getFromServer() throws IOException {
        try  {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        int n = 0;
        while ((n = outcomingchannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            baos.write(byteBuffer.array(), 0, n);
        }
        ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bios);
        return ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace( );
            return null;
        }
    }
}
