package hex.music.core;

import hex.music.core.consumer.AbcConsumer;
import hex.music.core.domain.Tune;
import hex.music.core.producer.AbcProducer;
import hex.music.core.service.command.CommandExecutor;
import hex.music.core.service.command.tune.SaveTuneCommand;
import hex.music.core.service.support.PuHandlerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author hln
 */
public class Main {

    public static final String FOLK = "FOLK";

    public static void main(String[] args) throws FileNotFoundException, IOException {

        PuHandlerFactory PU_HANDLER_FACTORY = new PuHandlerFactory();
        CommandExecutor commandExecutor = new CommandExecutor(PU_HANDLER_FACTORY);

// Importera låt:
//        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Polska efter Törnblom.abc");
//        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Låtar/Friskn gammel kar.abc");
//        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Låtar/Kaisa.abc");
//        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Låtar/Sorkar/Brudmarsch och Brudpolska.abc");
//        List<Tune> tuneList = new AbcConsumer(stream).consume();
        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Snällpolska.abc");
        List<Tune> tuneList = new AbcConsumer(stream, "UTF-8").consume();
        for (Tune tune : tuneList) {
//            commandExecutor.executeInTransaction(new SaveTuneCommand(tune), FOLK);
//            System.out.println(tune.getTitle());
            System.out.println(new AbcProducer(tune).produce());
        }
        stream.close();
//        
// Skriva ut lista på låtar:
////        
//        List<Tune> tunes = commandExecutor.execute(new FindAllTunesCommand(), FOLK);
//        String resultString = commandExecutor.execute(new GetAbcDocCommand(tunes), FOLK);
//        File resultFile = new File("/home/hln/Skrivbord/Låtar.abc");
//        new AbcFileWriter(resultString, resultFile).write();
////
//
//        File abcFile = new File("/home/hln/Skrivbord/Polska efter Olof Törnblom.abc");
//        try {
//            InputStream abcStream = new FileInputStream(abcFile);
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            
//
//        MetaData tune = commandExecutor.execute(new GetTuneCommand(1L), FOLK);
//        String abcDoc = commandExecutor.execute(new GetAbcDocCommand(tune), FOLK);
//        File abcFile = new File("/home/hln/Skrivbord/" + tune.getTitle() + ".abc");
//        new SimpleFileWriter(abcDoc, abcFile, "ISO-8859-1").write();
//        System.out.println(tune.getTitle());
//        for (Voice voice : tune.getVoices()) {
//            System.out.println(voice.getClef().getType().getCode());
//        }
// Skapa en låt:
//
//        Tune tune = new AbcTune("Snällpolska");
//        tune.setComposer("Håkan Lidén");
//        Key key = new AbcKey();
//        key.setSignature(Key.Signature.D_DOR);
//        tune.setKey(key);
//        tune.setRythm("Slängpolska");
//        tune.setHistory("Skriven 14 december i Bergsjö");
//        tune.setNotes("Ingen riktig låt, bara skriven för att testa");
//        tune.setTranscriber("Håkan Lidén");
//        tune.setUnitNoteLength("1/8");
//        tune.setMeter("3/4");
//        tune.setTempo("1/4=112");
//        Voice voice1 = new AbcVoice(0, new AbcClef(Clef.Type.TREBLE));
//        voice1.setVoiceId("V1");
//        voice1.setVoiceIndex(0);
//        voice1.setName("Fiol");
//        voice1.setShortName("F");
//        voice1.setTune(tune);
//        String body1 = "A | D>E F>G A>d | \n"
//                + "f/e/d/c/ d3 |]";
//        voice1.setBody(body1);
////        Clef clef = new AbcClef(Clef.Type.ALTO);
//        Voice voice2 = new AbcVoice(1, new AbcClef(Clef.Type.ALTO));
//        voice2.setVoiceId("V2");
////        voice2.setVoiceIndex(1);
//        voice2.setName("Altfiol");
//        voice2.setShortName("A");
//        voice2.setTune(tune);
//        String body2 = "F | A,>C D>E F>G | \n"
//                + "A/G/F/E/ F3 |]";
//        voice2.setBody(body2);
//        tune.addVoice(voice1);
//        tune.addVoice(voice2);
//        System.out.println(tune.getTitle());
//        tune.getVoices().stream().map((voice) -> {
//            System.out.println("V:" + voice.getVoiceId());
//            return voice;
//        }).forEach((voice) -> {
//            System.out.println(voice.getBody());
//        });
//        Long id = commandExecutor.executeInTransaction(new SaveTuneCommand(tune), "FOLK").getId();
    }
}
