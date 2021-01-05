package ru.vladrus13.methods;

import ru.vladrus13.bean.Bean;
import ru.vladrus13.utils.NRMSE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generic extends Method {

    final ArrayList<Double> answer = new ArrayList<>();

    void naive(ArrayList<Bean> beans) {
        IntStream.range(0, beans.get(0).getAttributes().size()).forEach(element -> answer.add(0.0));
        for (Bean bean : beans) {
            for (int i = 0; i < bean.getAttributes().size(); i++) {
                answer.set(i, answer.get(i) + bean.getAttributes().get(i));
            }
        }
        for (int i = 0; i < answer.size(); i++) {
            answer.set(i, answer.get(i) / beans.size());
        }
    }

    void heat(ArrayList<Bean> beans) {
        IntStream.range(0, beans.get(0).getAttributes().size()).forEach(element -> answer.add(0.0));
        ArrayList<Double> gensStarters = new ArrayList<>(List.of(0.0, 1.0, 10.0, 500.0, 1000.0, 50000.0, 1000000000000.0));
        for (int i = 0; i < beans.get(0).getAttributes().size(); i++) {
            ArrayList<Double> oneAttrBeans = new ArrayList<>();
            for (Bean bean : beans) {
                oneAttrBeans.add(bean.getAttributes().get(i));
            }
            ArrayList<Double> gens = new ArrayList<>();
            for (Double starter : gensStarters) {
                IntStream.range(0, beans.get(0).getAttributes().size()).forEach(element -> gens.add(starter));
            }
            for (int generation = 0; generation < 5; generation++) {
                ArrayList<Double> parentsChance = new ArrayList<>();
                for (Double gen : gens) {
                    parentsChance.add(NRMSE.calculateOne(oneAttrBeans, gen));
                }
                double sum = 0.0;
                for (double chance : parentsChance) {
                    sum += chance;
                }
                double newGeneration = 0.0;
                for (double chance : parentsChance) {
                    newGeneration += chance / sum;
                }
                gens.add(newGeneration);
                answer.set(i, newGeneration);
            }
        }
    }

    public Generic(ArrayList<Bean> beans) {
        super();
        heat(beans);
    }

    public ArrayList<Double> getAnswer() {
        return answer;
    }

    @Override
    public String getName() {
        return "Generic";
    }
}
