package hex.music.io;

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
public class AbcDocumentParser {

    private final InputStream stream;
    private final String encoding;
    private final boolean isUpdate;

    public AbcDocumentParser() {
        this(null);
    }

    public AbcDocumentParser(InputStream stream) {
        this(stream, false);
    }

    public AbcDocumentParser(InputStream stream, String encoding) {
        this(stream, encoding, false);
    }

    public AbcDocumentParser(InputStream stream, boolean isUpdate) {
        this(stream, AbcConstants.ABC_ENCODING, isUpdate);
    }

    public AbcDocumentParser(InputStream stream, String encoding, boolean isUpdate) {
        this.stream = stream;
        this.encoding = encoding;
        this.isUpdate = isUpdate;
    }

    public List<Tune> parse() throws UnsupportedEncodingException, IOException {
        List<Tune> result = new ArrayList<>();
        if (stream != null && encoding != null) {
            InputStreamReader reader = new InputStreamReader(stream, AbcConstants.ABC_ENCODING);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String currentLine;
            Tune currentTune = null;
            Voice currentVoice = null;
            while ((currentLine = bufferedReader.readLine()) != null) {
                currentLine = new String(currentLine.getBytes(AbcConstants.ABC_ENCODING), encoding);
                if (currentLine.length() < 2 || currentLine.startsWith("%")) {
                    // Skip
                } else if (matchesField(Field.X, currentLine)) {
                    currentTune = new AbcTune();
                    currentVoice = null;
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
                        setValueFromLine(currentTune, currentLine);
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
            bufferedReader.close();
        }
        return result;
    }

    private Key handleKeyProperties(String line) {
        Map<String, String> properties = createPropertyMap(line.substring(2));
        Key.Signature keySignature = Key.Signature.getByCode(properties.get("fieldValue"));
        Clef clef = createClef(properties);
        Key result = new AbcKey(keySignature, clef);
        return result;
    }

    private Voice handleVoiceProperties(String line) {
        Map<String, String> properties = createPropertyMap(line.substring(2));
        Voice result = new AbcVoice();
        result.setVoiceId(properties.get("fieldValue"));
        result.setClef(createClef(properties));
        if (properties.containsKey("name")) {
            result.setName(properties.get("name"));
        }
        if (properties.containsKey("subname")) {
            result.setShortName(properties.get("subname"));
        }
        return result;
    }

    private Map<String, String> createPropertyMap(String line) {
        Map<String, String> properties = new HashMap<>();
        List<String> lineParts = splitProperties(line);
        if (lineParts.size() > 0) {
            String key = "fieldValue";
            for (String p : lineParts) {
                if (key == null) {
                    key = p;
                } else {
                    properties.put(key, p.replaceAll("\"", ""));
                    key = null;
                }
            }
        }
        return properties;
    }

    private List<String> splitProperties(String line) {
        List<String> result = new ArrayList<>();
        Matcher m = Pattern.compile("[^\\s\"=']+|\"[^\"]*\"|'[^']*'").matcher(line);
        while (m.find()) {
            result.add(m.group());
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

    private void setValueFromLine(Tune tune, String line) {
        String field = line.substring(0, 1);
        String value = getFieldValue(line);
        switch (field) {
            case "C":
                if (value.toLowerCase().startsWith("efter")) {
                    tune.setOriginator(value);
                } else {
                    tune.setComposer(value);
                }
                break;
            case "H":
                tune.setHistory(value);
                break;
            case "K":
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
}
