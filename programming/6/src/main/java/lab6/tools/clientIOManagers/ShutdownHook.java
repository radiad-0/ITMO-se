package lab6.tools.clientIOManagers;

import lab6.excepcions.MyException;
import lab6.tools.ClientRequest;
import lab6.tools.OutputManager;
import lab6.tools.Serializer;
import lab6.tools.UDPConnection;

import java.net.SocketAddress;

public class ShutdownHook extends Thread {
    private UDPConnection udpConnection;
    private OutputManager outputManager;
    private SocketAddress serverAddress;

    public ShutdownHook(UDPConnection udpConnection, OutputManager outputManager, SocketAddress serverAddress) {
        this.udpConnection = udpConnection;
        this.outputManager = outputManager;
        this.serverAddress = serverAddress;
    }

    @Override
    public void run(){
        try {
            outputManager.println("программа завершена");
            udpConnection.sendData(Serializer.serialize(new ClientRequest("exit", "exit")), serverAddress);
        } catch (MyException ignored) {}
    }
}

