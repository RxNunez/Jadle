package dao;

/**
 * Created by Guest on 8/23/17.
 */
import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
public class Sql2oReviewDao implements ReviewDao {

    private final Sql2o sql2o;

    public Sql2oReviewDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Review review) {
        String sql = "INSERT INTO reviews (writtenby, rating, restaurantid) VALUES (:writtenby, :rating, :restaurantid)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("writtenby", review.getWrittenBy())
                    .addParameter("rating", review.getRating())
                    .addParameter("restaurantid", review.getRestaurantId())
//                    .addParameter("createdat", "createdat")
//                    .addColumnMapping("CREATEDAT", "createdat")
                    .addColumnMapping("WRITTENBY", "writtenby")
                    .addColumnMapping("RATING", "rating")
                    .addColumnMapping("RESTAURANTID", "restaurantid")
                    .executeUpdate()
                    .getKey();
            review.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Review> getAllReviewsByRestaurant(int restaurantId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews WHERE restaurantId = :restaurantid")
                    .addColumnMapping("RESTAURANTID", "restaurantid")
                    .addParameter("restaurantId", restaurantId)
                    .executeAndFetch(Review.class);
        }
    }

}
