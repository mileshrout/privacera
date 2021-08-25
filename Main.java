package com.milesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream

        try {
            // Fetch the number of test case to be executed.
            int numberOfTest = sc.nextInt();

            // Validate constraint 1. 1<=T<=200
            if(numberOfTest > 200 || numberOfTest < 1) throw new InputException("Number of Test should be between 1 and 200");

            List<HashMap<Integer, Integer>> testCases = new ArrayList<>(numberOfTest);
            for (int i = 0; i < numberOfTest; i++) {
                // Fetch the number of cheese blocks.
                int numberOfBlocks = sc.nextInt();

                // Validate constraint. 1<=n<=1000
                if(numberOfBlocks > 1000 || numberOfBlocks < 1) {
                    throw new InputException("Number of cheese blocks should be between 1 and 1000");
                }
                sc.nextLine();

                // Fetch the cheese block string.
                String cheeseBlockString= sc.nextLine();

                // Validate and add it to the list of test cases
                testCases.add(validateString(cheeseBlockString));
            }
            for(HashMap<Integer, Integer> testCase : testCases){
                HashMap<Integer, Integer> sortedMap = sortCheeseBlocksByValue(testCase);
                System.out.println(findMaxAmount(sortedMap));
            }

        } catch (InputException e){
            System.err.println("Validation exception : "+e.getMessage());
        } catch (Exception e){
            System.err.println("Exception while executing program : "+e.getMessage());
        } finally {
            sc.close();
        }
        System.exit(0);
    }

    /**
     * Validate the input string
     * validate the constraint 1<=a[i]<=10000
     * @param input
     * @return the map of unsorted values
     * @throws InputException
     */
    private static HashMap<Integer, Integer> validateString(String input) throws InputException {
        String[] cheeseBlocks = input.split(" ");
        HashMap<Integer,Integer> temp = new HashMap<>();
        for (int i = 0; i < cheeseBlocks.length; i++) {
            int value = Integer.parseInt(cheeseBlocks[i]);
            if(value > 10000 || value < 1) throw new InputException("Weight of block should be within 1 and 10000");
            temp.put(i,value);
        }
        return temp;
    }

    /**
     * Driver method which combines the business logic.
     * @param testCase
     * @return the maximum amount of cheese
     */
    private static Integer findMaxAmount(HashMap<Integer, Integer> testCase){
        HashMap<Integer, Integer> sortedMap = sortCheeseBlocksByValue(testCase);
        Integer maxAmount = findMaxCheeseAmount(sortedMap);
        return maxAmount;
    }

    /**
     * Sort the Map as per the value of the blocks.
     * @param unsortedMap
     * @return the Map in sorted order
     */
    private static HashMap<Integer, Integer> sortCheeseBlocksByValue(HashMap<Integer,Integer> unsortedMap) {
        HashMap<Integer,Integer> sortedMap= unsortedMap.entrySet().stream().sorted((i1,i2) -> i2.getValue().compareTo(i1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
        return sortedMap;
    }

    /**
     * Calculate the maximum amount of cheese consumed by mouse.
     * @param sortedMap
     * @return the maximum amount
     */
    private static Integer findMaxCheeseAmount(HashMap<Integer, Integer> sortedMap){
        Integer maxAmount = 0;
        maxAmount = sortedMap.entrySet().stream().findFirst().get().getValue();
        Integer key = sortedMap.entrySet().stream().findFirst().get().getKey();
        List<Integer> indexToAvoid = new ArrayList<>();
        indexToAvoid.add(key+1);
        indexToAvoid.add(key-1);
        for (Map.Entry<Integer,Integer> entry : sortedMap.entrySet()) {
            Integer keyToVerify = entry.getKey();
            Integer valueToAdd = entry.getValue();
            if(key != keyToVerify && !indexToAvoid.contains(keyToVerify)) {
                key = keyToVerify;
                maxAmount = maxAmount + valueToAdd;
                indexToAvoid.add(keyToVerify - 1);
                indexToAvoid.add(keyToVerify + 1);
            }
        }
        return maxAmount;
    }

}
