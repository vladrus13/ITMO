package tests;

import impl.evol.e.Bug;
import impl.evol.e.InteractorE;
import impl.evol.e.Point;
import ru.vladrus13.system.interactor.Interactor;

import java.util.ArrayList;
import java.util.Random;

public class Test5_5 extends Test {

    public void add(ArrayList<Interactor> interactors, Bug bug) {
        Random random = new Random(394564086);
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                for (double chance = 0.00; chance <= 1.0; chance += 0.1) {
                    InteractorE interactor = new InteractorE(i, j,
                            new Point(random.nextInt(i), random.nextInt(j)),
                            new Point(random.nextInt(i), random.nextInt(j)),
                            random.nextInt(4), chance);
                    interactor.setStartBug(bug.copy());
                    interactors.add(interactor);
                }
            }
        }
    }

    @Override
    public double OK_score() {
        return 10.0;
    }

    @Override
    public double RE_score() {
        return 0.1;
    }

    @Override
    public double TL_score() {
        return 0.2;
    }

    @Override
    public double WA_score() {
        return 0;
    }
}