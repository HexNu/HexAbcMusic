package hex.music.core.consumer;

import hex.music.core.AbcConstants;
import hex.music.core.AbcConstants.Field;
import hex.music.core.domain.Clef;
import hex.music.core.domain.Key;
import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import hex.music.core.domain.impl.AbcClef;
import hex.music.core.domain.impl.AbcKey;
import hex.music.core.domain.impl.AbcTune;
import hex.music.core.domain.impl.AbcVoice;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if (currentLine.length() < 2 || currentLine.startsWith("%")) {
                // Skip
            } else if (matchesField(Field.X, currentLine)) {
                currentTune = new AbcTune();
                result.add(currentTune);
                if (isUpdate) {
                    currentTune.setId(Long.valueOf(getFieldValue(currentLine)));
                }
            } else if (currentTune != null) {
                if (matchesField(Field.V, currentLine)) {
                    currentVoice = handleVoiceProperties(currentLine);
                    currentTune.addVoice(currentVoice);
                    currentVoice.setTune(currentTune);
                } else if (!isInTuneBody(currentLine)) {
                    extractValue(currentTune, currentLine);
                } else {
                    if (currentVoice == null) {
                        currentVoice = new AbcVoice();
                        currentTune.addVoice(currentVoice);
                        currentVoice.setTune(currentTune);
                    }
                    currentVoice.addBodyLine(currentLine);
                }
            }
        }
        return result;
    }

    private List<String> splitProperties(String line) {
        List<String> result = new ArrayList<>();
        Matcher m = Pattern.compile("[^\\s\"=']+|\"[^\"]*\"|'[^']*'").matcher(line);
        while (m.find()) {
            result.add(m.group());
        }
        return result;
    }

    private Key handleKeyProperties(String line) {
        Key result = new AbcKey();
        List<String> lineParts = splitProperties(line);
        Map<String, String> properties = new HashMap<>();
        if (lineParts.size() > 0) {
            result.setSignature(Key.Signature.getByCode(lineParts.get(0).substring(2)));
        }
        if (lineParts.size() > 1) {
            String key = null;
            String value = null;
            for (String p : lineParts) {
                if (!p.startsWith("K:")) {
                    if (key == null) {
                        key = p;
                    } else if (value == null) {
                        value = p.replaceAll("\"", "");
                        properties.put(key, value);
                        key = null;
                        value = null;
                    }
                }
            }
        }
        Clef clef = createClef(properties);
        result.setClef(clef);
        return result;
    }

    private Voice handleVoiceProperties(String line) {
        Voice result = new AbcVoice();
        List<String> lineParts = splitProperties(line);
        Map<String, String> properties = new HashMap<>();
        if (lineParts.size() > 0) {
            result.setVoiceId(lineParts.get(0).substring(2));
        }
        if (lineParts.size() > 1) {
            String key = null;
            String value = null;
            for (String p : lineParts) {
                if (!p.startsWith("V:")) {
                    if (key == null) {
                        key = p;
                    } else if (value == null) {
                        value = p.replaceAll("\"", "");
                        properties.put(key, value);
                        key = null;
                        value = null;
                    }
                }
            }
        }
        Clef clef = createClef(properties);
        result.setClef(clef);
        if (properties.containsKey("name")) {
            result.setName(properties.get("name"));
        }
        if (properties.containsKey("subname")) {
            result.setShortName(properties.get("subname"));
        }
        return result;
    }

    private Clef createClef(Map<String, String> properties) {
        Clef clef = new AbcClef();
        if (properties.containsKey("clef")) {
            clef.setType(Clef.Type.getByCode(properties.get("clef")));
        }
        if (properties.containsKey("transpose")) {
            try {
                clef.setTranspose(Integer.valueOf(properties.get("transpose")));
            } catch (NumberFormatException e) { // Do nothing
            }
        }
        if (properties.containsKey("middle")) {
            clef.setMiddle(properties.get("middle"));
        }
        return clef;
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
                tune.setKey(handleKeyProperties(line));
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
        return line.length() > 2 && !line.substring(0, 2).matches(Field.getStartRegexp());
    }

    private boolean matchesField(Field field, String line) {
        return line.startsWith(field.name() + ":");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        String testar = "V:V1 name=\"1:a Fiol\" subname=\"F 1\" clef=treble";
//        InputStream stream = new FileInputStream("/home/hln/Skrivbord/Låtar/Kaisa.abc");
//        List<Tune> consume = new AbcConsumer(stream).consume();
//        consume.stream().forEach((t) -> {
//            System.out.println(new AbcProducer(t).produce());
//            System.out.println(t.getTitle());
//            System.out.println(t.getSubheader());
//            System.out.println(t.getComposer());
//            System.out.println(t.getOriginator());
//            System.out.println(t.getMeter());
//            System.out.println(t.getRythm());
//            System.out.println(t.getUnitNoteLength());
//            System.out.println(t.getKey().getVoiceId());
//            System.out.println(t.getVoices().get(0).getBody());
//        });
    }
}
