while (<>) {
    s/(a(.)*?a)(a(.)*?a)(a(.)*?a)/bad/g;
    print; 
}