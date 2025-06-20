import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecommender {
    public static void main(String[] args) throws Exception {
        // Create a map of user preferences
        FastByIDMap<GenericUserPreferenceArray> preferences = new FastByIDMap<>();

        // User 1 preferences
        List<GenericPreference> user1Prefs = new ArrayList<>();
        user1Prefs.add(new GenericPreference(1L, 101L, 4.0f));
        user1Prefs.add(new GenericPreference(1L, 102L, 3.0f));
        preferences.put(1L, new GenericUserPreferenceArray(user1Prefs));

        // User 2 preferences
        List<GenericPreference> user2Prefs = new ArrayList<>();
        user2Prefs.add(new GenericPreference(2L, 101L, 2.0f));
        user2Prefs.add(new GenericPreference(2L, 103L, 5.0f));
        preferences.put(2L, new GenericUserPreferenceArray(user2Prefs));

        // Create a DataModel from preferences
        DataModel model = new GenericDataModel(preferences);

        // Similarity algorithm
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // Nearest neighbor based on similarity
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(1, similarity, model);

        // Build the recommender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Generate recommendations for user 1
        List<RecommendedItem> recommendations = recommender.recommend(1L, 2);

        // Print recommendations
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Recommended Item: " + recommendation.getItemID() + " , Value: " + recommendation.getValue());
        }
    }
}
