import impl.evol.e.Asking;
import impl.evol.e.Bug;
import impl.evol.e.Mono;
import impl.evol.e.Semantic;
import ru.vladrus13.system.log.LogSettings;
import tests.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Launcher {

    public static String toString(Mono mono) {
        int state = mono.state;
        switch (mono.type) {
            case PLUS -> {
                return "+";
            }
            case MINUS -> {
                return "-";
            }
            case NOTHING -> {
                return "N" + state;
            }
            case MOVE -> {
                return "F" + state;
            }
            case SAVE -> {
                return "S" + state;
            }
            case TURN_LEFT -> {
                return "[" + state;
            }
            case TURN_RIGHT -> {
                return "]" + state;
            }
            default -> throw new IllegalStateException("Wrong type of mono: " + mono.type);
        }
    }

    public static String toString(Asking asking) {
        Semantic ifFalse = asking.ifFalse;
        Semantic ifTrue = asking.ifTrue;
        int move = asking.move;
        LogSettings.isLog = false;
        switch (asking.type) {
            case IF_IT_SAVED -> {
                return "?S{" + toString(ifTrue) + "}{" + toString(ifFalse) + "}";
            }
            case IF_CAN_MOVE -> {
                return "M" + move + "{" + toString(ifTrue) + "}{" + toString(ifFalse) + "}";
            }
            case IF_AT_TARGET -> {
                return "?T{" + toString(ifTrue) + "}{" + toString(ifFalse) + "}";
            }
            case IF_BETTER_MOVE -> {
                return "C" + move + "{" + toString(ifTrue) + "}{" + toString(ifFalse) + "}";
            }
            case IF_BETTER_THAN_SAVED -> {
                return "?B{" + toString(ifTrue) + "}{" + toString(ifFalse) + "}";
            }
            default -> throw new IllegalStateException("Wrong type of asking: " + asking.type);
        }
    }

    public static String toString(Semantic semantic) {
        if (semantic instanceof Mono m) {
            return toString(m);
        }
        if (semantic instanceof Asking a) {
            return toString(a);
        }
        throw new IllegalStateException("Wrong type of semantic: " + semantic.getClass().getName());
    }

    public static String toString(Bug bug) {
        StringBuilder returned = new StringBuilder();
        for (Semantic semantic : bug.semantics) {
            returned.append(toString(semantic)).append("\n");
        }
        return returned.toString();
    }

    public static int length(Bug bug) {
        int max = 0;
        for (Semantic semantic : bug.semantics) {
            max = Math.max(toString(semantic).length(), max);
        }
        return max;
    }

    public static void main(String[] args) {
        ArrayList<Test> tests = new ArrayList<>(Arrays.asList(new Test1_1(), new Test2_2(), new Test3_3(),
                new Test3_3_1(), new Test5_5(), new Test10_10()));
        EvolutionAlgorithm evol = new Evol();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AtomicBoolean isComplete = new AtomicBoolean(false);
        executorService.submit(() -> {
            try {
                evol.run(tests);
                isComplete.set(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        while (!isComplete.get()) {
            try {
                ResultPart current = evol.current();
                if (current != null) {
                    System.out.println(current);
                } else {
                    System.out.println("null");
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
