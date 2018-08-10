package com.vanancrm.Task;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class manipulate {
    public static void main(String args[]) {
        Integer[] array = new Integer[]{1, 2, 3, 4,5,6,7,8,9};

//int[] array = new int[]{1, 2, 3, 4}; //does not work

// Shuffle the elements in the array
        List<Integer> l = Arrays.asList(array);
        //System.out.println(l);



       for(int i=1; i<=28; i++) {

           Collections.shuffle(l);
           System.out.println(l);
       }
    }
}
