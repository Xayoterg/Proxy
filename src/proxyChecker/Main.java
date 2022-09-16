package proxyChecker;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("C://Users/user/ip.txt");
            int i;
            String address = " ";
            while ((i = fis.read()) != -1) {
                if (i == 13) continue;
                else if (i == 10) {


                    String ip = address.split("\t")[0];
                    int port = Integer.parseInt(address.split("\t")[1]);
                    checkProxy(ip, port);
                    address = "";
                } else {
                    address += (char) i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkProxy(String ip, int port) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
                    URL url = new URL("https://vozhzhaev.ru/test.php");
                    URLConnection connection = url.openConnection(proxy);
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine = "";
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine + " РАБОТАЕТ");

                    }
                    FileWriter fw = new FileWriter("C://Users/user/ip.txtv", true);
                    fw.write(ip + ":" + port + "\n");
                    fw.flush();
                    fw.close();
                } catch (Exception e) {
                    System.out.println(ip + " не работает");
                }
            }

        });
        thread.start();

    }

}
