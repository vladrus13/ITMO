package tests;

import impl.evol.e.Bug;
import impl.evol.e.InteractorE;
import impl.evol.e.Point;
import ru.vladrus13.system.interactor.Interactor;

import java.util.ArrayList;
import java.util.Random;

public class Test2_2 extends Test {

    public void add(ArrayList<Interactor> interactors, Bug bug) {
        Random random = new Random(49735945);
        for (int i = 2; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                for (int is = 0; is < i; is++) {
                    for (int js = 0; js < j; js++) {
                        InteractorE interactor = new InteractorE(i, j,
                                new Point(random.nextInt(i), random.nextInt(j)),
                                new Point(is, js),
                                random.nextInt(4), 0.0);
                        interactor.setStartBug(bug.copy());
                        interactors.add(interactor);
                    }
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
