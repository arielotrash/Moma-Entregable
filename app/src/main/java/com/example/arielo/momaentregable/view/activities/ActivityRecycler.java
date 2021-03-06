package com.example.arielo.momaentregable.view.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.arielo.momaentregable.R;
import com.example.arielo.momaentregable.view.RecyclerView.FragmentRecyclerView;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.arielo.momaentregable.view.fragments.DetalleFragmentViewPager.POSICION;

public class ActivityRecycler extends AppCompatActivity  implements FragmentRecyclerView.NotificadorDeActivityRVA {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView imageViewPhotoUser;
    private TextView textViewNameUser;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new ListenerMenu());
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorDeFragmentosActivityRecycler,new FragmentRecyclerView()).commit();
        View viewHeader = navigationView.getHeaderView(0);
        textViewNameUser = viewHeader.findViewById(R.id.textViewNameUser_NavigationViewHeader);
        imageViewPhotoUser = viewHeader.findViewById(R.id.imageViewPhotoUser_NavigationViewHeader);
        if (firebaseUser != null) {
            textViewNameUser.setText(firebaseUser.getDisplayName());
            Glide.with(this).load(firebaseUser.getPhotoUrl() + "/picture?type=large").into(imageViewPhotoUser);
        }
    }

    private class ListenerMenu implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.itemInicio:
                    startActivity(new Intent(ActivityRecycler.this,ActivityMainLogIn.class));
                    break;
                case R.id.itemSignOut:
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(ActivityRecycler.this,ActivityMainLogIn.class));

                    break;
                case R.id.itemChatOnline:
                    if (firebaseUser.getDisplayName() != null){
                        startActivity(new Intent(ActivityRecycler.this,ActivityChat.class));
                    }else {
                        Toast.makeText(ActivityRecycler.this, "DEBE LOGEARSE CON FACEBOOK PARA USAR EL CHAT", Toast.LENGTH_SHORT).show();
                    }

            }
            drawerLayout.closeDrawers();
            return true;
        }
    }

    @Override
    public void notificadorRVA(int posicon) {

        Intent intent = new Intent(ActivityRecycler.this, ActivityDetail.class);
        Bundle bundle = new Bundle();
        bundle.putInt(POSICION,posicon);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
