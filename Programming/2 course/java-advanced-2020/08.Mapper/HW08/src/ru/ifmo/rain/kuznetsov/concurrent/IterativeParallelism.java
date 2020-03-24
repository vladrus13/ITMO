package ru.ifmo.rain.kuznetsov.concurrent;

import info.kgeorgiy.java.advanced.concurrent.AdvancedIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// bad English

public class IterativeParallelism implements AdvancedIP {

    private final ParallelMapper mapper;

    public IterativeParallelism() {this.mapper = null; }

    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Very abstract parallelism function on many args
     * @param countThreads - count threads
     * @param list         - list, whose we run
     * @param mapper       - function, whose apply to all elements
     * @param reducer      - function, whose join all elements
     * @param <T>          - type of elements
     * @param <R>          - type of returned
     * @return result of apply and join on list
     * @throws InterruptedException if have problem with threads
     */
    private <T, R> R run(int countThreads, List<T> list, Function<Stream<T>, R> mapper, Function<Stream<R>, R> reducer) throws InterruptedException {
        int realCountThreads = Math.min(countThreads, list.size());
        List<Thread> threads = new ArrayList<>();
        List<R> returned;
        int blockSize = list.size() / realCountThreads;
        int surplus = list.size() - (blockSize * realCountThreads);
        List <Stream<T>> partedStreams = new ArrayList<>();
        int last = 0, right;
        for (int i = 0; i < realCountThreads; i++) {
            right = last + blockSize + (surplus > 0 ? 1 : 0);
            surplus--;
            partedStreams.add(list.subList(last, right).stream());
            last = right;
        }
        if (this.mapper != null) {
            returned = this.mapper.map(mapper, partedStreams);
        } else {
            returned = new ArrayList<>(Collections.nCopies(realCountThreads, null));
            for (int i = 0; i < realCountThreads; i++) {
                int finalI = i;
                threads.add(new Thread(() -> returned.set(finalI, mapper.apply(partedStreams.get(finalI)))));
                threads.get(i).start();
            }
            InterruptedException exception = null;
            for (int i = 0; i < realCountThreads; i++) {
                try {
                    threads.get(i).join();
                } catch (InterruptedException e) {
                    if (exception == null) {
                        exception = e;
                    } else {
                        exception.addSuppressed(e);
                    }
                }
            }
        }
        return reducer.apply(returned.stream());
    }

    @Override
    public String join(int i, List<?> list) throws InterruptedException {
        return run(i, list, stream -> stream.map(Object::toString).collect(Collectors.joining()), stream -> stream.collect(Collectors.joining()));
    }

    @Override
    public <T> List<T> filter(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return run(i, list, stream -> stream.filter(predicate).collect(Collectors.toList()), stream -> stream.flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Override
    public <T, U> List<U> map(int i, List<? extends T> list, Function<? super T, ? extends U> function) throws InterruptedException {
        return run(i, list, stream -> stream.map(function).collect(Collectors.toList()), stream -> stream.flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Override
    public <T> T minimum(int i, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return run(i, list, stream -> stream.min(comparator).get(), stream -> stream.min(comparator).get());
    }

    @Override
    public <T> T maximum(int i, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return minimum(i, list, Collections.reverseOrder(comparator));
    }

    @Override
    public <T> boolean all(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return run(i, list, stream -> stream.allMatch(predicate), stream -> stream.allMatch(Boolean::booleanValue));
    }

    @Override
    public <T> boolean any(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return !all(i, list, x -> !predicate.test(x));
    }
    
    private <T> T applyReduce(final Stream<T> stream, final Monoid<T> monoid) {
        return stream.reduce(monoid.getIdentity(), monoid.getOperator());
    }

    @Override
    public <T> T reduce(int i, List<T> list, Monoid<T> monoid) throws InterruptedException {
        return run(i, list, s -> applyReduce(s, monoid), s -> applyReduce(s, monoid));
    }

    private <T, R> R applyMapReduce(final Stream<T> stream, final Monoid<R> monoid, final Function<T, R> function) {
        return applyReduce(stream.map(function), monoid);
    }

    @Override
    public <T, R> R mapReduce(int i, List<T> list, Function<T, R> function, Monoid<R> monoid) throws InterruptedException {
        return run(i, list, s -> applyMapReduce(s, monoid, function), s -> applyReduce(s, monoid));
    }
}
