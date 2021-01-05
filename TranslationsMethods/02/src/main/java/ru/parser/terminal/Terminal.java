package ru.parser.terminal;

/**
 * Terminal class for {@link Token}
 */
public enum Terminal {
    /**
     * Reference (*)
     */
    REFERENCE {
        @Override
        public boolean isFirst(char c) {
            return c == '*';
        }
    },
    /**
     * Name of function, variable, etc.
     */
    NAME {
        @Override
        public boolean isFirst(char c) {
            return Character.isAlphabetic(c) || c == '_';
        }

        @Override
        public boolean isContinue(char c) {
            return Character.isAlphabetic(c) || c == '_' || Character.isDigit(c);
        }

        @Override
        public boolean isContainData() {
            return true;
        }
    },
    /**
     * Open bracket
     */
    OPEN_BRACKET {
        @Override
        public boolean isFirst(char c) {
            return c == '(';
        }
    },
    /**
     * Close bracket
     */
    CLOSE_BRACKET {
        @Override
        public boolean isFirst(char c) {
            return c == ')';
        }
    },
    /**
     * Semicolon
     */
    SEMICOLON {
        @Override
        public boolean isFirst(char c) {
            return c == ';';
        }
    },
    /**
     * End of line
     */
    END {
        @Override
        public boolean isFirst(char c) {
            return System.lineSeparator().chars().anyMatch(element -> element == c);
        }
    },
    /**
     * Comma
     */
    COMMA {
        @Override
        public boolean isFirst(char c) {
            return c == ',';
        }
    };

    /**
     * Is this char can be first of token
     * @param c char
     * @return true, if can, false else
     */
    public boolean isFirst(char c) {
        return false;
    }

    /**
     * Is this char can be into of token
     * @param c char
     * @return true, if can, false else
     */
    public boolean isContinue(char c) {
        return false;
    }

    /**
     * Is this type contain data
     * @return true, if can, else false
     */
    public boolean isContainData() {
        return false;
    }
}
