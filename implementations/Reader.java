package implementations;

import enums.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reader {
    private final String inputPath;

    public Reader(final String inputPath) {
        this.inputPath = inputPath;
    }

    public String getInputPath() {
        return inputPath;
    }

    public Input readFile() {
        JSONParser jsonParser = new JSONParser();
        List<Child> children = new ArrayList<>();
        List<Gift> gifts = new ArrayList<>();
        List<AnnualChange> annualChanges = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(new FileReader(inputPath));
            int numberOfYears = ((Long) jsonObject.get("numberOfYears")).intValue();

            double santaBudget = ((Long) jsonObject.get("santaBudget")).doubleValue();
            JSONObject jsonInitialData = (JSONObject) jsonObject.get("initialData");
            JSONArray jsonChildren = (JSONArray)
                    jsonInitialData.get("children");
            JSONArray jsonGifts = (JSONArray)
                    jsonInitialData.get("santaGiftsList");
            JSONArray jsonAnnualChanges = (JSONArray)
                    jsonObject.get("annualChanges");

            for (Object jsonChild : jsonChildren) {
                children.add(new Child(
                        ((Long) ((JSONObject) jsonChild).get("id")).intValue(),
                        (String) ((JSONObject) jsonChild).get("lastName"),
                        (String) ((JSONObject) jsonChild).get("firstName"),
                        ((Long) ((JSONObject) jsonChild).get("age")).intValue(),
                        Utils.convertStringToCity((String) ((JSONObject) jsonChild).get("city")),
                        (double) ((Long) ((JSONObject) jsonChild).get("niceScore")).intValue(),
                        Utils.convertJsonArrayToListCategory((JSONArray)
                                ((JSONObject) jsonChild).get("giftsPreferences"))
                ));
            }
            for (Object jsonGift : jsonGifts) {
                gifts.add(new Gift(
                        (String) ((JSONObject) jsonGift).get("productName"),
                        ((Long) ((JSONObject) jsonGift).get("price")).doubleValue(),
                        Utils.convertStringToCategory((String)
                                ((JSONObject) jsonGift).get("category"))
                ));
            }
            for (Object jsonAnnualChange : jsonAnnualChanges) {
                List<Gift> newGifts = new ArrayList<>();
                List<Child> newChildren = new ArrayList<>();
                List<Child> childrenUpdates = new ArrayList<>();
                for (Object jsonGift : (JSONArray)
                        ((JSONObject) jsonAnnualChange).get("newGifts")) {
                    newGifts.add(new Gift(
                            (String) ((JSONObject) jsonGift).get("productName"),
                            ((Long) ((JSONObject) jsonGift).get("price")).doubleValue(),
                            Utils.convertStringToCategory((String)
                                    ((JSONObject) jsonGift).get("category"))
                    ));
                }
                for (Object jsonChild : (JSONArray) ((JSONObject)
                        jsonAnnualChange).get("newChildren")) {
                    newChildren.add(new Child(
                            ((Long) ((JSONObject) jsonChild).get("id")).intValue(),
                            (String) ((JSONObject) jsonChild).get("lastName"),
                            (String) ((JSONObject) jsonChild).get("firstName"),
                            ((Long) ((JSONObject) jsonChild).get("age")).intValue(),
                            Utils.convertStringToCity((String)
                                    ((JSONObject) jsonChild).get("city")),
                            ((Long) ((JSONObject) jsonChild).get("niceScore")).doubleValue(),
                            Utils.convertJsonArrayToListCategory((JSONArray)
                                    ((JSONObject) jsonChild).get("giftsPreferences"))
                    ));
                }
                List<Integer> ids = new ArrayList<>();

                for (Object jsonChild : (JSONArray)
                        ((JSONObject) jsonAnnualChange).get("childrenUpdates")) {
                    int id = ((Long) ((JSONObject) jsonChild).get("id")).intValue();
                    ids.add(id);
                    List<Category> giftsPreferences =
                            Utils.convertJsonArrayToListCategory((JSONArray)
                                    ((JSONObject) jsonChild).get("giftsPreferences"));

                    if (((JSONObject) jsonChild).get("niceScore") == null) {
                        childrenUpdates.add(new Child(id,
                                null, null, 0, null, null,
                                giftsPreferences
                        ));
                    } else {
                        childrenUpdates.add(new Child(id,
                                null, null, 0, null,
                                (double) ((Long) ((JSONObject) jsonChild).get("niceScore")).intValue(),
                                giftsPreferences
                        ));
                    }
                    if(children.stream().anyMatch((x) -> x.getId() == id)) {
                        if (ids.stream().filter((x) -> x == id).count() >= 2) {
                            children.stream().forEach((x) -> System.out.println(x.getId()));
                            Child child = children.stream().filter((x) -> x.getId() == id)
                                    .collect(Collectors.toList()).get(0);
                            List<List<Category>> preferences = child.getNextYears();
                            List<Category> lastPreferences = preferences.get(preferences.size() - 1);
                            giftsPreferences = Stream.concat(giftsPreferences.stream(), lastPreferences.stream())
                                    .distinct()
                                    .collect(Collectors.toList());
                            preferences.remove(preferences.size() - 1);
                        }
                        List<Category> finalGiftsPreferences = giftsPreferences;
                        children.stream().filter((x) -> x.getId() == id)
                                .findFirst().stream().forEach(x -> x.addNextYears(finalGiftsPreferences));
                    }
                }
                children.stream().filter((x) -> !ids.contains(x.getId())).forEach((x) -> x.addNextYears(new ArrayList<>()));

                annualChanges.add(new AnnualChange(
                        ((Long) ((JSONObject) jsonAnnualChange).get("newSantaBudget"))
                                .doubleValue(),
                        newGifts, newChildren, childrenUpdates
                ));
            }
            Input input = new Input(numberOfYears, santaBudget, children, gifts, annualChanges);
            for (Child child : children) {
                input.addObserver(child);
            }
            return input;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
