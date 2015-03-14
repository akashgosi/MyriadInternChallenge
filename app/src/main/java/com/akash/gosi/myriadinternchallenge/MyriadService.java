package com.akash.gosi.myriadinternchallenge;
import java.util.List;
import retrofit.http.*;



public interface MyriadService {

    @FormUrlEncoded
    @POST("/api/v1/subscribe")
    User getSubscription(@Field("email") String email);

    //@GET("/api/v1/kingdoms")
    //List<Kingdoms> getKingdoms();

}
