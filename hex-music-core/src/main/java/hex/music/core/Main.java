package hex.music.core;

import hex.music.core.service.command.CommandExecutor;
import hex.music.core.service.support.PuHandlerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class Main {

    public static final String FOLK = "FOLK";

    public static void main(String[] args) {

        PuHandlerFactory PU_HANDLER_FACTORY = new PuHandlerFactory();
        CommandExecutor commandExecutor = new CommandExecutor(PU_HANDLER_FACTORY);

        File abcFile = new File("/home/hln/Skrivbord/Polska efter Olof Törnblom.abc");
        try {
            InputStream abcStream = new FileInputStream(abcFile);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            
//
//        MetaData tune = commandExecutor.execute(new GetTuneCommand(1L), FOLK);
//        String abcDoc = commandExecutor.execute(new GetAbcDocCommand(tune), FOLK);
//        File abcFile = new File("/home/hln/Skrivbord/" + tune.getTitle() + ".abc");
//        new SimpleFileWriter(abcDoc, abcFile, "ISO-8859-1").write();
//        System.out.println(tune.getTitle());
//        for (Voice voice : tune.getVoices()) {
//            System.out.println(voice.getClef().getType().getCode());
//        }
//
//        MetaData meta = new AbcMetaData("Snällpolska");
//        meta.setComposer("Håkan Lidén");
//        meta.setKey(Key.D_DOR);
//        meta.setRythm("Slängpolska");
//        meta.setHistory("Skriven i december i Bergsjö");
//        meta.setNotes("Ingen riktig låt, bara skriven för att testa");
//        meta.setTranscriber("Håkan Lidén");
//        meta.setUnitNoteLength("1/8");
//        meta.setMeter("3/4");
//        meta.setTempo("1/4=112");
//        Voice voice1 = new AbcVoice();
//        voice1.setCode("V1");
//        voice1.setVoiceIndex(0);
//        voice1.setName("1:a stämman");
//        voice1.setShortName("1 st");
//        voice1.setMetaData(meta);
//        String body1 = "A | D>E F>G A>d | \n"
//                + "f/e/d/c/ d3 |]";
//        voice1.setBody(body1);
//        Voice voice2 = new AbcVoice();
//        voice2.setCode("V2");
//        voice2.setVoiceIndex(1);
//        voice2.setName("2:a stämman");
//        voice2.setShortName("2 st");
//        voice2.setMetaData(meta);
//        String body2 = "F | A,>C D>E F>G | \n"
//                + "A/G/F/E/ A3 |]";
//        voice2.setBody(body2);
//        meta.addVoice(voice1);
//        meta.addVoice(voice2);
//        System.out.println(meta.getTitle());
//        meta.getVoices().stream().map((voice) -> {
//            System.out.println("V:" + voice.getCode());
//            return voice;
//        }).forEach((voice) -> {
//            System.out.println(voice.getBody());
//        });
//        Long id = commandExecutor.executeInTransaction(new SaveTuneCommand(meta), "FOLK").getId();
    }
}
