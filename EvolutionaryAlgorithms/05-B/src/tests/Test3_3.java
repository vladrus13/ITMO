package tests;

import impl.evol.e.Bug;
import impl.evol.e.InteractorE;
import impl.evol.e.Point;
import ru.vladrus13.system.interactor.Interactor;

import java.util.ArrayList;
import java.util.Random;

public class Test3_3 extends Test {

    public void add(ArrayList<Interactor> interactors, Bug bug) {
        Random random = new Random(2952343);
        for (double chance = 0.0; chance <= 1.0; chance += 0.05) {
            InteractorE interactor = new InteractorE(3, 3,
                    new Point(random.nextInt(3), random.nextInt(3)),
                    new Point(random.nextInt(3), random.nextInt(3)),
                    random.nextInt(4), chance);
            interactor.setStartBug(bug.copy());
            interactors.add(interactor);
        }
    }

    @Override
    public double OK_score() {
        return 10.0;
    }

    @Override
    public double RE_score() {
        return 0.2;
    }

    @Override
    public double TL_score() {
        return 1;
    }

    @Override
    public double WA_score() {
        return 0;
    }
}
