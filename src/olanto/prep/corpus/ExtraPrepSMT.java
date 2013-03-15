/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.prep.corpus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Jacques guyot
 */
public class ExtraPrepSMT {

    static SetOfSentence sos;

    public static void main(String[] args) {
        // TODO code application logic here
        String drive = "D:";
        String excludeThis = drive + "/CERN/cern2-tuning-eval/notincorpus.so";
        String corpusSO = drive + "/CERN/SMT-ENFR/corpus.so";
        String corpusTA = drive + "/CERN/SMT-ENFR/corpus.ta";
        String outSO = drive + "/CERN/SMT-ENFR/CLEAN/corpus.so";
        String outTA = drive + "/CERN/SMT-ENFR/CLEAN/corpus.ta";
        int minlen = 50;
        sos = new SetOfSentence(excludeThis, minlen);
        processAFile(corpusSO, corpusTA, minlen, outSO, outTA);
    }

    public static void processAFile(String corpusSO, String corpusTA, int minlen, String outcorpusSO, String outcorpusTA) {
        int count = 0;
        try {
            System.out.println("open :" + corpusSO + ", minlen: " + minlen);
            BufferedReader inSO = new BufferedReader(new InputStreamReader(new FileInputStream(corpusSO)));
            BufferedReader inTA = new BufferedReader(new InputStreamReader(new FileInputStream(corpusTA)));
            OutputStreamWriter outSO = new OutputStreamWriter(new FileOutputStream(outcorpusSO), "UTF-8");
            OutputStreamWriter outTA = new OutputStreamWriter(new FileOutputStream(outcorpusTA), "UTF-8");
            String wSO = inSO.readLine();  // tous le temps ensemble pour garder la synchro
            String wTA = inTA.readLine();
            while (wSO != null) {
                if (wSO.length() < minlen) {  // copie
                    outSO.append(wSO + "\n");
                    outTA.append(wTA + "\n");
                } else {
                    // test si ok
                    Integer res = sos.hsub.get(wSO);
                    if (res != null) { // reject
                        System.out.println(wSO);
                        count++;
                    } else { // copie
                        outSO.append(wSO + "\n");
                        outTA.append(wTA + "\n");
                    }
                }
                wSO = inSO.readLine();
                wTA = inTA.readLine();
            }
            inSO.close();
            inTA.close();
            outSO.close();
            outTA.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("rejected:" + count);
    }
}
