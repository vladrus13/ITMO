def z3main():
    import z3
    a2 = []
    s = z3.Solver()
    for i in range(100):
        a2.append(z3.BitVec(str(i), 32))
        s.add(a2[i] >= 32)
        s.add(a2[i] <= 127)
    s.add(63 * ((65 * (a2[0] + 685)) ^ 0x1226A) == 7891254)
    s.add(13 * ((693 * (a2[1] + 703)) ^ 0x2CFC) == 7410715)
    s.add(43 * ((853 * (a2[2] + 382)) ^ 0x309A) == 17084158)
    s.add(45 * ((941 * (a2[3] + 168)) ^ 0x14DB9) == 7658910)
    s.add(75 * ((583 * (a2[4] + 212)) ^ 0x5B12) == 13246350)
    s.add(87 * ((159 * (a2[5] + 398)) ^ 0x1486B) == 2826369)
    s.add(19 * ((403 * (a2[6] + 372)) ^ 0x1138F) == 2610486)
    s.add(61 * ((21 * (a2[7] + 815)) ^ 0x23E1) == 1625467)
    s.add(61 * ((671 * (a2[8] + 51)) ^ 0x9EC0) == 4130737)
    s.add(29 * ((335 * (a2[9] + 396)) ^ 0x13DBD) == 7121240)
    s.add(81 * ((869 * (a2[10] + 622)) ^ 0xC1C0) == 50111703)
    s.add(51 * ((665 * (a2[11] + 180)) ^ 0xDFB4) == 12772899)
    s.add(65 * ((895 * (a2[12] + 189)) ^ 0xA573) == 13937495)
    s.add(81 * ((687 * (a2[13] + 286)) ^ 0xE15C) == 26218080)
    s.add(57 * ((325 * (a2[14] + 642)) ^ 0x131F2) == 11048538)
    s.add(61 * ((573 * (a2[15] + 100)) ^ 0xA839) == 4330634)
    s.add(21 * ((61 * (a2[16] + 609)) ^ 0x9976) == 279090)
    s.add(79 * ((593 * (a2[17] + 744)) ^ 0x6CC2) == 40172685)
    s.add(45 * ((943 * (a2[18] + 66)) ^ 0x125C8) == 10847160)
    s.add(23 * ((273 * (a2[19] + 640)) ^ 0x582E) == 4901599)
    s.add(79 * ((513 * (a2[20] + 815)) ^ 0x168FB) == 33479963)
    s.add(95 * ((407 * (a2[21] + 750)) ^ 0xCEF5) == 34311530)
    s.add(31 * ((795 * (a2[22] + 594)) ^ 0x10E41) == 13776617)
    s.add(49 * ((217 * (a2[23] + 938)) ^ 0x17FE0) == 6626270)
    s.add(55 * ((551 * (a2[24] + 711)) ^ 0x107DF) == 27949955)
    s.add(33 * ((587 * (a2[25] + 378)) ^ 0x14FA9) == 11109747)
    s.add(17 * ((495 * (a2[26] + 549)) ^ 0xD5EC) == 4714559)
    s.add(59 * ((701 * (a2[27] + 691)) ^ 0x12660) == 26284028)
    s.add(17 * ((727 * (a2[28] + 479)) ^ 0x5E14) == 7558506)
    s.add(57 * ((551 * (a2[29] + 259)) ^ 0x7176) == 9464280)
    s.add(51 * ((215 * (a2[30] + 298)) ^ 0x3162) == 4636053)
    s.add(23 * ((423 * (a2[31] + 922)) ^ 0xCCFF) == 9783004)
    s.add(47 * ((67 * (a2[32] + 388)) ^ 0x12E93) == 4054502)
    s.add(35 * ((407 * (a2[33] + 654)) ^ 0xFAEC) == 9747780)
    s.add(97 * ((253 * (a2[34] + 318)) ^ 0xE602) == 9338869)
    s.add(81 * ((773 * (a2[35] + 343)) ^ 0x152A3) == 23270571)
    s.add(87 * ((575 * (a2[36] + 65)) ^ 0x16CB3) == 5169888)
    s.add(99 * ((813 * (a2[37] + 488)) ^ 0x23D4) == 47217456)
    s.add(89 * ((683 * (a2[38] + 914)) ^ 0x245F) == 63727382)
    s.add(35 * ((429 * (a2[39] + 375)) ^ 0xB4C6) == 8380680)
    s.add(69 * ((883 * (a2[40] + 941)) ^ 0x98A2) == 66603837)
    s.add(49 * ((887 * (a2[41] + 726)) ^ 0xFD0F) == 33778101)
    s.add(37 * ((641 * (a2[42] + 782)) ^ 0x5D8B) == 21567411)
    s.add(15 * ((607 * (a2[43] + 293)) ^ 0xA27F) == 2981925)
    s.add(77 * ((295 * (a2[44] + 204)) ^ 0x7C6C) == 5773229)
    s.add(85 * ((631 * (a2[45] + 811)) ^ 0x10DD0) == 54480835)
    s.add(99 * ((21 * (a2[46] + 578)) ^ 0x13860) == 6491826)
    s.add(11 * ((597 * (a2[47] + 480)) ^ 0xA163) == 4237970)
    s.add(93 * ((97 * (a2[48] + 18)) ^ 0xC2DD) == 5690484)
    s.add(17 * ((793 * (a2[49] + 713)) ^ 0xCE46) == 10064782)
    s.add(91 * ((555 * (a2[50] + 528)) ^ 0x5242) == 30714138)
    s.add(25 * ((913 * (a2[51] + 291)) ^ 0x9C6B) == 8376400)
    s.add(101 * ((793 * (a2[52] + 694)) ^ 0xA1F2) == 63894216)
    s.add(93 * ((567 * (a2[53] + 422)) ^ 0xA426) == 24467184)
    s.add(63 * ((109 * (a2[54] + 78)) ^ 0xFA21) == 2895480)
    s.add(13 * ((793 * (a2[55] + 678)) ^ 0x5586) == 8353163)
    s.add(47 * ((423 * (a2[56] + 313)) ^ 0x11D5B) == 11885783)
    s.add(101 * ((539 * (a2[57] + 703)) ^ 0xF509) == 45360413)
    s.add(81 * ((131 * (a2[58] + 29)) ^ 0x12063) == 7512912)
    s.add(19 * ((335 * (a2[59] + 595)) ^ 0x1605D) == 3430469)
    s.add(69 * ((693 * (a2[60] + 34)) ^ 0x14C76) == 304911)
    s.add(57 * ((97 * (a2[61] + 711)) ^ 0x7CF6) == 4764744)
    s.add(27 * ((97 * (a2[62] + 437)) ^ 0x1204) == 1181412)
    s.add(83 * ((209 * (a2[63] + 105)) ^ 0x13DB) == 4042266)
    s.add(71 * ((59 * (a2[64] + 24)) ^ 0x148ED) == 6225351)
    s.add(71 * ((829 * (a2[65] + 983)) ^ 0x4D9C) == 64838478)
    s.add(91 * ((339 * (a2[66] + 375)) ^ 0x161BB) == 18406388)
    s.add(37 * ((823 * (a2[67] + 732)) ^ 0x1289) == 23879985)
    s.add(73 * ((159 * (a2[68] + 315)) ^ 0x733B) == 7050632)
    s.add(75 * ((775 * (a2[69] + 875)) ^ 0xC0B4) == 57603750)
    s.add(93 * ((515 * (a2[70] + 637)) ^ 0x1CB1) == 36340122)
    s.add(87 * ((831 * (a2[71] + 359)) ^ 0x17C3C) == 26432427)
    s.add(73 * ((21 * (a2[72] + 49)) ^ 0xECD) == 130305)
    s.add(59 * ((633 * (a2[73] + 772)) ^ 0x10A3) == 32132344)
    s.add(77 * ((679 * (a2[74] + 559)) ^ 0x82DE) == 32098451)
    s.add(99 * ((321 * (a2[75] + 488)) ^ 0x9017) == 15574878)
    s.add(85 * ((579 * (a2[76] + 560)) ^ 0x33AF) == 32200125)
    s.add(61 * ((855 * (a2[77] + 895)) ^ 0xEE31) == 55446804)
    s.add(65 * ((213 * (a2[78] + 597)) ^ 0x13994) == 12884560)
    s.add(61 * ((207 * (a2[79] + 735)) ^ 0x15A15) == 15746845)
    s.add(81 * ((959 * (a2[80] + 908)) ^ 0xD441) == 78895296)
    s.add(13 * ((473 * (a2[81] + 431)) ^ 0x15787) == 2224261)
    s.add(41 * ((897 * (a2[82] + 455)) ^ 0x3FEA) == 21468133)
    s.add(27 * ((93 * (a2[83] + 170)) ^ 0xE442) == 889812)
    s.add(17 * ((219 * (a2[84] + 240)) ^ 0x429C) == 811325)
    s.add(29 * ((375 * (a2[85] + 248)) ^ 0x93FB) == 4985709)
    s.add(87 * ((435 * (a2[86] + 631)) ^ 0x6592) == 26030661)
    s.add(81 * ((937 * (a2[87] + 128)) ^ 0x7C2E) == 20970495)
    s.add(95 * ((383 * (a2[88] + 770)) ^ 0x11B31) == 25418770)
    s.add(81 * ((201 * (a2[89] + 49)) ^ 0xD626) == 3376971)
    s.add(19 * ((315 * (a2[90] + 126)) ^ 0x1858E) == 747593)
    s.add(13 * ((611 * (a2[91] + 900)) ^ 0xE3EE) == 8268624)
    s.add(61 * ((795 * (a2[92] + 254)) ^ 0xE38) == 17485284)
    s.add(47 * ((265 * (a2[93] + 593)) ^ 0xEB7A) == 6857488)
    s.add(65 * ((997 * (a2[94] + 982)) ^ 0x1501B) == 72981350)
    s.add(65 * ((525 * (a2[95] + 220)) ^ 0xE726) == 9921275)
    s.add(49 * ((731 * (a2[96] + 281)) ^ 0x5490) == 12874407)
    s.add(71 * ((991 * (a2[97] + 615)) ^ 0x12288) == 47390299)
    s.add(67 * ((349 * (a2[98] + 999)) ^ 0xC9BE) == 23041434)
    s.add(41 * ((91 * (a2[99] + 695)) ^ 0x7FCD) == 3659865)
    s.check()
    model = s.model()
    result = []
    for m in model:
        result.append([int(str(m)), int(str(model[m]))])
    result.sort(key=lambda x: x[0])
    answer = ''.join([chr(r[1]) for r in result])
    return answer


if __name__ == '__main__':
    print(z3main())
