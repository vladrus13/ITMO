while (<>) {
    print if /\b(.+)\1+\b/;
}