import impl.evol.e.*;
import ru.vladrus13.system.interactor.Interactor;
import tests.Test;

import javax.xml.transform.Result;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Evol implements EvolutionAlgorithm {

    public static final int SIZE_RESULT = 100;
    public int tries = 0;
    public Random random = new Random(19348048);
    public double[] maxScore;
    public List<Test> tests;
    PriorityBlockingQueue<ResultPart> resultParts = new PriorityBlockingQueue<>(SIZE_RESULT * 2, resultPartComparator);

    public static Comparator<ResultPart> resultPartComparator = Comparator.reverseOrder();

    public ResultPart getResult(Bug bug, int genome) {
        ArrayList<Double> results = new ArrayList<>();
        for (int i = 0; i < tests.size(); i++) {
            double result = tests.get(i).test(bug);
            results.add(result);
            if (maxScore[i] - result > 0.0001) {
                break;
            }
        }
        double[] resultsReal = new double[results.size()];
        for (int i = 0; i < results.size(); i++) {
            resultsReal[i] = results.get(i);
        }
        return new ResultPart(resultsReal, bug, genome);
    }

    public int maxDepth(Mono mono) {
        return 1;
    }

    public int maxDepth(Asking asking) {
        return Math.max(maxDepth(asking.ifTrue), maxDepth(asking.ifFalse)) + 1;
    }

    public int maxDepth(Semantic semantic) {
        if (semantic instanceof Mono m) {
            return maxDepth(m);
        }
        if (semantic instanceof Asking a) {
            return maxDepth(a);
        }
        throw new IllegalStateException("Wrong type of semantic: " + semantic.getClass().getName());
    }

    public Asking getRandomAsking(Bug bug) {
        AskingType monoType = AskingType.values()[random.nextInt(AskingType.values().length)];
        int move = random.nextInt(4);
        return new Asking(monoType, getRandomMono(), getRandomMono(), move, bug);
    }

    public Mono getRandomMono() {
        MonoType monoType = MonoType.values()[random.nextInt(MonoType.values().length)];
        int move = random.nextInt(4);
        return new Mono(monoType, move);
    }

    public Semantic getRandomSemantic(Bug bug) {
        if (random.nextDouble() > 0.5) {
            return getRandomAsking(bug);
        } else {
            return getRandomMono();
        }
    }

    public void change(Asking from, int depth, Semantic replaced) {
        if (depth == 1) {
            if (random.nextDouble() > 0.5) {
                from.ifTrue = replaced;
            } else {
                from.ifFalse = replaced;
            }
        } else {
            boolean isTrue = random.nextDouble() > 0.5;
            Semantic child = isTrue ? from.ifTrue : from.ifFalse;
            if (child instanceof Mono) {
                if (isTrue) {
                    from.ifTrue = replaced;
                } else {
                    from.ifFalse = replaced;
                }
            }
            if (child instanceof Asking a) {
                change(a, depth - 1, replaced);
            }
        }
    }

    public Collection<Bug> getRandom(Bug bug) {
        ArrayList<Bug> bugs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Bug real;
            while (true) {
                Bug newBug = bug.copy();
                int maxDepth = maxDepth(bug.semantics[i]);
                Semantic randomSemantic = getRandomSemantic(newBug);
                if (newBug.semantics[i] instanceof Mono) {
                    newBug.semantics[i] = randomSemantic;
                } else {
                    if (newBug.semantics[i] instanceof Asking a) {
                        change(a, random.nextInt(maxDepth) + 1, randomSemantic);
                    }
                }
                if (Launcher.length(newBug) < 101) {
                    real = newBug;
                    break;
                }
            }
            bugs.add(real);
        }
        return bugs;
    }

    public Semantic get(Parser parser, Bug bug) {
        return parser.current() != '?' && parser.current() != 'M' && parser.current() != 'C' ? new Mono(parser) : new Asking(parser, bug);
    }

    public Bug getBug(String[] str, Bug random) {
        Semantic[] semantics = new Semantic[5];
        for (int i = 0; i < 5; i++) {
            semantics[i] = get(new Parser(str[i]), random);
        }
        Bug bug = new Bug(semantics);
        for (int i = 0; i < 5; i++) {
            semantics[i].setBug(bug);
        }
        return bug;
    }

    @Override
    public void run(List<Test> tests) {
        int countThreads = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);
        this.tests = tests;
        Bug startBug = new Bug(new Semantic[]{
                new Mono(MonoType.NOTHING, 0),
                new Mono(MonoType.NOTHING, 0),
                new Mono(MonoType.NOTHING, 0),
                new Mono(MonoType.NOTHING, 0),
                new Mono(MonoType.NOTHING, 0)
        });
        startBug = getBug(new String[]{
                "?T{+}{N1}",
                "M0{F0}{[1}",
                "N2",
                "N3",
                "N4"}, startBug);
        maxScore = new double[tests.size()];
        for (int i = 0; i < tests.size(); i++) {
            ArrayList<Interactor> temp = new ArrayList<>();
            tests.get(i).add(temp, startBug.copy());
            maxScore[i] = temp.size() * tests.get(i).OK_score();
        }
        resultParts.add(getResult(startBug, 0));
        boolean isFound = false;
        AtomicInteger count = new AtomicInteger(0);
        while (!isFound) {
            tries++;
            ResultPart resultPart = resultParts.peek();
            if (resultParts.size() > 50) {
                resultParts.poll();
            }
            if (resultPart.results().length == maxScore.length &&
                    maxScore[maxScore.length - 1] - resultPart.results()[maxScore.length - 1] < 0.1) {
                isFound = true;
            }
            for (int i = 0; i < 10; i++) {
                for (Bug bug : getRandom(resultPart.bug())) {
                    if (count.get() == countThreads) {
                        resultParts.add(getResult(bug, resultPart.genome() + 1));
                    } else {
                        executorService.submit(() -> {
                            count.incrementAndGet();
                            resultParts.add(getResult(bug, resultPart.genome() + 1));
                            count.decrementAndGet();
                        });
                    }
                }
            }
            if (resultParts.size() > 10 * SIZE_RESULT) {
                PriorityBlockingQueue<ResultPart> newQueue = new PriorityBlockingQueue<>(SIZE_RESULT * 2, resultPartComparator);
                for (int i = 0; i < SIZE_RESULT; i++) {
                    newQueue.add(resultParts.poll());
                }
                resultParts = newQueue;
            }
        }
    }

    @Override
    public ResultPart current() {
        return resultParts.peek();
    }
}
