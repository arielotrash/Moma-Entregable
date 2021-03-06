package com.example.arielo.momaentregable.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arielo.momaentregable.R;
import com.example.arielo.momaentregable.controller.ArtistController;
import com.example.arielo.momaentregable.controller.PaintController;
import com.example.arielo.momaentregable.helper.ResultListener;
import com.example.arielo.momaentregable.model.pojo.Artist;
import com.example.arielo.momaentregable.model.pojo.Paint;
import com.example.arielo.momaentregable.view.adapter.ViewPagerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFragmentViewPager extends Fragment {
    private ViewPager viewPager;
    private List<Paint> paintList;
    private List<Artist> artistList;
    private ViewPagerAdapter viewPagerAdapter;
    public static final String POSICION = "posicion";



    public DetalleFragmentViewPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_fragment_view_pager, container, false);
        viewPager = view.findViewById(R.id.contenedorDeFragmentosViewPager);
        pedirArtistas();

        return view;
    }

 /*   public void LeerLista(){
        DatabaseReference mDatabase;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase= firebaseDatabase.getReference();
        DatabaseReference referencelista = mDatabase.child("artist");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList = new ArrayList<>();
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                    Artist artistEnPosicion = dataSnapshotChild.getValue(Artist.class);
                    artistList.add(artistEnPosicion);
                }
                queseyo();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        referencelista.addListenerForSingleValueEvent(valueEventListener);
    }*/

    private void pedirArtistas(){
        ArtistController artistController = new ArtistController(getContext());
        artistController.obtenerArtists(new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                artistList = resultado;
                queseyo();
            }
        });

    }
    private void queseyo() {
        PaintController paintController = new PaintController(getContext());
        paintController.obtenerPaints(new ResultListener<List<Paint>>() {
            @Override
            public void finish(List<Paint> resultado) {
                paintList = resultado;
                viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), artistList, paintList);
                viewPager.setAdapter(viewPagerAdapter);
                Bundle bundle = getArguments();
                int posicion = bundle.getInt(POSICION);
                viewPager.setCurrentItem(posicion);
                viewPager.setOffscreenPageLimit(10);
            }
        });
    }
}
