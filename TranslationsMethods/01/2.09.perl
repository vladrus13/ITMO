while (<>) {
    s/\([^)]*\)/()/g;
    print; 
}