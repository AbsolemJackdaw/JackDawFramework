package framework.resourceLoaders;

import framework.resourceLoaders.exceptions.ResLocExc;

import java.nio.file.Path;

public class ResourceLocation {

    private final String path;

    public ResourceLocation(String path) {
        this.path = path;
        if (!isValidString(path))
            throw new ResLocExc("Non [a-z0-9/_.-] character in " + path);
    }

    public static boolean validPathChar(char p_135829_) {
        return p_135829_ == '_' || p_135829_ == '-' || p_135829_ >= 'a' && p_135829_ <= 'z' || p_135829_ >= '0' && p_135829_ <= '9' || p_135829_ == '/' || p_135829_ == '.';
    }

    private boolean isValidString(String string) {
        for (char c : string.toCharArray())
            return validPathChar(c);
        return false;
    }

    public String raw() {
        return path;
    }

    public Path getPath() {
        return Path.of(path);
    }
}
