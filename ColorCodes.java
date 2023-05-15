public class ColorCodes {
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String RED_TEXT = "\u001B[31m	";
    public static final String GREEN_TEXT = "\u001B[32m";
    public static final String YELLOW_TEXT = "\u001B[33m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String PURPLE_TEXT = "\u001B[35m";
    public static final String CYAN_TEXT = "\u001B[36m";
    public static final String WHITE_TEXT = "\u001B[37m";

    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
}

/*
	Color Name		Color code		Background Color		Background Color code
	BLACK					\u001B[30m		BLACK_BACKGROUND		\u001B[40m
	RED						\u001B[31m		RED_BACKGROUND			\u001B[41m
	GREEN					\u001B[32m		GREEN_BACKGROUND		\u001B[42m
	YELLOW				\u001B[33m		YELLOW_BACKGROUND		\u001B[43m
	BLUE					\u001B[34m		BLUE_BACKGROUND			\u001B[44m
	PURPLE				\u001B[35m		PURPLE_BACKGROUND		\u001B[45m
	CYAN					\u001B[36m		CYAN_BACKGROUND			\u001B[46m
	WHITE					\u001B[37m		WHITE_BACKGROUND		\u001B[47m
	*/