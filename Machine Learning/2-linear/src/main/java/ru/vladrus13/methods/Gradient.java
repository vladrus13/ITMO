package ru.vladrus13.methods;

import ru.vladrus13.bean.Bean;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Gradient extends Method {

    final ArrayList<Double> w;
    final double k;

    public Double scalarProduce(ArrayList<Double> a, ArrayList<Double> b) {
        double result = 0.0;
        assert a.size() == b.size();
        for (int i = 0; i < a.size(); i++) {
            result += a.get(i) * b.get(i);
        }
        return result;
    }

    public ArrayList<Double> batchRunner(ArrayList<Bean> beans, int batchSize, ArrayList<Double> w, int start) {
        int m = beans.get(0).getAttributes().size();
        ArrayList<Double> differ = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            differ.add(beans.get(i + start).getY() - scalarProduce(w, beans.get(i + start).getAttributes()));
        }
        ArrayList<Double> grad = new ArrayList<>();
        IntStream.range(0, m).forEach(element -> grad.add(0.0));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < batchSize; j++) {
                grad.set(i, grad.get(i) - 2 * differ.get(j) * beans.get(j + start).getAttributes().get(i));
            }
            grad.set(i, grad.get(i) / batchSize);
        }
        ArrayList<Double> scalars = new ArrayList<>();
        IntStream.range(0, batchSize).forEach(element -> scalars.add(scalarProduce(grad, beans.get(element + start).getAttributes())));
        double bbb = 0.0;
        for (int i = 0; i < batchSize; i++) {
            bbb += scalars.get(i) * scalars.get(i);
        }
        double mmm = 0;
        if (bbb == 0) {
            mmm = 0;
        } else {
            for (int i = 0; i < batchSize; i++) {
                mmm += differ.get(i) * scalars.get(i);
            }
            mmm = mmm / bbb * -1;
        }
        for (int i = 0; i < m; i++) {
            grad.set(i, grad.get(i) * mmm / k);
        }
        return grad;
    }

    public Gradient(ArrayList<Bean> beans, double k) {
        super();
        this.k = k;
        long time = System.currentTimeMillis();
        int m = beans.get(0).getAttributes().size();
        int n = beans.size();
        int batchSize = (int) Math.log(beans.size());
        w = new ArrayList<>();
        IntStream.range(0, m).forEach(element -> w.add(0.0));
        boolean ban = false;
        while (!ban) {
            int position;
            for (position = 0; position < n - batchSize + 1; position += batchSize) {
                ArrayList<Double> differ = batchRunner(beans, batchSize, w, position);
                for (int i = 0; i < m; i++) {
                    w.set(i, w.get(i) - differ.get(i));
                }
                if (System.currentTimeMillis() - time >= 800) {
                    ban = true;
                    break;
                }
            }
            if (position != n - batchSize + 1) {
                position -= batchSize;
                int count = n - position;
                ArrayList<Double> differ = batchRunner(beans, count, w, position);
                for (int i = 0; i < m; i++) {
                    w.set(i, w.get(i) - differ.get(i));
                }
            }
        }
    }

    public ArrayList<Double> getAnswer() {
        return w;
    }

    @Override
    public String getName() {
        return "Gradient";
    }
}
