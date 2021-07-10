package tests;

import impl.evol.e.Bug;
import ru.vladrus13.system.interactor.Interactor;
import ru.vladrus13.system.result.Result;
import ru.vladrus13.system.result.Table;
import ru.vladrus13.system.test.InteractorLauncher;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;

public abstract class Test {
    public abstract double OK_score();

    abstract double RE_score();

    abstract double TL_score();

    abstract double WA_score();

    public abstract void add(ArrayList<Interactor> interactors, Bug bug);

    public double test(Bug bug) {
        ArrayList<Interactor> interactors = new ArrayList<>();
        add(interactors, bug);
        try {
            Table table = NullInteractorLauncher.generate(new NullProgramLauncher(), interactors, Executors.newSingleThreadExecutor(), null);
            double count = 0.0;
            for (Map<Integer, Result> results : table.results.values()) {
                for (Result result : results.values()) {
                    switch (result.resultType) {
                        case OK -> count += OK_score();
                        case RUNTIME_ERROR -> count += RE_score();
                        case TIME_LIMIT -> count += TL_score();
                        case WRONG_ANSWER -> count += WA_score();

                    }
                }
            }
            return count;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
