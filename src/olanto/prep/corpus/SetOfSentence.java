package olanto.prep.corpus;

import java.io.*;
import java.util.HashMap;

/**
 *
 * @author jg
 */
public class SetOfSentence {

    static int totset;
    public HashMap<String, Integer> hsub = new HashMap<>(100);

    public SetOfSentence(String sourceSub, int minlen) {
        try {

            processAFile(sourceSub, minlen);
            System.out.println("tot retained sentences:" + totset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processAFile(String fileName, int minlen) {

        try {
            System.out.println("open :" + fileName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader insource = new BufferedReader(isr);
            String w = insource.readLine();
            while (w != null) {
                if (w.length() < minlen) {
                    System.out.println(" exclude: |" + w + "|");
                } else {
                    hsub.put(w, totset);
                    totset++;
                }
                w = insource.readLine();
            }
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getid(String s) {
        return hsub.get(s);
    }
}
