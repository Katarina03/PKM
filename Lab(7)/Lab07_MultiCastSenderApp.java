package ua.edu.chmnu.ki.networks.udp.multicast;

import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ua.edu.chmnu.ki.networks.common.CmdLineParser.extractValue;

public class Lab07_MultiCastSenderApp {
    public static void main(String[] args) throws SocketException, IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        String tmp = "";
        try(FileReader reader = new FileReader("Ip_log.txt"))
        {
            // char by char
            int c;
            while((c=reader.read())!=-1){

                tmp+=(char)c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        String[] group = tmp.split("\n");
        int port = 5559;
        for(int index = 0;index<group.length;index++) {

            for (int i = 0; i < args.length; ++i) {
                String value = extractValue(args[i], "-g:");
                if (value != null) {
                    group[index] = value;
                    continue;
                }

                value = extractValue(args[i], "-p:");
                if (value != null) {
                    port = Integer.parseInt(value);
                }

            }

            int finalIndex = index;
            MultiCastSender sender = new MultiCastSender(group[index], port).setAction(() -> {
                String toSend = "["+group[finalIndex]+ " Hello" +"] Message";
                return toSend.getBytes();
            });
            service.submit(sender);

        }

    }

}
