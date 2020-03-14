package ru.ifmo.rain.kuznetsov.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Concurrent implements ListIP {

    private <T, R> R run(int countThreads, List<? extends T> list, Function<Stream<? extends T>, ? extends R> mapper, Function<Stream<? extends R>, ? extends R> reducer) throws InterruptedException {
        int realCountThreads = Math.min(countThreads, list.size());
        List<Thread> threads = new ArrayList<>();
        List<R> returned = new ArrayList<>(Collections.nCopies(realCountThreads, null));
        int blockSize = list.size() / realCountThreads;
        int surplus = list.size() - (blockSize * realCountThreads);
        List <Stream<? extends T>> partedStreeams = new ArrayList<>();
        int last = 0, right;
        for (int i = 0; i < realCountThreads; i++) {
            right = last + blockSize + (surplus > 0 ? 1 : 0);
            surplus--;
            partedStreeams.add(list.subList(last, right).stream());
            last = right;
        }
        for (int i = 0; i < realCountThreads; i++) {
            int finalI = i;
            threads.add(new Thread(() -> returned.set(finalI, mapper.apply(partedStreeams.get(finalI)))));
            threads.get(i).start();
        }
        InterruptedException exception = null;
        for (int i = 0; i < realCountThreads; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
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
}
