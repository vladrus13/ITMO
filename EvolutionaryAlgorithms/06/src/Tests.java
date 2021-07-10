import ru.vladrus13.system.interactor.Interactor;

import java.util.ArrayList;
import java.util.Random;

public class Tests extends Test {

    int id = 0;

    public void rec(ArrayList<Interactor> interactors, String a1, String a2) {
        if (a1.length() == 8) {
            if (a2.length() == 8) {
                interactors.add(new InteractorF(id++, a1, a2).setBox(new Launcher.OneMaxBox(8)));
            } else {
                rec(interactors, a1, a2 + "0");
                rec(interactors, a1, a2 + "1");
            }
        } else {
            rec(interactors, a1 + "0", a2);
            rec(interactors, a1 + "1", a2);
        }
    }

    public void rec_8(ArrayList<Interactor> interactors, String a1, String a2) {
        if (a1.length() < 7) {
            interactors.add(new InteractorF(id++, a1, a2).setBox(new Launcher.OneMaxBox(a1.length())));
            rec_8(interactors, a1 + "0", a2 + "0");
            rec_8(interactors, a1 + "0", a2 + "1");
            rec_8(interactors, a1 + "1", a2 + "0");
            rec_8(interactors, a1 + "1", a2 + "1");
        }
    }

    public void add8(ArrayList<Interactor> interactors) {
        rec(interactors, "", "");
    }

    public void addUnder8(ArrayList<Interactor> interactors) {
        rec_8(interactors, "", "");
    }

    public String randomString(Random random, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(random.nextBoolean() ? "1" : "0");
        }
        return sb.toString();
    }

    public void smolRandomTests(ArrayList<Interactor> interactors) {
        Random random = new Random(48540);
        for (int i = 0; i < 15000; i++) {
            int size = random.nextInt(10) + 8;
            interactors.add(new InteractorF(id++, randomString(random, size), randomString(random, size)).setBox(new Launcher.OneMaxBox(size)));
        }
    }

    public void randomTests(ArrayList<Interactor> interactors) {
        Random random = new Random(30394850);
        for (int i = 5; i <= 100000; i += 500) {
            interactors.add(new InteractorF(id++, randomString(random, i), randomString(random, i)).setBox(new Launcher.OneMaxBox(i)));
        }
    }

    @Override
    public void add(ArrayList<Interactor> interactors) {
        // interactors.add(new InteractorF(id++, "00101010100101111", "11010001001110011").setBox(new Launcher.OneMaxBox(17)));
        randomTests(interactors);
        /*
        00101010100101111
        11010001001110011
        interactors.add(new InteractorF(id++, "000001", "000000").setBox(new Launcher.OneMaxBox(6)));
        interactors.add(new InteractorF(id++, "000", "000").setBox(new Launcher.OneMaxBox(3)));
        interactors.add(new InteractorF(id++, "00000000", "00000000").setBox(new Launcher.OneMaxBox(8)));
        interactors.add(new InteractorF(id++, "00000001", "00000000").setBox(new Launcher.OneMaxBox(8)));
        interactors.add(new InteractorF(id++, "00000000", "11111111").setBox(new Launcher.OneMaxBox(8)));*/

    }
}
