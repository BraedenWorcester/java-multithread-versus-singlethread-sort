public class progJava {
    public static double[] arr;

    public static void main(String[] args) {

        arr = new double[Integer.parseInt(args[0])];
        for (int i = 0; i < arr.length; i++) { // populate array with semi-random numbers
            arr[i] = 1 + Math.random() * 1000;
        }

        System.out.println("");
        System.out.print("Threaded Sorting |");
        long start = System.nanoTime(); // start benchmark
        long lastTick = System.nanoTime();

        double[][] splitArrays = splitArray(arr);
        double[] oneHalf = splitArrays[0];
        double[] twoHalf = splitArrays[1];

        SortThread thread1 = new SortThread(oneHalf); // send first thread off to sort first half
        thread1.start();
        SortThread thread2 = new SortThread(twoHalf); // send second thread off to sort second half
        thread2.start();

        try {
            thread1.join();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            thread2.join();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        double[] mergedArray = new double[oneHalf.length + twoHalf.length];
        MergeThread thread3 = new MergeThread(mergedArray, oneHalf, twoHalf); // send third thread off to merge halves
        thread3.start();

        try {
            thread3.join();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        long end = System.nanoTime(); // end benchmark

        System.out.println("| " + (end - start) / 1000000 + " ms");

        System.out.println("");
        System.out.print("Standard Sorting |");
        start = System.nanoTime(); // start benchmark

        SortThread thread4 = new SortThread(arr); // send fourth thread off to sort conventionally
        thread4.start();

        try {
            thread4.join();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        end = System.nanoTime(); // end benchmark

        System.out.println("| " + (end - start) / 1000000 + " ms");

        System.out.println("");
    }

    // returns an array containing the split arrays
    public static double[][] splitArray(double[] arr) { 
        double[] firstHalf = new double[arr.length / 2];                            // new array half the size of original array
        System.arraycopy(arr, 0, firstHalf, 0, firstHalf.length);                   // copy first half of original into new
        double[] secondHalf = new double[arr.length - firstHalf.length];            // new array half the size of original array
        System.arraycopy(arr, firstHalf.length, secondHalf, 0, secondHalf.length);  // copy second half of original into new

        double[][] splitArrays = new double[2][]; // create 2d array to hold the two halves
        splitArrays[0] = firstHalf;
        splitArrays[1] = secondHalf;
        return splitArrays;
    }

    public static void sortArray(double[] arr, String threadName) {
        long lastTick = System.currentTimeMillis();
        int i;
        int offset; // offset is for keeping track of position in nested loop
        for (i = 1; i < arr.length; i++) {
            if (System.currentTimeMillis() > lastTick + 200 && (threadName.equals("Thread-0") || threadName.equals("Thread-3"))){
                System.out.print("-");
                lastTick = System.currentTimeMillis();
            }
            for (offset = 0; i - offset > 0; offset++) {
                if (arr[i - offset] < arr[i - offset - 1]) { // if the spot to the left of us has something bigger
                    double temp = arr[i - offset]; // remember our number
                    arr[i - offset] = arr[i - offset - 1]; // replace our spot with the bigger number
                    arr[i - offset - 1] = temp; // fill the bigger number's old spot with our number
                }
            }
        }
    }

    public static void mergeArray(double[] arr, double[] half1, double[] half2) {
        int offset1 = 0; // keeps track of position in half1
        int offset2 = 0; // keeps track of position in half 2
        for (int i = 0; i < arr.length; i++) {
            if (offset1 >= half1.length) {  // if we're at the end of half1
                arr[i] = half2[offset2];    // use a number from half2
                offset2++;
                continue;
            } else if (offset2 >= half2.length) {   // if we're at the end of half2
                arr[i] = half1[offset1];            // use a number from half1
                offset1++;
                continue;
            }

            if (half1[offset1] <= half2[offset2]) { // if the current number from half1 is smaller than the number from half2
                arr[i] = half1[offset1];            // use the half1 number
                offset1++;
            } else {                                // otherwise
                arr[i] = half2[offset2];            // use the half2 number
                offset2++;
            }
        }
    }
}

class SortThread extends Thread {
    private double[] arr;

    public SortThread(double[] array) {
        arr = array;
    }

    public void run() {
        progJava.sortArray(arr, currentThread().getName());
    }
}

class MergeThread extends Thread {
    private double[] arr, oneHalf, twoHalf;

    public MergeThread(double[] array, double[] firstHalf, double[] secondHalf) {
        arr = array;
        oneHalf = firstHalf;
        twoHalf = secondHalf;
    }

    public void run() {
        progJava.mergeArray(arr, oneHalf, twoHalf);
    }
}