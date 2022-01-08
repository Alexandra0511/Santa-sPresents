package implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import enums.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Writer {

    private final FileWriter file;
    private Input input;

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Writer(final String path) throws IOException {
        this.file = new FileWriter(path);
    }

    public JSONObject writeFile() throws IOException {
        JSONArray children = new JSONArray();
        Map<Child, List<Gift>> receivedGiftsMap = input.receivedGifts();
        Map<Child, Double> budgetMap = input.budget();
        JSONObject childrenOutput = new JSONObject();
        for (Child child : input.getChildren()) {
            JSONArray receivedGifts = new JSONArray();
            if (child.getAge() <= 18) {
                Map childOutput = new LinkedHashMap();
                childOutput.put("id", child.getId());
                childOutput.put("lastName", child.getLastName());
                childOutput.put("firstName", child.getFirstName());
                childOutput.put("city", Utils.convertCityToString(child.getCity()));
                childOutput.put("age", child.getAge());
                List<String> giftPreferences = new LinkedList<>();
                for (Category categ : child.getGiftsPreferences()) {
                    giftPreferences.add(Utils.convertCategoryToString(categ));
                }
                childOutput.put("giftsPreferences", giftPreferences);
                childOutput.put("averageScore", child.averageScore());
                childOutput.put("niceScoreHistory", List.copyOf(child.getNiceScores()));
                childOutput.put("assignedBudget", budgetMap.get(child));

                for (Gift gift : receivedGiftsMap.get(child)) {
                    Map receivedGift = new LinkedHashMap();
                    receivedGift.put("productName", gift.getProductName());
                    receivedGift.put("price", gift.getPrice());
                    receivedGift.put("category", Utils.convertCategoryToString(gift.getCategory()));
                    receivedGifts.add(receivedGift);
                }
                childOutput.put("receivedGifts", receivedGifts);
                children.add(childOutput);
            }
        }
        childrenOutput.put("children", children);
        return childrenOutput;
    }

    public void closeJSON(final JSONObject array) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writeValueAsString(array);

            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
