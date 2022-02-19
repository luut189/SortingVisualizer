import javax.sound.sampled.LineUnavailableException;

public class BubbleSort extends Sort {
    
    static int arrayIndex = 0;
    static int compareIndex = Integer.MAX_VALUE;
    static int pitch;

    public static int[] swapBubble(int[] arr) {
        pitch = arr.length % 100;
        if(arr[compareIndex] > arr[compareIndex+1]) {
            if(Panel.hasSound) {
                try {
                    Sound.tone(arr[compareIndex]*pitch, 1);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
            swap(arr, compareIndex, compareIndex+1);
        }

        if((compareIndex+1) >= (arr.length - arrayIndex - 1)) {
            arrayIndex++;
            compareIndex = 0;
        } else compareIndex++;

        return arr;
    }
}
