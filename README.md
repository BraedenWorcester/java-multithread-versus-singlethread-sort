# Summary
This Java source code measures the time it takes to bubble sort a randomly generated array with two threads, versus the time for a singular thread. 

- In the case of two-thread-sorting, the array is split; each half handed to a dedicated thread for sorting, before being recombined into a single now-sorted array.

- In the case of single-thread-sorting, the entire array is handed to a single thread for sorting. Good thing I'm here to break this stuff down.

# Usage
Arguments:

    int array_size // mandatory - determines the size of the randomly generated array, e.g 50 will result in an array w/ 50 elements
    
Sample in/output:

    $ java progJava.java 100000

    Threaded Sorting |--------| 1635 ms

    Standard Sorting |------------------------------| 6095 ms
    
    
    $ java progJava.java 500

    Threaded Sorting || 3 ms

    Standard Sorting || 2 ms
    
    
    $ java progJava.java 500000

    Threaded Sorting |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| 40700 ms

    Standard Sorting |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| 155127 ms
    
Written and tested in Java 11.0.15
