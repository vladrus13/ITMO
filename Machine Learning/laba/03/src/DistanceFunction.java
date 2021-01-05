import java.util.ArrayList;

public enum DistanceFunction {
    MANHATTAN {
        @Override
        public double get(ArrayList<Integer> a, ArrayList<Integer> b) {
            double distance = 0;
            for (int i = 0; i < a.size(); i++) {
                distance += Math.abs(a.get(i) - b.get(i));
            }
            return distance;
        }
    }, EUCLIDEAN {
        @Override
        public double get(ArrayList<Integer> a, ArrayList<Integer> b) {
            double distance = 0;
            for (int i = 0; i < a.size(); i++) {
                distance += Math.pow(a.get(i) - b.get(i), 2);
            }
            return Math.sqrt(distance);
        }
    }, CHEBYSHEV {
        @Override
        public double get(ArrayList<Integer> a, ArrayList<Integer> b) {
            double distance = 0;
            for (int i = 0; i < a.size(); i++) {
                distance = Math.max(distance, Math.abs(a.get(i) - b.get(i)));
            }
            return distance;
        }
    };

    public abstract double get(ArrayList<Integer> a, ArrayList<Integer> b);
}
