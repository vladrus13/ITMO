$isStarted = 0;
$isBeLineBreak = 0;

while (<>) {
    s/<[^>]*>//g;
    if (/^\s*$/) {
        # this is empty string
        if ($isStarted) {
            $isBeLineBreak = 1;
        }
    } else {
        $isStarted = 1;
        s/^\s+|\s+$//g;
        s/(\s){2,}/ /g;
        if ($isBeLineBreak) {
            $isBeLineBreak = 0;
            print "\n";
        }
        print;
        print "\n";
    }
}