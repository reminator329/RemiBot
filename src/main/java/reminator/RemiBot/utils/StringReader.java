package reminator.RemiBot.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringReader {
    private final Iterator<Character> string;
    private Character lastChar;

    public StringReader(String string) {
        this.string = Arrays.stream(string.chars().mapToObj(c -> (char)c).toArray(Character[]::new)).iterator();
    }

    public List<String> readStrings() {
        List<String> rep = new ArrayList<>();
        String str;
        while ((str = readNextString()) != null) {
            rep.add(str);
        }
        return rep;
    }

    private String readNextString() {
        if(!string.hasNext()) {
            return null;
        }
        Character start = string.next();
        if(start == ' ') {
            while (string.hasNext()) {
                start = string.next();
                if(start != ' ') {
                    break;
                }
            }
        }
        if(!string.hasNext()) {
            return null;
        }
        if(start == '"') {
            return readUntilQuote();
        }
        lastChar = start;
        return readUntilSpace();
    }

    private String readUntilQuote() {
        StringBuilder str = new StringBuilder();
        boolean isBackslash = false;
        while(string.hasNext()) {
            Character character = string.next();
            if(character == '\\') {
                isBackslash = true;
                continue;
            }
            if(character == '"') {
                if(isBackslash) {
                    continue;
                }else{
                    break;
                }
            }
            str.append(character);
            isBackslash = false;
        }
        return str.toString();
    }

    private String readUntilSpace() {
        StringBuilder str = new StringBuilder();
        str.append(lastChar);
        while(string.hasNext()) {
            Character character = string.next();
            if(character == ' ') {
                break;
            }
            str.append(character);
        }
        return str.toString();
    }
}
