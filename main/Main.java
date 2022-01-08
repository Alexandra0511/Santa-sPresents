package main;

import checker.Checker;
import common.Constants;
import implementations.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws IOException {
        for (int i = 1; i <= Constants.TESTS_NUMBER; i++) {
            String testInput = "tests/test" + i + ".json";
            Reader reader = new Reader(testInput);
            Input input = reader.readFile();

            String testOutput = "output/out_" + i + ".json";
            Writer fileWriter = new Writer(testOutput);
            fileWriter.setInput(input);
            JSONObject jsonObject = new JSONObject();
            JSONArray arrayResult = new JSONArray();
            arrayResult.add(fileWriter.writeFile());

            for (int j = 0; j < input.getNumberOfYears(); j++) {
                AnnualChange annualChange = input.getAnnualChanges().get(j);
                input.setSantaBudget(annualChange.getNewSantaBudget());
                for (Child child : annualChange.getNewChildren()) {
                    child.setAge(child.getAge() - 1);
                    input.addNewChildren(child);
                    for (int k = j; k < input.getNumberOfYears(); k++) {
                        List<Child> childWithId = input.getAnnualChanges()
                                .get(k).getChildrenUpdates();

                        if (childWithId.stream().anyMatch((x) -> child.getId() == x.getId())) {
                            child.addNextYears(childWithId.stream()
                                    .filter((x) -> child.getId() == x.getId())
                                    .collect(Collectors.toList())
                                    .get(0).getGiftsPreferences());
                        } else {
                            child.addNextYears(new ArrayList<>());
                        }
                    }
                    input.addObserver(child);
                }
                input.getChildren().removeIf((x) -> x.getAge() == Constants.TEEN_MAX_AGE + 1);
                for (Gift gift : annualChange.getNewGifts()) {
                    input.getGifts().add(gift);
                }
                for (Child childUpdate : annualChange.getChildrenUpdates()) {
                    input.getChildren().stream().filter(t -> t.getId() == childUpdate.getId())
                            .collect(Collectors.toList())
                            .forEach(c -> c.setNewNiceScore(childUpdate.getNiceScores().get(0)));
                }
                input.announce();
                arrayResult.add(fileWriter.writeFile());
            }
            jsonObject.put("annualChildren", arrayResult);
            fileWriter.closeJSON(jsonObject);
        }

        Checker.calculateScore();
    }
}
