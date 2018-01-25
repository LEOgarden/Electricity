package android.leo.electricity.utils;

/**
 * Created by Leo on 2017/9/7.
 */

public class ConCla {
    // http://192.168.0.63:8080/ws/Power/powerService/newUserApply
    public static String getConCla(String ip, String port, String projectName, String procName,
                                   String action) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        if (ip != null && ip.length() > 0) {
            sb.append(ip);
        } else {
            sb.append("localhost");
        }
        sb.append(":");
        if (port != null && port.length() > 0) {
            sb.append(port);
        } else {
            sb.append("8080");
        }
        sb.append("/");
        sb.append(projectName);
        sb.append("/");
        sb.append(procName);
        sb.append("/");
        sb.append(action);
        return sb.toString();
    }

}
