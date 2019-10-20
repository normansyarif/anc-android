package ac.id.unja.anc.Api;

import java.util.List;

import ac.id.unja.anc.Models.FAQ;
import ac.id.unja.anc.Models.Forum;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api/forums")
    Call<List<Forum>> getForumIndex(
            @Query("per_page") int per_page,
            @Query("page") int page
    );

    @GET("api/forums")
    Call<List<Forum>> searchForumIndex(
            @Query("search") String keyword,
            @Query("per_page") int per_page,
            @Query("page") int page
    );

    @GET("api/faqs")
    Call<List<FAQ>> getFAQIndex(
            @Query("per_page") int per_page,
            @Query("page") int page
    );

    @GET("api/faqs")
    Call<List<FAQ>> searchFAQIndex(
            @Query("search") String keyword,
            @Query("per_page") int per_page,
            @Query("page") int page
    );
}
