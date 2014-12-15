package hex.music.core.consumer;

import hex.music.core.AbcConstants;
import hex.music.core.AbcConstants.Field;
import hex.music.core.domain.Key;
import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import hex.music.core.domain.impl.AbcTune;
import hex.music.core.domain.impl.AbcVoice;
import hex.music.core.producer.AbcProducer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class AbcConsumer {

    private final InputStream stream;
    private final boolean isUpdate;

    public AbcConsumer() {
        this(null);
    }

    public AbcConsumer(InputStream stream) {
        this(stream, false);
    }

    public AbcConsumer(InputStream stream, boolean isUpdate) {
        this.stream = stream;
        this.isUpdate = isUpdate;
    }

    public List<Tune> consume() throws UnsupportedEncodingException, IOException {
        List<Tune> result = new ArrayList<>();
        InputStreamReader reader = new InputStreamReader(stream, AbcConstants.ABC_ENCODING);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String currentLine;
        Tune currentTune = null;
        Voice currentVoice = null;
        while ((currentLine = bufferedReader.readLine()) != null) {
            System.out.println(currentLine);
            if (matchesField(Field.X, currentLine)) {
                currentTune = new AbcTune();
                result.add(currentTune);
                if (isUpdate) {
                    currentTune.setId(Long.valueOf(getFieldValue(currentLine)));
                }
            } else if (currentTune != null) {
                if (matchesField(Field.V, currentLine)) {
                    currentVoice = new AbcVoice();
                    // TODO: Försöka sätta properties på voice, antagligen genom att läsa tecken för tecken...
                    currentTune.addVoice(currentVoice);
                } else if (!isInTuneBody(currentLine)) {
                    extractValue(currentTune, currentLine);
                } else {
                    if (currentVoice == null) {
                        currentVoice = new AbcVoice();
                        currentTune.addVoice(currentVoice);
                    }
                    currentVoice.addBodyLine(currentLine);
                }
            }
        }
        return result;
    }

    private void extractValue(Tune tune, String line) {
        String field = line.substring(0, 1);
        String value = getFieldValue(line);
        switch (field) {
            case "C":
                tune.setComposer(value);
                break;
            case "H":
                tune.setHistory(value);
                break;
            case "K":
                // TODO: Införa properties för Key och försöka hantera inkommande sträng
                // på samma sätt som för voice.
                // antagligen genom att läsa tecken för tecken...
                tune.setKey(Key.getByCode(value));
                break;
            case "L":
                tune.setUnitNoteLength(value);
                break;
            case "M":
                tune.setMeter(value);
                break;
            case "N":
                tune.setNotes(value);
                break;
            case "O":
                tune.setRegion(value);
                break;
            case "P":
                // TODO: Parts, not implemented
                break;
            case "Q":
                tune.setTempo(value);
                break;
            case "R":
                tune.setRythm(value);
                break;
            case "S":
                tune.setSource(value);
                break;
            case "T":
                if (tune.getTitle() == null || tune.getTitle().equals("")) {
                    tune.setTitle(value);
                } else if (tune.getSubheader() == null || tune.getSubheader().equals("")) {
                    tune.setSubheader(value);
                }
                break;
            case "V":
//                tune.setSource(value);
                break;
            case "Z":
                tune.setTranscriber(value);
                break;

        }
    }

    public String getFieldValue(String line) {
        return line.replaceFirst(Field.getStartRegexp(), "");
    }

    public boolean isInTuneBody(String line) {
        return !line.substring(0, 2).matches(Field.getStartRegexp());
    }

    private boolean matchesField(Field field, String line) {
        return line.startsWith(field.name() + ":");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Låtar/Kaisa.abc");
        List<Tune> consume = new AbcConsumer(stream).consume();
        consume.stream().forEach((t) -> {
            System.out.println(new AbcProducer(t).produce());
//            System.out.println(t.getTitle());
//            System.out.println(t.getSubheader());
//            System.out.println(t.getComposer());
//            System.out.println(t.getOriginator());
//            System.out.println(t.getMeter());
//            System.out.println(t.getRythm());
//            System.out.println(t.getUnitNoteLength());
//            System.out.println(t.getKey().getCode());
//            System.out.println(t.getVoices().get(0).getBody());
        });
    }
}
