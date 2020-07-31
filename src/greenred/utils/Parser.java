package greenred.utils;

public class Parser {

    public Parser() {
    }

    public final void parseToInteger(String line, String regex, Wrapper<Integer>... args) {
        String[] split = line.split(regex);
        for (int i = 0; i < args.length; ++i) {
            args[i].setValue(Integer.valueOf(split[i]));
        }
    }
}
