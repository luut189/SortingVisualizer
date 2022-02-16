public abstract class Sort {
    public static int[] swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        return arr;
    }
}
