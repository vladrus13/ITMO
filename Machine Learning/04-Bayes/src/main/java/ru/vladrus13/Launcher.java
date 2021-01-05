package ru.vladrus13;

import ru.vladrus13.bean.Message;
import ru.vladrus13.bean.NaiveBayes;
import ru.vladrus13.bean.Part;
import ru.vladrus13.bean.Point;
import ru.vladrus13.drawing.ImageCreator;
import ru.vladrus13.drawing.ImageSaver;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Launcher {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Part> parts = new ArrayList<>();
        Path messages = Path.of("resources").resolve("messages");
        System.out.println("    --- Start reading");
        for (int i = 1; i < 11; i++) {
            parts.add(new Part(messages.resolve("part" + i)));
        }
        System.out.println("    --- Finish reading");
        for (int preAlpha = 2; preAlpha < 7; preAlpha++) {
            for (int gram = 1; gram < 4; gram++) {
                BinarySearchMachine binarySearchMachine = new BinarySearchMachine();
                int lSpam = 1;
                long perfectLegit;
                long lLegitPrevious = 0;
                long lLegitCurrent = binarySearchMachine.nextStep(lLegitPrevious);
                int finalPreAlpha = preAlpha;
                int finalGram = gram;
                System.out.println("    --- Binary upper");
                int countWrong = 1;
                while (countWrong > 0) {
                    lLegitPrevious = lLegitCurrent;
                    lLegitCurrent = binarySearchMachine.nextStep(lLegitCurrent);
                    long finalLLegitCurrent = lLegitCurrent;
                    countWrong = IntStream.range(0, 10)
                            .map(kicked -> getCountWrong(parts, finalPreAlpha, finalGram, kicked, lSpam, finalLLegitCurrent))
                            .sum();
                }
                System.out.println("    --- Binary search");
                while (lLegitCurrent - lLegitPrevious > 1) {
                    long m = (lLegitCurrent + lLegitPrevious) / 2;
                    if (IntStream.range(0, 10)
                            .map(kicked -> getCountWrong(parts, finalPreAlpha, finalGram, kicked, lSpam, m))
                            .sum() > 0) {
                        lLegitPrevious = m;
                    } else {
                        lLegitCurrent = m;
                    }
                }
                perfectLegit = lLegitCurrent;
                System.out.printf("Perfect legit for prealpha = %d, gram = %d: %d%n", preAlpha, gram, perfectLegit);
                ArrayList<Integer> counter = new ArrayList<>();
                long step = 1;
                if (perfectLegit - lSpam > 20) {
                    step = (perfectLegit - lSpam) / 20;
                }
                ExecutorService executorService = Executors.newFixedThreadPool(Thread.activeCount());
                int count = 0;
                AtomicInteger finishCount = new AtomicInteger();
                for (long lLegit = lSpam; lLegit <= perfectLegit; lLegit += step) {
                    counter.add(null);
                    long finalLLegit1 = lLegit;
                    int finalCount = count;
                    executorService.submit(() -> {
                        counter.set(finalCount, IntStream.range(0, 10)
                                .map(kicked -> getCountWrong(parts, finalPreAlpha, finalGram, kicked, lSpam, finalLLegit1))
                                .sum());
                        finishCount.getAndIncrement();
                    });
                    count++;
                }
                //noinspection StatementWithEmptyBody
                while (finishCount.get() != counter.size()) {
                    // empty
                }
                executorService.awaitTermination(1, TimeUnit.SECONDS);
                ArrayList<Point> points = new ArrayList<>();
                int size = counter.size();
                int perOne = 800 / size;
                int ideal = counter.get(0);
                for (int i = 0; i < counter.size(); i++) {
                    points.add(new Point(i * perOne, 800 * counter.get(i) / ideal));
                }
                String file = String.format("a=%d-n=%d", preAlpha, gram);
                ImageSaver.save(ImageCreator.draw("", file, points), file);
            }
        }
    }

    private static int getCountWrong(ArrayList<Part> parts, int preAlpha, int gram, int kicked, int lSpam, long lLegit) {
        int countWrong = 0;
        NaiveBayes naiveBayes = new NaiveBayes(2,
                2 * ((double) 1 / Math.pow(3, preAlpha)),
                new ArrayList<>(List.of((double) lSpam, (double) lLegit)));
        teach(naiveBayes, parts, gram, kicked);
        for (Message message : parts.get(kicked).getMessages()) {
            ArrayList<ArrayList<String>> messages = message.getMessages(gram);
            double v = 0;
            for (ArrayList<String> s : messages) {
                ArrayList<Double> predict = naiveBayes.predict(s);
                v += predict.get(1);
            }
            countWrong += (message.getC() == 1 && v / messages.size() <= 0.5 ? 1 : 0);
        }
        return countWrong;
    }

    private static void teach(NaiveBayes naiveBayes, ArrayList<Part> parts, int gram, int kicked) {
        for (int i = 0; i < 10; i++) {
            if (i != kicked) {
                parts.get(i).getMessages()
                        .forEach(element -> element.getMessages(gram).forEach(cool -> naiveBayes.teach(element.getC(), cool)));
            }
        }
        naiveBayes.readyToPredict();
    }

    protected static class BinarySearchMachine {
        long binaryStep;

        BinarySearchMachine() {
            binaryStep = 1;
        }

        public long nextStep(long x) {
            if (binaryStep > 1e15) {
                throw new ArithmeticException("Step is big");
            }
            long result = x + binaryStep;
            binaryStep *= 2;
            return result;
        }
    }
}
