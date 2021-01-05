while (<>) {
    s/(\w)\1/$1/g;
    print; 
}