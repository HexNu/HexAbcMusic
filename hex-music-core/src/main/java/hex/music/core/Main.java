package hex.music.core;

import hex.music.core.domain.Tune;
import hex.music.core.service.command.CommandExecutor;
import hex.music.core.service.command.tune.FindAllTunesCommand;
import hex.music.core.service.command.tune.GetAbcDocCommand;
import hex.music.core.service.support.PuHandlerFactory;
import hex.music.core.util.AbcFileWriter;
import hex.music.core.util.SimpleFileWriter;
import java.io.File;
import java.util.List;

/**
 *
 * @author hln
 */
public class Main {

    public static final String FOLK = "FOLK";

    public static void main(String[] args) {

        PuHandlerFactory PU_HANDLER_FACTORY = new PuHandlerFactory();
        CommandExecutor commandExecutor = new CommandExecutor(PU_HANDLER_FACTORY);
        
        List<Tune> tunes = commandExecutor.execute(new FindAllTunesCommand(), FOLK);
        String resultString = commandExecutor.execute(new GetAbcDocCommand(tunes), FOLK);
        File resultFile = new File("/home/hln/Skrivbord/Låtar.abc");
        new AbcFileWriter(resultString, resultFile).write();
        
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
//
//        Tune tune = new AbcTune("Snällpolska");
//        tune.setComposer("Håkan Lidén");
//        Key key = new AbcKey();
//        key.setType(Key.Type.D_DOR);
//        tune.setKey(key);
//        tune.setRythm("Slängpolska 2");
//        tune.setHistory("Skriven i december i Bergsjö");
//        tune.setNotes("Ingen riktig låt, bara skriven för att testa");
//        tune.setTranscriber("Håkan Lidén");
//        tune.setUnitNoteLength("1/8");
//        tune.setMeter("3/4");
//        tune.setTempo("1/4=112");
//        Voice voice1 = new AbcVoice();
//        voice1.setCode("V1");
//        voice1.setVoiceIndex(0);
//        voice1.setName("1:a stämman");
//        voice1.setShortName("1 st");
//        voice1.setTune(tune);
//        String body1 = "A | D>E F>G A>d | \n"
//                + "f/e/d/c/ d3 |]";
//        voice1.setBody(body1);
//        Clef clef = new AbcClef(Clef.Type.ALTO);
//        Voice voice2 = new AbcVoice(1, clef);
//        voice2.setCode("V2");
////        voice2.setVoiceIndex(1);
//        voice2.setName("2:a stämman");
//        voice2.setShortName("2 st");
//        voice2.setTune(tune);
//        String body2 = "F | A,>C D>E F>G | \n"
//                + "A/G/F/E/ F3 |]";
//        voice2.setBody(body2);
//        tune.addVoice(voice1);
//        tune.addVoice(voice2);
//        System.out.println(tune.getTitle());
//        tune.getVoices().stream().map((voice) -> {
//            System.out.println("V:" + voice.getCode());
//            return voice;
//        }).forEach((voice) -> {
//            System.out.println(voice.getBody());
//        });
//        Long id = commandExecutor.executeInTransaction(new SaveTuneCommand(tune), "FOLK").getId();
    }
}
