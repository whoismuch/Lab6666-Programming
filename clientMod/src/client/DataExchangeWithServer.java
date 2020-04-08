package client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DataExchangeWithServer {
    private static SocketChannel outcomingchannel;
    private Charset charset;
    private byte[] outcoming;
    private ByteBuffer byteBuffer;

    public DataExchangeWithServer(SocketChannel outcomingchannel){
        this.outcomingchannel = outcomingchannel;
        charset = StandardCharsets.UTF_8;
    }

    public void sendToServer(String message) throws IOException {
        outcoming = new byte[(message.getBytes(charset).length)];
        outcoming = message.getBytes(charset);
        byteBuffer = ByteBuffer.wrap(outcoming);
        outcomingchannel.write(byteBuffer);
    }

    public String getFromServer() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byteBuffer = ByteBuffer.allocate(5000);
        while (outcomingchannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                System.out.println((char) b);
                baos.write(b);
            }
        }
        String message = new String(baos.toByteArray(), charset);
       // System.out.println(message );
        return message.trim( );
    }
}
