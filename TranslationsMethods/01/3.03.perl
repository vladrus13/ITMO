my %set;
 
while ($input = <>) {
    if ($input =~ /<\s*a.+\bhref\s*=\s*['"](.*?)['"].*?>/sg) {
        # print $input;
        $href = $1;
        $href =~ /(?<pref>[^:\/\?#.\.]+:)?(\/\/)?(\w+(:\w+)?@)?(?<site>[^\/\?#:]+)(?<port>:\d+)?([\:\/\?#].*)?/;
        $pref = $+{pref};
        $site = $+{site};
        $port = $+{port};
        if (!($site =~ /^[\s\.]*$/)) {
            $set{$site} = 1;
        }
    }
}
for $link (sort keys %set) {
    print "$link\n" if length $link != 0
}