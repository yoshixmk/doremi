import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

public class Main {

    private static final String[] HEADER = new String[] { "開始時間", "終了時間" };

    public static void main(String[] args) {

        String[] workStartCmd = {
            "/bin/sh",
            "-c",
            "pmset -g log | grep -e \"DarkWake to FullWake from Deep Idle\" -e \"DarkWake to FullWake from Standby\" -e \" Wake from Standby\" -e \"LidOpen\" | cut -c 1-20" };

        try {
            List<String> workStartResult = new ArrayList<>();
            Process p = Runtime.getRuntime().exec(workStartCmd);
            workStartResult.addAll(getResultString(p));

            workStartResult.sort(Comparator.naturalOrder());
            workStartResult.stream().forEach(s -> {
                System.out.println(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // WorkTime workTime = new WorkTime(HEADER[0], HEADER[1]);
        String fileName = "yourfile.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeNext(HEADER);
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
        return Arrays.stream(lines.toArray(new String[] {})).collect(Collectors.toList());
    }
}
