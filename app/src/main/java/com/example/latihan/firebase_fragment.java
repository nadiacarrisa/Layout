package com.example.latihan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class firebase_fragment extends Fragment {
    private String id;
    private EditText namamtk;
    private EditText sks;
    private EditText dosen;
    private Button buttonSimpan;
    private RecyclerView recyclerView;
    private Button buttonHapus;
    private FirebaseFirestore firebaseFirestoreDb;
    private CollectionReference fireRef;
    private FirestoreRecyclerAdapter<Matakuliah, DataViewHolder> adapter;
    private Query query;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firebase, container, false);
        namamtk = view.findViewById(R.id.namaMtk);
        recyclerView = view.findViewById(R.id.listfirestore);
        sks = view.findViewById(R.id.sks);
        dosen = view.findViewById(R.id.dosen);
        buttonSimpan = view.findViewById(R.id.simpanButton);
        buttonHapus = view.findViewById(R.id.hapusButton);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        fireRef = firebaseFirestoreDb.collection("DaftarMhs");
        query = fireRef.orderBy("namamtk", Query.Direction.ASCENDING);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!namamtk.getText().toString().isEmpty() && !sks.getText().toString().isEmpty()&& !dosen.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                } else {
                    Toast.makeText(requireActivity(), "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirestoreRecyclerOptions<Matakuliah> options = new FirestoreRecyclerOptions.Builder<Matakuliah>()
                .setQuery(query, Matakuliah.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Matakuliah, DataViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i, @NonNull Matakuliah data) {
                dataViewHolder.dsn.setText(data.getDosen());
                dataViewHolder.mtk.setText(data.getNamamtk());
                dataViewHolder.sks.setText(String.valueOf(data.getSks()));
            }

            @NonNull
            @Override
            public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_matakuliah, parent, false);
                return new DataViewHolder(view);
            }
            public void deleteItem(int position){
                getSnapshots().getSnapshot(position).getReference().delete();
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private class DataViewHolder extends RecyclerView.ViewHolder {
        TextView mtk;
        TextView dsn;
        TextView sks;
        public DataViewHolder(View itemView){
            super(itemView);
            mtk = itemView.findViewById(R.id.matakuliah);
            dsn = itemView.findViewById(R.id.dosen);
            sks = itemView.findViewById(R.id.sks);
        }
    }
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    public void getid(){
        fireRef.addSnapshotListener((Executor) this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    return;
                }
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Matakuliah matakuliah = documentSnapshot.toObject(Matakuliah.class);
                    id = documentSnapshot.getId();
                    Toast.makeText(requireActivity(),id, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void tambahMahasiswa() {
        Matakuliah mhs = new Matakuliah(namamtk.getText().toString(),
                Integer.parseInt(sks.getText().toString()),
                dosen.getText().toString());

        firebaseFirestoreDb.collection("DaftarMhs").document().set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
    private void getDataMahasiswa() {
        DocumentReference docRef = firebaseFirestoreDb.collection("DaftarMhs").document("mhs1");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Matakuliah mhs = document.toObject(Matakuliah.class);
                        namamtk.setText(mhs.getNamamtk());
                        sks.setText(mhs.getSks());
                        dosen.setText(mhs.getDosen());

                    } else {
                        Toast.makeText(requireActivity(), "Document tidak ditemukan",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Document error : " + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void deleteDataMahasiswa(String id) {
        firebaseFirestoreDb.collection("DaftarMhs").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        namamtk.setText("");
                        sks.setText("");
                        dosen.setText("");
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
//        buttonHapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteDataMahasiswa();
//            }
//        });
    }
}
