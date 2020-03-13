import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/6/28 13:53
 **/
public class wifiTest {

    @Test
    public void wifi(){
        try {
//获取wifi   netsh wlan show networks mode=bssid
            String command = "cmd.exe /c netsh wlan show networks mode=bssid";
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
            String line;
            String address="";
            while ((line = br.readLine()) != null) {
                System.out.println("line:" + line);
                if (line.indexOf("SSID") > 0) {
                    int index = line.indexOf(":");
                    index += 2;
                    address = line.substring(index);
                    System.out.println("address:"+address);
                }
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //TEST jdk
    @Test
    public void jdkTest(){
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                byte[] mac = el.nextElement().getHardwareAddress();
                if (mac == null)
                    continue;

                StringBuilder builder = new StringBuilder();
                for (byte b : mac) {
//                    builder.append(hexByte(b));
                    builder.append("-");
                }
                builder.deleteCharAt(builder.length() - 1);
                System.out.println(builder.toString());

            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
