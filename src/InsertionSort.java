import javax.sound.sampled.LineUnavailableException;

public class InsertionSort extends Sort {
    
    static int compareIndex = Integer.MAX_VALUE, arrayIndex = Integer.MAX_VALUE;
    static int pitch;
    static int key;
    static boolean startOfIteration = false;

    public static int[] swapInsertion(int[] arr) {
        pitch = arr.length % 100;

        if (startOfIteration == false) {
            key = arr[arrayIndex];
            compareIndex = arrayIndex - 1;
            startOfIteration = true;
        }

        if (compareIndex >= 0 && arr[compareIndex] > key) {
            try {
                Sound.tone(arr[arrayIndex]*pitch, 10);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            arr[compareIndex + 1] = arr[compareIndex];
            compareIndex--;
            return arr;
        }
        else {
            arr[compareIndex + 1] = key;
            arrayIndex++;
        }

        startOfIteration = false;
        return arr;
    }

}
