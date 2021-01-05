while (<>) {
    print if /\([^\(\)]*(\b[^\(\)]*\b)[^\(\)]*\)/;
    # some blank brackets  word  some blank brackets
}