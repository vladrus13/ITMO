import tests.Test;

import java.util.List;

public interface EvolutionAlgorithm {
    void run(List<Test> tests);

    ResultPart current();
}
