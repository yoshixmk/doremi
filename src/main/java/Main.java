import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // alias isw='pmset -g log | grep "DarkWake to FullWake from Deep Idle" | grep `date +%Y-%m-%d` | head -n 1 | cut -c 1-20'
        // alias isw2='pmset -g log | grep LidOpen | grep `date +%Y-%m-%d` | head -n 1 | cut -c 1-20'
        // alias isw3='pmset -g log | grep "DarkWake to FullWake from Standby" | grep `date +%Y-%m-%d` | head -n 1 | cut -c 1-20'
        
        String[] workStartCmd1 = {"/bin/sh", "-c", "pmset -g log | grep LidOpen" };
        String[] workStartCmd2 = {"/bin/sh", "-c", "pmset -g log | grep -e \"DarkWake to FullWake from Deep Idle\" -e \"DarkWake to FullWake from Standby\"" };

        try {
            List<String> workStartResult = new ArrayList<>(); 
            for (String[] cmd : Arrays.asList(workStartCmd1, workStartCmd2)) {
                Process p = Runtime.getRuntime().exec(cmd);
                workStartResult.addAll(getResultString(p));
            }
            workStartResult.sort(Comparator.naturalOrder());
            workStartResult.stream().forEach(s -> {
                System.out.println(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static List<String> getResultString(Process p) throws IOException {
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return Arrays.stream(lines.toArray(new String[]{})).map(s -> {return s.substring(0, 20);}).collect(Collectors.toList());
    }
}
