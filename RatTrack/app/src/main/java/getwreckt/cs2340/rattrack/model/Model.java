package getwreckt.cs2340.rattrack.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by aguy on 10/8/17.
 * Model Facade. Instantiated on startup. Keeps track of the current user. Holds the list of
 * all RatSightings.
 */

public class Model {
    private Model _model = new Model();
    public static ArrayList<RatSighting> ratSightings = new ArrayList<>();
    private static User currentUser;

    private static DatabaseReference mDataRef;
    private static FirebaseAuth mAuth;

    /**
     * Creates a new instance of model. Sets the Current instance of the Database and author.
     */
    private Model() {
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * returns the logged in user
     * @return Logged in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the Model's current user to passed in user
     * @param user logged in user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
