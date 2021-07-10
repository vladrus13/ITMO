package tests;

import impl.evol.e.Bug;
import impl.evol.e.InteractorE;
import impl.evol.e.Point;
import ru.vladrus13.system.interactor.Interactor;

import java.util.ArrayList;
import java.util.Random;

public class Test1_1 extends Test {

    public void add(ArrayList<Interactor> interactors, Bug bug) {
        Random random = new Random(39754068);
        InteractorE interactor = new InteractorE(1, 1,
                new Point(random.nextInt(1), random.nextInt(1)),
                new Point(random.nextInt(1), random.nextInt(1)),
                random.nextInt(4), 0.0);
        interactor.setStartBug(bug.copy());
        interactors.add(interactor);
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
        return 0.1;
    }

    @Override
    public double WA_score() {
        return 0;
    }
}
