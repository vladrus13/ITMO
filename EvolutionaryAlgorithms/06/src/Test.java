import ru.vladrus13.system.interactor.Interactor;
import ru.vladrus13.system.result.Table;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public abstract class Test {
    public abstract void add(ArrayList<Interactor> interactors);

    public Table test() {
        ArrayList<Interactor> interactors = new ArrayList<>();
        add(interactors);
        try {
            return NullInteractorLauncher.generate(new NullProgramLauncher(), interactors, Executors.newFixedThreadPool(15), null);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
